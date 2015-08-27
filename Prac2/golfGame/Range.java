package golfGame;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/*
	Modified by Shaaheen Sacoor
	SCRSHA001

	Class that will act as the range and store any balls that are hit on to the field here
	All balls on field are kept here until bollie can remove from this class
 */
public class Range {

	//Instance variables
	private AtomicBoolean cartOnField; //Flag for if Bollie is on field
	AtomicInteger numBallsOnField; //Indicates number of balls on the field currently
	LinkedList<golfBall> ballsOnFieldList; //List to store all golf ball objects in

	/*
	Constructor for Range
	-Creates list to store golf balls
	-Stores Bollie flag
	 */
	public Range(AtomicBoolean cart){
		//Create new list to store golf balls
		ballsOnFieldList = new LinkedList<golfBall>();
		//Int to store balls on field
		numBallsOnField = new AtomicInteger();
		//set flag variable
		cartOnField = cart;
	}

	/*
	Method to allow balls to hit on field
	SYNCHRONISED INSIDE METHOD
	 */
	protected void hitBallOntoField(golfBall ball){
		//Synchronized to prevent interleaving where threads get the linked
		//list at the same time and add to at same time, thus losing a write
		//So golf balls don't get lost
		synchronized (this){
			ballsOnFieldList.push(ball);
		}
		//This is an atomic variable so doesn't need to be synchronized
		numBallsOnField.getAndIncrement();
	}

	/*
	Method to allow Bollie collect Balls from the field
	 */
	protected synchronized golfBall[] collectAllBallsFromField(){
		//Set Bollie on field flag to true so Golfers know Bollie is on the field
		cartOnField.set(true);
		System.out.println("Collecting golf balls Num: " + numBallsOnField.get() + " Balls : " + Arrays.toString(ballsOnFieldList.toArray()));
		//Get array of golf Balls of all golf Balls currently on the field
		golfBall[] collected = new golfBall[ballsOnFieldList.size()]; //Creates new array that can store all balls on the field
		//Put all balls on field into a new array
		for (int i = 0; i < collected.length; i++) {
			collected[i] = ballsOnFieldList.pop();
		}
		System.out.println("Done collecting ");
		//Change the number of balls on the field down by the number of balls collectedd
		numBallsOnField.set(numBallsOnField.get() - collected.length);

		//Return the array with all the collected golf ball objects
		return collected;
	}

	/*
	Method to indicate that bollie is off the field
	 */
	protected void setCartToOff(){
		cartOnField.set(false);
	}
}
