package com.activity;

import com.whoiskiller.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * Init activity of game
 * @author Luck(Liu Junjie)
 *
 */
public class InitActivity extends Activity implements OnClickListener{

	private ImageView infoBtn, startBtn, playersBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init);
		infoBtn = (ImageView) findViewById(R.id.infoBtn);
		startBtn = (ImageView) findViewById(R.id.startBtn);
		playersBtn = (ImageView) findViewById(R.id.playersBtn);
		infoBtn.setOnClickListener(this);
		startBtn.setOnClickListener(this);
		playersBtn.setOnClickListener(this);
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
}
