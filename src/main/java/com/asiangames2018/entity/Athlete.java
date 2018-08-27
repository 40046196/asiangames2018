package com.asiangames2018.entity;

import java.util.Collection;

import java.sql.Blob;

/**
 * The entity for athlete data
 * @author lion
 *
 */
public class Athlete {
	public Athlete() {
		
	}
	
	public Athlete(String athleteId,String athleteName, String familyName, String birthdate, String countryId,
			String sportId, int height, int weight) {
		this.athleteId = athleteId;
		this.athleteName = athleteName;
		this.familyName = familyName;
		this.birthdate = birthdate;
		this.countryId = countryId;
		this.sportId = sportId;
		this.height = height;
		this.weight = weight;
		
	}
	
	public Athlete(String athleteId, String athleteName, String familyName, String birthdate, String countryId,
			String sportId, int height, int weight, String beginning, String coach, String memorable, String influence,
			String nickname, String language, String hobbies, Blob photo) {
		this.athleteId = athleteId;
		this.athleteName = athleteName;
		this.familyName = familyName;
		this.birthdate = birthdate;
		this.countryId = countryId;
		this.sportId = sportId;
		this.height = height;
		this.weight = weight;
		this.beginning = beginning;
		this.coach = coach;
		this.memorable = memorable;
		this.influence = influence;
		this.nickname = nickname;
		this.language = language;
		this.hobbies = hobbies;
		this.photo = photo;
	}
	public String getAthleteId() {
		return athleteId;
	}
	public void setAthleteId(String athleteId) {
		this.athleteId = athleteId;
	}
	public String getAthleteName() {
		return athleteName;
	}
	public void setAthleteName(String athleteName) {
		this.athleteName = athleteName;
	}
	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getSportId() {
		return sportId;
	}
	public void setSportId(String sportId) {
		this.sportId = sportId;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getBeginning() {
		return beginning;
	}
	public void setBeginning(String beginning) {
		this.beginning = beginning;
	}
	public String getCoach() {
		return coach;
	}
	public void setCoach(String coach) {
		this.coach = coach;
	}
	public String getMemorable() {
		return memorable;
	}
	public void setMemorable(String memorable) {
		this.memorable = memorable;
	}
	public String getInfluence() {
		return influence;
	}
	public void setInfluence(String influence) {
		this.influence = influence;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getHobbies() {
		return hobbies;
	}
	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}
	public Blob getPhoto() {
		return photo;
	}
	public void setPhoto(Blob photo) {
		this.photo = photo;
	}
	public Collection getSocial() {
		return social;
	}
	public void setSocial(Collection social) {
		this.social = social;
	}
	public Collection getHighlight() {
		return highlight;
	}
	public void setHighlight(Collection highlight) {
		this.highlight = highlight;
	}	
	
	@Override
	public String toString() {
		return "Athlete [athleteId=" + athleteId + ", athleteName=" + athleteName + ", familyName=" + familyName
				+ ", birthdate=" + birthdate + ", countryId=" + countryId + ", sportId=" + sportId + ", height="
				+ height + ", weight=" + weight + ", beginning=" + beginning + ", coach=" + coach + ", memorable="
				+ memorable + ", influence=" + influence + ", nickname=" + nickname + ", language=" + language
				+ ", hobbies=" + hobbies + ", photo=" + photo + "]";
	}
	String athleteId;
	String athleteName;
	String familyName;
	String birthdate;
	String countryId;
	String sportId;
	int height;
	int weight;
	// biography part from here downward
	String beginning;
	String coach;
	String memorable;
	String influence;
	String nickname;
	String language;
	String hobbies;
	Blob photo; 

	Collection social;
	Collection highlight;
}
