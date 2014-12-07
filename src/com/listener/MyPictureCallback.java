package com.listener;

import com.intf.OnCamTakePicFinished;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;

/**
 * Picture taken callback to provide picture processing functionalities
 * @author Luck(Liu Junjie)
 *
 */
public class MyPictureCallback implements PictureCallback{

	//temporary storage of the processed image
	private Bitmap bitmap = null;
	//invocation for the caller
	private OnCamTakePicFinished listener;
	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		//stopping the preview
		camera.stopPreview();
		//bytes->bitmap
		setBitmap(BitmapFactory.decodeByteArray(data,0,data.length));
		//invoke the caller
        listener.camPicTaken();
	}
	public MyPictureCallback(OnCamTakePicFinished callback) {
		listener = callback;
	}
	public Bitmap getBitmap(){
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap){
		this.bitmap = bitmap;
	}
}
