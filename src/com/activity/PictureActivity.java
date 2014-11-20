package com.activity;

import com.entity.Config;
import com.whoiskiller.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PictureActivity extends Activity implements OnClickListener{
	LinearLayout ll;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture);
		findViewById(R.id.rm1).setOnClickListener(this);
		findViewById(R.id.rm2).setOnClickListener(this);
		findViewById(R.id.rm3).setOnClickListener(this);
		ImageView i1 = (ImageView) findViewById(R.id.i1);
		i1.setImageBitmap((Bitmap) Config.imgList.get(0));
		ll = (LinearLayout)findViewById(R.id.ll);

	}
    public Bitmap zoomBitmap(Bitmap bitmap, int w, int h){  
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
	@Override
	public void onClick(View v) {
		if(R.id.rm1 == v.getId()){
			ll.removeViewAt(0);
		} else if(R.id.rm2 == v.getId()){
			ll.removeViewAt(1);
		} else if(R.id.rm3 == v.getId()){
			ll.removeViewAt(2);
			
		}
	}

}
