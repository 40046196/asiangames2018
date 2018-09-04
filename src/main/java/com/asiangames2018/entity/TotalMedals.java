package com.asiangames2018.entity;

/**
 * Athlete Total Medals
 * @author lion
 *
 */
public class TotalMedals {
	
	public TotalMedals() {
		this.gold = 0;
		this.silver = 0;
		this.bronze = 0;
	}
	public TotalMedals(String athleteId, int gold, int silver, int bronze) {
		this.athleteId = athleteId;
		this.gold = gold;
		this.silver = silver;
		this.bronze = bronze;
	}
	
	public String getAthleteId() {
		return athleteId;
	}
	public void setAthleteId(String athleteId) {
		this.athleteId = athleteId;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public int getSilver() {
		return silver;
	}
	public void setSilver(int silver) {
		this.silver = silver;
	}
	public int getBronze() {
		return bronze;
	}
	public void setBronze(int bronze) {
		this.bronze = bronze;
	}

	@Override
	public String toString() {
		return "TotalMedals [athleteId=" + athleteId + ", gold=" + gold + ", silver=" + silver + ", bronze=" + bronze
				+ "]";
	}

	private String athleteId;
	private int gold;
	private int silver;
	private int bronze;
}
