package com.rampgreen.caretakermobile.model;

import java.util.ArrayList;

public class ListHolder 
{

	static ArrayList<TextDisplaySettings> textDisplaySettings;

	public static ArrayList<TextDisplaySettings> getTextDisplaySettingList()
	{	
		if(textDisplaySettings == null)
		{
			textDisplaySettings = new ArrayList<TextDisplaySettings>();
		}
		return textDisplaySettings;
	}
}
