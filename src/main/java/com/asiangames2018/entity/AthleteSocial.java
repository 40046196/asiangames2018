package com.asiangames2018.entity;

/**
 * Entity for athlete Social Media such as twitter, instagram, facebook, weiboo,wechat, etc
 * @author lion
 *
 */
public class AthleteSocial {
	public AthleteSocial(String athleteId, String socialAccount) {
		this.athleteId = athleteId;
		this.socialAccount = socialAccount;
	}
	public String getAthleteId() {
		return athleteId;
	}
	public void setAthleteId(String athleteId) {
		this.athleteId = athleteId;
	}
	public String getSocialAccount() {
		return socialAccount;
	}
	public void setSocialAccount(String socialAccount) {
		this.socialAccount = socialAccount;
	}
	@Override
	public String toString() {
		return "AthleteSocial [athleteId=" + athleteId + ", socialAccount=" + socialAccount + "]";
	}
	String athleteId;
	String socialAccount;
}
