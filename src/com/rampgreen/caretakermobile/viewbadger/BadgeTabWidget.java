package com.rampgreen.caretakermobile.viewbadger;

import java.util.HashMap;

import com.rampgreen.caretakermobile.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TabWidget;

public class BadgeTabWidget extends TabWidget {

HashMap<Integer, Badge> map;
	
	public BadgeTabWidget(Context context) {
		super(context);
		
		map = new HashMap<Integer, Badge>();
		
	}
	
	public BadgeTabWidget(Context c, AttributeSet set){
		super(c, set);
		
		map = new HashMap<Integer, Badge>();
	}
	
	public int getBadgeNumAtIndex(int index){
		Badge b = map.get(index);
		if (b == null){
			return 0;
		}
		else{
			return b.getNum();
		}
	}
	
	public void setBadgeAtIndex(int num, int index){
		Badge b = map.get(index);
		if (b == null){
			b = new Badge();
			map.put(index, b);
		}
		b.setNum(num);
		
		this.getChildAt(index).setVisibility(View.INVISIBLE);
		this.getChildAt(index).setVisibility(View.VISIBLE);
	}
	
	@Override
	protected boolean drawChild (Canvas canvas, View child, long drawingTime){
		boolean b = super.drawChild(canvas, child, drawingTime);
		
		// figure out our index in the tabs, need it for the badge number
		int index = 0;
		for(int i=0; i < this.getTabCount(); i++){
			if (this.getChildAt(i) == child){
				index = i;
				break;
			}
		}
		
		int num = this.getBadgeNumAtIndex(index);
		
		if (num > 0){
			Bitmap src = BitmapFactory.decodeResource(this.getResources(),R.drawable.badge);
			Bitmap badge = Bitmap.createScaledBitmap(src, 34, 29, true);
			
			int x =  child.getRight()-badge.getWidth()-5;
			
			canvas.drawBitmap(badge, x, 0, new Paint());
			
			Typeface face = Typeface.create("Verdana", Typeface.BOLD);
			Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			paint.setTypeface(face);
			paint.setTextSize(12);
			paint.setARGB(255, 255, 255, 255);
			
			String text = ""+num;
			Rect bounds = new Rect();
			paint.getTextBounds(text, 0, text.length(), bounds);
			
			canvas.drawText(
					text, 
					(x+badge.getWidth()/2)-bounds.width()/2, 
					// -1 here because the badge icon is looking as it does with some
					// more space at the bottom for shadows, might need change if the
					// size of the icon is changed
					(badge.getHeight()/2)+bounds.height()/2 -2, 
					paint);
			
		}
		
		return b;
	}
	
	private class Badge {
		int num;
		
		public Badge(){
			num = 0;
		}
		
		public void setNum(int num){
			this.num = num;
		}
		
		public int getNum(){
			return num;
		}		
	}
}
