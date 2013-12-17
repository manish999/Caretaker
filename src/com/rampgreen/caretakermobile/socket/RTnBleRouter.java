package com.rampgreen.caretakermobile.socket;
//package com.rtneuro.routerapp;
//
//import java.util.Timer;
//import java.util.TimerTask;
//import java.util.UUID;
//
//import com.rtneuro.routerapp.R;
//
//import android.app.Activity;
//import android.app.Service;
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothGatt;
//import android.bluetooth.BluetoothGattCallback;
//import android.bluetooth.BluetoothGattCharacteristic;
//import android.bluetooth.BluetoothGattDescriptor;
//import android.bluetooth.BluetoothGattService;
//import android.bluetooth.BluetoothManager;
//import android.bluetooth.BluetoothProfile;
//import android.content.Context;
//import android.util.Log;
//import android.widget.Toast;
//
///***********************************************************
//* RTnBleRouter
//* RTneuro Bluetooth Low Energy Router
//* The Bluetooth low energy class handles the scanning, connection
//* service discovery and custom ble service implementation 
//* 
//* @author 	Camilo Tejeiro for	RTNeuro�
//* 			,=,e
//************************************************************/
//public class RTnBleRouter
//{
//	/********************* Class Properties **********************/
//	/* Gather the TAG for this class for debugging purposes */ 
//	private static final String TAG = RTnBleRouter.class.getSimpleName();
//	
//	/****************************************** Class Defines *********************************************/
//	/* timeout for ble scanning */
//	private static final long SCAN_TIMEOUT = 10000;
//	/* defines for the different connection states */
//    private static final int STATE_DISCONNECTED = 0;
//    private static final int STATE_CONNECTING = 1;
//    private static final int STATE_CONNECTED = 2;
//    
//    
//    /* services and characteristics unique identifiers */
//    private static final UUID RTN_SERVICE_16BIT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");  										
//    private static final UUID RTN_SERVICE_128BIT_UUID = UUID.fromString("569a1101-b87f-490c-92cb-11ba5ea5167c"); 
//    private static final UUID RTN_TX_CHARACTERISTIC_UUID = UUID.fromString("569a2001-b87f-490c-92cb-11ba5ea5167c"); 
//    private static final UUID RTN_RX_CHARACTERISTIC_UUID = UUID.fromString("569a2000-b87f-490c-92cb-11ba5ea5167c"); 
//    private static final UUID CLIENT_CHARACTERISTIC_CONFIG = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
//    
//  	/****************************************** Class Variables *********************************************/
//    /* variable to hold a reference of the main context */
//	private Service routerService;
//	/* Hold the instance of the RTnRouter to communicate ble updates when needed*/
//	RTnRouter myRtnRouterInstance;
//    /* string to hold the remote ble device address */
//    public static String RTnDeviceAdx;
//    /* string to hold the remote ble device name */
//    public static String RTnDeviceName;
//	/* Flag to indicate when the scanning of ble devices is complete */
//	private boolean scanningComplete = false;
//	/* Flag to indicate when the discovering of supported services is complete */
//	private boolean discoveryComplete = false;
//	/* variable used to keep track of the connection state with the ble device */
//	private int myConnectionState = STATE_DISCONNECTED;
//	
//    /* Bluetooth adapter to get a hold of the ble radio properties*/
//	private BluetoothAdapter myBluetoothAdapter;
//	/* The Bluetooth Gatt profile allows you to communicate with ble devices */
//	private BluetoothGatt myBluetoothGatt;
//	/* The variable to keep track of the RTneuro Gatt service when found*/
//	private BluetoothGattService RTnGattService = null;
//	/* The variable to keep track of the RTneuro Tx characteristic when found*/
//	private BluetoothGattCharacteristic RTnTxCharacteristic = null;
//	/* The variable to keep track of the RTneuro Rx characteristic when found*/
//	private BluetoothGattCharacteristic RTnRxCharacteristic = null;
//	/* The variable to keep track of the RTneuro Rx descriptor when found*/
//	private BluetoothGattDescriptor RTnRxdescriptor = null;
//	/* timer used to keep track of the scan timeout*/
//	private Timer scanTimeOutTimer;
//	
//	/* byte array to keep track of the incoming data*/
//	private byte[] receivedPacket;
//	/* a reference to the main activity */ 
//	private Activity mainActivity;
//	/* Flag to keep track of any errors arising from the ble setup routine */ 
//	private boolean bleRouterErrorThrown = false;
//	
//    /***********************************************************************
//     * RTnBleRouter (Constructor)
//     * RTneuro Ble Router Constructor 
//     * Here is where we actually configure the Ble adapter and
//     * check whether ble is supported and enabled
//     * @param activity			The Context of the main activity
//     * @param service			A handle of the router service
//     * @param RTnRouterInstance The instance of the RTnRouter calling class
//     ***********************************************************************/
//	public RTnBleRouter( Activity activity, Service service, RTnRouter RTnRouterInstance )
//	{
//		
//		// hold a reference of the router service
//		routerService = service;
//		
//		// hold a reference of the RTnRouter instance
//		myRtnRouterInstance = RTnRouterInstance;
//		
//		// hold a reference of the main activity
//		mainActivity = activity;
//		
//		// Use the bluetooth manager to get the bluetooth adapter, i.e. to be able to get a handle
//		// of the radio adapter
//		final BluetoothManager myBluetoothManager =
//		        (BluetoothManager) routerService.getSystemService(Context.BLUETOOTH_SERVICE);
//		
//		// get the bluetooth radio adapter
//		myBluetoothAdapter = myBluetoothManager.getAdapter();
//		
//		// Checks if Bluetooth is supported on the device.
//		if ( myBluetoothAdapter == null )
//		{
//			showToastMessage(R.string.bluetooth_smart_not_supported);
//			bleRouterErrorThrown = true; 
//			return;
//		}
//		
//		// now before proceding make sure that bluetooth is enabled
//		if (myBluetoothAdapter.isEnabled() != true)
//		{
//			showToastMessage(R.string.bluetooth_disabled);
//			bleRouterErrorThrown = true;
//			return;
//		}
//	}
//	
//	// this call back gets called when a new Bluetooth event is triggered in the underlying bluetooth radio adapter
//	private BluetoothAdapter.LeScanCallback myBleScanCallback = new BluetoothAdapter.LeScanCallback()
//	{
//		
//		@Override
//		public void onLeScan( BluetoothDevice device, int rssi, byte [] scanRecord )
//		{
//			
//			Log.v(TAG, "Device found: " + device.getName() + " rssi (signal strength, dB):  " + rssi);
//			
//			// since the scan is filtered by Uuid, we know is the right one, grab the device address and name
//			RTnDeviceAdx = device.getAddress();
//			RTnDeviceName = device.getName();
//
//			Log.i(TAG, "Stop ongoing scan");
//			// make sure to cancel the ongoing scan and the timer 
//			scanningComplete = true;
//			myBluetoothAdapter.stopLeScan(myBleScanCallback);
//			scanTimeOutTimer.cancel();
//			
//		}
//	};
//	
//	// this call back gets called when a new ble Gatt event is triggered in the ble stack
//	private final BluetoothGattCallback myGattCallback = new BluetoothGattCallback()
//	{
//		@Override
//		public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) 
//		{
//	            
//	        if (newState == BluetoothProfile.STATE_CONNECTED) 
//	        {
//	        	myConnectionState = STATE_CONNECTED;
//	            Log.i(TAG, "Connected to GATT server.");
//	
//	        } 
//	        
//	        else if (newState == BluetoothProfile.STATE_CONNECTING)
//	        {
//	        	myConnectionState = STATE_CONNECTING;
//	            Log.i(TAG, "Connecting to GATT server.");
//	        	
//	        }
//	        
//	        else if (newState == BluetoothProfile.STATE_DISCONNECTED) 
//	        {
//	        	myConnectionState = STATE_DISCONNECTED;
//	            Log.i(TAG, "Disconnected from GATT server.");
//	        }
//		}
//		
//        @Override
//        // New services discovered
//        public void onServicesDiscovered(BluetoothGatt gatt, int status) 
//        {	
//            if (status == BluetoothGatt.GATT_SUCCESS) 
//            {
//            	Log.v(TAG, "onServicesDiscovered received: " + status);
//            	discoveryComplete = true;
//            } 
//            else 
//            {
//                Log.v(TAG, "onServicesDiscovered received: " + status);
//            }
//        }
//        
//        @Override
//        // Result of a characteristic read operation
//        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) 
//        {
//        	
//        	if ((characteristic.getUuid().toString()).equals( RTN_RX_CHARACTERISTIC_UUID.toString() ))
//        	{
//            	Log.v(TAG, "RTN_RX_Characteristic callback");
//            	
//            	receivedPacket = characteristic.getValue();
//            	
//            	// Inform the RTnRouter instance that you received a packet 
//            	myRtnRouterInstance.bleRxCallback( receivedPacket);
//        	}
//        }
//        
//        @Override
//        // result of a characteristic write operation
//        public void onCharacteristicWrite (BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status)
//        {
//            if (status == BluetoothGatt.GATT_SUCCESS) 
//            {
//            	Log.v(TAG, "RTN_TX_Characteristic write");
//            }
//            else
//            {
//            	Log.v(TAG, "RTN_TX_Characteristic failure");
//            }
//        }
//        
//        @Override
//        // Result of a characteristic read operation
//        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic,int status) 
//        {
//            if (status == BluetoothGatt.GATT_SUCCESS) 
//            {
//            	Log.v(TAG, "RTN_RX_Characteristic read");
//            }
//            else
//            {
//            	Log.v(TAG, "RTN_RX_Characteristic read failure");
//            }
//        }
//        
//        
//	};
//	
//    /************************************************************
//     * scanRTnBleDevices
//     * Scan RTneuro Ble Devices
//     * This methods triggers the start scanning for ble devices
//     * it also implements a timer to handle the timeout
//     ******************************************************************/
//	public void scanRTnBleDevices()
//	{
//		// we will need to implement a list for the user to choose the device they want to
//		// choose to connect to.
////		scanTimeOutTimer = new Timer();
//		
////		Log.i(TAG, "Scanning Started");
//
////		scanTimeOutTimer.schedule(new TimerTask()
////		{
////			
////			@Override
////			public void run()
////			{
////				Log.w(TAG, "Scanning Timed Out");
////				// trigger an error to end the service
////				bleRouterErrorThrown = true;
////				myBluetoothAdapter.stopLeScan(myBleScanCallback);
////				// after stoping the ble scan inform the user
////				showToastMessage(R.string.scanning_time_out);
////				scanningComplete = true;
////				return;
////			}
////		}, SCAN_TIMEOUT);
//		
//		//set the scanning flag
////		scanningComplete = false;
////		// start the ble scan with a filter to the RTnService
////		myBluetoothAdapter.startLeScan(new UUID[]{RTN_SERVICE_16BIT_UUID}, myBleScanCallback);
//	}
//	
//    /************************************************************
//     * connectToRTnBleDevices
//     * connect to RTneuro Ble Devices
//     * This method gets called to connect to the remote ble device 
//     * after scanning
//     ******************************************************************/
//	public void connectToRTnBleDevice()
//	{
//		
////		final BluetoothDevice currentDevice = myBluetoothAdapter.getRemoteDevice(RTnDeviceAdx);
////		
////		myBluetoothGatt = currentDevice.connectGatt( routerService, false, myGattCallback );
//	}
//	
//    /************************************************************
//     * discoverServices
//     * discover Services
//     * This method starts the dicovering of services from the connected
//     * device
//     ******************************************************************/
//	public void discoverRTnServices()
//	{
////		discoveryComplete = false;
////		myBluetoothGatt.discoverServices();
//	}
//	
//    /************************************************************
//     * startRTnRouterService
//     * start RTneuro Custom Router ble Service
//     * This method checks for support for the RTneuro custom service,
//     * characteristics and descriptors, it then subscribes to 
//     * notification on the RX characteristic
//     ******************************************************************/
//	public void startRTnRouterService()
//	{
//		
////		// get a handle of the RTnService
////		RTnGattService = myBluetoothGatt.getService( RTN_SERVICE_128BIT_UUID );
////		
////		// get a handle of the RTneuro RX and Tx Characteristics
////		RTnRxCharacteristic = RTnGattService.getCharacteristic( RTN_RX_CHARACTERISTIC_UUID );
////		RTnTxCharacteristic = RTnGattService.getCharacteristic( RTN_TX_CHARACTERISTIC_UUID );
////		
////		// get a handle of the notification descriptor
////		RTnRxdescriptor = RTnRxCharacteristic.getDescriptor( CLIENT_CHARACTERISTIC_CONFIG );
////		
////		
////		// if peripheral device does not support RTnService terminate and inform user
////		if (RTnGattService == null)
////		{
////			Log.e(TAG, "RTnService characteristics not supported");
////			showToastMessage( R.string.rtn_service_not_supported );
////			bleRouterErrorThrown = true;
////			return;
////		}
////		
////		//if peripheral device does not support RTnCharacteristics terminate and inform user
////		if (RTnRxCharacteristic == null || RTnTxCharacteristic == null)
////		{
////			Log.e(TAG, "RTnService characteristics not supported");
////			showToastMessage( R.string.rtn_service_not_supported );
////			bleRouterErrorThrown = true;
////			return;
////		}
////		
////		//if peripheral device does not support RTnRXdescriptor terminate and inform user
////		if (RTnRxdescriptor == null)
////		{
////			Log.e(TAG, "RTnService Rx descriptor not supported");
////			showToastMessage( R.string.rtn_service_not_supported );
////			bleRouterErrorThrown = true;
////			return;
////		}
////		
////		// make sure we subscribe to notification on the Rx characteristic
////		myBluetoothGatt.setCharacteristicNotification( RTnRxCharacteristic, true );
////		RTnRxdescriptor.setValue( BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE );
////		myBluetoothGatt.writeDescriptor(RTnRxdescriptor);
//
//	}
//	
//    /************************************************************
//     * sendToRTnBleDevice
//     * send to RTneuro ble device
//     * This method is called when you need to send a packet to the remote
//     * ble device
//     * @return boolean	returns true on success setting the characteristic,
//     * 					false otherwise
//     ******************************************************************/
//	public boolean sendToRTnBleDevice(byte[] byteToTransmit)
//	{
////		boolean success;
////		success = RTnTxCharacteristic.setValue( byteToTransmit );
////		
////		if (success == true)
////		{
////			success = myBluetoothGatt.writeCharacteristic( RTnTxCharacteristic );
////			
////			if (success == true)
////				return true;
////			else
////				return false;
////		}
////		else
//			return false;
//	}
//	
//    /************************************************************
//     * isScanning
//     * is scanning for devices?
//     * @return boolean	returns true on scanning ongoing, false otherwise
//     ******************************************************************/
//	public boolean isScanning()
//	{
////		if (scanningComplete == true)
////			return false;
////		else
//			return true;
//	}
//	
//    /************************************************************
//     * isConnected
//     * is connected to bluetooth remote devices? 
//     * @return boolean	returns true on scanning ongoing, false otherwise
//     ******************************************************************/
//	public boolean isConnected()
//	{
////		if (myConnectionState == STATE_CONNECTED)
////			return true;
////		else
//			return false;
//	}
//	
//    /************************************************************
//     * isSDiscoveringServices
//     * is discovering services from the connected ble device?
//     * @return boolean	returns true on scanning ongoing, false otherwise
//     ******************************************************************/
//	public boolean isDiscoveringServices()
//	{
////		if (discoveryComplete == true)
////			return false;
////		else
//			return true;
//	}
//	
//    /************************************************************
//     * showToastMessage
//     * this method gets called to invoe a runnable on the UI thread to
//     * show a message to the user
//     * @param messageId	a message id (found in string resources) to 
//     * 					be passed to the toast message for displaying
//     ******************************************************************/
//	public void showToastMessage(final int messageId)
//	{
//	    mainActivity.runOnUiThread( new Runnable()
//		{	
//			@Override
//			public void run()
//			{
//				// send the toast to the UI thread
////				Toast.makeText( mainActivity, messageId, Toast.LENGTH_LONG ).show();
//			}
//		} );
//	}
//	
//    /************************************************************
//     * getBleErrorStatus
//     * get Bluetooth low energy error status
//     * @return boolean	a flag indicating whether the setup routine for 
//     * 					the ble router failed
//     ******************************************************************/
//	public boolean getBleErrorStatus()
//	{
//		return bleRouterErrorThrown;
//	}
//	
//}
