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
	private int numBallsInBucket;
	
	
	
	Golfer(BallStash stash,Range field, AtomicBoolean cartFlag, AtomicBoolean doneFlag) {
		sharedStash = stash; //shared 
		sharedField = field; //shared
		cartOnField = cartFlag; //shared
		done = doneFlag;
		golferBucket = new golfBall[ballsPerBucket];
		swingTime = new Random();
		myID=newGolfID();
		numBallsInBucket = 0;
	}

	public static int newGolfID() {
		noGolfers++;
		return noGolfers;
	}
	
	public void setBallsPerBucket (int noBalls) {
		ballsPerBucket=noBalls;
	}
	public int getBallsPerBucket () {
		return ballsPerBucket;
	}
	public void run() {
		//Variable kept in attempt to store a more accurate measure of the remaining stash
		//as threads all accessing the stash at the same time results in the incorrect
		//stash remaining amount displayed
		//int remainingStash = 0;
		while (done.get()!=true) {

			System.out.println(">>> Golfer #"+ myID + " trying to fill bucket with "+getBallsPerBucket()+" balls.");

			try {
				golferBucket = sharedStash.getBucketBalls(myID);
				//remainingStash = sharedStash.getSizeStash();
				//If driving range closes while the golfer has been waiting for balls
				//Stop the thread (i.e Golfer leaves range)
				if (done.get()){
					break; //exits loop
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (golferBucket == null){
				numBallsInBucket = 0;
			}
			else{
				numBallsInBucket = golferBucket.length;
			}
			//System.out.println("<<< Golfer #"+ myID + " filled bucket with          " +numBallsInBucket + " balls" + " remaining stash " + remainingStash);
			for (int b=0;b<numBallsInBucket;b++)
			{ //for every ball in bucket

				try {
					sleep(swingTime.nextInt(2000));
					sharedField.hitBallOntoField(golferBucket[b]);
					System.out.println("Golfer #"+ myID + " hit ball #"+golferBucket[b].getID()+" onto field");


				} catch (InterruptedException e) {
					e.printStackTrace();
				} //      swing

				//!!wait for cart if necessary if cart there
				while(cartOnField.get()){
					//Wait
				}
				//break;
			}
		}
		System.out.println("Golfer #" + myID + " left the range...");
	}
}
