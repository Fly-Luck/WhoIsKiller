package com.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;

import com.entity.Config;
import com.entity.MySurfaceView;
import com.whoiskiller.R;
  

public class MainActivity extends Activity implements OnClickListener{
	private Button takePicBtn;
	private Button showPicBtn;
	private EditText nameTxt;
	private MySurfaceView sView;
	private int sViewWidth = 500;
	private int sViewHeight = 500;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		takePicBtn = (Button) findViewById(R.id.takePicBtn);
		showPicBtn = (Button) findViewById(R.id.showPicBtn);
		nameTxt = (EditText) findViewById(R.id.nameTxt);
		sView = (MySurfaceView) findViewById(R.id.sView);
		sView.setNameTxt(nameTxt);
		takePicBtn.setOnClickListener(this);
		showPicBtn.setOnClickListener(this);
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
			} else if(nameTxt.getText().toString().equals("")){
				new AlertDialog.Builder(this).setMessage("Empty player name!").setTitle("Error").show();
			} else if(Config.getInstance().hasPlayerIn(nameTxt.getText().toString())){
				new AlertDialog.Builder(this).setMessage("Duplicated player name!").setTitle("Error").show();
				return;
			} else{
				sView.camTakePic();
			}
		}
		if(v.getId() == showPicBtn.getId()){
			showPicBtn.setText(Config.getInstance().getTotalPlayers()+"");
		}
	}

}
