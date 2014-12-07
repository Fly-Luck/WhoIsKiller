package com.activity;

import com.entity.Config;
import com.entity.Player;
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
 * Role Assigning Activity
 * @author Luck(Liu Junjie)
 *
 */
public class RoleAssignActivity extends Activity implements OnClickListener, OnTouchListener{

	private ImageView hintTxt;
	private ImageView playerImg;
	private ImageView iAmBtn;
	private ImageView nextBtn;
	private static final int STAGE_PASS = 1;
	private static final int STAGE_CHECKING = 2;
	private static final int STAGE_ID = 3;
	private int stage;
	private int curPos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_role_assign);
		bindViews();
		stage = STAGE_PASS;
		curPos = 0;
		Config.getInstance().assignIds();
		if(Config.playerList.get(curPos).getPlayerId() == Player.ID_JUDGE)
			curPos++;
		passPhone();
	}
	private void bindViews() {
		hintTxt = (ImageView) findViewById(R.id.hintTxt);
		playerImg = (ImageView) findViewById(R.id.playerImg);
		iAmBtn = (ImageView) findViewById(R.id.iAmBtn);
		nextBtn = (ImageView) findViewById(R.id.nextBtn);

		nextBtn.setOnClickListener(this);
		nextBtn.setOnTouchListener(this);
		iAmBtn.setOnClickListener(this);
	}
	private void passPhone(){
		toggleUI();
		stage = STAGE_CHECKING;
	}
	private void checkingId(){
		toggleUI();
		stage = STAGE_ID;
	}
	private void showId(){
		toggleUI();
		stage = STAGE_PASS;
		curPos++;
	}
	private void toggleUI(){
		switch (stage) {
		case STAGE_PASS:
			hintTxt.setImageResource(R.drawable.pass_phone);
			iAmBtn.setVisibility(ImageView.INVISIBLE);
			nextBtn.setVisibility(ImageView.VISIBLE);
			playerImg.setImageBitmap(Config.playerList.get(curPos).getPlayerPicture());
			break;
		case STAGE_CHECKING:
			hintTxt.setImageResource(R.drawable.pass_phone);
			iAmBtn.setVisibility(ImageView.VISIBLE);
			iAmBtn.setImageResource(R.drawable.i_am);
			nextBtn.setVisibility(ImageView.INVISIBLE);
			playerImg.setImageBitmap(Config.playerList.get(curPos).getPlayerPicture());
			break;
		case STAGE_ID:
			hintTxt.setImageResource(R.drawable.you_are);
			iAmBtn.setVisibility(ImageView.VISIBLE);
			nextBtn.setVisibility(ImageView.VISIBLE);
			switch (Config.playerList.get(curPos).getPlayerId()) {
			case Player.ID_CIVIL:
				playerImg.setImageResource(R.drawable.civil_big);
				iAmBtn.setImageResource(R.drawable.civilian);
				break;
			case Player.ID_POLICE:
				playerImg.setImageResource(R.drawable.police_big);
				iAmBtn.setImageResource(R.drawable.police);
				break;
			case Player.ID_KILLER:
				playerImg.setImageResource(R.drawable.killer_big);
				iAmBtn.setImageResource(R.drawable.killer);
				break;
			}
			break;
		}
	}
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.nextBtn || v.getId() == R.id.iAmBtn){
			switch (stage) {
			case STAGE_PASS:
				if(curPos > Config.playerList.size() - 1)
					startActivity(new Intent(getBaseContext(), GameActivity.class));
				else if(Config.playerList.get(curPos).getPlayerId() != Player.ID_JUDGE)
					passPhone();
				else curPos++;
				break;
			case STAGE_CHECKING:
				checkingId();
				break;
			case STAGE_ID:
				showId();
				break;
			}
		}
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(v.equals(nextBtn)){
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				nextBtn.setImageResource(R.drawable.yes_down);
				return true;
			} else if(event.getAction() == MotionEvent.ACTION_UP){
				nextBtn.setImageResource(R.drawable.yes);
				v.performClick();
				return true;
			}
		}
		return false;
	}
}
