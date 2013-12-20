package com.rampgreen.caretakermobile.socket.model;

import com.rampgreen.caretakermobile.util.StringUtils;

public class StatusData {
	private String device_id;
	private String deviceState = "0";
	private String sensorState = "0";
	private String transducerState = "0";
	private String batteryState = "0";
	private String batteryPercentage = "0";
	private String tts = "0";
	private String trs = "0";
	private int packetNumber = 0;
	private String queryType;
	private String mrid_last_two;// last two digit of mac ID of wristBand

	public String getDeviceState() {
		return deviceState;
	}

	public void setDeviceState(String deviceState) {
		this.deviceState = deviceState;
		this.deviceState = StringUtils.cutString(this.deviceState, 12);
	}

	public String getSensorState() {
		return sensorState;
	}

	public void setSensorState(String sensorState) {
		this.sensorState = sensorState;
//		this.sensorState = StringUtils.cutString(this.sensorState, 12);
	}

	public String getTransducerState() {
		return transducerState;
	}

	public void setTransducerState(String transducerState) {
		this.transducerState = transducerState;
	}

	public String getBatteryState() {
		return batteryState;
	}

	public void setBatteryState(String batteryState) {
		this.batteryState = batteryState;
	}

	public String getBatteryPercentage() {
		return batteryPercentage;
	}

	public void setBatteryPercentage(String batteryPercentage) {
		this.batteryPercentage = batteryPercentage+" %";
	}

	public String getTts() {
		return tts;
	}

	public void setTts(String tts) {
		this.tts = tts;
	}

	public String getTrs() {
		return trs;
	}

	public void setTrs(String trs) {
		this.trs = trs;
	}

	public int getPacketNumber() {
		return packetNumber;
	}

	public void setPacketNumber(int packetNumber) {
		this.packetNumber = packetNumber;
	}

	public String getQueryType()
	{
		return queryType;
	}

	public void setQueryType(String queryType)
	{
		this.queryType = queryType;
	}
	
	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public String getMrid_last_two()
	{
		return mrid_last_two;
	}

	public void setMrid_last_two(String mrid_last_two)
	{
		this.mrid_last_two = mrid_last_two;
	}


}