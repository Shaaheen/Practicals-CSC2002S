package golfGame;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Range {
	private static int sizeStash=20;
	private AtomicBoolean cartOnField;

	AtomicInteger numBallsOnField;
	golfBall[] ballsOnField;
	LinkedList<golfBall> ballsOnFieldList;

	//constructor
	public Range(int initStash, AtomicBoolean cart){
		ballsOnField = new golfBall[initStash];
		ballsOnFieldList = new LinkedList<golfBall>();
		numBallsOnField = new AtomicInteger();
		cartOnField = cart;
	}

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

	protected synchronized golfBall[] collectAllBallsFromField(){
		cartOnField.set(true);
		System.out.println("Collecting golf balls Num: " + numBallsOnField.get() + " Balls : " + Arrays.toString(ballsOnFieldList.toArray()));
		golfBall[] collected = new golfBall[ballsOnFieldList.size()];
		for (int i = 0; i < collected.length; i++) {
			collected[i] = ballsOnFieldList.pop();
		}
		System.out.println("Done collecting ");
		numBallsOnField.set(numBallsOnField.get() - collected.length);
		return collected;
	}

	protected void setCartToOff(){
		cartOnField.set(false);
	}
}
