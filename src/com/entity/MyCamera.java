package com.entity;

import java.io.IOException;
import java.util.List;

import com.activity.MainActivity;
import com.intf.OnCamOpenFinished;
import com.intf.OnCamTakePicFinished;
import com.listener.MyPictureCallback;
import com.listener.MyShutterCallback;

import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.view.SurfaceHolder;

/**
 * Entity class for camera & parameters
 * Singleton pattern
 * @author Luck
 *
 */
public class MyCamera implements OnCamTakePicFinished{
	//camera hardware instance
	private Camera camera;
	@Deprecated
	//bitmap cache
	private Bitmap bitmap;
	//camera parameters
	private Parameters cParam;
	private int preWidth;
	private int preHeight;
	private int picWidth;
	private int picHeight;
	private int picFormat;
	private String focusMode;
	private int dispOrient;
	private boolean previewing;
	private int currentCam;
	//player name
	private String pName;
	//event callback instances
	private MyPictureCallback jpegCallback;
	private MyShutterCallback shutterCallback;
	/**
	 * Singleton holder
	 * @author Luck
	 *
	 */
	private static class MyCameraHolder{
		public static MyCamera INSTANCE = new MyCamera();
	}
	/**
	 * Singleton internal constructor
	 */
	private MyCamera(){
		preWidth = 500;
		preHeight = 500;
		picWidth = 640;
		picHeight = 480;
		picFormat = ImageFormat.JPEG;
		focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO;
		dispOrient = 90;
		previewing = false;
		currentCam = 0;
		bitmap = null;
		jpegCallback = new MyPictureCallback(this);
		shutterCallback = new MyShutterCallback();
	}
	/**
	 * Singleton getter
	 * @return
	 */
	public static MyCamera getInstance(){
		return MyCameraHolder.INSTANCE;
	}
	/**
	 * camera open implementation
	 */
	public void doOpen(OnCamOpenFinished callback){
		try{
			camera = Camera.open(currentCam);
			callback.camOpened();
		} catch(RuntimeException e){
			e.printStackTrace();
			System.out.println("RT Exception");
		}
	}
	/**
	 * camera preview implementation
	 * @param holder
	 */
	public void doPreview(SurfaceHolder holder){
		if(previewing){
			doStop();
			return;
		}
		if(camera == null)
			return;
		cParam = camera.getParameters();
		//supported lists
		List<Size> preSizes = cParam.getSupportedPreviewSizes();
		List<Size> picSizes = cParam.getSupportedPreviewSizes();
		List<Integer> picFormats = cParam.getSupportedPictureFormats();
		List<String> focusModes = cParam.getSupportedFocusModes();
		//set to default preview size
		cParam.setPreviewSize(preSizes.get(0).width, preSizes.get(0).height);
		for (Size size : preSizes) {
			//if it's supported preview size
			if(size.width == picWidth && size.height == picHeight){
				cParam.setPreviewSize(picWidth, picHeight);				
			}
		}
		//set to default picture size
		cParam.setPictureSize(picSizes.get(0).width, picSizes.get(0).height);
		for (Size size : picSizes) {
			//if it's supported picture size
			if(size.width == picWidth && size.height == picHeight){
				cParam.setPictureSize(picWidth, picHeight);	
			}
		}
		//set to default picture format
		cParam.setPictureFormat(picFormats.get(0));
		for (Integer format: picFormats) {
			//if it's supported picture format
			if(format.equals(picFormat)){
				cParam.setPictureFormat(picFormat);
			}
		}
		//set to default focus mode
		cParam.setFocusMode(focusModes.get(0));
		for (String mode : focusModes) {
			//if it's supported focus mode
			if(mode.equals(focusMode)){
				cParam.setFocusMode(focusMode);
			}
		}
		//correcting default rotation
		camera.setDisplayOrientation(dispOrient);
		//front camera needs to rotate more
		if(currentCam == 1){
			cParam.set("rotation", 180);
		}
		camera.setParameters(cParam);
		try {
			camera.setPreviewDisplay(holder);
			camera.startPreview();
		} catch (IOException e) {
			e.printStackTrace();
		}
		previewing = true;

	}
	/**
	 * camera stop implementation
	 */
	public void doStop(){
		if(camera != null){
			camera.setPreviewCallback(null);
			camera.stopPreview();
			camera.release();
			camera = null;
			previewing = false;
		}
	}
	/**
	 * camera take picture implementation
	 */
	public void doTakePic(){
		camera.takePicture(shutterCallback, null, null, jpegCallback);
		previewing = false;
	}
	
	@Override
	/**
	 * Implementation of OnCamTakePicFinished intf,
	 * Invoked when a picture is PROCESSED
	 * CACHE the picture in this function
	 */
	public void camPicTaken(){
		Bitmap bitmap = jpegCallback.getBitmap();
		previewing = true;
		camera.startPreview();
		Matrix matrix = new Matrix();
		matrix.reset();
		if(currentCam == 0){
			//rotate the picture clockwise 90 degree
			matrix.setRotate(90);
		} else {
			//rotate pre 90 degree
			matrix.preRotate(90);
			//flip the mirror image
		    float[] mirrorY = { -1, 0, 0, 0, 1, 0, 0, 0, 1};
		    matrix = new Matrix();
		    Matrix matrixMirrorY = new Matrix();
		    matrixMirrorY.setValues(mirrorY);
		    matrix.postConcat(matrixMirrorY);
		    //rotate pre 90 degree
		    matrix.preRotate(90);
		}
		Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		Bitmap zoomedBitmap = zoomBitmap(newBitmap, 100, 100);
		Config.playerList.add(new Player(zoomedBitmap));
		MainActivity.refreshPlayerNum();
		//Config.getInstance().setTotalPlayers(Config.getInstance().getTotalPlayers()+1);
	}
	/**
	 * camera switch implementation
	 * @param callback
	 */
	public void doSwitch(OnCamOpenFinished callback){
		doStop();
		switch (currentCam) {
		case 0:
			currentCam = 1;
			break;
		case 1:
			currentCam = 0;
			break;
		}
		doOpen(callback);
	}
	/**
	 * Zoom the bitmap to a required pair of w/h
	 * @param bitmap
	 * @param w
	 * @param h
	 * @return
	 */
	private Bitmap zoomBitmap(Bitmap bitmap, int w, int h){  
        int width = bitmap.getWidth();  
        int height = bitmap.getHeight();  
        Matrix matrix = new Matrix();  
        float scaleWidth = ((float) w / width);  
        float scaleHeight = ((float) h / height);  
        matrix.postScale(scaleWidth, scaleHeight);  
        Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,  
                matrix, true);  
        return newBmp;  
    }
	public Camera getCamera() {
		return camera;
	}
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	public Parameters getcParam() {
		return cParam;
	}
	public void setcParam(Parameters cParam) {
		this.cParam = cParam;
	}
	@Deprecated
	public Bitmap getBitmap() {
		return bitmap;
	}
	@Deprecated
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public int getPreWidth() {
		return preWidth;
	}
	public void setPreWidth(int preWidth) {
		this.preWidth = preWidth;
	}
	public int getPreHeight() {
		return preHeight;
	}
	public void setPreHeight(int preHeight) {
		this.preHeight = preHeight;
	}
	public int getPicWidth() {
		return picWidth;
	}
	public void setPicWidth(int picWidth) {
		this.picWidth = picWidth;
	}
	public int getPicHeight() {
		return picHeight;
	}
	public void setPicHeight(int picHeight) {
		this.picHeight = picHeight;
	}
	public int getPicFormat() {
		return picFormat;
	}
	public void setPicFormat(int picFormat) {
		this.picFormat = picFormat;
	}
	public String getFocusMode() {
		return focusMode;
	}
	public void setFocusMode(String focusMode) {
		this.focusMode = focusMode;
	}
	public int getDispOrient() {
		return dispOrient;
	}
	public void setDispOrient(int dispOrient) {
		this.dispOrient = dispOrient;
	}
	public boolean isPreviewing() {
		return previewing;
	}
	public void setPreviewing(boolean previewing) {
		this.previewing = previewing;
	}
	public MyPictureCallback getJpegCallback() {
		return jpegCallback;
	}
	public void setJpegCallback(MyPictureCallback jpegCallback) {
		this.jpegCallback = jpegCallback;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	
}
