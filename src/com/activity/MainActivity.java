package com.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

import com.entity.Config;
import com.entity.MySurfaceView;
import com.whoiskiller.R;
/**
 * Picture taking activity
 * @author Luck
 *
 */
public class MainActivity extends Activity implements OnClickListener{
	private Button takePicBtn;
	private Button showPicBtn;
	private Button switchBtn;
	private MySurfaceView sView;
	private int sViewWidth = 500;
	private int sViewHeight = 500;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		takePicBtn = (Button) findViewById(R.id.takePicBtn);
		showPicBtn = (Button) findViewById(R.id.showPicBtn);
		switchBtn = (Button) findViewById(R.id.switchBtn);
		sView = (MySurfaceView) findViewById(R.id.sView);
		takePicBtn.setOnClickListener(this);
		showPicBtn.setOnClickListener(this);
		switchBtn.setOnClickListener(this);
		LayoutParams params = sView.getLayoutParams();
		params.height = sViewHeight;
		params.width = sViewWidth;
		sView.setLayoutParams(params);
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.takePicBtn){
			if(Config.getInstance().getTotalPlayers() == Config.MAX_PLAYERS){
				new AlertDialog.Builder(this).setMessage("Maximum players reached!").setTitle("Error").show();
				return;
			} else{
				sView.camTakePic();
			}
		}
		if(v.getId() == R.id.switchBtn){
			sView.camSwitch(sView);
		}
		if(v.getId() == showPicBtn.getId()){
			showPicBtn.setText(Config.getInstance().getTotalPlayers()+"");
			startActivity(new Intent(this.getBaseContext(), PictureActivity.class));
		}
	}

}
