package golfGame;

import java.util.ArrayList;
import java.util.Arrays;
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
	protected golfBall[] getBucketBalls() throws InterruptedException {
		synchronized (this){
			ArrayList<golfBall> tempAry = new ArrayList<golfBall>();
			golfBall[] retrieved;
			int counter = 0;
			if (golfBallsList.isEmpty()){
				System.out.println("Empty waiting to be filled...");
				this.wait();
			}
			while(!golfBallsList.isEmpty()){
				if (counter >= sizeBucket){
					break;
				}
				tempAry.add(golfBallsList.pop());
				counter++;
			}
			if (tempAry.size() != 0 ){
				retrieved = new golfBall[tempAry.size()];
				sizeStash-=retrieved.length;
				System.out.println("Size of stash reduced from " + (sizeStash+retrieved.length) + " to " + sizeStash);
				return tempAry.toArray(retrieved);
			}

			return null;
		}
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
	protected synchronized void addBallsToStash(golfBall[] ballsCollected,int noCollected){
		this.notifyAll();
		sizeStash+=noCollected;
		for (int i = 0; i < ballsCollected.length; i++) {
			golfBallsList.push(ballsCollected[i]);
		}
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
