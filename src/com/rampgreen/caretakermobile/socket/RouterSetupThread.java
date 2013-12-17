package com.rampgreen.caretakermobile.socket;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;

/***********************************************************
* RouterSetupThread
* Router Setup Thread
* This Thread actually is used to run the router configuring
* and connection routines in the background
* 
* @author 	Camilo Tejeiro for	RTNeuro�
* 			,=,e
************************************************************/
public class RouterSetupThread extends Thread
{
	//********************* Class Properties **********************/
	/* Gather the TAG for this class for debugging purposes */ 
	private static final String TAG = RouterSetupThread.class.getSimpleName();
	
	//********************* Class Variables **********************/ 
	/* a variable to hold a reference of the service */
	private Service routerService;
	/* declare a RTneuro router object*/
	private RTnRouter myRTnRouter;
	
	/* variables to get a handle of all configured routers */ 
//	private RTnBleRouter configuredBleRouter;
	private RTnInternetRouter configuredInternetRouter; 
	
	/* variable to keep a reference of the main activity */
	private Activity mainActivity;
	
    /************************************************************
     * RouterSetupThread (Constructor)
     * Router Setup Thread 
     * The constructor for this thread class
     * @param threadName		The name of the current thread
     * @param activity		The Context of the main activity
     * @param service 		a handler to the router service
     ******************************************************************/
	public RouterSetupThread( String threadName, Activity activity, Service service)
	{
		super(threadName);
		// just get a handle of the router service instance
		routerService = service;
		
		// just a handle of the main activity
		mainActivity = activity;
	}
	
    public void run()
    {
		// so that this thread can create children
		Looper.prepare();
		
		// initialize the gateway router
		myRTnRouter = new RTnRouter(mainActivity, routerService);
		
		// get pre configured instances of every individual router
//		configuredBleRouter = myRTnRouter.getRTnBleRouter();
		configuredInternetRouter = myRTnRouter.getRTnInternetRouter();
		
		// make sure the initial ble and internet router setup did not encounter errors
		if (configuredInternetRouter.getInternetErrorStatus())// ||configuredBleRouter.getBleErrorStatus() == true 
		{
			terminateRouterService();
			return;
		}
		
		//attempt to connect to RTnCloudServer
		configuredInternetRouter.connectToRTnCloudServer();
		
		Log.i(TAG, "sleep while trying to connect to cloud server");
		
		// wait while trying to connect to the cloud
		while (configuredInternetRouter.isConnected() != true)
		{
			if (configuredInternetRouter.getInternetErrorStatus() == true)
			{
				// make sure we get out if there is an error
				return;	
			}
			else
			{
				try
				{
					Thread.sleep(1000);
				}
				catch ( InterruptedException e )
				{}
			}
		}
		
		// make sure we did not find an error while trying to connect to the cloud
		if (configuredInternetRouter.getInternetErrorStatus() == true)
		{
			terminateRouterService();
			return;
		}
		
		// start scanning for ble devices
//		configuredBleRouter.scanRTnBleDevices();
		
		Log.i(TAG, "sleep while ble scanning is ongoing");
		
		// wait until scanning is complete
//		while (configuredBleRouter.isScanning())
//		{
//			if (configuredBleRouter.getBleErrorStatus() == true)
//			{
//				//make sure we get out if there is an error
//				return;
//			}
//			else
//			{
//				try
//				{
//					Thread.sleep(1000);
//				}
//				catch ( InterruptedException e )
//				{}
//			}
//		}
		
		// check to see if there was an error while on scanning
//		if (configuredBleRouter.getBleErrorStatus() == true)
//		{
//			terminateRouterService();
//			return;
//		}
		
		//connect to the remote RTneuro ble device
//		configuredBleRouter.connectToRTnBleDevice();
		
		Log.i(TAG, "sleep while trying to connect to ble device");
		
		// wait until we connect
//		while (configuredBleRouter.isConnected() != true)
//		{
//			if (configuredBleRouter.getBleErrorStatus() == true)
//			{
//				return;	
//			}
//			else
//			{
//				try
//				{
//					Thread.sleep(1000);
//				}
//				catch ( InterruptedException e )
//				{}
//			}
//		}
		
		// make sure we did not found an error while connecting to the device
//		if (configuredBleRouter.getBleErrorStatus() == true)
//		{
//			terminateRouterService();
//			return;
//		}
		
		// dicover the services supported by the RTnDevice
//		configuredBleRouter.discoverRTnServices();
		
//		Log.i(TAG, "sleep while trying ble discovery is ongoing");
//		
//		// wait for discovery
//		while (configuredBleRouter.isDiscoveringServices() == true)
//		{
//			if (configuredBleRouter.getBleErrorStatus() == true)
//			{
//				return;
//			}
//			else
//			{
//				try
//				{
//					Thread.sleep(1000);
//				}
//				catch ( InterruptedException e )
//				{}
//			}
//		}
		
		// make sure we did not find an error while on discovery
//		if (configuredBleRouter.getBleErrorStatus() == true)
//		{
//			terminateRouterService();
//			return;
//		}
//		
//		Log.i(TAG, "start the RTnRouter streaming service");
//		// start the RTneuro ble service
//		configuredBleRouter.startRTnRouterService();

    }
    
    /************************************************************
     * terminateRouterService
     * terminate Router Service
     * This method is called when we find setup errors and we want
     * to terminate the router service gracefully
     ******************************************************************/
	public void terminateRouterService()
    {
		// send an intent to stop the router service
    	Intent intent = new Intent();
    	intent.setClass(routerService, RouterService.class); 
    	routerService.stopService(intent);
    }
}
