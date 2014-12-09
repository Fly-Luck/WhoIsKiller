package com.activity;

import com.whoiskiller.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

/**
 * Init activity of game
 * @author Luck(Liu Junjie)
 *
 */
public class InitActivity extends Activity implements OnClickListener, OnTouchListener{

	private ImageView infoBtn, startBtn, playersBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init);
		infoBtn = (ImageView) findViewById(R.id.infoBtn);
		startBtn = (ImageView) findViewById(R.id.startBtn);
		playersBtn = (ImageView) findViewById(R.id.playersBtn);
		infoBtn.setOnClickListener(this);
		infoBtn.setOnTouchListener(this);
		startBtn.setOnClickListener(this);
		startBtn.setOnTouchListener(this);
		playersBtn.setOnClickListener(this);
		playersBtn.setOnTouchListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.startBtn){
			startActivity(new Intent(this.getBaseContext(), MainActivity.class));			
		} else if(v.getId() == R.id.infoBtn){
			startActivity(new Intent(this.getBaseContext(), AuthorActivity.class));
		} else if(v.getId() == R.id.playersBtn){
			startActivity(new Intent(this.getBaseContext(), PictureActivity.class));
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(v.getId() == R.id.startBtn || v.getId() == R.id.infoBtn || v.getId() == R.id.playersBtn){
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				v.setAlpha(0.5f);
				return true;
			} else if(event.getAction() == MotionEvent.ACTION_UP){
				v.setAlpha(1f);
				v.performClick();
				return true;
			}
		}
		return false;
	}
}
