package com.activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.entity.Config;
import com.entity.Player;
import com.whoiskiller.R;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Role Assigning Activity
 * @author Luck
 *
 */
public class RoleAssignActivity extends Activity implements OnClickListener{

	private TextView hintTxt;
	private ImageView playerImg;
	private Button nextBtn;
	private static final int STAGE_PASS = 1;
	private static final int STAGE_ID = 2;
	private int stage;
	private int curPos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_role_assign);
		hintTxt = (TextView) findViewById(R.id.hintTxt);
		playerImg = (ImageView) findViewById(R.id.playerImg);
		nextBtn = (Button) findViewById(R.id.nextBtn);
		nextBtn.setOnClickListener(this);
		stage = STAGE_PASS;
		curPos = 0;
		genRandomIds();
		passPhone();
	}
	private void genRandomIds() {
		int total = Config.playerList.size() - 1;
		int killers = total/3;
		int polices = killers;
		ArrayList<Integer> randomPos = new ArrayList<Integer>();
		int jdgPos = -1;
		for(Player player: Config.playerList){
			if(player.getPlayerId() == Player.ID_JUDGE){
				jdgPos = Config.playerList.indexOf(player);
				break;
			}
		}
		for(int i=0;i<Config.playerList.size();i++){
			if(jdgPos == i)
				continue;
			randomPos.add(i);
		}
		Collections.shuffle(randomPos);
		for(int i=0;i<randomPos.size();i++){
			int pos = randomPos.get(i);
			if(Config.playerList.get(pos).getPlayerId() == Player.ID_JUDGE)
				continue;
			if(i < killers){
				Config.playerList.get(pos).setPlayerId(Player.ID_KILLER);
			} else if(i < killers + polices){
				Config.playerList.get(pos).setPlayerId(Player.ID_POLICE);
			}
		}
	}
	private void passPhone(){
		hintTxt.setText("Please pass the phone to");
		playerImg.setImageBitmap(Config.playerList.get(curPos).getPlayerPicture());
		stage = STAGE_ID;
	}
	private void showId(){
		switch (Config.playerList.get(curPos).getPlayerId()) {
		case Player.ID_CIVIL:
			//playerImg.setBackgroundResource(R.id.civil);
			hintTxt.setText("You are a Civilian");
			break;
		case Player.ID_POLICE:
			//playerImg.setBackgroundResource(R.id.police);						
			hintTxt.setText("You are a Policeman");
			break;
		case Player.ID_KILLER:
			//playerImg.setBackgroundResource(R.id.killer);						
			hintTxt.setText("You are a Killer");
			break;
		}
		stage = STAGE_PASS;
		curPos++;
	}
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.nextBtn){
			switch (stage) {
			case STAGE_PASS:
				if(curPos > Config.playerList.size() - 1)
					startActivity(new Intent(getBaseContext(), GameActivity.class));
				else if(Config.playerList.get(curPos).getPlayerId() != Player.ID_JUDGE)
					passPhone();
				else curPos++;
				break;
			case STAGE_ID:
				showId();
				break;
			}
			
		}
	}
}
