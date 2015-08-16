package golfGame;

import java.util.Random;
import java.util.concurrent.Semaphore;
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
	private Semaphore sharedTees;
	
	
	
	Golfer(BallStash stash,Range field, AtomicBoolean cartFlag, AtomicBoolean doneFlag,Semaphore tees) {
		sharedStash = stash; //shared 
		sharedField = field; //shared
		cartOnField = cartFlag; //shared
		done = doneFlag;
		golferBucket = new golfBall[ballsPerBucket];
		swingTime = new Random();
		myID=newGolfID();
		numBallsInBucket = 0;
		sharedTees = tees;
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

		System.out.println("!!!!!!!!!!!!!!! Golfer #" + myID + " entered the range !!!!!!!!!!!!!!!");

		while (done.get()!=true) {

			System.out.println(">>> Golfer #"+ myID + " trying to fill bucket with "+getBallsPerBucket()+" balls.");

			try {
				golferBucket = sharedStash.getBucketBalls(myID);
				sleep(500);
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
			for (int b=0;b<numBallsInBucket;b++)
			{ //for every ball in bucket

				try {
					//Will try to get a tee so golfer can hit balls
					//(Only when starting to swing)
					if (b == 0){
						sharedTees.acquire();
						System.out.println("Golfer #" + myID + " Got a Tee 				remaining Tees " + sharedTees.availablePermits());
					}

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

				if (b == numBallsInBucket -1){
					sharedTees.release();
					System.out.println("Golfer #" + myID + " Left Tee			remaining Tees " + sharedTees.availablePermits());
				}
			}

		}
		System.out.println("XXXXXXXXXXX Golfer #" + myID + " left the range... XXXXXXXXXXX");
	}
}
