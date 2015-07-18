package com.useanalias.medicinereminder;

import java.util.*;
import lejos.nxt.*;

public class MedicineReminder {
	
	static String times_path = "medicine_times.txt";
	
	public static void main(String[] args) {
		Sound.setVolume(Sound.VOL_MAX);
		ArrayList<Time> alarms = new ConfigReader().read(times_path);
		if (alarms == null)
			exitWithError("Could not read " + times_path);
		Time current_time = new TimeSelector().selectTime();
		if (current_time == null)
			System.exit(0);
		new AlarmManager(current_time, alarms).launch();
	}
	
	static void exitWithError(String error) {
		System.out.println("ERROR: " + error);
		Button.waitForAnyPress();
		System.exit(1);
	}
}
