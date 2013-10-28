package com.rampgreen.caretakermobile.network;
///*
// * Copyright (c) 2012 Google Inc.
// * 
// * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
// * in compliance with the License. You may obtain a copy of the License at
// * 
// * http://www.apache.org/licenses/LICENSE-2.0
// * 
// * Unless required by applicable law or agreed to in writing, software distributed under the License
// * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
// * or implied. See the License for the specific language governing permissions and limitations under
// * the License.
// */
//
//package in.rampgreen.analytics.network;
//
//import java.io.IOException;
//
//import android.app.ProgressDialog;
//import android.os.AsyncTask;
//
//import com.example.caretakermobile.util.AppLog;
//
///**
// * Asynchronous task that also takes care of common needs, such as displaying progress,
// * authorization, exception handling, and notifying UI when operation succeeded.
// * 
// * @author Yaniv Inbar
// */
//abstract class CommonAsyncTask extends AsyncTask<Void, Void, Integer> {
//
//	final FragmentRealTimeNew activity;
//	final Analytics client;
//	private ProgressDialog progressBar;
//
//	CommonAsyncTask(FragmentRealTimeNew activity) {
//		this.activity = activity;
//		client = activity.service;
//	}
//
//	@Override
//	protected void onPreExecute() {
//		super.onPreExecute();
//		activity.numAsyncTasks++;
//		activity.setRefreshActionButtonState(true);
//		//    progressBar = ProgressDialog.show(activity.getActivity(), null, "Loading...", true);
//	}
//
//	@Override
//	protected final Integer doInBackground(Void... ignored) {
//		try {
//			return doInBackground();
//			//      return true;
//		} catch (final GooglePlayServicesAvailabilityIOException availabilityException) {
//			activity.showGooglePlayServicesAvailabilityErrorDialog(
//					availabilityException.getConnectionStatusCode());
//		} catch (UserRecoverableAuthIOException userRecoverableException) {
//			activity.startActivityForResult(
//					userRecoverableException.getIntent(), FragmentRealTimeNew.REQUEST_AUTHORIZATION);
//		} catch (IOException e) {
//			AppLog.e("IO Exception in Fragment realtime new....");
//		}
//		return 0;
//		//    return false;
//	}
//
//	@Override
//	protected final void onPostExecute(Integer success) {
//		super.onPostExecute(success);
//		if (0 == --activity.numAsyncTasks) {
//			if(progressBar != null && progressBar.isShowing())
//				progressBar.dismiss();
//			activity.setRefreshActionButtonState(false);
//			//      progressBar.setVisibility(View.GONE);
//		}
//
//		//    if(success > 0) {
//		activity.refreshView(success);
//		//    }
//
//		//    if (success) {
//		//      activity.refreshView();
//		//    }
//	}
//
//	abstract protected Integer doInBackground() throws IOException;
//}
