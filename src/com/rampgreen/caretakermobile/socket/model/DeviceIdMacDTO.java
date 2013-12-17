package com.rampgreen.caretakermobile.socket.model;
public class DeviceIdMacDTO {
	private String device_id;
	private String mac_address;
	private String mrid_last_two;// last two digit of mac ID of wristBand
	private String queryType;

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public String getMac_address() {
		return mac_address;
	}

	public void setMac_address(String mac_address) {
		this.mac_address = mac_address;
	}

	public String getQueryType()
	{
		return queryType;
	}

	public void setQueryType(String queryType)
	{
		this.queryType = queryType;
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