package com.activity;

import android.R.bool;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import java.util.ArrayList;

import com.entity.Config;
import com.entity.Player;
import com.whoiskiller.MyAdapter;
import com.whoiskiller.R;
import com.whoiskiller.ViewHolder;
import com.whoiskiller.R.id;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
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
	private ListView lv;
	private MyAdapter mAdapter;
	private ArrayList<String> list;
	private TextView tv_show;// 用于显示选中的条目数
	private int flagchecked=-1, killed = -1, voted = -1;
	private Button fctBtn, idBtn, linesBtn, cdtBtn;
	private static Config config = Config.getInstance();
	private ImageView headImg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		bind();
		initialList();
		startGame();
	}
	/**
	 * Start Game
	 * @author Wu Dong
	 *
	 */
	private void startGame(){
		
	}
	
	private void initDate() {
		for (int i = 0; i < config.playerList.size(); i++) {
			list.add("data" + "   " + i);
		}
	}
	// 刷新listview和TextView的显示
	private void dataChanged() {
		mAdapter.notifyDataSetChanged();
	}
	private void bind(){
		/* 实例化各个控件 */
        lv = (ListView) findViewById(R.id.lv);
        tv_show = (TextView) findViewById(R.id.tv);
        list = new ArrayList<String>();
        fctBtn = (Button) findViewById(R.id.function);
        idBtn = (Button) findViewById(R.id.showperson);
        linesBtn = (Button)findViewById(R.id.lines);
        fctBtn.setOnClickListener(this);
        idBtn.setOnClickListener(this);
        linesBtn.setOnClickListener(this);
        headImg = (ImageView) findViewById(R.id.head);
        cdtBtn = (Button) findViewById(R.id.condition);
    }
	private void initialList(){
		config.testmode();
		// 为Adapter准备数据
        initDate();
        // 实例化自定义的MyAdapter
        mAdapter = new MyAdapter(list, this);
        // 绑定Adapter
        lv.setAdapter(mAdapter);

        // 绑定listView的监听器
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
                // 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
            	ViewHolder holder = (ViewHolder) arg1.getTag();
                // 改变CheckBox的状态
                holder.cb.toggle();
                // 将CheckBox的选中状况记录下来
                MyAdapter.getIsSelected().put(arg2, holder.cb.isChecked()); 
                if(flagchecked != -1)
                	MyAdapter.getIsSelected().put(flagchecked, false);
                // 调整选定条目
                if (holder.cb.isChecked() == true) {
                	flagchecked = arg2;
                	//BitmapDrawable bm =new BitmapDrawable(getResources(), config.playerList.get(flagchecked).getPlayerPicture());
                	//headImg.setBackground(bm);
                } else {
                	flagchecked = -1;
                }
                dataChanged();
                tv_show.setText("已选中"+arg2+"项");
            }
        });
	}

	public boolean checkIfIsEnd(){
		//String temp = ""+config.getKillersLeft()+config.getPolicesLeft()+config.getCivilsLeft();
		//new AlertDialog.Builder(this).setMessage(temp).setTitle("Congratulations!").show();
		
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
				//TODO
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
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.function){
			if(config.getCurrentStatus() == -1){//initial time
				Toast.makeText(getApplicationContext(), "GameStart",Toast.LENGTH_SHORT).show();
				fctBtn.setText("暗杀");
				config.setCurrentStatus(config.G_STAT_NIGHT);
				clearList();
			}
			/**
			 * KILL person in NIGHT
			 */
			else if(config.getCurrentStatus() == config.G_STAT_NIGHT){
				if(flagchecked != -1){
					config.playerDie(flagchecked);
					if(checkIfIsEnd()){
						//
					}else{
						config.setCurrentStatus(config.G_STAT_CHECK);
						fctBtn.setText("验身");
						killed = flagchecked;
						clearList();
					}
				}else{
					Toast.makeText(getApplicationContext(), "别着急，先选人再杀！",Toast.LENGTH_SHORT).show();
				}
			}
			/**
			 * Check person in NIGHT
			 *
			 */
			else if(config.getCurrentStatus() == config.G_STAT_CHECK){
				if(flagchecked != -1){
					Toast.makeText(getApplicationContext(), config.playerList.get(flagchecked).getPlayerId()+"",Toast.LENGTH_SHORT).show();
					clearList();
					fctBtn.setText("天亮");
					config.setCurrentStatus(config.G_STAT_DAY);
				}else{
					Toast.makeText(getApplicationContext(), "别着急，先选人再检查！",Toast.LENGTH_SHORT).show();
				}
			}
			/**
			 * Tell the Die person in the last night
			 *
			 */
			else if(config.getCurrentStatus() == config.G_STAT_DAY){
				//TODO
				clearList();
				fctBtn.setText("投票");
				Toast.makeText(getApplicationContext(), "今晚死的人是"+killed,Toast.LENGTH_SHORT).show();
				config.setCurrentStatus(config.G_STAT_VOTE);
			}
			else if(config.getCurrentStatus() == config.G_STAT_VOTE){
				if(flagchecked != -1){
					//TODO Vote the player
					config.playerDie(flagchecked);
					if(checkIfIsEnd()){
						//TODO
					}else{
						config.setCurrentStatus(config.G_STAT_INIT);
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
			//TODO
		}
		if(v.getId() == R.id.lines){
			//TODO
		}
	}
}
