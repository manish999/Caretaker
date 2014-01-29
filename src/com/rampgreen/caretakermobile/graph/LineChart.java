package com.rampgreen.caretakermobile.graph;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;

import com.rampgreen.caretakermobile.R;
import com.rampgreen.caretakermobile.util.DateUtils;

import java.util.ArrayList;
import java.util.Random;


public class LineChart implements IChart{

	private static final int SERIES_NR = 2;
	private Context context;
	XYMultipleSeriesDataset dataset;
	private Resources res;
	XYMultipleSeriesRenderer renderer;
	public LineChart(Context context) {
		this.context = context;
		dataset = new XYMultipleSeriesDataset();
		res = context.getResources();
	}

	private XYMultipleSeriesDataset getDemoDataset() {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		final int nr = 10;
		Random r = new Random();
		for (int i = 0; i < SERIES_NR; i++) {
			XYSeries series = new XYSeries("Demo series " + (i + 1));
			for (int k = 0; k < nr; k++) {
				series.add(k, 20 + r.nextInt() % 100);
			}
			dataset.addSeries(series);
		}
		return dataset;
	}

	private XYMultipleSeriesRenderer getDemoRenderer() {
		if(renderer != null) {
			return renderer;
		}
		renderer = new XYMultipleSeriesRenderer();

		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		renderer.setPointSize(2f);
		renderer.setMargins(new int[] {20, 35, 0, 0});
		XYSeriesRenderer r = new XYSeriesRenderer();
		r.setColor(res.getColor(R.color.graph_line_orange));
		r.setPointStyle(PointStyle.CIRCLE);
		r.setPointStrokeWidth(0);
		r.setFillBelowLine(true);
		r.setFillBelowLineColor(res.getColor(R.color.graph_fill_orange));
		r.setLineWidth(2);
		r.setFillPoints(true);
		renderer.addSeriesRenderer(r);
		//		r = new XYSeriesRenderer();
		//		r.setPointStyle(PointStyle.CIRCLE);
		//		r.setColor(Color.GREEN);
		//		r.setFillPoints(true);
		//		r.setFillBelowLine(true);
		//		r.setFillBelowLineColor(Color.GREEN);
		//		renderer.addSeriesRenderer(r);

//		renderer.setDisplayChartValues(true);
		renderer.setShowCustomTextGrid(true);
		renderer.setAxesColor(Color.DKGRAY);
		renderer.setXLabelsColor(Color.BLACK);
		renderer.setYLabelsColor(0, Color.BLACK);
		renderer.setLabelsColor(res.getColor(R.color.blue_dark));
		renderer.setShowLabels(true);
		renderer.setYTitle("Visits");
		renderer.setXTitle("Date");
		renderer.setAntialiasing(true);
		renderer.setGridColor(Color.LTGRAY);
		renderer.setShowGrid(true);
		renderer.setApplyBackgroundColor(true);
		renderer.setBackgroundColor(Color.WHITE);
		renderer.setMarginsColor(Color.WHITE);
		//		int color = renderer.getBackgroundColor();
		renderer.setZoomEnabled(false, false);
		renderer.setPanEnabled(false, false);
		return renderer;
	}

	public void setXYTitle(String xTitle, String yTitle) {
		renderer = getDemoRenderer();
		renderer.setXTitle(xTitle);
		renderer.setYTitle(yTitle);
	}

	@Override
	public GraphicalView prepareChartView() {
		return ChartFactory.getTimeChartView(context, dataset, renderer, DateUtils.FORMAT_YYYYMMDD_DASHESH);
	}

	@Override
	public void startChartActivity() {
		Intent intent = ChartFactory.getLineChartIntent(context, getDemoDataset(), getDemoRenderer());
		context.startActivity(intent);
	}
	
	@Override
	public void setXYList(ArrayList<?> lineList) {
		XYSeries graphLine = new XYSeries("");
		for (int i = 0; i < lineList.size(); i++) {
			Object obj =lineList.get(i);
			if(obj instanceof TimeSeries) {
				graphLine = (TimeSeries) obj;	
			} else if(obj instanceof XYSeries) {
				graphLine = (XYSeries) obj;
			}
			dataset.addSeries(graphLine);
		}
	}
}
