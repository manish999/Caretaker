package com.rampgreen.caretakermobile.viewbadger;


public class BadgeTabManager {

private static BadgeTabManager instance;
	
	private BadgeTabWidget widget;
	
	private BadgeTabManager(BadgeTabWidget widget){
		this.widget = widget;
	}
	
	public static void init(BadgeTabWidget widget){
		instance = new BadgeTabManager(widget);
	}
	
	public static BadgeTabManager getInstance(){
		if (instance == null){
			throw new RuntimeException("BadgeTabManager has not been initialized with a BadgeTabWidget!");
		}
		return instance;
	}
	
	public int getBadgeNumAtIndex(int index){
		return widget.getBadgeNumAtIndex(index);
	}
	
	public void setBadgeAtIndex(int num, int index){
		widget.setBadgeAtIndex(num, index);
	}

}
