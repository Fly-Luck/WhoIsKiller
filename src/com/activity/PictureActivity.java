package com.activity;

import java.util.ArrayList;

import com.entity.Config;
import com.entity.Player;
import com.util.MyArrayAdapter;
import com.whoiskiller.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;

/**
 * Picture showing & removing activity
 * @author Luck(Liu Junjie)
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
	@SuppressWarnings("static-access")
	public void bindGridAdapter(){
		playerGrid = (GridView) findViewById(R.id.playerGrid);		
		ArrayList<Object> playerPics = new ArrayList<Object>();
		for (Player player : Config.getInstance().playerList) {
			playerPics.add(player.getPlayerPicture());
		}
		MyArrayAdapter adapter = new MyArrayAdapter(PictureActivity.this, R.layout.players, playerPics, 1);
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
