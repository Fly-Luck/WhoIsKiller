package com.entity;

import android.graphics.Bitmap;

/**
 * Entity class of player variables
 * @author Luck(Liu Junjie)
 *
 */
public class Player {
	//identities of the player
	public final static int ID_JUDGE = 0;
	public final static int ID_CIVIL = 1;
	public final static int ID_KILLER = 2;
	public final static int ID_POLICE = 3;
	//identity of player, NOT an ordered number
	private int playerId;
	//player seat
	private int playerSeat;
	//player status - dead or alive
	private int playerStatus;
	//player picture
	private Bitmap playerPicture;
	public Player(){
		reset();
	}
	public Player(Bitmap bitmap){
		reset();
		playerPicture = bitmap;
	}
	private void reset(){
		playerId = ID_CIVIL;
		playerSeat = 0;
		playerStatus = Config.P_STAT_ALIVE;
		//Changed
		Bitmap.Config conf = Bitmap.Config.ARGB_8888;
		Bitmap bmp = Bitmap.createBitmap(200, 200, conf);
		playerPicture = bmp;		
		//Before
		//playerPicture = null;
	}
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	public int getPlayerSeat() {
		return playerSeat;
	}
	public void setPlayerSeat(int playerSeat) {
		this.playerSeat = playerSeat;
	}
	public int getPlayerStatus() {
		return playerStatus;
	}
	public void setPlayerStatus(int playerStatus) {
		this.playerStatus = playerStatus;
	}
	public Bitmap getPlayerPicture() {
		return playerPicture;
	}
	public void setPlayerPicture(Bitmap playerPicture) {
		this.playerPicture = playerPicture;
	}
	
}
