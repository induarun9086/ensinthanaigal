package com.ensinthanaigal.data;

import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Credentials {
	@PrimaryKey
	private Long credentialsID;

	private String type;

	private String parameterName;

	private String parameterValue;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public Long getCredentialsID() {
		return credentialsID;
	}

	public void setCredentialsID(Long credentialsID) {
		this.credentialsID = credentialsID;
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String paramenterValue) {
		this.parameterValue = paramenterValue;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
