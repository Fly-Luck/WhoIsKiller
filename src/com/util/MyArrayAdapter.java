package com.util;

import java.util.ArrayList;
import java.util.List;

import com.activity.PictureActivity;
import com.entity.Config;
import com.whoiskiller.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Adapter to provide data for gridview or listview
 * @author Luck(Liu Junjie)
 *
 */
public class MyArrayAdapter extends ArrayAdapter<Object> {

	//activity instance
	private Context context;
	//item layout of gridview
	private int textViewResourceId;
	//data to feed the gridview
	private ArrayList<Object> objects;
	//view type
	private int type;
	public MyArrayAdapter(Context context, int textViewResourceId,
			List<Object> objects, int type) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.textViewResourceId = textViewResourceId;
		this.objects = (ArrayList<Object>) objects;
		this.type = type;
	}
	
	@Override
	//final position so it can be passed into buttons' OnClick steadily
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(null == convertView){
			//get a layout instance
			LayoutInflater inflater = ((Activity) context).getLayoutInflater(); 
			//get a child layout instance(R.layout.players)
			convertView = inflater.inflate(textViewResourceId, parent, false);
		} else {
			convertView = (RelativeLayout) convertView;
		}
		Object item = objects.get(position);
		ImageView img;
		if(type == 1){
			img = (ImageView) convertView.findViewById(R.id.playerPic);
			Button btn = (Button) convertView.findViewById(R.id.remBtn);
			btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//position is final
					Config.playerList.remove(position);
					//player -1
					//Config.getInstance().setTotalPlayers(Config.getInstance().getTotalPlayers()-1);
					PictureActivity pa = (PictureActivity)context;
					pa.bindGridAdapter();
				}
			});
			img.setImageBitmap((Bitmap) item);
		} else{
			img = (ImageView) convertView.findViewById(R.id.playerPic_judge);
			img.setImageBitmap((Bitmap)item);
		}

		return convertView;
	}
}
