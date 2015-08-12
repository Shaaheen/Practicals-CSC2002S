package golfGame;

import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Range {
	private static int sizeStash=20;
	private AtomicBoolean cartOnField;

	//ADD variable: ballsOnField collection;
	golfBall[] ballsOnField;
	LinkedList<golfBall> ballsOnFieldList;

	//Add constructors
	protected Range(int initStash){
		ballsOnField = new golfBall[initStash];
		ballsOnFieldList = new LinkedList<golfBall>();
	}
	//ADD method: collectAllBallsFromField(golfBall [] ballsCollected) 

	//ADD method: hitBallOntoField(golfBall ball)
	protected void hitBallOntoField(golfBall ball){
		ballsOnFieldList.push(ball);
	}

	protected golfBall[] collectAllBallsFromField(){
		synchronized (this){
			golfBall[] collected = new golfBall[ballsOnFieldList.size()];
			for (int i = 0; i < collected.length; i++) {
				collected[i] = ballsOnFieldList.pop();
			}
			return collected;
		}
	}
}
