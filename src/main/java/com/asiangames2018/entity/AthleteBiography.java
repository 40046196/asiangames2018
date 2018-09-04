package com.asiangames2018.entity;

import java.util.Collection;

/**
 * Athlete Biography - The biography could include a list of their highlights (prestation)
 *   and social medias
 * @author lion
 *
 */
public class AthleteBiography {
	public AthleteBiography() {
		
	}
	
	public AthleteBiography(String athleteId, String beginning, String debut, String reason, String coach,
			String training, String ambition, String awards, String hero, String memorable, String influence,
			String nickname, String relatives, String injuries, String education, String language, String hobbies,
			String additionalInformation) {
		this.athleteId = athleteId;
		this.beginning = beginning;
		this.debut = debut;
		this.reason = reason;
		this.coach = coach;
		this.training = training;
		this.ambition = ambition;
		this.awards = awards;
		this.hero = hero;
		this.memorable = memorable;
		this.influence = influence;
		this.nickname = nickname;
		this.relatives = relatives;
		this.injuries = injuries;
		this.education = education;
		this.language = language;
		this.hobbies = hobbies;
		this.additionalInformation = additionalInformation;
	}
	
	public String getAthleteId() {
		return athleteId;
	}
	public void setAthleteId(String athleteId) {
		this.athleteId = athleteId;
	}
	public String getBeginning() {
		return beginning;
	}
	public void setBeginning(String beginning) {
		this.beginning = beginning;
	}
	public String getDebut() {
		return debut;
	}
	public void setDebut(String debut) {
		this.debut = debut;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getCoach() {
		return coach;
	}
	public void setCoach(String coach) {
		this.coach = coach;
	}
	public String getTraining() {
		return training;
	}

	public void setTraining(String training) {
		this.training = training;
	}
	public String getAmbition() {
		return ambition;
	}
	public void setAmbition(String ambition) {
		this.ambition = ambition;
	}
	public String getAwards() {
		return awards;
	}
	public void setAwards(String awards) {
		this.awards = awards;
	}
	public String getHero() {
		return hero;
	}
	public void setHero(String hero) {
		this.hero = hero;
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
	public String getRelatives() {
		return relatives;
	}
	public void setRelatives(String relatives) {
		this.relatives = relatives;
	}
	public String getInjuries() {
		return injuries;
	}
	public void setInjuries(String injuries) {
		this.injuries = injuries;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
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
	public Collection getSocialMedia() {
		return socialMedia;
	}
	public void setSocialMedia(Collection socialMedia) {
		this.socialMedia = socialMedia;
	}
	public Collection getHighlight() {
		return highlight;
	}
	public void setHighlight(Collection highlight) {
		this.highlight = highlight;
	}
	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}


	@Override
	public String toString() {
		return "AthleteBiography [athleteId=" + athleteId + ", beginning=" + beginning + ", debut=" + debut
				+ ", reason=" + reason + ", coach=" + coach + ", training=" + training + ", ambition=" + ambition
				+ ", awards=" + awards + ", hero=" + hero + ", memorable=" + memorable + ", influence=" + influence
				+ ", nickname=" + nickname + ", relatives=" + relatives + ", injuries=" + injuries + ", education="
				+ education + ", language=" + language + ", hobbies=" + hobbies + ", additionalInformation="
				+ additionalInformation + ", socialMedia=" + socialMedia + ", highlight=" + highlight + "]";
	}

	private String athleteId;
	private String beginning;
	private String debut;
	private String reason;
	private String coach;
	private String training;
	private String ambition;
	private String awards;
	private String hero;
	private String memorable;
	private String influence;
	private String nickname;
	private String relatives;
	private String injuries;
	private String education;
	private String language;
	private String hobbies;
	private String additionalInformation;
	private Collection socialMedia;
	private Collection highlight;

}
