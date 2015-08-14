package golfGame;


import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class DrivingRangeApp {


	public static void main(String[] args) throws InterruptedException {
		AtomicBoolean done  =new AtomicBoolean(false);

		//read these in as command line arguments instead of hard coding
		int noGolfers =5;
		int sizeStash=20;
		int sizeBucket=5;
		BallStash.setSizeStash(sizeStash);
		BallStash.setSizeBucket(sizeBucket);
		Golfer.setBallsPerBucket(sizeBucket);
		
		//initialize shared variables
		Range range = new Range(sizeStash);
		BallStash stash = new BallStash(sizeStash);
		AtomicBoolean cart = new AtomicBoolean(false);

		System.out.println("=======   River Club Driving Range Open  ========");
		System.out.println("======= Golfers:"+noGolfers+" balls: "+sizeStash+ " bucketSize:"+sizeBucket+"  ======");

		//create threads and set them running
		for (int i = 0; i < noGolfers; i++) {
			Golfer newGolfer = new Golfer(stash,range,cart,done);
			newGolfer.start();
		}
		Bollie myBOI = new Bollie(stash,range,done);
		myBOI.start();

		//for testing, just run for a bit
		Thread.sleep(30000);// this is an arbitrary value - you may want to make it random
		done.set(true);
		System.out.println("=======  River Club Driving Range Closing ========");

		
	}

}
