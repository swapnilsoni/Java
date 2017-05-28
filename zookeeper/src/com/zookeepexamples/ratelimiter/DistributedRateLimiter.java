package com.zookeepexamples.ratelimiter;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreV2;
import org.apache.curator.framework.recipes.locks.Lease;
import org.apache.curator.retry.RetryNTimes;

public class DistributedRateLimiter {
	private CuratorFramework curatorFrameWork = null;
	private static final String ADDRESS = "127.0.0.1";
	private InterProcessSemaphoreV2 rateLimiter = null;
	private static final String PATH = "/rateLimiter2";
	
	public DistributedRateLimiter() throws Exception {
		start().postStartInit();
		
	}
	
	private void postStartInit() {
//		System.out.println(curatorFrameWork.isStarted());
		rateLimiter = new InterProcessSemaphoreV2(curatorFrameWork, PATH, 1);
//		rateLimiter.
	}
	
	private DistributedRateLimiter start() throws Exception {
		RetryPolicy retryPolicy = new RetryNTimes(3, (int) TimeUnit.SECONDS.toMillis(1));
		curatorFrameWork = CuratorFrameworkFactory.newClient(ADDRESS, retryPolicy);
		curatorFrameWork.start();
		curatorFrameWork.blockUntilConnected();
		return this;
	}
	
	
	public Collection<Lease> acquire() throws Exception {
		return rateLimiter.acquire(1);
	}
	
	public void release(Collection<Lease> leases) throws Exception {
		rateLimiter.returnAll(leases);
//		leases.forEach(lease -> rateLimiter.returnLease(lease));
	}
	
	private static void sleep(int milisec) {
		try {
			Thread.sleep(milisec);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		DistributedRateLimiter rateLimiter = new DistributedRateLimiter();
		
		
		System.out.println("api 1");
//		Lease lease =
		rateLimiter.acquire();
//		sleep(5000);
//		rateLimiter.release(lease);
//		System.out.println("api 2");
//		Lease lease2 = rateLimiter.acquire();
//		rateLimiter.release(lease2);
		/*Generate a number of clients*/
		
	}

}
