package com.activity;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import java.util.ArrayList;

import com.entity.Config;
import com.entity.Player;
import com.whoiskiller.MyAdapter;
import com.whoiskiller.MyAdapter_img;
import com.whoiskiller.R;
import com.whoiskiller.ViewHolder;

import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Game Page
 * @author Wu Dong
 *
 */ 
public class GameActivity extends Activity implements OnClickListener{
	private ListView lv, lv_id;
	private MyAdapter mAdapter;
	private MyAdapter_img mAdapter_img;
	private ArrayList<String> list;
	private ArrayList<String>list_img;
	private TextView tv_show;// 用于显示选中的条目数
	private int flagchecked=-1, killed = -1, flagchecked_player=-1;
	private Button fctBtn, idBtn, linesBtn, pusBtn;
	private TextView cdtBtn;
	private static Config config = Config.getInstance();
	private ImageView headImg;
	private AlertDialog.Builder builder, builder2;
	private AlertDialog alert, alert2;
	private int flag=0, judgePos;
	
	/**
	 * Start Game
	 * @author Wu Dong
	 *
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		bind();
		initialList();	
	}
	
	private void dataChanged() {
		mAdapter.notifyDataSetChanged();
	}
	
	private void bind(){
		/* 实例化各个控件并绑定对应函数 */
        lv = (ListView) findViewById(R.id.lv);
        lv_id = new ListView(GameActivity.this);
        tv_show = (TextView) findViewById(R.id.tv);
        list = new ArrayList<String>();
        list_img = new ArrayList<String>();
        pusBtn = (Button) findViewById(R.id.pause);
        fctBtn = (Button) findViewById(R.id.function);
        idBtn = (Button) findViewById(R.id.showperson);
        linesBtn = (Button)findViewById(R.id.lines);
        cdtBtn = (TextView) findViewById(R.id.condition);
        fctBtn.setOnClickListener(this);
        pusBtn.setOnClickListener(this);
        idBtn.setOnTouchListener(buttonListener_id);
        linesBtn.setOnTouchListener(buttonListener_lines);
        headImg = (ImageView) findViewById(R.id.head);
        builder = new AlertDialog.Builder(GameActivity.this);
        builder2 = new AlertDialog.Builder(GameActivity.this);
    }
	
	/**
	 * 制作左侧的list
	 * 
	 */
	private void initialList(){
		// 为Adapter准备数据
		for (int i = 0; i < Config.playerList.size(); i++) {
			/*第一个1代表死否活着，第二个1表示是否选中, 第三个表示是否显示身份*/
			if(config.playerList.get(i).getPlayerId()==Player.ID_JUDGE){
				judgePos = i;
			}else{
				list.add("110");
			}
			list_img.add("1");
		}
        // 实例化自定义的MyAdapter
        mAdapter = new MyAdapter(list, this, judgePos);
        mAdapter_img = new MyAdapter_img(list_img, this);
        
        // 绑定Adapter
        lv.setAdapter(mAdapter);
        lv_id.setAdapter(mAdapter_img);
        
        // 绑定listView的监听器
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
                ViewHolder holder = (ViewHolder) arg1.getTag();
            	if(holder.iflive == 1 || config.getCurrentStatus()==Config.G_STAT_CHECK){
	                holder.cb.setChecked(true);
	                MyAdapter.getIsSelected().put(arg2, holder.cb.isChecked()); 
	                if(flagchecked != -1 && flagchecked != arg2){
	                	yesUp(flagchecked);
	                	MyAdapter.getIsSelected().put(flagchecked, false);
	                }
	                //if (holder.cb.isChecked() == true) {
	                	flagchecked = arg2;
	                	if(flagchecked>=judgePos)
	                		flagchecked_player = flagchecked+1;
	                	else {
	                		flagchecked_player = flagchecked;
						}
	                	BitmapDrawable bm =new BitmapDrawable(getResources(), Config.playerList.get(flagchecked_player).getPlayerPicture());
	                	headImg.setBackground(bm);
	                	yesDown(flagchecked);
	                /*} else {
	                	flagchecked = -1;
	                	flagchecked_player = -1;
	                }*/
	                dataChanged();
	                tv_show.setText("");
	            }else if(holder.iflive ==0) {
	            	//
	            }
            }
        });
	}
	
	/**
	 * 检查游戏是否结束
	 * @return boolean
	 */

	public boolean checkIfIsEnd(){
		switch (config.getResult()) {
			case (Config.R_CIVIL_WIN) : {
				new AlertDialog.Builder(this).setMessage("Civil and Cops Win!").setTitle("Congratulations!").show();
				return true;
			}
			case(Config.R_KILLER_WIN):{
				new AlertDialog.Builder(this).setMessage("Killers Win!").setTitle("Congratulations!").show();
				return true;
			}
			case(Config.R_PENDING):{
				break;
			}
		}
		return false;
	}
	public void clearList(){
		if(flagchecked!=-1){
			MyAdapter.getIsSelected().put(flagchecked, false);
			yesUp(flagchecked);
			flagchecked = -1;
			flagchecked = -1;
		}
		dataChanged();
	}
	
	/**
	 * 玩家死亡函数
	 * @param position
	 */
	private void playerDie(int position, int position2) {
		config.playerDie(position2);
		String temp = list.get(position);
		list.set(position,"0"+temp.substring(1));
		dataChanged();
	}
	
	/**
	 * Onclick 函数绑定
	 * 
	 */
	@Override
	public void onClick(View v) {
		/**
		 * function button
		 * 
		 */
		if(v.getId() == R.id.function){
			if(config.getCurrentStatus() == -1){
				Toast.makeText(getApplicationContext(), "Night "+Config.getInstance().getCurrentDay(),Toast.LENGTH_SHORT).show();
				cdtBtn.setText("Killer killing");
				fctBtn.setBackgroundResource(R.drawable.kill);
				config.setCurrentStatus(Config.G_STAT_NIGHT);
				clearList(); 
			}
			/* 
			 * KILL person in NIGHT
			 */
			else if(config.getCurrentStatus() ==Config.G_STAT_NIGHT){
				if(flagchecked != -1){
					playerDie(flagchecked, flagchecked_player);
					if(checkIfIsEnd()){
						//
					}else{
						config.setCurrentStatus(Config.G_STAT_CHECK);
						cdtBtn.setText("Police checking");
						fctBtn.setBackgroundResource(R.drawable.check);
						killed = flagchecked;
						clearList();
					}
				}else{
					Toast.makeText(getApplicationContext(), "Please choose a player to kill",Toast.LENGTH_SHORT).show();
				}
			}
			/*
			 * 
			 * Check person in NIGHT
			 *
			 */
			else if(config.getCurrentStatus() == Config.G_STAT_CHECK){
				if(flagchecked != -1){
					String checkedId= "";
					switch (Config.playerList.get(flagchecked_player).getPlayerId()) {
						case Player.ID_CIVIL:{
							checkedId = "Civil";
							break;
						}
						case Player.ID_KILLER:{
							checkedId = "Killer";
							break;
						}
						case Player.ID_POLICE:{
							checkedId = "Police";
							break;
						}
					}
					//Toast.makeText(getApplicationContext(), checkedId,Toast.LENGTH_SHORT).show();
					displayCheckedId(checkedId);
					clearList();
					fctBtn.setBackgroundResource(R.drawable.dawn);
					cdtBtn.setText("Dawn is coming");
					config.setCurrentStatus(Config.G_STAT_DAY);
				}else{
					Toast.makeText(getApplicationContext(), "Please choose a player to check",Toast.LENGTH_SHORT).show();
				}
			}
			/*
			 *
			 * Tell the Die person in the last night
			 *
			 */
			else if(config.getCurrentStatus() == Config.G_STAT_DAY){
				clearList();
				fctBtn.setBackgroundResource(R.drawable.vote);
				cdtBtn.setText("Voting the killer");
				//Toast.makeText(getApplicationContext(), "今晚死的人是"+killed,Toast.LENGTH_SHORT).show();
				displayDeadPerson(killed);
				config.setCurrentStatus(Config.G_STAT_VOTE);
			}
			else if(config.getCurrentStatus() == Config.G_STAT_VOTE){
				if(flagchecked != -1){
					playerDie(flagchecked, flagchecked_player);
					if(checkIfIsEnd()){
					}else{
						config.setCurrentStatus(Config.G_STAT_INIT);
						cdtBtn.setText("Dark is coming");
						fctBtn.setBackgroundResource(R.drawable.dark);
						clearList();
						config.setCurrentDay(config.getCurrentDay()+1);
					}
				}else{
					Toast.makeText(getApplicationContext(), "Please choose a player to vote",Toast.LENGTH_SHORT).show();
				}
			}
		}
		if(v.getId() == R.id.showperson){
			openIdDialog();
		}
		if(v.getId() == R.id.lines){
			
		}
		if(v.getId() == R.id.pause){
			pauseFunction();
		}
	}
	
	/**
	 * 显示被检查的人身份
	 * 
	 */
	private void displayCheckedId(String checkId){
		String tempString = "";
		tempString = "This is "+ checkId +" !";
		AlertDialog.Builder builder = new Builder(GameActivity.this);
		//builder.setMessage(tempString);
		builder.setTitle("Secretly tell police");
		ImageView view = new ImageView(this);
		if(checkId.equals("Killer"))
			view.setImageResource(R.drawable.he_is_killer);
		else view.setImageResource(R.drawable.he_is_not_killer);
		view.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		builder.setView(view);
		builder.setPositiveButton("OK", null);
		builder.create().show();
	}
	/**
	 * 显示昨天死亡的人
	 */
	private void displayDeadPerson(int people) {
		String tempString = "";
		tempString = "Last night player " + (people+1) +" died!";
		AlertDialog.Builder builder = new Builder(GameActivity.this);
		builder.setMessage(tempString);
		builder.setTitle("Judge's Announcement");
		ImageView view = new ImageView(this);
		if(people >= judgePos){
			people++;
		}
		Bitmap playerPic = Config.playerList.get(people).getPlayerPicture();
		Matrix matrix = new Matrix();
		matrix.postScale(2.5f,2.5f);
		Bitmap resizeBmp = Bitmap.createBitmap(playerPic,0,0,playerPic.getWidth(),playerPic.getHeight(),matrix,true);
		view.setImageBitmap(resizeBmp);
		view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		view.setLayoutParams(new LayoutParams(500, 500));
		builder.setView(view);
		builder.create().show();
	}
	/**
	 * 暂停界面
	 */
	private void pauseFunction(){
		 AlertDialog.Builder builder = new Builder(GameActivity.this);
		 builder.setMessage("这里是内容");
		 builder.setTitle("提示");
		 builder.create().show();
	}

	/**
	 * 显示法官台词的函数
	 * 
	 */
	private OnTouchListener buttonListener_lines = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			int action = event.getAction();
			if (action == MotionEvent.ACTION_DOWN) { // 按下
				openLinesDialog();
				} else if (action == MotionEvent.ACTION_UP) { // 松开   
					alert2.dismiss();
					}
			return false;
		}  
	};
	
	/**
	 * 显示法官台词
	 */
	private void openLinesDialog(){
		String tempMessage = "";
		switch (config.getCurrentStatus()) {
			case (Config.G_STAT_CHECK):{
				tempMessage = "Police, open eyes, \nchoose one to check!";
				break;
			}case (Config.G_STAT_DAY):{
				tempMessage = "It's Dawn, everybody open eyes!";
				break;
			}case (Config.G_STAT_INIT):{
				tempMessage = "It's Dark, everybody close eyes!";
				break;
			}
			case (Config.G_STAT_KILL):{
				tempMessage = "Killers, open eyes, \nchoose one to kill!";
				break;
			}case (Config.G_STAT_NIGHT):{
				tempMessage = "Killers, open eyes, \nchoose one to kill!";
				break;
			}case (Config.G_STAT_VOTE):{
				tempMessage = "Vote for who you think is the Killer!";
				break;
			}
		}
		builder2.setMessage(tempMessage);
		alert2 = builder2.show();

	}
	
	/**
	 * 
	 * 按下去yes的函数
	 */
	private void yesDown(int position){
		String temp = list.get(position);
		list.set(position,temp.substring(0,1)+"0"+temp.substring(2));
		dataChanged();
	}
	
	/**
	 * 取消yes的函数
	 * 
	 */
	private void yesUp(int position){
		String temp = list.get(position);
		list.set(position,temp.substring(0,1)+"1"+temp.substring(2));
		dataChanged();
	}
	
	/**
	 * 显示提示身份的Dialog
	 */
	private void openIdDialog() {
		int i=0;
		for(i=0; i<list.size(); i++){
			String temp = list.get(i);
			list.set(i,temp.substring(0, 2)+"1");
		}
		dataChanged();
	}
	private void closeIdDialog(){
		int i=0;
		for(i=0; i<list.size(); i++){
			String temp = list.get(i);
			list.set(i,temp.substring(0,2)+"0");
		}
		dataChanged();
	}
	/**
	 * 显示每个人身份的函数
	 * 
	 */
	private OnTouchListener buttonListener_id = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			int action = event.getAction();
			 if (action == MotionEvent.ACTION_DOWN) {
				 openIdDialog();
			  } else if (action == MotionEvent.ACTION_UP) {   
				  //alert.dismiss();
				  closeIdDialog();
			  }
			return false;
		}  
	};
	
}
