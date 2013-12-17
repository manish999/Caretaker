package com.rampgreen.caretakermobile.model;

public class RainbowPacketModel
{


	public enum Packet {
		INSTRUCTION_PACKET("00"), STATUS_PACKET("01");

		String value;
		private Packet(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};

	/**
	 *  Constants are used to request the battery,sensorstate, etc info from health sensor device
	 *     StatusQueryPacket model variable will have any one of the defined constants.  
	 * 
	 * @author Manish Pathak
	 */
	public enum StatusQueryState {
		NO_REQUEST_FOR_INFO("0"), REQUEST_FOR_INFO("1");

		String value;
		private StatusQueryState(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};

	//packetTye:01
	class StatusQueryPacket{
		private String deviceState;
		private String sensorState;
		private String transducersState;
		private String batteryState;
		private String transducersTransmitState;
		private String requestTransmitState;
		private String Reserved;//TBD
	}

	//packetTye:00	
	class InstructionQueryPacket{
		private String temp;
		private String ppg;//heart rate
		private String acc;//accelerometer
		private String gsr;// GSR
		private String vibro;
		private String led;
		private String tts;
		private String trs;
	} 

	public enum Temp {
		LEAVE_SENSOR_UNCHANGED("00"), SENSOR_ON("01"), SENSOR_OFF("10"), SENSOR_CALIBRATE("11");

		String value;
		private Temp(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};

	public enum HeartRate {
		LEAVE_SENSOR_UNCHANGED("00"), SENSOR_ON("01"), SENSOR_OFF("10"), SENSOR_CALIBRATE("11");

		String value;
		private HeartRate(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};	

	public enum Accelerometer {
		LEAVE_SENSOR_UNCHANGED("00"), SENSOR_ON("01"), SENSOR_OFF("10"), SENSOR_CALIBRATE("11");

		String value;
		private Accelerometer(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};	

	public enum Gsr {
		LEAVE_SENSOR_UNCHANGED("00"), SENSOR_ON("01"), SENSOR_OFF("10"), SENSOR_CALIBRATE("11");

		String value;
		private Gsr(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};	

	public enum Vibro {
		LEAVE_TRANSDUCER_UNCHANGED("0"), TURN_ON_VIBRATION_TRANSDUCER("1");

		String value;
		private Vibro(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};	

	public enum Led {
		LEAVE_ALL_LED_UNCHANGED("000"), TURN_ON_LED3_YELLOW("001"), TURN_ON_LED2_YELLOW("010"), TURN_ON_LED1_YELLOW("100");
		// OTHER NEED TO BE ADDED AS IN DOC SPECIFIED.
		String value;
		private Led(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};	

	/**
	 *
	 * used to control how long the vibro and LEDs are On
	 * @author Manish Pathak
	 */
	
	public enum TTS {
		LEAVE_TIMER_UNCHANGED("00"), 
		SET_TIME_TO_SHORTEST_TIME_ON("01"), 
		SET_TIME_TO_MODERATE_DEFAULT_TIME_ON("10"), 
		SET_TIME_TO_LONGEST_TIME_ON("11");

		String value;
		private TTS(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};	


	/**
	 *
	 * used to control the data transmission rates for the device
	 * @author Manish Pathak
	 */
	public enum TRS {
		LEAVE_TRANSMISSION_RATE_UNCHANGED("00"), 
		LOWEST_TRANSMISSION_RATE("01"), 
		DEFAULT_MODERATE_TRANSMISSION_RATE("10"), 
		HIGHEST_TRANSMISSION_RATE("11"),;

		String value;
		private TRS(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};	


}
