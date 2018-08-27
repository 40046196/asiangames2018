package com.asiangames2018.entity;

import java.sql.Blob;

/**
 * Sport entity
 * @author lion
 *
 */
public class Sport {
	
	public Sport(String sportId, String sportName, Blob sportIcon, Blob sportImage) {
		this.sportId = sportId;
		this.sportName = sportName;
		this.sportIcon = sportIcon;
		this.sportImage = sportImage;
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
	public Blob getSportIcon() {
		return sportIcon;
	}
	public void setSportIcon(Blob sportIcon) {
		this.sportIcon = sportIcon;
	}
	public Blob getSportImage() {
		return sportImage;
	}
	public void setSportImage(Blob sportImage) {
		this.sportImage = sportImage;
	}
	
	
	@Override
	public String toString() {
		return "Sport [sportId=" + sportId + ", sportName=" + sportName + "]";
	}
	private String sportId;
	private String sportName;
	private Blob sportIcon;
	private Blob sportImage;


}
