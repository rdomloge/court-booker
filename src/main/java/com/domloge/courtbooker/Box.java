package com.domloge.courtbooker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Box {
	
	private static final Logger logger = LoggerFactory.getLogger(Box.class);

	public static void create(String[] lines) {
		char borderChar = '@';
		int maxLineLen = 0;
		for (String line : lines) {
			maxLineLen = Math.max(maxLineLen, line.length());
		}
		int boxWidth = maxLineLen +10;
		StringBuilder boxTop = new StringBuilder();
		boxTop.append(repeat(borderChar, boxWidth));
		logger.info(boxTop.toString());
		
		for (String line : lines) {
			StringBuilder boxedLine = new StringBuilder();
			boxedLine.append(borderChar);
			int pad = (boxWidth - 2 - line.length()) / 2;
			boxedLine.append(repeat(' ', pad));
			boxedLine.append(line);
			if(pad*2 + 2 + line.length() >= boxWidth) {
				boxedLine.append(repeat(' ', pad));
			}
			else {
				boxedLine.append(repeat(' ', pad+1));
			}
			boxedLine.append(borderChar);
			logger.info(boxedLine.toString());
		}
		
		logger.info(boxTop.toString());
	}
	
	private static String repeat(char c, int times) {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i < times; i++) {
			sb.append(c);
		}
		return sb.toString();
	}
}
