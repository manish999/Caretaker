package com.rampgreen.caretakermobile.model;

import java.util.ArrayList;

class VisualDisplaySettings  extends BaseDeleteSettings {
		public VisualDisplaySettings(String userId)
		{
			super(userId);
		}
		
		public VisualDisplaySettings(String userId, String diseaseType) {
			super(userId, diseaseType);
		}
		
		
		private int x;
		private int y;
		@Override
		public ArrayList<?> getSettingList()
		{
			// TODO Auto-generated method stub
			return null;
		}
		
	}