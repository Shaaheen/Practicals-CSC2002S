package golfGame;

import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BallStash {
	//static variables
	private static int sizeStash=20;
	private static int sizeBucket=4;
	//ADD variables: a collection of golf balls, called stash
	golfBall[] balls;
	LinkedList<golfBall> golfBallsList;
	
	
	//ADD methods:
	public BallStash(int initStash){
		this.sizeStash = initStash;
		this.balls = new golfBall[initStash];
		golfBallsList = new LinkedList<golfBall>();
		for (int i = 0; i < initStash; i++) {
			golfBallsList.push(new golfBall());
		}
	}

	/*
		Method to "take" balls from the stack
		Just reduce the stack by the size of a bucket
	 */
	protected synchronized golfBall[] getBucketBalls(){
		if (sizeStash >sizeBucket){
			sizeStash-=sizeBucket;
			System.out.println("Size of stash reduced from " + (sizeStash+sizeBucket) + " to " + sizeStash);
			return getBallArray(sizeBucket);
		}
		System.out.println("Empty");
		return null;
	}

	/*
		Method to get golf balls in form of array instead of linked list
	 */
	private golfBall[] getBallArray(int numBalls){
		golfBall[] forGolfer = new golfBall[numBalls];
		for (int i = 0; i < numBalls; i++) {
			forGolfer[i] = golfBallsList.pop();
		}
		return forGolfer;
	}

	// addBallsToStash
	protected void addBallsToStash(int ballsCollected,int noCollected){
		sizeStash+=noCollected;
	}
	// getBallsInStash - return number of balls in the stash
	
	
	//getters and setters for static variables - you need to edit these
	public static  void setSizeBucket (int noBalls) {
		sizeBucket=noBalls;
	}
	public static int getSizeBucket () {
		return sizeBucket;
	}
	public static void setSizeStash (int noBalls) {
		sizeStash=noBalls;
	}
	public static int getSizeStash () {
		return sizeStash;
	}
	
	
}
