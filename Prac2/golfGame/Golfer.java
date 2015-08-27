package golfGame;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/*
	Modified by Shaaheen Sacoor
	SCRSHA001

	Class which simulates the acts of golfers
 */
public class Golfer extends Thread {

	//Done variable to indicate when the game is over
	private AtomicBoolean done;
	//flag to indicate to golfers that Bollie is on the field
	private AtomicBoolean cartOnField;
	
	private static int noGolfers; //shared amongst threads
	private  static int ballsPerBucket=4; //shared amongst threads
	
	private int myID; //Unique ID of golfer
	
	private golfBall  [] golferBucket; //Array that contains all the golf balls currently in the golfers bucket
	private BallStash sharedStash; //link to shared stash
	private Range sharedField; //link to shared field
	private Random swingTime; //random time generator for how long a golfer takes to swing
	private int numBallsInBucket; //Max amount of balls a golfer is allowed to carry
	private Semaphore sharedTees;  //Tells the golfer if there is enough tees available for the golfer to start swinging
	private int numBucketsPerGolfer; //Max amount of buckets a golfer is allowed to use
	private Semaphore allowedToSwing; //This will be used to make golfer wait when bollie is on the field
	
	
	/*
	Constructor that sets all instance variables
	 */
	public Golfer(BallStash stash,int ballPerB, Range field, AtomicBoolean cartFlag, AtomicBoolean doneFlag, Semaphore tees, int numOfBuckets,Semaphore letSwing) {
		//Set variables
		sharedStash = stash; //shared
		sharedField = field; //shared
		cartOnField = cartFlag; //shared
		ballsPerBucket = ballPerB; //local
		done = doneFlag; //shared
		golferBucket = new golfBall[ballsPerBucket];
		swingTime = new Random(); //create random number generator
		myID=newGolfID(); //unique
		numBallsInBucket = 0; //Initially have 0 balls
		sharedTees = tees; //shared
		numBucketsPerGolfer = numOfBuckets;  //local
		allowedToSwing = letSwing; //shared
	}

	/*
	Method to set unique golf IDs to each golfer
	Not synchronised as golfer threads are created in the sequential code
	 */
	public static int newGolfID() {
		noGolfers++;
		return noGolfers;
	}

	/*
	Method to return the current number of balls in the golfers bucket
	 */
	public int getBallsPerBucket () {
		return ballsPerBucket;
	}

	/*
	Main method that is run when thread is started
	Will simulate the actions of a golfer
	 */
	public void run() {

		System.out.println("!!!!!!!!!!!!!!! Golfer #" + myID + " entered the range !!!!!!!!!!!!!!!");
		//Sets intial buckets used to 0 as golfer can only use limited amount of buckets - extra credit
		int bucketsUsed = 0;

		//Loop until game is over
		while (done.get()) {

			//Golfer used all of his/her buckets - done golfing
			if (bucketsUsed > numBucketsPerGolfer){
				System.out.println("########### Golfer #" + myID + " used all his/her buckets ###########");
				break;
			}

			System.out.println(">>> Golfer #"+ myID + " trying to fill bucket with "+getBallsPerBucket()+" balls.");

			try {
				//Retrieves golf balls from the ball stash into golfers bucket
				golferBucket = sharedStash.getBucketBalls(myID);
				//Time taken to simulate the filling of the bucket
				sleep(500);
				//If driving range closes while the golfer has been waiting for balls
				//Stop the thread (i.e Golfer leaves range)
				if (done.get()){
					break; //exits loop
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//If couldn't retrieve any balls then golfer did not use any buckets yet - set back to 0
			if (golferBucket == null){
				numBallsInBucket = 0;
			}
			else{ //if golfer could retrieve any balls from stash then - used up a bucket
				numBallsInBucket = golferBucket.length;
			}

			//Simulates golfer swinging - goes through array of golf balls in bucket and hits onto field
			for (int b=0;b<numBallsInBucket;b++)
			{ //for every ball in bucket
				try {
					//Will try to get a tee so golfer can hit balls
					//(Only when starting to swing)
					if (b == 0){
						sharedTees.acquire(); //Gets tee
						System.out.println("Golfer #" + myID + " Got a Tee 				remaining Tees " + sharedTees.availablePermits());
					}

					//Random time to swing
					sleep(swingTime.nextInt(2000));
					//Hits current golf ball in bucket onto the field
					sharedField.hitBallOntoField(golferBucket[b]);
					System.out.println("Golfer #"+ myID + " hit ball #"+golferBucket[b].getID()+" onto field");


				} catch (InterruptedException e) {
					e.printStackTrace();
				} //      swing

				//!!wait for cart if necessary if cart there
				if(cartOnField.get()){
					//Wait
					try {
						//Bollie would have drained all the permits thus no permits would
						//be available to the golfer until bollie releases all of them - which he does all at once
						allowedToSwing.acquire();
						//As soon as golfer is able to pass, bollie is off the field
						//no need to hold permit anymore so release it so it can be used again
						allowedToSwing.release();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				//Last iteration of bucket - Last ball in bucket
				//Leave the tee for another golfer and have used a bucket
				if (b == numBallsInBucket -1){
					sharedTees.release(); //Make tee available
					System.out.println("Golfer #" + myID + " Left Tee			remaining Tees " + sharedTees.availablePermits());
					bucketsUsed ++; //Used a bucket
				}
			}

		}
		//End off run so golfer leaves the range
		System.out.println("XXXXXXXXXXX Golfer #" + myID + " left the range... XXXXXXXXXXX");
	}
}
