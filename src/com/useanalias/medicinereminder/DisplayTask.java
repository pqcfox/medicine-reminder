package com.useanalias.medicinereminder;

import java.util.*;
import lejos.nxt.*;

public class DisplayTask extends TimerTask {

	final int[] clock_position = {4, 2};
	final int[][] alarm_positions = {{4, 4}, {4, 5}, {4, 6}};
	final int[][] debug_positions = {{15, 0}, {0, 0}};
	final int[] indicator_position = {2, 4};
	
	Time current;
	ArrayList<Time> alarms;
	Time rate;
	LightSensor ls;
	TouchSensor ts;
	boolean debug = false;
	
	public DisplayTask(Time current, ArrayList<Time> alarms, Time rate) {
		this.current = current;
		this.alarms = alarms;
		this.rate = rate;
		this.ls = new LightSensor(SensorPort.S1, false);
		this.ts = new TouchSensor(SensorPort.S4);
		
		Button.ENTER.addButtonListener(new ButtonListener() {
			@Override
			public void buttonPressed(Button b) {
				debug = !debug;
			}

			@Override
			public void buttonReleased(Button b) {}
		});
	}

	@Override
	public void run() {
		LCD.clear();
		this.current = this.current.add(this.rate);
		ArrayList<Time> nearest = getClosest(current, alarms, alarm_positions.length);
		LCD.drawString(current.toString(), clock_position[0], clock_position[1]);
		LCD.drawString(">", indicator_position[0], indicator_position[1]);
		
		for (int i = 0; i < nearest.size(); i++) {
			int[] position = alarm_positions[i];
			LCD.drawString(nearest.get(i).toString(), position[0], position[1]);
		}
				
		if(debug) {
			LCD.drawString(Integer.toString(this.ls.readValue()), debug_positions[0][0], debug_positions[0][1]);
			LCD.drawString(this.ts.isPressed() ? "T" : "F", debug_positions[1][0], debug_positions[1][1]);
		}
	}

	ArrayList<Time> getClosest(Time current, ArrayList<Time> alarms, int max_count) {
		ArrayList<Time> deltas = new ArrayList<Time>();
		ArrayList<Time> closest = new ArrayList<Time>();
		int count = alarms.size() < max_count ? alarms.size() : max_count;
		
		for (Time alarm : alarms) {
			deltas.add(alarm.subtract(current));
		}
		
		ArrayList<Time> sorted_deltas = sorted(deltas);
						
		for (int i = 0; i < count; i++) {
			int index = deltas.indexOf(sorted_deltas.get(i));
			closest.add(alarms.get(index));
		}
				
		return closest;
	}
	
	@SuppressWarnings("unchecked")
	<T> ArrayList<T> sorted(ArrayList<T> list) {
		T[] array = (T[]) new Object[list.size()];
		list.toArray(array);
		Arrays.sort(array);
		ArrayList<T> sorted = new ArrayList<T>();
		
		for (T item : array) {
			sorted.add(item);
		}
		
		return sorted;
	}
}
