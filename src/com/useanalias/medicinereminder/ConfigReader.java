package com.useanalias.medicinereminder;

import java.io.*;
import java.util.*;

public class ConfigReader {
	
	public ArrayList<Time> read(String path) {
		FileInputStream fis = openFile(path);
		ArrayList<String> lines = readLines(fis);
		return parseLines(lines);
	}
	
	ArrayList<Time> parseLines(ArrayList<String> time_strings) {
		ArrayList<Time> times = new ArrayList<Time>();
		
		for (String time_string : time_strings) {
			ArrayList<String> parts = split(time_string.trim(), ':');
			int hour = Integer.parseInt(parts.get(0));
			int minute = Integer.parseInt(parts.get(1));
			int second = Integer.parseInt(parts.get(2));
			times.add(new Time(hour, minute, second));
		}
		
		return times;
	}
	
	FileInputStream openFile(String path) {
		File times = new File(path);
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(times);
		} catch (FileNotFoundException e) {
			return null;
		}
		
		return fis;
	}
	
	// Based on http://lejos.sourceforge.net/forum/viewtopic.php?t=4618
	
	String readLine(FileInputStream fis) {
		StringBuilder sb = new StringBuilder();
		
		try {
			int b = fis.read();
			if (b < 0) return null;
			
			while (b >= 0 && b != 10) {
				sb.append((char)b);
				b = fis.read();
			}
		} catch (IOException e) {
			return null;
		}
		
		return sb.toString();
	}
	
	ArrayList<String> readLines(FileInputStream fis) {
		ArrayList<String> lines = new ArrayList<String>();
		String line = "";
		
		while ((line = readLine(fis)) != null) {
			lines.add(line);
		}
		
		return lines;
	}
	
	static ArrayList<String> split(String s, char c) {
		ArrayList<String> parts = new ArrayList<String>();
		int index; 
		
		while ((index = s.indexOf(c)) > 0) {
			parts.add(s.substring(0, index));
			s = s.substring(index + 1);
		}
		
		parts.add(s);
		return parts;
	}
}
