package com.rampgreen.caretakermobile.model;

public class AlertSettingsDTO {
	private String code;
	private String message;
	private SettingsDTO alerts;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public SettingsDTO getAlerts() {
		return alerts;
	}

	public void setAlerts(SettingsDTO alerts) {
		this.alerts = alerts;
	}

}
