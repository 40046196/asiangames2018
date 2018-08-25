package com.asiangames2018.entity;

/**
 * Sport entity
 * @author lion
 *
 */
public class Sport {
	
	public Sport(String sportId, String sportName) {
		this.sportId = sportId;
		this.sportName = sportName;
	}
	public String getSportId() {
		return sportId;
	}
	public void setSportId(String sportId) {
		this.sportId = sportId;
	}
	public String getSportName() {
		return sportName;
	}
	public void setSportName(String sportName) {
		this.sportName = sportName;
	}
	@Override
	public String toString() {
		return "Sport [sportId=" + sportId + ", sportName=" + sportName + "]";
	}
	private String sportId;
	private String sportName;

}
