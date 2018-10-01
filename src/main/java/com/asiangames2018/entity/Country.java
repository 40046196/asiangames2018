package com.asiangames2018.entity;

import java.sql.Blob;

/**
 * The entity for country participant Asian Games 2018
 * 
 * @author lion
 *
 */
public class Country {
    public Country() {

    }

    public Country(String countryId, String countryName) {
	this.countryId = countryId;
	this.countryName = countryName;
    }

    public Country(String countryId, String countryName, Blob countryFlag) {
	this.countryId = countryId;
	this.countryName = countryName;
	this.countryFlag = countryFlag;
    }

    public String getCountryId() {
	return countryId;
    }

    public void setCountryId(String countryId) {
	this.countryId = countryId;
    }

    public String getCountryName() {
	return countryName;
    }

    public void setCountryName(String countryName) {
	this.countryName = countryName;
    }

    public Blob getCountryFlag() {
	return countryFlag;
    }

    public void setCountryFlag(Blob countryFlag) {
	this.countryFlag = countryFlag;
    }

    public int getTotalAhtletes() {
	return totalAhtletes;
    }

    public void setTotalAhtletes(int totalAhtletes) {
	this.totalAhtletes = totalAhtletes;
    }

    @Override
    public String toString() {
	return "Country [countryId=" + countryId + ", countryName=" + countryName + ", countryFlag=" + countryFlag
		+ ", totalAhtletes=" + totalAhtletes + "]";
    }

    private String countryId;
    private String countryName;
    private Blob countryFlag;
    private int totalAhtletes;
}
