package com.zookeepexamples.ratelimiter;

import java.util.Collection;
import java.util.Collections;

import org.apache.curator.framework.recipes.locks.Lease;

public class Client {
	public static void main(String[] args) throws Exception {
		Runnable client1 = new Runnable() {
			TestResource client = new TestResource();
			DistributedRateLimiter rateLimiter = new DistributedRateLimiter();
			@Override
			public void run() {
				while(true) {
					try {
						Collection<Lease> leases = rateLimiter.acquire();
						client.service();
						Thread.sleep(500);
						rateLimiter.release(leases);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		new Thread(client1).start();
		
		Runnable client2 = new Runnable() {
			TestResource client = new TestResource();
			DistributedRateLimiter rateLimiter = new DistributedRateLimiter();
			@Override
			public void run() {
				while(true) {
					try {
						Collection<Lease> leases = rateLimiter.acquire();
						client.service();
						Thread.sleep(500);
						rateLimiter.release(leases);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}
		};
		
		new Thread(client2).start();
	}
}
