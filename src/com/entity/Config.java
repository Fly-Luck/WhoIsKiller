package com.entity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Entity class of game configurations
 * Singleton pattern
 * @author Luck
 *
 */
public class Config {
	//player status
	public static final int P_STAT_ALIVE = 0;
	public static final int P_STAT_DEAD = 1;
	//game circle process
	public static final int G_STAT_INIT = -1;
	public static final int G_STAT_NIGHT = 0;
	public static final int G_STAT_KILL = 1;
	public static final int G_STAT_CHECK = 2;
	public static final int G_STAT_DAY = 3;
	public static final int G_STAT_VOTE = 4;
	//game results
	public static final int R_PENDING = 0;
	public static final int R_KILLER_WIN = 1;
	public static final int R_CIVIL_WIN = 2;
	//game limits
	public static final int MIN_PLAYERS = 7;
	public static final int MAX_PLAYERS = 15;
	//configurations
	private int currentDay;
	private int currentStatus;
	private int totalPlayers;
	private int totalKillers;
	private int totalCivils;
	private int totalPolices;
	private int killersLeft;
	private int civilsLeft;
	private int policesLeft;
	@Deprecated
	//cache map to store image
	public static HashMap<String, Object> imgCache;
	@Deprecated
	//cache list to store image
	public static ArrayList<Object> imgList;
	//list of players
	public static ArrayList<Player> playerList;
	/**
	 * Singleton Holder
	 * @author Luck
	 *
	 */
	private static class ConfigHolder{
		public static Config INSTANCE = new Config();
	}
	/**
	 * Singleton internal constructor
	 */
	private Config(){
		playerList = new ArrayList<Player>();
		restart();
		imgCache = new HashMap<String, Object>();
		imgList = new ArrayList<Object>();
	}
	/**
	 * Singleton instance getter
	 * @return
	 */
	public static Config getInstance(){
		return ConfigHolder.INSTANCE;
	}
	@Deprecated
	/**
	 * This is not useful any more for name is no longer identifying a player
	 * @param name
	 * @return
	 */
	public boolean hasPlayerIn(String name){
		return imgCache.containsKey(name);
	}
	/**
	 * Reset configurations
	 */
	public void restart(){
		currentDay = 1;
		currentStatus = G_STAT_INIT;
		totalPlayers = 0;
		totalKillers = 0;
		totalCivils = 0;
		totalPolices = 0;
		killersLeft = 0;
		civilsLeft = 0;
		policesLeft = 0;
		for (Player player: playerList) {
			//respawn all
			player.setPlayerStatus(P_STAT_ALIVE);
			//reset all identities to civil
			player.setPlayerId(Player.ID_CIVIL);
		}
	}
	/**
	 * find Player instance by seat
	 * @param seat
	 * @return
	 */
	public Player findPlayerBySeat(int seat){
		for (Player player : playerList) {
			if(seat == player.getPlayerSeat())
				return player;
		}
		return null;
	}
	/**
	 * Player dies, triggered when currentStatus(G_STAT_KILL -> G_STAT_CHECK)
	 * or currentStatus(G_STAT_VOTE -> G_STAT_NIGHT)
	 * @param position
	 */
	public void playerDie(int position){
		if(position > 0 && position < playerList.size()){
			playerList.get(0).setPlayerStatus(P_STAT_DEAD);
		}
	}
	/**
	 * Get game round result
	 * 1 = killer win, 2 = civil win, 0 = ongoing
	 * @return
	 */
	public int getResult(){
		//killers all dead
		if(killersLeft == 0)
			return R_CIVIL_WIN;
		//killers >= police + civils
		else if(killersLeft > 0 && killersLeft > (policesLeft + civilsLeft))
			return R_KILLER_WIN;
		else return R_PENDING;
	}
	public int getTotalPlayers() {
		return totalPlayers;
	}
	public void setTotalPlayers(int totalPlayers) {
		this.totalPlayers = totalPlayers;
	}
	public int getCurrentDay() {
		return currentDay;
	}
	public void setCurrentDay(int currentDay) {
		this.currentDay = currentDay;
	}
	public int getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(int currentStatus) {
		this.currentStatus = currentStatus;
	}
	public int getTotalKillers() {
		return totalKillers;
	}
	public void setTotalKillers(int totalKillers) {
		this.totalKillers = totalKillers;
	}
	public int getTotalCivils() {
		return totalCivils;
	}
	public void setTotalCivils(int totalCivils) {
		this.totalCivils = totalCivils;
	}
	public int getTotalPolices() {
		return totalPolices;
	}
	public void setTotalPolices(int totalPolices) {
		this.totalPolices = totalPolices;
	}
	public int getKillersLeft() {
		return killersLeft;
	}
	public void setKillersLeft(int killersLeft) {
		this.killersLeft = killersLeft;
	}
	public int getCivilsLeft() {
		return civilsLeft;
	}
	public void setCivilsLeft(int civilsLeft) {
		this.civilsLeft = civilsLeft;
	}
	public int getPolicesLeft() {
		return policesLeft;
	}
	public void setPolicesLeft(int policesLeft) {
		this.policesLeft = policesLeft;
	}
}
