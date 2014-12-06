package com.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import java.util.ArrayList;

import com.entity.Config;
import com.entity.Player;
import com.whoiskiller.MyAdapter;
import com.whoiskiller.MyAdapter_img;
import com.whoiskiller.R;
import com.whoiskiller.ViewHolder;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
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
	private ArrayList<String> list, list_img;
	private TextView tv_show;// 用于显示选中的条目数
	private int flagchecked=-1, killed = -1;
	private Button fctBtn, idBtn, linesBtn, cdtBtn, pusBtn;
	private static Config config = Config.getInstance();
	private ImageView headImg;
	private AlertDialog.Builder builder, builder2;
	private AlertDialog alert, alert2;
	private int flag=0;
	
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
        cdtBtn = (Button) findViewById(R.id.condition);
		
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
		//config.testmode();
		// 为Adapter准备数据
		for (int i = 0; i < Config.playerList.size(); i++) {
			list.add("1");
			list_img.add("1");
		}
        // 实例化自定义的MyAdapter
        mAdapter = new MyAdapter(list, this);
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
            	if(holder.tv.getText() =="1" || config.getCurrentStatus()==Config.G_STAT_CHECK){
	                holder.cb.toggle();
	                MyAdapter.getIsSelected().put(arg2, holder.cb.isChecked()); 
	                if(flagchecked != -1)
	                	MyAdapter.getIsSelected().put(flagchecked, false);
	                if (holder.cb.isChecked() == true) {
	                	flagchecked = arg2;
	                	BitmapDrawable bm =new BitmapDrawable(getResources(), Config.playerList.get(flagchecked).getPlayerPicture());
	                	headImg.setBackground(bm);
	                	//headImg.setBackgroundResource(R.drawable.conan);
	                } else {
	                	flagchecked = -1;
	                }
	                dataChanged();
	                tv_show.setText("已选中"+arg2+"项");
	            }else if(holder.tv.getText() =="Die"){
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
		MyAdapter.getIsSelected().put(flagchecked, false);
		flagchecked = -1;
		dataChanged();
	}
	
	/**
	 * 玩家死亡函数
	 * @param position
	 */
	private void playerDie(int position) {
		config.playerDie(position);
		list.set(position, "0");
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
				Toast.makeText(getApplicationContext(), "GameStart",Toast.LENGTH_SHORT).show();
				cdtBtn.setText("杀手杀人时刻");
				fctBtn.setText("暗杀");
				config.setCurrentStatus(Config.G_STAT_NIGHT);
				clearList();
			}
			/* 
			 * KILL person in NIGHT
			 */
			else if(config.getCurrentStatus() ==Config.G_STAT_NIGHT){
				if(flagchecked != -1){
					playerDie(flagchecked);
					if(checkIfIsEnd()){
						//
					}else{
						config.setCurrentStatus(Config.G_STAT_CHECK);
						cdtBtn.setText("警察验人杀人时刻");
						fctBtn.setText("验身");
						killed = flagchecked;
						clearList();
					}
				}else{
					Toast.makeText(getApplicationContext(), "别着急，先选人再杀！",Toast.LENGTH_SHORT).show();
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
					switch (Config.playerList.get(flagchecked).getPlayerId()) {
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
					Toast.makeText(getApplicationContext(), checkedId,Toast.LENGTH_SHORT).show();
					clearList();
					fctBtn.setText("天亮");
					cdtBtn.setText("天亮了，告诉大家谁死了吧！");
					config.setCurrentStatus(Config.G_STAT_DAY);
				}else{
					Toast.makeText(getApplicationContext(), "别着急，先选人再检查！",Toast.LENGTH_SHORT).show();
				}
			}
			/*
			 *
			 * Tell the Die person in the last night
			 *
			 */
			else if(config.getCurrentStatus() == Config.G_STAT_DAY){
				clearList();
				fctBtn.setText("投票");
				cdtBtn.setText("投票谁是凶手？");
				Toast.makeText(getApplicationContext(), "今晚死的人是"+killed,Toast.LENGTH_SHORT).show();
				config.setCurrentStatus(Config.G_STAT_VOTE);
			}
			else if(config.getCurrentStatus() == Config.G_STAT_VOTE){
				if(flagchecked != -1){
					playerDie(flagchecked);
					if(checkIfIsEnd()){
					}else{
						config.setCurrentStatus(Config.G_STAT_INIT);
						cdtBtn.setText("夜幕降临");
						fctBtn.setText("天黑");
						clearList();
						config.setCurrentDay(config.getCurrentDay()+1);
					}
				}else{
					Toast.makeText(getApplicationContext(), "别着急，先选人再投票！",Toast.LENGTH_SHORT).show();
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
		builder2.setMessage("Lines");
		alert2 = builder2.show();
	}
	
	/**
	 * 显示提示身份的Dialog
	 */
	private void openIdDialog() {
		if(flag==0){
			builder.setView(lv_id);
			alert = builder.show();
			flag++;
		}else{
			alert.show();
		}
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
				  alert.dismiss();
			  }
			return false;
		}  
	};
	
}
