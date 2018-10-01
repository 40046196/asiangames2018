package com.asiangames2018.entity;

/**
 * An athlete can have award achievement (prestation) This class will list an
 * athlete achievement
 * 
 * @author lion
 *
 */
public class AthleteHighlight {

    public AthleteHighlight(String athleteId, String eventName, String rank, String sportCategory, String year,
	    String location, String bestScoreTime) {
	this.athleteId = athleteId;
	this.eventName = eventName;
	this.rank = rank;
	this.sportCategory = sportCategory;
	this.year = year;
	this.location = location;
	this.bestScoreTime = bestScoreTime;
    }

    public String getAthleteId() {
	return athleteId;
    }

    public void setAthleteId(String athleteId) {
	this.athleteId = athleteId;
    }

    public String getEventName() {
	return eventName;
    }

    public void setEventName(String eventName) {
	this.eventName = eventName;
    }

    public String getRank() {
	return rank;
    }

    public void setRank(String rank) {
	this.rank = rank;
    }

    public String getSportCategory() {
	return sportCategory;
    }

    public void setSportCategory(String sportCategory) {
	this.sportCategory = sportCategory;
    }

    public String getYear() {
	return year;
    }

    public void setYear(String year) {
	this.year = year;
    }

    public String getLocation() {
	return location;
    }

    public void setLocation(String location) {
	this.location = location;
    }

    /**
     * @return the bestScoreTime
     */
    public String getBestScoreTime() {
	return bestScoreTime;
    }

    /**
     * @param bestScoreTime
     *            the bestScoreTime to set
     */
    public void setBestScoreTime(String bestScoreTime) {
	this.bestScoreTime = bestScoreTime;
    }

    @Override
    public String toString() {
	return "AthleteHighlight [athleteId=" + athleteId + ", eventName=" + eventName + ", rank=" + rank + ", event="
		+ sportCategory + ", year=" + year + ", location=" + location + ", bestScoreTime=" + bestScoreTime
		+ "]";
    }

    String athleteId;
    String eventName;
    String rank;
    String sportCategory;
    String year;
    String location;
    String bestScoreTime;

}
