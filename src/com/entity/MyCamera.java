package com.entity;

import java.io.IOException;

import com.intf.OnCamOpenFinished;
import com.intf.OnCamTakePicFinished;
import com.listener.MyPictureCallback;
import com.listener.MyShutterCallback;

import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
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
			camera = Camera.open();
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
		//TO-DO: more camera parameter settings, and RIGHT SETTINGS!
		//e.g. setting picture size based on cParam.getSupportedSizes();
		cParam.setPreviewSize(preWidth, preHeight);
		cParam.setPictureFormat(picFormat);
		cParam.setPictureSize(picWidth, picHeight);
		cParam.setFocusMode(focusMode);
		camera.setDisplayOrientation(dispOrient);
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
		setBitmap(jpegCallback.getBitmap());
		previewing = true;
		camera.startPreview();
		//rotate the picture clockwise 90 degree
		Matrix matrix = new Matrix();
		matrix.reset();
		matrix.setRotate(90);
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		bitmap = zoomBitmap(bitmap, 100, 100);
		Config.imgList.add(bitmap);
		Config.getInstance().setTotalPlayers(Config.getInstance().getTotalPlayers()+1);
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
	public Bitmap getBitmap() {
		return bitmap;
	}
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
