package golfGame;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
/*
	Modified by Shaaheen Sacoor
	SCRSHA001

	Ball stash class to store and keep track of all golf balls in  storage
	Golfers are able to retrieve a limited amount of golf balls at a time
	Bollie is able to fill up the storage of balls
 */

public class BallStash {

	//Instance variables of ball storage/stash
	private int sizeStash; //Storage counter
	private int sizeBucket;//Amount of balls a golfer is allowed to take
	LinkedList<golfBall> golfBallsList; //list of gloadl balls
	AtomicBoolean doneFlag; //Indicates when game is over
	
	
	//Constructor - Takes in stash size, max balls per bucket and game over flag
	public BallStash(int initStash,int sizeBucket,AtomicBoolean done){
		//Set all instance variables
		this.sizeBucket = sizeBucket;
		this.doneFlag = done;
		this.sizeStash = initStash;
		//Use linked list to store golf ball objects so as to be able
		//to push balls into it at a random order i.e in the quickest fashion
		this.golfBallsList = new LinkedList<golfBall>();

		//Initially create new golf ball object for every position in stash
		for (int i = 0; i < initStash; i++) {
			golfBallsList.push(new golfBall());
		}
	}

	/*
		Method to "take" balls from the stack
		Will remove the top balls in the linked list
		maximum balls taken is 5 but golfers can take smaller amounts if thats
		all thats left
	 */
	protected synchronized golfBall[] getBucketBalls(int golferID) throws InterruptedException {
		//This is to to store all the golf balls that will be removed from storage
		LinkedList<golfBall> tempAry = new LinkedList<golfBall>();
		golfBall[] retrieved; //stores golf balls in proper array
		int counter = 0; // keeps count of balls taken
		//If there is nothing left in stash then make golfer wait
		if (golfBallsList.isEmpty()){
			System.out.println("Golfer #" + golferID + " is waiting for stash to be filled...");
			//Make all threads that reach this point wait until Bollie notifies them to wake up
			this.wait();
			//If the driving range closes while the golfer is trying
			//to get balls then stop him from proceeding as he will be
			//told to leave
			if (doneFlag.get()){
				return null;
			}
		}
		//while there is still balls in stash to take
		while(!golfBallsList.isEmpty()){
			//If reached max amount of balls allowed then stop
			if (counter >= sizeBucket){
				break;
			}
			//Else add the golf ball to temporary stored golf balls list
			tempAry.add(golfBallsList.pop());
			//Increment counter as a new ball was retrieved
			counter++;
		}
		//If retrieved any balls then convert it into normal array
		if (tempAry.size() != 0 ){
			//Creation of retrieved method
			retrieved = new golfBall[tempAry.size()];
			//decrease stash size by balls taken
			sizeStash-=retrieved.length;

			System.out.println("Golfer #" + golferID + " filled bucket with " + retrieved.length + " balls           remaining stash: " + sizeStash);
			//Return array with all the retrieved balls found
			return tempAry.toArray(retrieved);
		}
		//Gets hit if Bollie didn't add enough balls for everyone
		return null;
	}

	/*
		Method to notify Golfers who are waiting for a bucket to leave the range
		when it closes
	 */
	public synchronized void notifyTheWaitingGolfers(){
		//Notifies all golfer thread to keep processing
		this.notifyAll();
	}

	// addBallsToStash method to allow bollie to add more golf balls to stash/storage
	protected synchronized void addBallsToStash(golfBall[] ballsCollected,int noCollected){
		//Increase size of stash seen appropriately
		sizeStash+=noCollected;
		//Add all new balls to the storage linked list
		for (int i = 0; i < ballsCollected.length; i++) {
			//Add to linked list
			golfBallsList.push(ballsCollected[i]);
		}
		//Notify all threads that are waiting for stash that it has been filled
		//and that they can try look for golf balls
		this.notifyAll();
	}
	// getBallsInStash - return number of balls in the stash

	/*
	Method to display the stash size
	 */
	public int getSizeStash () {
		return sizeStash;
	}
	
	
}
