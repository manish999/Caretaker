package com.rampgreen.caretakermobile.model;

/**
 * It is used to control all the bean of the application, every bean's instance is controlled by bean controller
 * 
 * @author Manish Pathak
 *
 */
public class BeanController 
{
	static LoginBean loginBean=null;
	static User userListBean=null;
	static TextDisplaySettings settingBean=null;
	
	
	public static LoginBean getLoginBean()
	{	
		if(loginBean == null)
		{
			loginBean = new LoginBean();
		}
		return loginBean;
	}
	
	public static User getUserBean()
	{	
		if(userListBean == null)
		{
			userListBean = new User();
		}
		return userListBean;
	}
	
	public static TextDisplaySettings getTextDisplaySettings()
	{	
		if(settingBean == null)
		{
			settingBean = new TextDisplaySettings();
		}
		return settingBean;
	}
}

