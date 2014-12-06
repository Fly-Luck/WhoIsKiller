package com.whoiskiller;

import java.util.ArrayList;
import java.util.HashMap;

import com.entity.Config;
import com.entity.Player;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

	// 填充数据的list
	private ArrayList<String> list;
	// 用来控制CheckBox的选中状况
	private static HashMap<Integer,Boolean> isSelected;
	// 上下文
	private Context context;
	// 用来导入布局
	private LayoutInflater inflater = null;
 
	// 构造器
	public MyAdapter(ArrayList<String> list, Context context) {
		this.context = context;
		this.list = list;
	        inflater = LayoutInflater.from(context);
	        isSelected = new HashMap<Integer, Boolean>();
	        // 初始化数据
	        initDate();
	    }

	// 初始化isSelected的数据
	private void initDate(){
	for(int i=0; i<list.size();i++) {
	            getIsSelected().put(i,false);
	        }
	    }
	 @Override
	 public int getCount() {
		 return list.size();
	 }

	 @Override
	 public Object getItem(int position) {
		 return list.get(position);
	 }
	 @Override
	 public long getItemId(int position) {
		 return position;
	 }

	 @Override
	 public View getView(int position, View convertView, ViewGroup parent) {
		 ViewHolder holder = null;
		 if (convertView == null) {
			 // 获得ViewHolder对象
			 holder = new ViewHolder();
			 // 导入布局并赋值给convertview
			 convertView = inflater.inflate(R.layout.listviewitem, null);
			
			 holder.ig = (ImageView) convertView.findViewById(R.id.item_ig);
			 holder.ig2 = (ImageView) convertView.findViewById(R.id.item_dis);
			 holder.cb = (CheckBox) convertView.findViewById(R.id.item_cb);
			 // 为view设置标签
	            convertView.setTag(holder);
	            } else {
	            	// 取出holder
	            	holder = (ViewHolder) convertView.getTag();
	            	}

		 holder.cb.setChecked(getIsSelected().get(position));
		 if(list.get(position).charAt(0) == '1'){
			 holder.iflive = 1;
			 BitmapDrawable bm =new BitmapDrawable(Config.playerList.get(position).getPlayerPicture());
			 holder.ig.setBackground(bm);
		 }else{
			 holder.iflive = 0; 
			 holder.ig.setBackgroundResource(R.drawable.skull);
		 }
		 if(list.get(position).charAt(2) == '1'){
			 switch (Config.playerList.get(position).getPlayerId()) {
			 	case(Player.ID_JUDGE):{
					holder.ig2.setBackgroundResource(R.drawable.voice);
			 		break;
			 	}
			 	case (Player.ID_CIVIL):{
					holder.ig2.setBackgroundResource(R.drawable.civil_big);
			 		break;
				}
			 	case (Player.ID_KILLER):{
					holder.ig2.setBackgroundResource(R.drawable.killer_big);
			 		break;
				}
			 	case (Player.ID_POLICE):{
					holder.ig2.setBackgroundResource(R.drawable.police_big);
			 		break;
				}
			 }
		}else{
			 holder.ig2.setBackgroundResource(R.drawable.yes);
		 }
		 
		 return convertView;
	}

	public static HashMap<Integer,Boolean> getIsSelected() {
		return isSelected;
	}
	

	public static void setIsSelected(HashMap<Integer,Boolean> isSelected) {
	        MyAdapter.isSelected = isSelected;
	    }
	
	
}
