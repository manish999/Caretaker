package com.rampgreen.caretakermobile.model;

import java.util.ArrayList;

public class ListHolder 
{

	static ArrayList<TextDisplaySettings> textDisplaySettings;
	static ArrayList<VisualDisplaySettings> textVisualSettings;

	public static ArrayList<TextDisplaySettings> getTextDisplaySettingList()
	{	
		if(textDisplaySettings == null)
		{
			textDisplaySettings = new ArrayList<TextDisplaySettings>();
		}
		return textDisplaySettings;
	}
	
	public static ArrayList<VisualDisplaySettings> getVisualDisplaySettingsList()
	{	
		if(textVisualSettings == null)
		{
			textVisualSettings = new ArrayList<VisualDisplaySettings>();
		}
		return textVisualSettings;
	}
}
