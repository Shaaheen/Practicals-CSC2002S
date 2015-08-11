package golfGame;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BallStash {
	//static variables
	private static int sizeStash=20;
	private static int sizeBucket=4;
	//ADD variables: a collection of golf balls, called stash
	golfBall[] balls;
	
	
	//ADD methods:
	public BallStash(int initStash){
		this.sizeStash = initStash;
		this.balls = new golfBall[initStash];
	}

	//getBucketBalls
	/*
		Method to "take" balls from the stack
		Just reduce the stack by the size of a bucket
	 */
	protected boolean getBucketBalls(){
		if (sizeStash >sizeBucket){
			sizeStash-=sizeBucket;
			return true;
		}
		return false;
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
