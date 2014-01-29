package com.rampgreen.caretakermobile.ui;

import org.json.JSONObject;

import android.os.Bundle;

import com.android.volley.VolleyError;
import com.rampgreen.caretakermobile.R;

public class Temp_Graph extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.temp_graph);
		      /* GraphViewSeries exampleSeries  = new GraphViewSeries(new GraphViewData[] {
		    	          new GraphViewData(00, 70)
		    	          , new GraphViewData(01,40)
		    	          , new GraphViewData(02,80)
		    	          , new GraphViewData(03, 30)
		    	          , new GraphViewData(04, 60)
		    	          , new GraphViewData(05, 45)
		    	          , new GraphViewData(06, 100)		    	         		    	          
		    	});
		       
		       GraphView graphView = new LineGraphView(Temp_Graph.this, "Current Heart Rate:" + 100 + "/min");		
		       graphView.setHorizontalLabels(new String[]{"25","35","52","25","30.2","50.5"});
		       graphView.setVerticalLabels(new String[]{"25","85"});
		    	graphView.addSeries(exampleSeries); // data 
		    		   
		    // draw sin curve  
		       int num = 85;  
		       GraphViewData[] data = new GraphViewData[num];  
		       double v=25;  
		       for (int i=0; i<num; i++) {  
		          v += 26.2;  
		          data[i] = new GraphViewData(i, Math.sin(v));  
		       }  
		       
		       // add data  
		       graphView.addSeries(new GraphViewSeries(data));  
		       // set view port, start=2, size=40  
		       graphView.addSeries(exampleSeries);
		       graphView.setViewPort(5, 80);  
		       graphView.setScrollable(true);  
		       // optional - activate scaling / zooming  
		       graphView.setScalable(true); 		       
		      
		       graphView.setCustomLabelFormatter(new CustomLabelFormatter() {  
		          @Override  
		          public String formatLabel(double value, boolean isValueX) {  
		             if (isValueX) {  
		                if (value < 5) {  
		                   return "small";  
		                } else if (value < 15) {  
		                   return "middle";  
		                } else {  
		                   return "big";  
		                }  
		             }  
		             return null; // let graphview generate Y-axis label for us  
		          }  
		       });  
		       
		       graphView.addSeries(exampleSeries);		      
		       graphView.setVerticalLabels(new String[]{"High 85","55","Low 25"});		      
		       graphView.getGraphViewStyle().setGridColor(Color.TRANSPARENT); 
		       graphView.getGraphViewStyle().setTextSize(getResources().getDimensionPixelOffset(10));
		       graphView.getGraphViewStyle().setNumHorizontalLabels(7);
		       graphView.getGraphViewStyle().setNumVerticalLabels(85);  		       
		       graphView.getGraphViewStyle().setVerticalLabelsWidth(80);
		       graphView.setScrollable(true);
		       graphView.setScalable(true);
		       graphView.getGraphViewStyle().setTextSize(17);		       
		       ((LineGraphView) graphView).setDrawDataPoints(true);
		       ((LineGraphView) graphView).setDataPointsRadius(8f);		
		       
		       LinearLayout layout = (LinearLayout) findViewById(R.id.layout);  
		       layout.addView(graphView);*/
	}	
	

	@Override
	public void onResponse(JSONObject response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub
		
	}	

}
