//package com.rampgreen.caretakermobile.socket;
//
//import android.app.Activity;
//import android.app.Service;
//import android.util.Log;
//
///***********************************************************
//* RTnRouter
//* RTneuro Router
//* The RTnRouter class provides the most basic functionality 
//* to be able to:
//* Setup and Connect using the RTnBleRouter
//* Setup and Connect using the RTnInternetRouter
//* 
//* @author 	Camilo Tejeiro for	RTNeuro�
//* 			,=,e
//* 
//************************************************************/
//public class RTnRouter 
//{
//	//********************* Class Properties **********************/
//	/* Gather the TAG for this class for debugging purposes */ 
//	private static final String TAG = RTnRouter.class.getSimpleName();
//	
//	//********************* Class Variables **********************/ 
//	/* declare the RTneuro Ble router to be able to connect to ble devices*/
////	private RTnBleRouter myRTnBleRouter;
//	/* declare the RTneuro Internet router to be able to connect to the server*/
//	private RTnInternetRouter myRTnInternetRouter;
//	/* a variable to hold a reference of the main context */
//	private Service routerService;
//	
//    /************************************************************
//     * RTnRouter (Constructor)
//     * RTneuro Router Constructor 
//     * Here is where we create instances of all the routers with 
//     * their defaul initial configuration for each type
//     * @param mainActivity	The Context of the main activity
//     * @param service		A reference to the Router service
//     ******************************************************************/
//	public RTnRouter(Activity mainActivity, Service service)
//	{
//		Log.i(TAG, "Initializing RTnRouter");
//		
//		// make sure we get a handle of the main context
//		routerService = service;
//		
//		// create instances of the different routers and pass both the main activity context
//		// and a handle of the current RTnRouter instance
////		myRTnBleRouter = new RTnBleRouter(mainActivity, routerService, this);
//		myRTnInternetRouter = new RTnInternetRouter(mainActivity, routerService, this);
//	
//	}
//	
//    /************************************************************
//     * getRTnBleRouter 
//     * Get RTneuro Ble Router
//     * Getter method used to access the configured instance of the
//     * RTnBleRouter
//     * @return RTnBleRouter 	The current RTnBleRouter instance
//     ******************************************************************/
////	public RTnBleRouter getRTnBleRouter()
////	{
////		return myRTnBleRouter;
////		
////	}
//    /************************************************************
//     * getRTnInternetRouter
//     * Get RTneuro Internet Router
//     * Getter method used to access the configured instance of the
//     * RTnInternetRouter
//     * @return RTnInternetRouter 	The current RTnInternetRouter instance
//     ******************************************************************/
//	public RTnInternetRouter getRTnInternetRouter()
//	{
//		return myRTnInternetRouter;
//		
//	}
//	
//    /************************************************************
//     * bleRxCallback
//     * Bluetooth Low Energy Receive Callback
//     * This method gets called everytime we get an incoming packet from
//     * the remote ble device
//     * @param rxPacket	the received packet byte array
//     ******************************************************************/
//	public void bleRxCallback(byte [] rxPacket)
//	{
//		// this method gets called when you receive a new packet
//		// here is where we will actually transmit data to the server
//		internetTransmit(rxPacket);
//	}
//	
//    /************************************************************
//     * internetRxCallback
//     * internet Receive Callback
//     * This method gets called everytime we get an incoming packet from
//     * the remote cloud server device
//     * @param rxPacket	the received packet byte array
//     ******************************************************************/
//	public void internetRxCallback(byte [] rxPacket)
//	{
//		// this method gets called when you receive a new packet from the server
//		// here is where we will actually transmit data to the device
//		bleTransmit(rxPacket);
//	}
//	
//    /************************************************************
//     * bleTransmit
//     * Bluetooth low energy transmit
//     * this is the method we call when we need to transmit data to 
//     * the ble device
//     * @param txPacket	The packet to send to the device
//     ******************************************************************/
//	public void bleTransmit(byte [] txPacket)
//	{
//		// transmit data to the device
////		myRTnBleRouter.sendToRTnBleDevice(txPacket);
//	}
//    /************************************************************
//     * internetTransmit
//     * internet transmit
//     * this is the method we call when we need to transmit data to 
//     * the cloud server device
//     * @param txPacket	The packet to send to the server
//     ******************************************************************/
//	public void internetTransmit(byte [] txPacket)
//	{
//		myRTnInternetRouter.sendToRTnCloudServer(txPacket);
//	}
//	
//	public void internetTransmit(String jsonText)
//	{
//		myRTnInternetRouter.sendToRTnCloudServer(jsonText);
//	}
//}
