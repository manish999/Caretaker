package com.rampgreen.caretakermobile.ui;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rampgreen.caretakermobile.graph.ChartComponent;
import com.rampgreen.caretakermobile.model.SensorData;
import com.rampgreen.caretakermobile.util.AppLog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GraphHome
{

	/** The view gsr. */
	private GraphicalView viewGsr;

	/** The ly gsr. */
	private LinearLayout lyGsr;

	/**
	 * Display graph.
	 * 
	 * @param UserName
	 *            the user name
	 * @param BiometricID
	 *            the biometric id
	 * @return the view
	 */
	public View displayGraph(Context context, ArrayList<SensorData> LIST_SensorData) {
		// chart creation
		TimeSeries series = new TimeSeries("");
		
		for (SensorData sensorData : LIST_SensorData)
		{
			Date date = null;
			try
			{
				date = getDateCurrentTimeZone(sensorData.updated_on);
			} catch (ParseException e)
			{
				e.printStackTrace();
			}
			series.add(date, sensorData.getgsr());
		}
		ChartComponent chartComponent = new ChartComponent(context);
		ArrayList<TimeSeries> linelist = new ArrayList<TimeSeries>();
		linelist.add(series);
		return chartComponent.constructGraph(ChartComponent.CHART_LINE, linelist, null, "GSRRR");
	}
	public View displayGraph(Context context, ArrayList<SensorData> LIST_SensorData, String UserID,String UserName, String BiometricID) {
		lyGsr = new LinearLayout(context);

		if (LIST_SensorData != null) {
			for (int i = 0; i < LIST_SensorData.size(); i++) {
				if (UserID.equals(LIST_SensorData.get(i).GetUserID())) {

					if (BiometricID.equals("GSR")) {
						String[] titles = new String[] { "Inside" };
						List<Date[]> x = new ArrayList<Date[]>();
						List<double[]> values = new ArrayList<double[]>();
						Date[] dates = new Date[LIST_SensorData.size()];
						double[] str = new double[LIST_SensorData.size()];

						for (int j = 0; j < LIST_SensorData.size(); j++) {
							try {//
								dates[j] = getDateCurrentTimeZone(LIST_SensorData
										.get(j).updated_on);
								str[j] = LIST_SensorData.get(j).getgsr();
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}

						x.add(dates);
						values.add(str);

						int[] colors = new int[] { Color.BLUE };
						PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE };
						XYMultipleSeriesRenderer renderer = buildRenderer(
								colors, styles);
						int length = renderer.getSeriesRendererCount();
						for (int i1 = 0; i1 < length; i1++) {
							((XYSeriesRenderer) renderer
									.getSeriesRendererAt(i1))
									.setFillPoints(true);
						}

						setChartSettings(renderer, UserName + " GSR"
								+ " chart", "Hour", "Ohms",
								x.get(0)[0].getTime(),
								x.get(0)[x.get(0).length - 1].getTime(),
								100000, 30000000, Color.BLACK, Color.BLACK);

						viewGsr = ChartFactory.getTimeChartView(context,
								buildDateDataset(titles, x, values), renderer,
								"h:mm a");
						viewGsr.refreshDrawableState();
						lyGsr.addView(viewGsr);
					} else if (BiometricID.equals("Heart Rate")) {
						String[] titles = new String[] { "HR", "Pulse" };

						List<Date[]> x = new ArrayList<Date[]>();

						for (int i1 = 0; i1 < titles.length; i1++) {
							Date[] dates = new Date[LIST_SensorData.size()];
							for (int j = 0; j < LIST_SensorData.size(); j++) {
								try {//
									dates[j] = getDateCurrentTimeZone(LIST_SensorData
											.get(j).updated_on);
								} catch (ParseException e) {
									e.printStackTrace();
								}
							}
							x.add(dates);
						}

						List<double[]> values = new ArrayList<double[]>();

						double[] str = new double[LIST_SensorData.size()];
						double[] str_pluse = new double[LIST_SensorData.size()];

						for (int j = 0; j < LIST_SensorData.size(); j++) {
							str[j] = LIST_SensorData.get(j).getpox_hr();
							str_pluse[j] = LIST_SensorData.get(j)
									.getpox_pulse();
						}

						values.add(str);
						values.add(str_pluse);

						int[] colors = new int[] { Color.BLUE, Color.GREEN };
						PointStyle[] styles = new PointStyle[] {
								PointStyle.CIRCLE, PointStyle.DIAMOND };
						XYMultipleSeriesRenderer renderer = buildRenderer(
								colors, styles);
						int length = renderer.getSeriesRendererCount();
						for (int i1 = 0; i1 < length; i1++) {
							((XYSeriesRenderer) renderer
									.getSeriesRendererAt(i1))
									.setFillPoints(true);
						}
						setChartSettings(renderer, UserName + " Heart Rate"
								+ " chart", "Hour", "Beats/min",
								x.get(0)[0].getTime(),
								x.get(0)[x.get(0).length - 1].getTime(), 30,
								300, Color.BLACK, Color.BLACK);

						viewGsr = ChartFactory.getTimeChartView(context,
								buildDateDataset(titles, x, values), renderer,
								"h:mm a");
						viewGsr.refreshDrawableState();
						lyGsr.addView(viewGsr);
					} else if (BiometricID.equals("SPO2")) {
						String[] titles = new String[] { "acc_X", "acc_Y",
						"acc_Z" };

						List<Date[]> x = new ArrayList<Date[]>();

						for (int i1 = 0; i1 < titles.length; i1++) {
							Date[] dates = new Date[LIST_SensorData.size()];
							for (int j = 0; j < LIST_SensorData.size(); j++) {
								try {//
									dates[j] = getDateCurrentTimeZone(LIST_SensorData
											.get(j).updated_on);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							x.add(dates);
						}

						List<double[]> values = new ArrayList<double[]>();

						double[] str_x = new double[LIST_SensorData.size()];
						double[] str_y = new double[LIST_SensorData.size()];
						double[] str_z = new double[LIST_SensorData.size()];

						for (int j = 0; j < LIST_SensorData.size(); j++) {
							str_x[j] = LIST_SensorData.get(j).getacc_x();
							str_y[j] = LIST_SensorData.get(j).getacc_y();
							str_z[j] = LIST_SensorData.get(j).getacc_z();
						}

						values.add(str_x);
						values.add(str_y);
						values.add(str_z);

						int[] colors = new int[] { Color.GREEN, Color.BLUE,
								Color.RED };
						PointStyle[] styles = new PointStyle[] {
								PointStyle.CIRCLE, PointStyle.DIAMOND,
								PointStyle.TRIANGLE };
						XYMultipleSeriesRenderer renderer = buildRenderer(
								colors, styles);
						int length = renderer.getSeriesRendererCount();
						for (int i11 = 0; i11 < length; i11++) {
							((XYSeriesRenderer) renderer
									.getSeriesRendererAt(i11))
									.setFillPoints(true);
						}
						setChartSettings(renderer, UserName + " Accelerometer"
								+ " chart", "Hour", "m/s",
								x.get(0)[0].getTime(),
								x.get(0)[x.get(0).length - 1].getTime(), -19.8,
								19.8, Color.BLACK, Color.BLACK);

						viewGsr = ChartFactory.getTimeChartView(context,
								buildDateDataset(titles, x, values), renderer,
								"h:mm a");
						viewGsr.refreshDrawableState();
						lyGsr.addView(viewGsr);
					} else if (BiometricID.equals("Temperature_ambient")) {
						String[] titles = new String[] { "Ambient", "Skin" };

						List<Date[]> x = new ArrayList<Date[]>();

						for (int i1 = 0; i1 < titles.length; i1++) {
							Date[] dates = new Date[LIST_SensorData.size()];
							for (int j = 0; j < LIST_SensorData.size(); j++) {
								try {//
									dates[j] = getDateCurrentTimeZone(LIST_SensorData
											.get(j).updated_on);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							x.add(dates);
						}

						List<double[]> values = new ArrayList<double[]>();

						double[] str_Ambient = new double[LIST_SensorData
						                                  .size()];
						double[] str_Skin = new double[LIST_SensorData.size()];

						for (int j = 0; j < LIST_SensorData.size(); j++) {
							str_Ambient[j] = LIST_SensorData.get(j)
									.gettemp_ambient();
							str_Skin[j] = LIST_SensorData.get(j).gettemp_skin();
						}

						values.add(str_Ambient);
						values.add(str_Skin);

						int[] colors = new int[] { Color.GREEN, Color.BLUE };
						PointStyle[] styles = new PointStyle[] {
								PointStyle.CIRCLE, PointStyle.DIAMOND };
						XYMultipleSeriesRenderer renderer = buildRenderer(
								colors, styles);
						int length = renderer.getSeriesRendererCount();
						for (int i1 = 0; i1 < length; i1++) {
							((XYSeriesRenderer) renderer
									.getSeriesRendererAt(i1))
									.setFillPoints(true);
						}
						setChartSettings(renderer, UserName + " Temperature" + " chart",
								"Hour", "Celsius", x.get(0)[0].getTime(),
								x.get(0)[x.get(0).length - 1].getTime(), 25,
								85, Color.BLACK, Color.BLACK);

						viewGsr = ChartFactory.getTimeChartView(context,
								buildDateDataset(titles, x, values), renderer,
								"h:mm a");
						viewGsr.refreshDrawableState();
						lyGsr.addView(viewGsr);
					}
				}
				else {
					TextView tv = new TextView(context);
					tv.setText("No " + BiometricID + " sensor data available for "
							+ UserName);
					lyGsr.addView(tv);
				}
			}
		} 
		return lyGsr;

	}

	// added by ishu
	/**
	 * Builds the date dataset.
	 * 
	 * @param titles
	 *            the titles
	 * @param xValues
	 *            the x values
	 * @param yValues
	 *            the y values
	 * @return the xY multiple series dataset
	 */
	protected static XYMultipleSeriesDataset buildDateDataset(String[] titles,
			List<Date[]> xValues, List<double[]> yValues) {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		int length = titles.length;
		for (int i = 0; i < length; i++) {
			TimeSeries series = new TimeSeries(titles[i]);
			Date[] xV = xValues.get(i);
			double[] yV = yValues.get(i);
			int seriesLength = xV.length;
			for (int k = 0; k < seriesLength; k++) {
				series.add(xV[k], yV[k]);
			}
			dataset.addSeries(series);
		}
		return dataset;
	}

	/**
	 * Sets the chart settings.
	 * 
	 * @param renderer
	 *            the renderer
	 * @param title
	 *            the title
	 * @param xTitle
	 *            the x title
	 * @param yTitle
	 *            the y title
	 * @param xMin
	 *            the x min
	 * @param xMax
	 *            the x max
	 * @param yMin
	 *            the y min
	 * @param yMax
	 *            the y max
	 * @param axesColor
	 *            the axes color
	 * @param labelsColor
	 *            the labels color
	 */
	protected static void setChartSettings(XYMultipleSeriesRenderer renderer,
			String title, String xTitle, String yTitle, double xMin,
			double xMax, double yMin, double yMax, int axesColor,
			int labelsColor) {
		renderer.setChartTitle(title);
		renderer.setXTitle(xTitle);
		renderer.setYTitle(yTitle);
		renderer.setXAxisMin(xMin);
		renderer.setXAxisMax(xMax);
		renderer.setYAxisMin(yMin);
		renderer.setYAxisMax(yMax);
		renderer.setAxesColor(axesColor);
		renderer.setLabelsColor(labelsColor);
		renderer.setXLabelsColor(Color.BLACK);
		renderer.setYLabelsColor(0, Color.BLACK);
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		renderer.setPointSize(5f);
		renderer.setMargins(new int[] { 20, 80, 15, 20 });

		renderer.setXLabels(10);
		renderer.setYLabels(10);
		renderer.setShowGrid(true);
		renderer.setXLabelsAlign(Align.CENTER);
		renderer.setYLabelsAlign(Align.RIGHT);
		renderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));
		renderer.setPanEnabled(false, false);
		renderer.setZoomRate(0.2f);
		renderer.setZoomEnabled(false, false);
		renderer.setZoomButtonsVisible(true);
		renderer.setInScroll(true);

	}

	/**
	 * Builds the renderer.
	 * 
	 * @param colors
	 *            the colors
	 * @param styles
	 *            the styles
	 * @return the xY multiple series renderer
	 */
	protected static XYMultipleSeriesRenderer buildRenderer(int[] colors,
			PointStyle[] styles) {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		setRenderer(renderer, colors, styles);
		return renderer;
	}

	/**
	 * Sets the renderer.
	 * 
	 * @param renderer
	 *            the renderer
	 * @param colors
	 *            the colors
	 * @param styles
	 *            the styles
	 */
	protected static void setRenderer(XYMultipleSeriesRenderer renderer,
			int[] colors, PointStyle[] styles) {
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		renderer.setPointSize(5f);
		renderer.setMargins(new int[] { 20, 30, 15, 20 });
		int length = colors.length;
		for (int i = 0; i < length; i++) {
			XYSeriesRenderer r = new XYSeriesRenderer();
			r.setColor(colors[i]);
			r.setPointStyle(styles[i]);
			renderer.addSeriesRenderer(r);
		}
	}


	/**
	 * Gets the date current time zone.
	 * 
	 * @param timestamp
	 *            the timestamp
	 * @return the date current time zone
	 * @throws ParseException
	 *             the parse exception
	 * @throws java.text.ParseException 
	 */
	//		public static Date getDateCurrentTimeZone(String timestamp)
	//				throws ParseException{
	//			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//			Date date = format.parse(timestamp);
	//			return date;
	//		}

	static int i = 0;
	public static Date getDateCurrentTimeZone(String timestamp)
			throws ParseException{
		i = i+1;
		AppLog.e("getDateCurrentTimeZone" + i);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = format.parse(timestamp);
		return date;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.android.volley.Response.ErrorListener#onErrorResponse(com.android
	 * .volley.VolleyError)
	 */
	//		@Override
	//		public void onErrorResponse(VolleyError error) {
	//			// TODO Auto-generated method stub
	//
	//		}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.android.volley.Response.Listener#onResponse(java.lang.Object)
	 */
	//		@Override
	//		public void onResponse(JSONObject response) {
	//			int code = Integer.parseInt(response.optString("code"));
	//			String msg = response.optString("message");
	//			switch (code) {
	//			case ParserError.CODE_ACTION_NOT_FOUND:
	//
	//				break;
	//			case ParserError.CODE_MISSING_ACTION:
	//
	//				break;
	//			case ParserError.CODE_MISSING_TASK:
	//
	//				break;
	//			case ParserError.CODE_CLIENT_AUTHORIZATION_FAILED:
	//
	//				break;
	//			case ParserError.CODE_TOKEN_GENERATION_FAILED:
	//
	//				break;
	//			case ParserError.CODE_USERNAME_REQUIRED:
	//
	//				break;
	//			case ParserError.CODE_PASSWORD_REQUIRED:
	//
	//				break;
	//			case ParserError.CODE_PASSWORD_WRONG:
	//				/* AppLog.showToast(this, "wrong password"); */
	//				break;
	//			case ParserError.CODE_USER_NOT_REGISTERED:
	//
	//				break;
	//			case ParserError.CODE_INVALID_TOKEN:
	//
	//				break;
	//			case ParserError.CODE_TOKEN_EXPIRED:
	//
	//				break;
	//			case ParserError.CODE_INTERNAL_SERVER_ERROR:
	//
	//				break;
	//			case ParserError.CODE_USER_ALREADY_REGISTERED:
	//
	//				break;
	//			case ParserError.CODE_SENSOR:
	//				Toast.makeText(context, "No sensor data available for this user",
	//						Toast.LENGTH_LONG).show();
	//				break;
	//			case ParserError.CODE_SUCCESS:
	//				mSensorDataList = parseSensorData(response);
	//				notifyDataSetChanged();
	//				break;
	//
	//			default:
	//				break;
	//			}
	//
	//			if (code != ParserError.CODE_SUCCESS) {
	//				AppLog.logToast(context, "error web service response code - " + code);
	//			}
	//		}

	/**
	 * Gets the sensor data.
	 * 
	 * @param userArray
	 *            the user array
	 */
	//		private void fetchSensorData(ArrayList<VisualDisplaySettings> userArray) {
	//			for (int i = 0; i < userArray.size(); i++) {
	//				Calendar c = Calendar.getInstance();
	//				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	//				String formattedDate = df.format(c.getTime());
	//				formattedDate = "2013-12-18";
	//				String StartDate = formattedDate + " 00:00:00";
	//				String EndDate = formattedDate + " 23:59:59";
	//
	//				MyRequestQueue queue = MyVolley.getRequestQueue();
	//				Map<String, String> loginParam = QueryHelper.getSensorDataQuery(
	//						token, userArray.get(i).getUserID(), StartDate, EndDate);
	//				CustomRequest customRequest = new CustomRequest(Method.POST,
	//						Constants.URL_WEB_SERVICE, loginParam, this, this);
	//
	//				customRequest.setTag(this);
	//				queue.add(customRequest);
	//				queue.start();
	//			}
	//		}

	/**
	 * Gets the sensor_ data.
	 * 
	 * @param jsonObject
	 *            the json object
	 * @return the array list
	 */
	//		private ArrayList<SensorData> parseSensorData(JSONObject jsonObject) {
	//			ArrayList<SensorData> sensorDataList = new ArrayList<SensorData>();
	//			try {
	//				JSONArray jArrayuser = jsonObject.getJSONArray("user_id");
	//				JSONArray jArray = jsonObject.getJSONArray("sensor_data");		
	//
	//				for (int i = 0; i < jArray.length(); i++) {
	//					JSONObject objJson = jArray.getJSONObject(i);
	//					jsonObject = jArray.getJSONObject(i);
	//
	//					SensorData sensorData = new SensorData();
	//					sensorData.UserID = jArrayuser.getString(0);
	//					sensorData.temp_ambient = Double.parseDouble(objJson.getString("temp_ambient"));
	//					sensorData.temp_skin = Double.parseDouble(objJson.getString("temp_skin"));
	//					sensorData.pox_hr = Double.parseDouble(objJson.getString("pox_hr"));
	//					sensorData.pox_pulse = Double.parseDouble(objJson.getString("pox_pulse"));
	//					sensorData.acc_x = Double.parseDouble(objJson.getString("acc_x"));
	//					sensorData.acc_y = Double.parseDouble(objJson.getString("acc_y"));
	//					sensorData.acc_z = Double.parseDouble(objJson.getString("acc_z"));
	//					sensorData.gsr = Double.parseDouble(objJson.getString("gsr"));
	//					sensorData.led1ac = Double.parseDouble(objJson.getString("led1ac"));
	//					sensorData.led2ac = Double.parseDouble(objJson.getString("led2ac"));
	//					sensorData.led1dc = Double.parseDouble(objJson.getString("led1dc"));
	//					sensorData.led2dc = Double.parseDouble(objJson.getString("led2dc"));
	//					sensorData.updated_on = objJson.getString("updated_on");
	//					sensorDataList.add(sensorData);
	//				}
	//			} catch (Exception e) {
	//				AppLog.e(e.getMessage());
	//			}
	//			return sensorDataList;
	//		}


}

