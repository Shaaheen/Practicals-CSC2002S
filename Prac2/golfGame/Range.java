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

	//ADD variable: ballsOnField collection;
	AtomicInteger numBallsOnField;
	golfBall[] ballsOnField;
	LinkedList<golfBall> ballsOnFieldList;

	//Add constructors
	protected Range(int initStash){
		ballsOnField = new golfBall[initStash];
		ballsOnFieldList = new LinkedList<golfBall>();
		numBallsOnField = new AtomicInteger();
	}
	//ADD method: collectAllBallsFromField(golfBall [] ballsCollected) 

	//ADD method: hitBallOntoField(golfBall ball)
	protected synchronized void hitBallOntoField(golfBall ball){
		ballsOnFieldList.push(ball);
		numBallsOnField.getAndIncrement();
	}

	protected synchronized golfBall[] collectAllBallsFromField(){
		System.out.println("Collecting golf balls Num: " + numBallsOnField.get() + " Balls : " + Arrays.toString(ballsOnFieldList.toArray()));
		golfBall[] collected = new golfBall[ballsOnFieldList.size()];
		for (int i = 0; i < collected.length; i++) {
			collected[i] = ballsOnFieldList.pop();
		}
		System.out.println("Done collecting ");
		numBallsOnField.set(numBallsOnField.get() - collected.length);
		return collected;
	}
}
