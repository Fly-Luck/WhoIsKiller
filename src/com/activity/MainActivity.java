package com.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.entity.Config;
import com.entity.MySurfaceView;
import com.whoiskiller.R;
/**
 * Picture taking activity
 * @author Luck(Liu Junjie)
 *
 */
public class MainActivity extends Activity implements OnClickListener, OnTouchListener{
	private ImageView takePicBtn;
	private ImageView showPicBtn;
	private ImageView switchBtn;
	private ImageView showPlayerBtn;
	public static TextView cameraHint;
	private MySurfaceView sView;
	private int sViewWidth = 500;
	private int sViewHeight = 500;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Config.getInstance().restart();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		takePicBtn = (ImageView) findViewById(R.id.takePicBtn);
		showPicBtn = (ImageView) findViewById(R.id.showPicBtn);
		switchBtn = (ImageView) findViewById(R.id.switchBtn);
		showPlayerBtn = (ImageView) findViewById(R.id.showPlayersBtn);
		cameraHint = (TextView) findViewById(R.id.cameraTxt);
		sView = (MySurfaceView) findViewById(R.id.sView);
		takePicBtn.setOnClickListener(this);
		showPicBtn.setOnClickListener(this);
		showPicBtn.setOnTouchListener(this);
		switchBtn.setOnClickListener(this);
		showPlayerBtn.setOnClickListener(this);
		LayoutParams params = sView.getLayoutParams();
		params.height = sViewHeight;
		params.width = sViewWidth;
		sView.setLayoutParams(params);
		refreshPlayerNum();
	}
	
	public static void refreshPlayerNum(){
		cameraHint.setText("Now we have "+Config.playerList.size()+" players");
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void onClick(View v) {
		if(v.equals(takePicBtn)){
			if(Config.getInstance().playerList.size() == Config.MAX_PLAYERS){
				new AlertDialog.Builder(this).setMessage("Maximum players reached!").setTitle("Error").show();
				return;
			} else{
				sView.camTakePic();
			}
		}
		if(v.equals(switchBtn)){
			sView.camSwitch(sView);
		}
		if(v.getId() == showPicBtn.getId()){
			if(Config.playerList.size() < 7){
				new AlertDialog.Builder(this).setMessage("Minimum players is 7!").setTitle("Error").show();
				return;
			}
			startActivity(new Intent(this.getBaseContext(), JudgeSelectActivity.class));
		}
		if(v.equals(showPlayerBtn)){
			startActivity(new Intent(this.getBaseContext(), PictureActivity.class));
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(v.getId() == R.id.showPicBtn){
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				showPicBtn.setImageResource(R.drawable.yes_down);
				return true;
			} else if(event.getAction() == MotionEvent.ACTION_UP){
				showPicBtn.setImageResource(R.drawable.yes);
				v.performClick();
				return true;
			}
		}
		return false;
	}

}
