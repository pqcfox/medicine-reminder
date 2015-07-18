package com.useanalias.medicinereminder;

import java.util.*;

import lejos.nxt.*;

public class AlarmTask extends TimerTask {

	final int LIGHT_THRESHOLD = 20;
	final int BEEP_RATE = 1 * 1000;
	final int SNOOZE_DELAY = 10 * 1000;
	final int SNOOZE_TIME = 30 * 60 * 1000;
	final SensorPort LIGHT_PORT = SensorPort.S1;
	final SensorPort TOUCH_PORT = SensorPort.S4;
	
	LightSensor ls;
	TouchSensor ts;
	boolean light = false;
	boolean snoozed = false;
	
	public AlarmTask() {
		ls = new LightSensor(LIGHT_PORT, false);
		ts = new TouchSensor(TOUCH_PORT);
	}
	
	@Override
	public void run() {
		Timer beeper = new Timer();
		beeper.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Sound.beepSequenceUp();
			}
		}, 0, BEEP_RATE);
		
		Timer press = new Timer();
		while (!light) {
			if(ls.getLightValue() > LIGHT_THRESHOLD) {
				light = true;
				beeper.cancel();
				ls.setFloodlight(true);
				
				press.schedule(new TimerTask() {
					@Override
					public void run() {
						new Timer().schedule(new AlarmTask(), SNOOZE_TIME);
						ls.setFloodlight(false);
						snoozed = true;
					}
				}, SNOOZE_DELAY);
			}
		}
		
		while (!snoozed) {
			if (ts.isPressed()) {
				press.cancel();
				ls.setFloodlight(false);
				return;
			}
		}
	}
}
