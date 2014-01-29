/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rampgreen.caretakermobile;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.rampgreen.caretakermobile.model.GcmMsgBean;
import com.rampgreen.caretakermobile.ui.ActivityNotification;
import com.rampgreen.caretakermobile.util.AppLog;
import com.rampgreen.caretakermobile.util.AppSettings;
import com.rampgreen.caretakermobile.util.Constants;
import com.rampgreen.caretakermobile.util.StringUtils;

/**
 * This {@code IntentService} does the actual handling of the GCM message.
 * {@code GcmBroadcastReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class GcmIntentService extends IntentService {
	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;
	String notificationTitle;

	public GcmIntentService() {
		super("GcmIntentService");
	}
	public static final String TAG = "GCM Demo";

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		// The getMessageType() intent parameter must be the intent you received
		// in your BroadcastReceiver.
		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
			
			/*
			 * Filter messages based on message type. Since it is likely that GCM will be
			 * extended in the future with new message types, just ignore any message types you're
			 * not interested in, or that you don't recognize.
			 */
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
				sendNotification("Error", "Send error: " + extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
				sendNotification("Deleted Messages","Deleted messages on server: " + extras.toString());
				// If it's a regular GCM message, do some work.
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
				GcmMsgBean gcmMsgBean = null;
				String msg = null;
				try
				{
					msg = extras.getString("message");
					JSONObject jsonObject = new JSONObject(msg);
					gcmMsgBean = new GcmMsgBean();
					gcmMsgBean.parseGcmMsg(jsonObject);
				} catch (JSONException e)
				{
					AppLog.printStackTrace(e);
				}
				
				if(gcmMsgBean == null || msg == null) {
					return;
				}
				
				String title = gcmMsgBean.getNotificationTitle();
				String subtitle = gcmMsgBean.getNotificationSubTitle();
				msg = title + ":" + subtitle;
				String msgList = (String)AppSettings.getPrefernce(this, null, AppSettings.STORE_NOTI_LIST, "");
				if(StringUtils.isEmpty(msgList)) {
					msgList = msg;
				} else {
					msgList = msgList + "|"+msg;
				}
				AppSettings.setPreference(this, null,AppSettings.STORE_NOTI_LIST, msgList);
				sendNotification(gcmMsgBean.getNotificationTitle(),gcmMsgBean.getNotificationSubTitle());
				AppLog.d(TAG, "Received: " + extras.toString());
				
			}
					
				
				// This loop represents the service doing some work.
//				for (int i = 0; i < 5; i++) {
//					Log.i(TAG, "Working... " + (i + 1)
//							+ "/5 @ " + SystemClock.elapsedRealtime());
//					try {
//						Thread.sleep(5000);
//					} catch (InterruptedException e) {
//					}
//				}
//				Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
				// Post notification of received message.
				
//			}
		}
		// Release the wake lock provided by the WakefulBroadcastReceiver.
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	// Put the message into a notification and post it.
	// This is just one simple example of what you might choose to do with
	// a GCM message.
	private void sendNotification(String notificationTitle, String msg) {
		mNotificationManager = (NotificationManager)
				this.getSystemService(Context.NOTIFICATION_SERVICE);

		Bundle bundle = new Bundle();
		bundle.putString(Constants.NOTIFICATION_MSG, msg);
		bundle.putString(Constants.CALLED_COMPONENT, Constants.SERVICE_GCM_INTENT);

		Intent notificationIntent =  new Intent(this, ActivityNotification.class);
		notificationIntent.putExtras(bundle);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
				Intent.FLAG_ACTIVITY_NEW_TASK);

		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, 0);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
		.setSmallIcon(R.drawable.ic_launcher)
		.setContentTitle(notificationTitle)
		.setStyle(new NotificationCompat.BigTextStyle()
		.bigText(msg))
		.setContentText(msg);

		mBuilder.setContentIntent(contentIntent);
		Notification notification = mBuilder.build();
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		mNotificationManager.notify(NOTIFICATION_ID, notification);
	}
}
