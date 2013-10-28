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
//package com.example.caretakermobile.network;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.example.caretakermobile.util.AppLog;
//import com.example.caretakermobile.util.AppSettings;
//
///**
// * Asynchronously load the tasks.
// * 
// * @author Yaniv Inbar
// */
//public class AsyncLoadTasks extends CommonAsyncTask {
//
//	AsyncLoadTasks(FragmentRealTimeNew tasksSample) {
//		super(tasksSample);
//	}
//
//	@Override
//	protected Integer doInBackground() throws IOException {
//		List<String> result = new ArrayList<String>();
//		//    GaData gadata = client.data().ga().get("ga:69701808", // Table Id. ga: + profile id.
//		//        "2013-04-01", // Start date.
//		//        "2013-04-14", // End date.
//		//        "ga:visits")
//		////        .setDimensions("ga:source,ga:keyword")
//		////        .setSort("-ga:visits,ga:source")
//		////        .setFilters("ga:medium==organic")
//		//        .setMaxResults(25)
//		//        .execute();
//		//    ***********************REALTIME FETCH DATA*********************
//		String profileID = (String) AppSettings.getPrefernce(activity.getActivity(), null, AppSettings.PROFILE_ID, "");
//		Get realtimeRequest = client.data().realtime().get(profileID,//ga:69701808
//				"ga:activeVisitors");
//		//    try {
//		RealtimeData realtimeData = realtimeRequest.execute();
//		AppLog.d("Active Visitors RESULT.....", realtimeData.getTotalsForAllResults().get("ga:activeVisitors")+"");
//		AppLog.d("realtime data", realtimeData.toPrettyString()+"");
//		return Integer.parseInt(realtimeData.getTotalsForAllResults().get("ga:activeVisitors"));
//		//      activity.refreshView(realtimeData.getTotalResults());
//		// Success.
//		//      ***********************Google Task fetcher*********************
//		//    List<Task> tasks = null; //=client.tasks().list("@default").setFields("items/title").execute().getItems();
//		//    if (tasks != null) {
//		//      for (Task task : tasks) {
//		//        result.add(task.getTitle());
//		//      }
//		//    } else {
//		//      result.add("No tasks.");
//		//    }
//		//    activity.tasksList = result;
//	}
//
//	public static void run(FragmentRealTimeNew tasksSample) {
//		new AsyncLoadTasks(tasksSample).execute();
//	}
//}
