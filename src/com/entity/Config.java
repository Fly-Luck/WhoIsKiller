package com.entity;

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
	//cache map to store image
	public static HashMap<String, Object> imgCache;
	public int getTotalPlayers() {
		return totalPlayers;
	}
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
		currentDay = 1;
		currentStatus = G_STAT_INIT;
		totalPlayers = 0;
		totalKillers = 0;
		totalCivils = 0;
		totalPolices = 0;
		killersLeft = 0;
		civilsLeft = 0;
		policesLeft = 0;
		imgCache = new HashMap<String, Object>();
	}
	/**
	 * Singleton instance getter
	 * @return
	 */
	public static Config getInstance(){
		return ConfigHolder.INSTANCE;
	}
	public boolean hasPlayerIn(String name){
		return imgCache.containsKey(name);
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
