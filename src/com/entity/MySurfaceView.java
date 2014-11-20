package com.entity;

import com.intf.OnCamOpenFinished;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;
import android.widget.EditText;

/**
 * Entity class for surface view
 * Holding reference to singleton camera
 * @author Luck
 *
 */
public class MySurfaceView extends SurfaceView implements Callback, OnCamOpenFinished{

	//reference to context
	private Context cont;
	//reference to camera instance
	private MyCamera camera;
	//holder instance to control the surface view
	private SurfaceHolder viewHolder;
	//EditText to get player names
	private EditText nameTxt;
	public MySurfaceView(Context context, AttributeSet attr) {
		super(context,attr);
		this.cont = context;
		viewHolder = getHolder();
		viewHolder.setFormat(PixelFormat.TRANSPARENT);
		//viewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		viewHolder.addCallback(this);
		camera = MyCamera.getInstance();
	}
	@Override
	/**
	 * Implementation of OnCamOpenFinished intf,
	 * Invoked when cam is opened,
	 * Surface view holder instance is passed internally
	 */
	public void camOpened() {
		camPreview();
	}	
	/**
	 * camera open controller
	 */
	public void camOpen(OnCamOpenFinished callback){
		camera.doOpen(callback);
	}
	/**
	 * camera preview controller
	 */
	public void camPreview(){
		camera.doPreview(viewHolder);
	}
	/**
	 * camera take pic controller
	 */
	public void camTakePic(){
		camera.setpName(nameTxt.getText().toString());
		camera.doTakePic();
	}
	/**
	 * camera stop controller
	 */
	public void camStop(){
		camera.doStop();
	}
	@Override
	/**
	 * Called when surface is created
	 */
	public void surfaceCreated(SurfaceHolder holder) {
		camOpen(this);
	}
	@Override
	/**
	 * Called when surface is updated
	 * @param holder
	 * @param format
	 * @param width
	 * @param height
	 */
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}
	@Override
	/**
	 * Called when surface is destroyed
	 */
	public void surfaceDestroyed(SurfaceHolder holder) {
		camStop();
	}
	public Context getCont() {
		return cont;
	}
	public void setContext(Context cont) {
		this.cont = cont;
	}
	public MyCamera getCamera() {
		return camera;
	}
	public void setCamera(MyCamera camera) {
		this.camera = camera;
	}
	public SurfaceHolder getViewHolder() {
		return viewHolder;
	}
	public void setViewHolder(SurfaceHolder holder) {
		this.viewHolder = holder;
	}
	public EditText getNameTxt() {
		return nameTxt;
	}
	public void setNameTxt(EditText nameTxt) {
		this.nameTxt = nameTxt;
	}
}
