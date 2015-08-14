package golfGame;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Golfer extends Thread {

	//remember to ensure thread saftey
	
	private AtomicBoolean done; 
	private AtomicBoolean cartOnField;
	
	private static int noGolfers; //shared amoungst threads
	private  static int ballsPerBucket=4; //shared amoungst threads
	
	private int myID;
	
	private golfBall  [] golferBucket;
	private BallStash sharedStash; //link to shared stash
	private Range sharedField; //link to shared field
	private Random swingTime;
	
	
	
	Golfer(BallStash stash,Range field, AtomicBoolean cartFlag, AtomicBoolean doneFlag) {
		sharedStash = stash; //shared 
		sharedField = field; //shared
		cartOnField = cartFlag; //shared
		done = doneFlag;
		golferBucket = new golfBall[ballsPerBucket];
		swingTime = new Random();
		myID=newGolfID();
	}

	public static int newGolfID() {
		noGolfers++;
		return noGolfers;
	}
	
	public static void setBallsPerBucket (int noBalls) {
		ballsPerBucket=noBalls;
	}
	public static int getBallsPerBucket () {
		return ballsPerBucket;
	}
	public void run() {
		
	while (done.get()!=true) {

		if (golferBucket == null){
			continue;
		}

			System.out.println(">>> Golfer #"+ myID + " trying to fill bucket with "+getBallsPerBucket()+" balls.");
		try {
			golferBucket = sharedStash.getBucketBalls();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("<<< Golfer #"+ myID + " filled bucket with          "+getBallsPerBucket()+" balls");
			
		if (golferBucket == null){
			continue;
		}
		for (int b=0;b<golferBucket.length;b++)
		{ //for every ball in bucket
			
		    try {
				sleep(swingTime.nextInt(2000));
				sharedField.hitBallOntoField(golferBucket[b]);
				System.out.println("Golfer #"+ myID + " hit ball #"+golferBucket[b].getID()+" onto field");
				
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			} //      swing

			if (done.get()){
				break;
			}
		    //!!wait for cart if necessary if cart there
		}
		
	      
	}
	}	
}
