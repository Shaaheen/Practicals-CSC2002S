package golfGame;


import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class DrivingRangeApp {


	public static void main(String[] args) throws InterruptedException {
		AtomicBoolean done  =new AtomicBoolean(false);
		Random golferEntranceTime = new Random();
		Semaphore teesAvailable = new Semaphore(5);
		int numBucketsPerGolfer = 3;

		
		//read these in as command line arguments instead of hard coding
		int noGolfers =5;
		int sizeStash=40;
		int sizeBucket=5;
		
		//initialize shared variables
		BallStash stash = new BallStash(sizeStash,done);
		stash.setSizeStash(sizeStash);
		stash.setSizeBucket(sizeBucket);

		AtomicBoolean cart = new AtomicBoolean(false);
		Range range = new Range(sizeStash,cart);

		System.out.println("=======   River Club Driving Range Open  ========");
		System.out.println("======= Golfers:"+noGolfers+" balls: "+sizeStash+ " bucketSize:"+sizeBucket+"  ======");

		//create threads and set them running
		for (int i = 0; i < noGolfers; i++) {
			Golfer newGolfer = new Golfer(stash,range,cart,done,teesAvailable,numBucketsPerGolfer);
			newGolfer.setBallsPerBucket(sizeBucket);
			Thread.sleep(golferEntranceTime.nextInt(3000));
			newGolfer.start();
		}
		Bollie myBOI = new Bollie(stash,range,done);
		myBOI.start();

		//for testing, just run for a bit
		Thread.sleep(30000);// this is an arbitrary value - you may want to make it random
		done.set(true);
		System.out.println("=======  River Club Driving Range Closing ========");
		stash.notifyTheWaitingGolfers();
	}

}
