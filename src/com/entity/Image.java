package com.entity;

import android.graphics.Bitmap;

/**
 * Entity class for image processing
 * #TO-BE-DEPRECATED
 * @author Luck
 *
 */
public class Image {
	private String name;
	private Bitmap bitmap;
	
	public Image(String name, Bitmap bitmap) {
		super();
		this.name = name;
		this.bitmap = bitmap;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
}
