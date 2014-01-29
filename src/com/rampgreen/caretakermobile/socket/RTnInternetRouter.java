//package com.rampgreen.caretakermobile.socket;
//import android.app.Activity;
//import android.app.Service;
//import android.content.Context;
//import android.net.NetworkInfo;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.rampgreen.caretakermobile.R;
//import com.rampgreen.caretakermobile.socket.model.GsonUtil;
//import com.rampgreen.caretakermobile.ui.util.WidgetUtil;
//import com.rampgreen.caretakermobile.util.AppLog;
//import com.rampgreen.caretakermobile.util.AppSettings;
//import com.rampgreen.caretakermobile.util.StringUtils;
//
//import de.tavendo.autobahn.WebSocketConnection;
//import de.tavendo.autobahn.WebSocketConnectionHandler;
//import de.tavendo.autobahn.WebSocketException;
//
///***********************************************************
// * RTnInternetRouter
// * RTneuro Internet Router
// * the Internet router handles all of the needed functionality
// * a hardware Internet router would have
// * 
// * @author 	Camilo Tejeiro for	RTNeuro�
// * 			,=,e
// ************************************************************/
//public class RTnInternetRouter
//{
//	/********************* Class Properties **********************/
//	/* Gather the TAG for this class for debugging purposes */ 
//	private static final String TAG = RTnInternetRouter.class.getSimpleName();
//
//	/************************** Class Defines ******************************/
//	/* Sets the server IP address and port that we will connect to */
//	private static final String WS_BASE_URI = "ws://121.240.116.173:8080/SensorSoc/cloud/mobile";
//	private static String wsUri = WS_BASE_URI;
//	/************************* Class Variables *******************************/
//	/* WebsocketConnection object to handle all supported tx/rx functionality */
//	private static WebSocketConnection myConnection = null;
//
//	/* variable to hold a reference of the main context */
//	private Context routerService;
//	/* Hold the instance of the RTnRouter to communicate updates when needed*/
//	RTnRouter myRtnRouterInstance;
//	/* variable used to keep track of the connection state with the cloud server */
//	private static boolean connectedState = false;
//	/* Network information object used to retrieve connection state */
//	private NetworkInfo netInfo;
//	/* variable used to hold a reference of the main activity */
//	private Activity mainActivity;
//	/* flag used to indicate whether we found an error with the internet router setup routine */
//	private boolean internetRouterErrorThrown = false;
//
//
//	/***********************************************************************
//	 * RTnInternetRouter (Constructor)
//	 * RTneuro Internet Router Constructor 
//	 * Here is where we actually configure the Internet adapter to its 
//	 * default values
//	 * @param activity		The Context of the main activity
//	 * @param service		a handle to the routerService
//	 * @param RTnRouterInstance The instance of the RTnRouter calling class
//	 ***********************************************************************/
//	public RTnInternetRouter( Activity activity, Service service, RTnRouter RTnRouterInstance )
//	{
//		// hold a reference to the routerService
//		routerService = service;
//
//		// hold a reference of the RTnRouter instance
//		myRtnRouterInstance = RTnRouterInstance;
//
//		// a handle to the main activity 
//		mainActivity = activity;
//
//		// if there is no internet access tell the user to connect  and terminate the app
//		if (! WidgetUtil.checkInternetConnection(routerService)) 
//		{
//			// inform the user to enable wifi or cellular to connect to the internet
//			showToastMessage(R.string.internet_disabled_no_access);
//			internetRouterErrorThrown = true;
//			return;
//
//		}
//
//		/* Initializes function to call for a WebSocket Connection */
//		myConnection = new WebSocketConnection();
//
//	}
//
//	/************************************************************
//	 * connectToRTnCloudServer
//	 * connect to RTneuro cloud server
//	 * This method gets called to connect to the remote server
//	 ******************************************************************/
//	public void connectToRTnCloudServer()
//	{
//
//		if(! WidgetUtil.checkInternetConnection(routerService)) {
//			// inform the user to enable wifi or cellular to connect to the internet
//			WidgetUtil.showSettingDialog(routerService);
//			showToastMessage(R.string.internet_disabled_no_access);
//			internetRouterErrorThrown = true;
//			return;
//		}
//
//		// if not connected, try to connect it .  
//		if (! isConnected()) {
//			String C1 = (String) AppSettings.getPrefernce(routerService, null, AppSettings.FIRST_LEFT_HEXDIGIT, "");
//			String C2 =	(String) AppSettings.getPrefernce(routerService, null, AppSettings.FIRST_RIGHT_HEXDIGIT, "");
//			String urId =	(String) AppSettings.getPrefernce(routerService, null, AppSettings.DEVICE_ID, "");
//			wsUri = WS_BASE_URI + "?deviceId="+urId+C1+C2;// append device Id to url 
//			connectToSocketCloud();
//		}
//	}
//
//	private void connectToSocketCloud() 
//	{
//		try
//		{
//			myConnection.connect( wsUri, 				
//					new WebSocketConnectionHandler()
//			{
//				@Override
//				// called when connected
//				public void onOpen()
//				{
//					AppLog.e( "Connected to " + wsUri);
//					sendDeviceID();
//					// get mac address send to cloud server
//					//					String macAddress = WidgetUtil.getMacAddress(routerService);
//					//					String macId = GsonUtil.createMacJsonString(macAddress);
//					//					if(macAddress != null) {
//					//						myRtnRouterInstance.internetTransmit(macId);
//					//						Log.e(TAG, "transmitting the macaddress to cloud" + macAddress);
//					//					}
//					connectedState = true;
//				}
//
//
//				@Override
//				// called upon an incoming byte[]  packet
//				public void onBinaryMessage( byte [] receivedPacket )
//				{
//					AppLog.e("Received Cloud Packet");
//					//					myRtnRouterInstance.internetRxCallback( receivedPacket );
//				}
//				@Override
//				// called upon an incoming String message
//				public void onTextMessage( String payload )
//				{
//					Log.e(TAG, "on Text message from the cloud"+payload);
//					if(RTnInternetRouter.onMessageCallback != null) {
//						RTnInternetRouter.onMessageCallback.receiveData(payload);
//					}
//				}
//				@Override
//				// called when disconnected or the connection is closed
//				public void onClose( int code, String reason )
//				{
//					AppLog.e("Connection lost."+reason);
////					showToastMessage(R.string.rtn_internet_error);
////					internetRouterErrorThrown = true;
//					connectedState = false; 
//				}
//			});
//		}
//		catch ( WebSocketException e)
//		{
//			AppLog.e(e.toString());
////			showToastMessage(R.string.rtn_internet_error);
////			internetRouterErrorThrown = true;
//			return;
//		}
//	}
//
//	/************************************************************
//	 * sendToRTnCloudServer
//	 * send to remote device
//	 * This method is called when you need to send a packet to the remote
//	 * cloud server device
//	 * @return boolean	returns true on success transmitting,
//	 * 					false otherwise
//	 ******************************************************************/
//	public boolean sendToRTnCloudServer(byte[] byteToTransmit)
//	{
//		AppLog.d(TAG, "Transmitted Packet to Cloud");
//		myConnection.sendBinaryMessage( byteToTransmit );
//		return true;
//	}
//
//	public static boolean sendToRTnCloudServer(String json)
//	{
//		if(myConnection == null)
//			AppLog.d(TAG, "myconnection == null , Transmitted json to Cloud"+json);
//		AppLog.d(TAG, "Transmitted json to Cloud"+json);
//
////		if(! myConnection.isConnected()) {
////			myConnection.disconnect();
////			
////		}
//		
//		if(myConnection != null && myConnection.isConnected()) {
//			myConnection.sendTextMessage( json );
//			return true;
//		}
//		return false;
//	}
//
//
//	/************************************************************
//	 * isConnected
//	 * is connected to the remote cloud server? 
//	 * @return boolean	returns true if connected, false otherwise
//	 ******************************************************************/
//	public boolean isConnected()
//	{
//		return connectedState ;
//
//	}
//
//	/************************************************************
//	 * showToastMEssage
//	 * this method generates a new runnable to send a message to the
//	 * UI thread for display purposes
//	 * @param messageId		the id of the message to get from the string
//	 * 						resources to display as a toast	
//	 ******************************************************************/
//	public void showToastMessage(final int messageId)
//	{
//		if(mainActivity != null) {
//			mainActivity.runOnUiThread( new Runnable()
//			{
//
//				@Override
//				public void run()
//				{
//					Toast.makeText( mainActivity, messageId, Toast.LENGTH_LONG ).show();
//				}
//			} );
//		}
//	}
//
//	/************************************************************
//	 * getInternetErrorStatus
//	 * get Internet Error Status
//	 * this method returns the value of the error status flag which indicates
//	 * whether there was an error seting up the internet router
//	 * @return boolean		the flag indicating the status of the Internet
//	 * 						router while being setup
//	 ******************************************************************/
//	public boolean getInternetErrorStatus()
//	{
//		return internetRouterErrorThrown;
//	}
//
//	private void parseDeviceIDIfPresent(String payload)
//	{
//		//		try {
//		//			Gson gson = new Gson();
//		//			DeviceIdMacDTO deviceDTO = gson.fromJson(payload, DeviceIdMacDTO.class);
//		//			String deviceId = deviceDTO.getDevice_id();
//		//			if(StringUtils.notEmpty(deviceId)) {
//		//				AppSettings.setPreference(routerService, null, AppSettings.DEVICE_ID, deviceId);
//		//			} else {
//		//				// device id is null
//		//			}
//		//		} catch (JsonSyntaxException e) {
//		//		}
//	}
//
//	public void sendDeviceID()
//	{
//		//C=C1C2 , 2 last digit of MAC id of the Health device
//		String C1 = (String) AppSettings.getPrefernce(routerService, null, AppSettings.FIRST_LEFT_HEXDIGIT, "");
//		String C2 =	(String) AppSettings.getPrefernce(routerService, null, AppSettings.FIRST_RIGHT_HEXDIGIT, "");
//		String deviceID =	(String) AppSettings.getPrefernce(routerService, null, AppSettings.DEVICE_ID, "");
//		//		AppLog.d(AppLog.APP_TAG, "deviceID with out 2 digit: " + deviceID);
//		//		deviceID = deviceID+C1+C2;
//		AppLog.d(AppLog.APP_TAG, "deviceID : " + deviceID);
//		String macID = WidgetUtil.getMacAddress(routerService);
//		String jsonString = GsonUtil.createDeviceIdJsonString(macID, deviceID, C1+C2);
//
//		sendToRTnCloudServer(jsonString);
//	}
//
//	/*********************** interface for callback*****************************/
//	static OnMessageCallback onMessageCallback;
//	public static void setOnMsgCallback(OnMessageCallback onMessageCallback)
//	{
//		RTnInternetRouter.onMessageCallback = onMessageCallback;
//	}
//	public static interface OnMessageCallback {
//		public void receiveData(String jsonString);
//	}
//	/**************************************************************************/
//	
//	public static boolean isSocketConnected() {
//		if(myConnection == null) {
//			return false;
//		}
//		return myConnection.isConnected();
//	}
//}
