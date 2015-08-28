import golfGame.*;
/*
	Modified by Shaaheen Sacoor
	SCRSHA001

	Main class to manage and create all appropriate thread objects with correct parameters
	Also, determines start and end of driving range and when golfers will enter
 */

import java.net.Inet4Address;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class DrivingRangeApp {

	public static void main(String[] args) throws InterruptedException {

		//Random time generator that will tell us when to make a Golfer enter the range
		Random golferEntranceTime = new Random();
		//Random time to tell you when to close
		Random closingTime = new Random();

		//INITIAL VARIABLES SET TO DEFAULT VALUES
		//CHANGED BELOW WITH COMMAND LINE PARAMS
		int noGolfers =5; //Variable to loop through to create Golfer threads
		int sizeStash=40; //Gets variable to set the initial size of the ball stash
		int sizeBucket=5; // stores the max amount of balls a golfer can keep

		//Extra Credit Variables
		int numBucketsPerGolfer = 3; //Max amount of buckets a golfer can use

		//Semaphore to keep track of Tees and fairness setting at true so Golfers get First In
		Semaphore teesAvailable = new Semaphore(5,true);

		//Semaphore to make the golfers wait for Bollie on the field
		// Bollie controls the permits for all golfers to make sure
		//they don't come on to the field
		//This is done to avoid spinning
		Semaphore blockGolfers = new Semaphore(noGolfers);

		// Done flag to indicate when driving range is closed
		AtomicBoolean done  =new AtomicBoolean(false);

		//Change initial variables to given command line
		if (args.length >=3){
			//Order Given in Assignment report
			noGolfers = Integer.parseInt(args[0]);
			sizeStash = Integer.parseInt(args[1]);
			sizeBucket = Integer.parseInt(args[2]);
		}

		//If given extra credit variables in command line
		if (args.length >=5){
			numBucketsPerGolfer = Integer.parseInt(args[3]);
			teesAvailable = new Semaphore(Integer.parseInt(args[4]),true);
		}

		//Share stash of golf balls between golfers - passed size of stash and done flag
		BallStash stash = new BallStash(sizeStash,sizeBucket,done);

		//Cart variable so golfer knows when Bollie is collecting golf balls
		AtomicBoolean cart = new AtomicBoolean(false);
		//Share range object which serves as the field balls are hit onto
		Range range = new Range(cart);

		//Print to screen that range is open
		System.out.println("================   River Club Driving Range Open  ========================");
		System.out.println("======= Golfers:"+noGolfers+" balls: "+sizeStash+ " bucketSize:"+sizeBucket+ " buckets per Golfer: " + numBucketsPerGolfer + " Tees: " + teesAvailable.availablePermits() +  "  ======");

		//Golfers share same random number generator to prevent
		//the same sequence of numbers appearing in different threads
		Random swingTimes = new Random();
		//Launch golfers (Allow golfers to enter range)
		for (int i = 0; i < noGolfers; i++) {
			//Creating Golfer thread with all necessary parameters
			Golfer newGolfer = new Golfer(stash,sizeBucket,range,cart,done,teesAvailable,numBucketsPerGolfer,blockGolfers,swingTimes);

			//Will sleep for a random amount of time so golfers enter field randomly
			Thread.sleep(golferEntranceTime.nextInt(3000));

			newGolfer.start();  //Runs thread
		}
		//Creation of Bollie thread which will collect balls from range and deliver to stash
		Bollie myBOI = new Bollie(stash,range,done,blockGolfers);
		myBOI.start(); //Runs Bollie

		//Make current thread sleep so other threads get priority on main console
		Thread.sleep(closingTime.nextInt(20000) + 5000);// Random time between 5 seconds and 30 seconds
		done.set(true); //Set to game over
		System.out.println("===============================  River Club Driving Range Closing ========================");
		//Notify all golfers that are waiting for golf balls for their bucket to leave
		stash.notifyTheWaitingGolfers();
	}

}
