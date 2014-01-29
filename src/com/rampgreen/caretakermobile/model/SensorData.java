package com.rampgreen.caretakermobile.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class SensorData.
 */
@SuppressWarnings("serial")
public class SensorData implements Serializable {

	public String UserID;
	/** The temp_ambient. */
	public double temp_ambient;
	
	/** The temp_skin. */
	public double temp_skin;
	
	/** The pox_hr. */
	public double pox_hr;
	
	/** The pox_pulse. */
	public double pox_pulse;
	
	/** The acc_x. */
	public double acc_x;
	
	/** The acc_y. */
	public double acc_y;
	
	/** The acc_z. */
	public double acc_z;
	
	/** The gsr. */
	public double gsr;
	
	/** The led1ac. */
	public double led1ac;
	
	/** The led2ac. */
	public double led2ac;
	
	/** The led1dc. */
	public double led1dc;
	
	/** The led2dc. */
	public double led2dc;
	
	public String updated_on;
	
	public String GetUserID(){
		return this.UserID;
	}
	public String gettime(){
		return this.updated_on;
	}
	/**
	 * Gets the temp_ambient.
	 *
	 * @return the temp_ambient
	 */
	public double gettemp_ambient() {
		return this.temp_ambient;
	}

	/**
	 * Gets the temp_skin.
	 *
	 * @return the temp_skin
	 */
	public double gettemp_skin() {
		return this.temp_skin;
	}

	/**
	 * Gets the pox_hr.
	 *
	 * @return the pox_hr
	 */
	public double getpox_hr() {
		return this.pox_hr;
	}

	/**
	 * Gets the pox_pulse.
	 *
	 * @return the pox_pulse
	 */
	public double getpox_pulse() {
		return this.pox_pulse;
	}
	
	/**
	 * Gets the acc_x.
	 *
	 * @return the acc_x
	 */
	public double getacc_x() {
		return this.acc_x;
	}

	/**
	 * Gets the acc_y.
	 *
	 * @return the acc_y
	 */
	public double getacc_y() {
		return this.acc_y;
	}

	/**
	 * Gets the acc_z.
	 *
	 * @return the acc_z
	 */
	public double getacc_z() {
		return this.acc_z;
	}

	/**
	 * Gets the gsr.
	 *
	 * @return the gsr
	 */
	public double getgsr() {
		return this.gsr;
	}
	
	/**
	 * Gets the led1ac.
	 *
	 * @return the led1ac
	 */
	public double getled1ac() {
		return this.led1ac;
	}

	/**
	 * Gets the led2ac.
	 *
	 * @return the led2ac
	 */
	public double getled2ac() {
		return this.led2ac;
	}

	/**
	 * Gets the led1dc.
	 *
	 * @return the led1dc
	 */
	public double getled1dc() {
		return this.led1dc;
	}

	/**
	 * Gets the led2dc.
	 *
	 * @return the led2dc
	 */
	public double getled2dc() {
		return this.led2dc;
	}

	/**
	 * Instantiates a new sensor data.
	 */
	public SensorData() {
		super();
	}

}