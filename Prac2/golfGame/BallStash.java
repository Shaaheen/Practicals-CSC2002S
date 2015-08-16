package golfGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class BallStash {
	//static variables
	private int sizeStash;
	private int sizeBucket=4;
	//ADD variables: a collection of golf balls, called stash
	golfBall[] balls;
	LinkedList<golfBall> golfBallsList;
	AtomicBoolean doneFlag;
	
	
	//ADD methods:
	public BallStash(int initStash,AtomicBoolean done){
		this.doneFlag = done;
		this.sizeStash = initStash;
		this.balls = new golfBall[initStash];
		this.golfBallsList = new LinkedList<golfBall>();

		for (int i = 0; i < initStash; i++) {
			golfBallsList.push(new golfBall());
		}
	}

	/*
		Method to "take" balls from the stack
		Just reduce the stack by the size of a bucket
	 */
	protected synchronized golfBall[] getBucketBalls(int golferID) throws InterruptedException {
		ArrayList<golfBall> tempAry = new ArrayList<golfBall>();
		golfBall[] retrieved;
		int counter = 0;
		if (golfBallsList.isEmpty()){
			System.out.println("Golfer #" + golferID + " is waiting for stash to be filled...");
			this.wait();
			//If the driving range closes while the golfer is trying
			//to get balls then stop him from proceeding as he will be
			//told to leave
			if (doneFlag.get()){
				return null;
			}
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

			System.out.println("Golfer #" + golferID + " filled bucket with " + retrieved.length + " balls           remaining stash: " + sizeStash);
			return tempAry.toArray(retrieved);
		}
		//Gets hit if Bollie didn't add enough balls for everyone
		return null;
	}

	/*
		Method to notify Golfers who are waiting for a bucket to leave the range
		when it closes
	 */
	protected synchronized void notifyTheWaitingGolfers(){
		this.notifyAll();
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
		sizeStash+=noCollected;
		for (int i = 0; i < ballsCollected.length; i++) {
			golfBallsList.push(ballsCollected[i]);
		}
		this.notifyAll();
	}
	// getBallsInStash - return number of balls in the stash
	
	
	//getters and setters for static variables - you need to edit these
	public void setSizeBucket (int noBalls) {
		sizeBucket=noBalls;
	}
	public int getSizeBucket () {
		return sizeBucket;
	}
	public void setSizeStash (int noBalls) {
		sizeStash=noBalls;
	}
	public int getSizeStash () {
		return sizeStash;
	}
	
	
}
