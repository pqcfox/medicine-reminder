package com.useanalias.medicinereminder;

import java.util.*;

import lejos.nxt.*;
import lejos.util.Delay;

public class AlarmTask extends TimerTask {

	final int LIGHT_THRESHOLD = 35;
	
	Time time;
	LightSensor ls;
	
	public AlarmTask(Time time) {
		this.time = time;
		ls = new LightSensor(SensorPort.S1);
		Sound.setVolume(Sound.VOL_MAX);
	}
	
	@Override
	public void run() {
		while (ls.readValue() < LIGHT_THRESHOLD) {
			Sound.beepSequenceUp();
			Delay.msDelay(500);
		}
	}
}
