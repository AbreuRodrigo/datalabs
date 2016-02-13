package com.landcraft.server.models;

public class JSONObject {
	private String clientToken;
	private int score;
	private int level;
	private String playerName;
	
	public JSONObject(String clientToken, int score, int level, String playerName) {
		super();
		this.clientToken = clientToken;
		this.score = score;
		this.level = level;
		this.playerName = playerName;
	}

	public String getClientToken() {
		return clientToken;
	}

	public void setClientToken(String clientToken) {
		this.clientToken = clientToken;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
}
