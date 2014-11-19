package com.listener;

import com.intf.OnCamTakePicFinished;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;

public class MyPictureCallback implements PictureCallback{

	private Bitmap bitmap = null;
	private OnCamTakePicFinished listener;
	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		camera.stopPreview();
		//bytes->bitmap
		setBitmap(BitmapFactory.decodeByteArray(data,0,data.length));
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
