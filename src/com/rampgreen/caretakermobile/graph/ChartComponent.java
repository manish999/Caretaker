package com.rampgreen.caretakermobile.graph;

import org.achartengine.GraphicalView;
import org.achartengine.chart.PieChart;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.util.AppSettings;
import com.rampgreen.caretakermobile.util.DateUtils;
import com.rampgreen.caretakermobile.util.MapUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class ChartComponent {

	/*
	// chart creation
			ChartComponent chartComponent = new ChartComponent(getActivity(), getView());
			if(whichChart == Constants.CHART_LINE){
				ArrayList<TimeSeries> linelist = new ArrayList<TimeSeries>();
				linelist.add(series);
				chartComponent.constructGraph(ChartComponent.CHART_LINE, linelist, null, listHeader);
			} else if(whichChart == Constants.CHART_PIE) {
				chartComponent.constructGraph(ChartComponent.CHART_PIE , null, chartDataList,"");
			} else {
			}
		*/	
	private Context mContext;
	private View view;
	public static final int CHART_LINE = 0; 
	public static final int CHART_PIE = 1; 
	public static int TYPE_GRAPH = CHART_LINE; 

	private class ChartTypeDataModel {
		ArrayList<?> graphLineDataList;
		HashMap<String, Double> graphPieDataMap;
	}

	public ChartComponent(Context mContext, View view) {
		this.mContext = mContext;
		this.view = view;
	}
	public ChartComponent(Context mContext) {
		this.mContext = mContext;
		this.view = view;
	}

	//	public ChartComponent(Context mContext, Activity activity) {
	//		this.mContext = mContext;
	//		this.inflater = inflater;
	//		this.mActivity = activity;
	//	}


	public View constructGraph(int graphType, ArrayList<?> graphLineDataList, HashMap<String, Double> graphPieDataMap, String lineYTitle) {
		/*GraphicalView graphicalView = null;
//		View viewe = view.findViewById(R.id.frag_chartlist);
//		View viewgraph = view.findViewById(R.id.frag_graph);
//		TextView tvTime = (TextView)view.findViewById(R.id.base_chart_timeframe);
//		RelativeLayout relativeLayout = (RelativeLayout)view.findViewById(R.id.base_chart_gallery_graph);
//		setQueryDate(tvTime);
//		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		if(graphType == CHART_LINE) {
			//			Chart chart = new Chart();
			//			View charView = chart.buildLineChart(mContext, null, null);
			//			charView.setLayoutParams(params);
			//			relativeLayout.addView(charView, params);

			LineChart lineChart = new LineChart(mContext);
			lineChart.setXYTitle("Date", lineYTitle);
			lineChart.setXYList(graphLineDataList);
			graphicalView = lineChart.prepareChartView();
//			relativeLayout.addView(graphicalView, params);

		} else if(graphType == CHART_PIE) {
//			PieChart pieChart = new PieChart(mContext);
//			pieChart.setPieChartData(MapUtil.sortByValue(graphPieDataMap));// sort data to show top 10 results 
			//			LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
			//			LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
			//					LinearLayout.LayoutParams.MATCH_PARENT,
			//					LineChartActivity.viewHeight - LineChartActivity.bottomViewHeight - 10);.
//			GraphicalView graphicalView = pieChart.prepareChartView();
//			relativeLayout.addView(graphicalView, params);

//						PieChart pieChart = new PieChart(mContext);
//						pieChart.setPieChartData(null);
//						GraphicalView graphicalView = pieChart.openChart();//ppieChart.prepareChartView();
//						graphicalView.setLayoutParams(params);
//						relativeLayout.addView(graphicalView, params);

			//			if(chartDataList.size() == 0){
			//				AppLog.showToast(this, "No data is available for this interval.");
			//			}
		} else {

		}
		return graphicalView; 
		*/
		
		LineChart lineChart = new LineChart(mContext);
		lineChart.setXYTitle("Date", lineYTitle);
		lineChart.setXYList(graphLineDataList);
		return lineChart.prepareChartView();
	}

//	public void constructList(String[] adapterArray) {
//		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//		ListView listView = (ListView)view.findViewById(R.id.base_chart_list);
//		SimpleListView simpleListView = new SimpleListView(mContext);
//		simpleListView.setLayoutParams(params);
//		listView.setAdapter(new ArrayAdapter<String>(mContext, R.layout.fruit,adapterArray));
//		//		View mainView = mActivity.getLayoutInflater().inflate(R.layout.frag_chart_list, null);
//		//		View graphView = mActivity.getLayoutInflater().inflate(R.layout.frag_graph, null);
//		//		RelativeLayout chartVW = (RelativeLayout)mActivity.findViewById(R.id.base_chart_gallery_graph);
//		//		//		RelativeLayout chartListView = (RelativeLayout)mActivity.findViewById(R.id.frabase_chart_list_frame);
//		//		ListView listView = (ListView)mActivity.findViewById(R.id.base_chart_list);
//		//
//		//		Chart chart = new Chart();
//		//		View charView = chart.buildLineChart(mContext, null, null);
//		//		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//		//				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//		//		charView.setLayoutParams(params);
//		//		chartVW.addView(charView, params);
//		//
//		//		SimpleListView simpleListView = new SimpleListView(mContext);
//		//		simpleListView.setLayoutParams(params);
//		//		listView.setAdapter(new ArrayAdapter<String>(mContext, R.layout.fruit,adapterArray));
//		//		getSupportActionBar().setTitle("Rating");
//
//
//		//		getSupportActionBar().setTitle("Rating");
//	}
	
//	public void constructList(TabbedBaseAdapter adapter, String listHeader) {
//		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//		ListView listView = (ListView)view.findViewById(R.id.base_chart_list);
//		SimpleListView simpleListView = new SimpleListView(mContext);
//		simpleListView.setLayoutParams(params);
//		listView.setAdapter(adapter);
//		
//		// set title 
//		View viewe = view.findViewById(R.id.frag_chartlist);
//		View viewgraph = view.findViewById(R.id.frag_graph);
//		TextView tvListHeader = (TextView)view.findViewById(R.id.tv_data);
//		tvListHeader.setText(listHeader);
//		
//		//		View mainView = mActivity.getLayoutInflater().inflate(R.layout.frag_chart_list, null);
//		//		View graphView = mActivity.getLayoutInflater().inflate(R.layout.frag_graph, null);
//		//		RelativeLayout chartVW = (RelativeLayout)mActivity.findViewById(R.id.base_chart_gallery_graph);
//		//		//		RelativeLayout chartListView = (RelativeLayout)mActivity.findViewById(R.id.frabase_chart_list_frame);
//		//		ListView listView = (ListView)mActivity.findViewById(R.id.base_chart_list);
//		//
//		//		Chart chart = new Chart();
//		//		View charView = chart.buildLineChart(mContext, null, null);
//		//		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//		//				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//		//		charView.setLayoutParams(params);
//		//		chartVW.addView(charView, params);
//		//
//		//		SimpleListView simpleListView = new SimpleListView(mContext);
//		//		simpleListView.setLayoutParams(params);
//		//		listView.setAdapter(new ArrayAdapter<String>(mContext, R.layout.fruit,adapterArray));
//		//		getSupportActionBar().setTitle("Rating");
//
//
//		//		getSupportActionBar().setTitle("Rating");
//	}

	
	protected void setQueryDate(TextView tvDate){
//		String curDate = DateUtils.getCurrentDate(DateUtils.FORMAT_YYYYMMDD_DASHESH);
//		String startDate = (String)AppSettings.getPrefernce(mContext, null, AppSettings.START_DATE, curDate);
//		String endDate = (String)AppSettings.getPrefernce(mContext, null, AppSettings.END_DATE, curDate);
//		tvDate.setText(startDate+" - "+endDate);
	}


}
