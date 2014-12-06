package com.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.entity.Config;
import com.entity.Player;
import com.util.MyArrayAdapter;
import com.whoiskiller.MyAdapter;
import com.whoiskiller.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SimpleAdapter.ViewBinder;

/**
 * Judge Selecting Activity
 * @author Luck
 *
 */
public class JudgeSelectActivity extends Activity implements OnClickListener, OnItemClickListener{

	private ListView jdgList;
	private Button prcdBtn;
	private int selectedPos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_judge_select);
		selectedPos = -1;
		jdgList = (ListView) findViewById(R.id.jdgSelList);
		prcdBtn = (Button) findViewById(R.id.jdgSelBtn);
		ArrayList<Object> playerPics = new ArrayList<Object>();
		for (Player player : Config.playerList) {
			playerPics.add(player.getPlayerPicture());
		}
		MyArrayAdapter adapter = new MyArrayAdapter(this, R.layout.player_pics, playerPics, 2);
		jdgList.setAdapter(adapter);
		jdgList.setOnItemClickListener(this);
		prcdBtn.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.jdgSelBtn){
			if(selectedPos == -1){
				new AlertDialog.Builder(this).setMessage("Please select the Judge!").setTitle("Error").show();
			} else{
				Config.playerList.get(selectedPos).setPlayerId(Player.ID_JUDGE);
				startActivity(new Intent(this.getBaseContext(), RoleAssignActivity.class));
			}
		}
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		for(int i=0;i<Config.playerList.size();i++){
			View child = jdgList.getChildAt(i);
			if(child != null)
				child.setBackgroundColor(Color.TRANSPARENT);
		}
		selectedPos = (int) id;
		view.setBackgroundColor(Color.BLUE);
	}
	
}
