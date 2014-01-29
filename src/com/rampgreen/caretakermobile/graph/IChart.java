package com.rampgreen.caretakermobile.graph;

import java.util.ArrayList;

import android.view.View;


public interface IChart {
	public abstract void setXYList(ArrayList<?> lineList);
	public abstract View prepareChartView();
	public abstract void startChartActivity();
}
