package com.rampgreen.caretakermobile.socket;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.rampgreen.caretakermobile.R;

/***********************************************************
* Router Service
* Router service is created to run in the background 
* after the activity GUI is finished that way the user can
* do other things while the router is still running
* the RT
* 
* @author 	Camilo Tejeiro for	RTNeuro�
* 			,=,e
* 
************************************************************/
public class RouterService extends Service
{
	// notification manager to be able to add a notification noting that the Router service is running
	private NotificationManager myNotificationManager;
	// the notification id to identify and update on the notification bar
	private int NOTIFICATION_ID = 10;
	
	//********************* Class Properties **********************/
	/* Gather the TAG for this class for debugging purposes */ 
	private static final String TAG = RouterService.class.getSimpleName();
	
	//********************* Class Variables **********************/ 
	/* the spawned thread to take care of connection in a non UI thread*/
	private Thread routerSetupThread;
	/* a reference to the main activity to interact with the UI */
	public static Activity mainActivity;
	
	@Override
	public void onCreate()
	{
		// on creation of this service create a notification manager to inform user
        myNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		//create the router thread make sure to pass the current activity context
//		routerSetupThread = new RouterSetupThread("RouterSetupThread", mainActivity, this);
//		// start the setup thread
//		routerSetupThread.start();
		
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) 
	{
		Log.i(TAG, "RTnRouter Service Started Running");
        // Display a notification about us starting.  We put an icon in the status bar.
        showNotification("RTnRouter Service Running");
        
        // tell the user the service started
        Toast.makeText(this, R.string.rtn_service_started, Toast.LENGTH_LONG).show();
        RTnRouter myRTnRouter = new RTnRouter(mainActivity, this);
		
		// get pre configured instances of every individual router
//		configuredBleRouter = myRTnRouter.getRTnBleRouter();
		RTnInternetRouter configuredInternetRouter = myRTnRouter.getRTnInternetRouter();
		if(!configuredInternetRouter.isConnected()) {
			configuredInternetRouter.connectToRTnCloudServer();
		}
        
		return Service.START_STICKY;
	}
	

	@Override
    public void onDestroy() 
	{
        // Cancel the persistent notification.
        myNotificationManager.cancel(NOTIFICATION_ID);
        
        // Tell the user we stopped.
        Toast.makeText(this, R.string.rtn_service_stopped, Toast.LENGTH_LONG).show();
    }

	@Override
	public IBinder onBind( Intent arg0 )
	{
		return null;
	}
	

	
	private void showNotification(String notificationText)
	{
//		// setup a new notification and its properties
//		Builder notificationBuilder = new Builder(this);
//		notificationBuilder.setSmallIcon( R.drawable.ic_launcher );
//		notificationBuilder.setContentTitle("RTnRouter Service Notification");
//		notificationBuilder.setContentText(notificationText);
//		
//		// display the notification in the notificationn bar
//		myNotificationManager.notify( NOTIFICATION_ID, notificationBuilder.build() );
		
	}

}
