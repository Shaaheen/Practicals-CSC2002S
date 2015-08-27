package golfGame;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
/*
	Modified by Shaaheen Sacoor
	SCRSHA001

	Class which manages the collecting and distributing golf balls from the Range and BallStash classes
	Will get all golf balls currently on the range and temporarily store them
	After this it will place the golf balls in the Balls Stash storage so as to let
	Golfers access it
	Enters to retrieve golf balls at random times
 */

public class Bollie extends Thread{

	//Instance variables
	private AtomicBoolean done;  // flag to indicate when threads should stop
	private BallStash sharedStash; //link to shared stash
	private Range sharedField; //link to shared field
	private Random waitTime; //Random time generator
	private Semaphore allowGolfersToSwing; //This is to make sure golfers wait while bollie is on the field

	//link to shared field
	public Bollie(BallStash stash, Range field, AtomicBoolean doneFlag,Semaphore allowGolfers) {
		//Set variables
		sharedStash = stash; //shared
		sharedField = field; //shared
		waitTime = new Random();//create generator
		done = doneFlag;
		allowGolfersToSwing = allowGolfers; //shared semaphore
	}
	
	/*
	Main method that gets launched when thread is started
	Will simulate the retrieving and distributing of golfballs from the Range to ball stash
	 */
	public void run() {
		//Loop until game is over
		while (done.get()!=true) {

			try {
				//This will make bollie act at random times between 0 and 5 seconds
				sleep(waitTime.nextInt(5000));

				System.out.println("*********** Bollie collecting balls   ************");

				//Stores number of permits available so as to release all of them later
				int permitsTaken = allowGolfersToSwing.availablePermits();
				//Makes sure golfers wait as they can't get a permit
				allowGolfersToSwing.drainPermits();
				//Set Bollie on field flag to true so golfers can stop swinging
				sharedField.setCartToOn();
				//Retrieves balls from the range
				golfBall[] ballsCollected = sharedField.collectAllBallsFromField();

				//If there were no balls on the field then go on to next iteration of loop
				//Hence bollie would wait for a random amount of time again and then see if theres
				//any balls on the field
				if (ballsCollected == null){
					continue;
				}

				System.out.println("Still on field - Golfers can finish swing (only one swing)");
				//To simulate the physical action of him retrieving balls
				sleep(2000);
				//Indicate that Bollie is done collecting balls
				sharedField.setCartToOff();
				//Release all the permits taken to allow all golfers to progress
				allowGolfersToSwing.release(permitsTaken);
				System.out.println("Off the field ");

				System.out.println("*********** Bollie adding balls to stash ************");
				//Distribute golf balls back into the ball stash/storage
				sharedStash.addBallsToStash(ballsCollected,ballsCollected.length);
				System.out.println("Done adding to stash");

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		}	
}
