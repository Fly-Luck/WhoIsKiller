package com.activity;

import java.util.ArrayList;

import com.entity.Config;
import com.entity.Player;
import com.util.MyArrayAdapter;
import com.whoiskiller.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Judge Selecting Activity
 * @author Luck(Liu Junjie)
 *
 */
public class JudgeSelectActivity extends Activity implements OnClickListener, OnItemClickListener, OnTouchListener{

	private ListView jdgList;
	private ImageView prcdBtn;
	private ImageView selectedJudge;
	private int selectedPos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_judge_select);
		selectedPos = -1;
		jdgList = (ListView) findViewById(R.id.jdgSelList);
		prcdBtn = (ImageView) findViewById(R.id.jdgSelBtn);
		selectedJudge = (ImageView) findViewById(R.id.showPic_judge);
		ArrayList<Object> playerPics = new ArrayList<Object>();
		for (Player player : Config.playerList) {
			playerPics.add(player.getPlayerPicture());
		}
		MyArrayAdapter adapter = new MyArrayAdapter(this, R.layout.player_pics, playerPics, 2);
		jdgList.setAdapter(adapter);
		jdgList.setOnItemClickListener(this);
		prcdBtn.setOnClickListener(this);
		prcdBtn.setOnTouchListener(this);
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
				child.setBackgroundResource(R.drawable.chosen);
		}
		selectedPos = (int) id;
		view.setBackgroundResource(R.drawable.choose_down);
		selectedJudge.setImageBitmap(Config.playerList.get(selectedPos).getPlayerPicture());
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(v.equals(prcdBtn)){
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				prcdBtn.setImageResource(R.drawable.yes_down);
				return true;
			} else if(event.getAction() == MotionEvent.ACTION_UP){
				prcdBtn.setImageResource(R.drawable.yes);
				v.performClick();
				return true;
			}
		}
		return false;
	}
	
}
