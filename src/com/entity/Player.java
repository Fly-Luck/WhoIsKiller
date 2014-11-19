package com.entity;

/**
 * Entity class of player variables
 * @author Luck
 *
 */
public class Player {
	//identities of the player
	public final static int ID_JUDGE = 0;
	public final static int ID_CIVIL = 1;
	public final static int ID_KILLER = 2;
	public final static int ID_POLICE = 3;
	private int playerId;
	private int playerSeat;
	private String playerPic;
	private int playerStatus;
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
	public String getPlayerPic() {
		return playerPic;
	}
	public void setPlayerPic(String playerPic) {
		this.playerPic = playerPic;
	}
	public int getPlayerStatus() {
		return playerStatus;
	}
	public void setPlayerStatus(int playerStatus) {
		this.playerStatus = playerStatus;
	}
	
}
