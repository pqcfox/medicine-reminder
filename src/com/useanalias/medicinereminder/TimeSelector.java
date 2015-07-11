package com.useanalias.medicinereminder;

import lejos.nxt.*;
import lejos.util.*;
import javax.microedition.lcdui.*;

public class TimeSelector {
	
	final int CENTER_X = 4;
	final int CENTER_Y = 4;
	final int[][] positions = {{4, 3}, {7, 3}, {10, 3}};
	final Time[] increments = {new Time(1, 0, 0), new Time(0, 1, 0), new Time(0, 0, 1)};
	
	public Time selectTime() {
		Time current = new Time(0, 0, 0);
		Graphics g = new Graphics();
		boolean menu_enabled = false;
		int position = 0;
		
		while (true) {
			displayTime(g, current, position, menu_enabled);
			
			switch (Button.waitForAnyPress()) {
				case Button.ID_ENTER:
					if (menu_enabled)
						return current;
					else 
						menu_enabled = true;
					break;
					
				case Button.ID_ESCAPE:
					if (menu_enabled)
						menu_enabled = false;
					else
						return null;
					break;
					
				case Button.ID_LEFT:
					if (menu_enabled)
						current = current.subtract(increments[position]);
					else
						position = (position - 1) % 3;
					break;
					
				case Button.ID_RIGHT:
					if (menu_enabled)
						current = current.add(increments[position]);
					else
						position = (position + 1) % 3;
					break;	
			}
			
			Delay.msDelay(50);
		}
	}
	
	void displayTime(Graphics g, Time time, int position, boolean menu_enabled) {
		int[] coords = positions[position]; 
		String arrows = menu_enabled ? "VV" : "vv";
		LCD.clear();
		LCD.drawString(arrows, coords[0], coords[1]);
		LCD.drawString(time.toString(), CENTER_X, CENTER_Y);
	}
}
