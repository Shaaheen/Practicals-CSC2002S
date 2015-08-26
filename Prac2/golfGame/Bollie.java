package golfGame;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Bollie extends Thread{

	private AtomicBoolean done;  // flag to indicate when threads should stop
	
	private BallStash sharedStash; //link to shared stash
	private Range sharedField; //link to shared field
	private Random waitTime;

	//link to shared field
	public Bollie(BallStash stash, Range field, AtomicBoolean doneFlag) {
		sharedStash = stash; //shared 
		sharedField = field; //shared
		waitTime = new Random();
		done = doneFlag;
	}
	
	
	public void run() {
		
		//while True
		golfBall [] ballsCollected = new golfBall[sharedStash.getSizeStash()];
		while (done.get()!=true) {
			try {
				sleep(waitTime.nextInt(5000));
				//sleep(5000);
				System.out.println("*********** Bollie collecting balls   ************");
				//Goes into Field object and retrieves balls hence no other thread should interrupt the Field
				//synchronized (sharedField){
					//Michelle - sharedField.collectAllBallsFromField(ballsCollected);

					ballsCollected = sharedField.collectAllBallsFromField();

					if (ballsCollected == null){
						continue;
					}

					System.out.println("Still on field - Golfers can finish swing (only one swing)");
					sleep(2000);
					sharedField.setCartToOff();
					System.out.println("Off the field ");

				//}

				// collect balls, no golfers allowed to swing while this is happening
				synchronized (sharedStash){
					System.out.println("*********** Bollie adding balls to stash ************");
					sharedStash.addBallsToStash(ballsCollected,ballsCollected.length);
					System.out.println("Done adding to stash");
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		}	
}
