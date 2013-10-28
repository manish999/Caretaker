package com.rampgreen.caretakermobile.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ValueUtil {
	public static double round(double paramDouble, int paramInt,
			RoundingMode paramRoundingMode) {
		return new BigDecimal(paramDouble)
		.setScale(paramInt, paramRoundingMode).doubleValue();
	}

	public static BigDecimal roundDouble(Double paramDouble) {
		return new BigDecimal(String.valueOf(paramDouble)).setScale(2, 4);
	}

	public static double roundTwoDecimals(double doubleValue) {
		return Double.valueOf(new DecimalFormat("#.##").format(doubleValue))
				.doubleValue();
	}
	public static double roundOneDecimals(double doubleValue) {
		return Double.valueOf(new DecimalFormat("#.#").format(doubleValue))
				.doubleValue();
	}

	public static ArrayList<Double> calcPercentage(ArrayList<Double> list) {
		Double sum = 0.0;
		for (Double object : list) {
			sum = sum + object.doubleValue();
		}

		for (Double item : list) {
			item = roundTwoDecimals((item * 100)/sum); 
		}
		return list;
	}
}