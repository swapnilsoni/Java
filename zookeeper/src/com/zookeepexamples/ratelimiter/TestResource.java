package com.zookeepexamples.ratelimiter;

import java.util.Timer;
import java.util.TimerTask;

public class TestResource  {
	private static int count = 0;
	private static Timer timer = new Timer();
	static {
		timer.schedule(new Counter(), 0, 1000);
	}
	
	public void service() {
//		System.out.println("called");
			count++;
	}
		
	 
	
	static class Counter extends TimerTask {
		
		@Override
		public void run() {
			System.out.println(count + "/sec");
			count = 0;
		}
		
	}
}
