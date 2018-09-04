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
	public Athlete(String athleteId, String athleteName, String familyName, String birthdate, String birthCity,
			String birthCountry, String countryId, String sportId, int height, int weight, Blob photo) {
		this.athleteId = athleteId;
		this.athleteName = athleteName;
		this.familyName = familyName;
		this.birthdate = birthdate;
		this.birthCity = birthCity;
		this.birthCountry = birthCountry;
		this.countryId = countryId;
		this.sportId = sportId;
		this.height = height;
		this.weight = weight;
		this.photo = photo;
	}
	/**
	 * @return the athleteId
	 */
	public String getAthleteId() {
		return athleteId;
	}
	/**
	 * @param athleteId the athleteId to set
	 */
	public void setAthleteId(String athleteId) {
		this.athleteId = athleteId;
	}
	/**
	 * @return the athleteName
	 */
	public String getAthleteName() {
		return athleteName;
	}
	/**
	 * @param athleteName the athleteName to set
	 */
	public void setAthleteName(String athleteName) {
		this.athleteName = athleteName;
	}
	/**
	 * @return the familyName
	 */
	public String getFamilyName() {
		return familyName;
	}
	/**
	 * @param familyName the familyName to set
	 */
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	/**
	 * @return the birthdate
	 */
	public String getBirthdate() {
		return birthdate;
	}
	/**
	 * @param birthdate the birthdate to set
	 */
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	/**
	 * @return the birthCity
	 */
	public String getBirthCity() {
		return birthCity;
	}
	/**
	 * @param birthCity the birthCity to set
	 */
	public void setBirthCity(String birthCity) {
		this.birthCity = birthCity;
	}
	/**
	 * @return the birthCountry
	 */
	public String getBirthCountry() {
		return birthCountry;
	}
	/**
	 * @param birthCountry the birthCountry to set
	 */
	public void setBirthCountry(String birthCountry) {
		this.birthCountry = birthCountry;
	}
	/**
	 * @return the countryId
	 */
	public String getCountryId() {
		return countryId;
	}
	/**
	 * @param countryId the countryId to set
	 */
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	/**
	 * @return the sportId
	 */
	public String getSportId() {
		return sportId;
	}
	/**
	 * @param sportId the sportId to set
	 */
	public void setSportId(String sportId) {
		this.sportId = sportId;
	}
	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
	/**
	 * @return the photo
	 */
	public Blob getPhoto() {
		return photo;
	}
	/**
	 * @param photo the photo to set
	 */
	public void setPhoto(Blob photo) {
		this.photo = photo;
	}
	/**
	 * @return the medals
	 */
	public TotalMedals getMedals() {
		return medals;
	}
	/**
	 * @param medals the medals to set
	 */
	public void setMedals(TotalMedals medals) {
		this.medals = medals;
	}
	/**
	 * @return the bio
	 */
	public AthleteBiography getBio() {
		return bio;
	}
	/**
	 * @param bio the bio to set
	 */
	public void setBio(AthleteBiography bio) {
		this.bio = bio;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Athlete [athleteId=" + athleteId + ", athleteName=" + athleteName + ", familyName=" + familyName
				+ ", birthdate=" + birthdate + ", birthCity=" + birthCity + ", birthCountry=" + birthCountry
				+ ", countryId=" + countryId + ", sportId=" + sportId + ", height=" + height + ", weight=" + weight
				+ ", photo=" + photo + ", medals=" + medals + ", bio=" + bio + "]";
	}
	String athleteId;
	String athleteName;
	String familyName;
	String birthdate;
	String birthCity;
	String birthCountry;
	String countryId;
	String sportId;
	int height; // in cm
	int weight;  // in cm
	Blob photo; 
	TotalMedals medals;
	AthleteBiography bio;
}
