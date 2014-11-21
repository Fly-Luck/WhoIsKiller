package com.activity;

import com.entity.Config;
import com.util.MyArrayAdapter;
import com.whoiskiller.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;

/**
 * Picture showing & removing activity
 * @author Luck
 *
 */
public class PictureActivity extends Activity implements OnClickListener{
	private GridView playerGrid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture);
		bindGridAdapter();
	}
	/**
	 * Bind an ArrayAdapter to the gridview
	 */
	public void bindGridAdapter(){
		playerGrid = (GridView) findViewById(R.id.playerGrid);
		MyArrayAdapter adapter = new MyArrayAdapter(PictureActivity.this, R.layout.players, Config.imgList);
		playerGrid.setAdapter(adapter);
	}
	@Override
	public void onClick(View v) {
	}
	public GridView getPlayerGrid() {
		return playerGrid;
	}
	public void setPlayerGrid(GridView playerGrid) {
		this.playerGrid = playerGrid;
	}

}
