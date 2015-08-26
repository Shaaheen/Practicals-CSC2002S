import golfGame.*;
/*
	Modified by Shaaheen Sacoor
	SCRSHA001
	
 */

import java.net.Inet4Address;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class DrivingRangeApp {


	public static void main(String[] args) throws InterruptedException {
		AtomicBoolean done  =new AtomicBoolean(false);

		//Random time generator that will tell us when to make a Golfer enter the range
		Random golferEntranceTime = new Random();
		Random closingTime = new Random();

		//Semaphore to keep track of Tees and fairness setting at true so Golfers get First In
		//First Out priority with Queues
		Semaphore teesAvailable = new Semaphore(5,true);
		int numBucketsPerGolfer = 3;


		//read these in as command line arguments instead of hard coding
		int noGolfers =5;
		int sizeStash=40;
		int sizeBucket=5;

		if (args.length >=3){
			noGolfers = Integer.parseInt(args[0]);
			sizeStash = Integer.parseInt(args[1]);
			sizeBucket = Integer.parseInt(args[2]);
		}
		
		//initialize shared variables

		//Share stash of golf balls between golfers - passed size of stash and done flag
		//Needs to know when game is over so as to tell empty bucketed golfers to leave
		//If range is closed
		BallStash stash = new BallStash(sizeStash,done);
		stash.setSizeStash(sizeStash);
		stash.setSizeBucket(sizeBucket);

		//Cart variable so golfer knows when Bollie is collecting golf balls
		AtomicBoolean cart = new AtomicBoolean(false);
		//Share range object which serves as the field balls are hit onto
		Range range = new Range(sizeStash,cart);

		System.out.println("================   River Club Driving Range Open  ========================");
		System.out.println("======= Golfers:"+noGolfers+" balls: "+sizeStash+ " bucketSize:"+sizeBucket+"  ======");

		//create threads and set them running
		//Creating Golfer threads with all neccessary parameters
		for (int i = 0; i < noGolfers; i++) {

			Golfer newGolfer = new Golfer(stash,range,cart,done,teesAvailable,numBucketsPerGolfer);
			newGolfer.setBallsPerBucket(sizeBucket);
			//Will sleep for a random amount of time so golfers enter field randomly
			Thread.sleep(golferEntranceTime.nextInt(3000));
			newGolfer.start();  //Runs thread
		}
		//Creation of Bollie thread
		Bollie myBOI = new Bollie(stash,range,done);
		myBOI.start(); //Runs Bollie

		//Make current thread sleep so other threads get priority on main console
		Thread.sleep(closingTime.nextInt(20000) + 5000);// Random time between 5 seconds and 30 seconds
		done.set(true); //Set to game over
		System.out.println("===============================  River Club Driving Range Closing ========================");
		//Notify all golfers that are waiting for golf balls for their bucket to leave
		stash.notifyTheWaitingGolfers();
	}

}
