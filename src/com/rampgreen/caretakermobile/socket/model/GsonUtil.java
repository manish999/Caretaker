package com.rampgreen.caretakermobile.socket.model;

import com.google.gson.Gson;

public class GsonUtil
{
//	public static String createMacJsonString(String macAddress) {
//		Gson gson = new Gson();
//		DeviceIdMacDTO dDeviceDTO = new DeviceIdMacDTO();
//		dDeviceDTO.setMac_address(macAddress);
//		String macJsonString = gson.toJson(dDeviceDTO);
//		return macJsonString;
//	}
	
	public static String createDeviceIdJsonString(String macAddress, String deviceID, String mridLastTwo) {
		Gson gson = new Gson();
		DeviceIdMacDTO dDeviceDTO = new DeviceIdMacDTO();
		dDeviceDTO.setDevice_id(deviceID);
		dDeviceDTO.setMrid_last_two(mridLastTwo);
		dDeviceDTO.setMac_address(macAddress);
		dDeviceDTO.setQueryType("1");
		String deviceIDJsonString = gson.toJson(dDeviceDTO);
		return deviceIDJsonString;
	}
	
	public static String createStatusDataJsonString(String deviceID, String mridLastTwo) {
		Gson gson = new Gson();
		StatusData statusData = new StatusData();
		statusData.setDevice_id(deviceID);
		statusData.setMrid_last_two(mridLastTwo);
		statusData.setQueryType("2");
		statusData.setBatteryState("1");
		statusData.setBatteryPercentage("1");
		String jsonString = gson.toJson(statusData);
		return jsonString;
	}
}
