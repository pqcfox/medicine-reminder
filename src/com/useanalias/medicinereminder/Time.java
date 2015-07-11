package com.useanalias.medicinereminder;

public class Time implements Comparable<Time>{
	int hour, minute, second;
	
	public Time(int hour, int minute, int second) {
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}
	
	void normalize() {
		int[] div_second = reduce(this.second, 60);
		this.second = div_second[0];
		this.minute += div_second[1];
		
		int[] div_minute = reduce(this.minute, 60);
		this.minute = div_minute[0];
		this.hour += div_minute[1];
		
		this.hour = reduce(this.hour, 24)[0];
	}
	
	int[] reduce(int a, int m) {
		int rem = 0;
		while (a < 0 || a >= m) {
			if (a < 0) {
				rem -= 1;
				a += m;
			} else {
				rem += 1;
				a -= m;
			}
		}
		
		int[] result = {a, rem};
		return result;
	}
	
	public int inSeconds() {
		return 3600 * this.hour + 60 * this.minute + this.second;
	}
	
	@Override
	public String toString() {
		String hour_pad = Integer.toString(this.hour);
		String minute_pad = Integer.toString(this.minute);
		String second_pad = Integer.toString(this.second);
		String[] pads = {hour_pad, minute_pad, second_pad};
		for(int i = 0; i < pads.length; i++) {
			if(pads[i].length() < 2) {
				pads[i] = "0" + pads[i];
			}
		}
		
		return pads[0] + ":" + pads[1] + ":" + pads[2];
	}
	
	public void setHour(int hour) {
		this.hour = hour;
	}
	
	public void setMinute(int minute) {
		this.minute = minute;
	}
	
	public void setSecond(int second) {
		this.second = second;
	}
	
	public Time add(Time other) {
		Time sum = new Time(0, 0, 0);
		sum.setHour(this.hour + other.hour);
		sum.setMinute(this.minute + other.minute);
		sum.setSecond(this.second + other.second);
		sum.normalize();
		return sum;
	}
	
	public Time subtract(Time other) {
		Time diff = new Time(0, 0, 0);
		diff.setHour(this.hour - other.hour);
		diff.setMinute(this.minute - other.minute);
		diff.setSecond(this.second - other.second);
		diff.normalize();
		return diff;
	}

	@Override
	public int compareTo(Time other) {
		return Integer.signnum(inSeconds() - other.inSeconds());
	}
}
