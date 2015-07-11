package com.useanalias.medicinereminder;

import java.util.*;

public class AlarmManager {
	
	final Time DISPLAY_RATE = new Time(0, 0, 1);
	final int DAY_LENGTH = 24 * 60 * 60 * 1000;
	
	Time current;
	ArrayList<Time> alarms;

	public AlarmManager(Time current, ArrayList<Time> alarms) {
		this.current = current;
		this.alarms = alarms;
	}
	
	public void launch() {
		scheduleTimers(this.current, this.alarms);
		displayTimers(this.current, this.alarms);
	}
	
	public void scheduleTimers(Time current, ArrayList<Time> alarms) {
		Timer alarm_timer = new Timer();
		for (Time alarm : alarms) {
			AlarmTask task = new AlarmTask(alarm);
			int delta = alarm.subtract(current).inSeconds() * 1000;
			alarm_timer.scheduleAtFixedRate(task, delta, DAY_LENGTH);
		}
	}
	
	public void displayTimers(Time current, ArrayList<Time> alarms) {
		Timer display_timer = new Timer();
		DisplayTask task = new DisplayTask(current, alarms, DISPLAY_RATE);
		display_timer.scheduleAtFixedRate(task, 0, DISPLAY_RATE.inSeconds() * 1000);
	}
}
