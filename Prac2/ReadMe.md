To run:
		cd into location of prac2 from terminal
		Run make and then Run "java DrivingRangeApp"
		This will launch the app will all default values set
		which are numGolfers = 5, stashSize = 40, bucketSize = 5
		
To set your own custom parameters:
		Run "java DrivingRangeApp numGolfers stashSize bucketSize"
		where variables are integer values
		eg "java DrivingRangeApp 5 20 3"
		Please read below to see how to use extra parameters
		
Extra credit
	More features were added for extra credit
	This means that there are more variables for the command line parameters
	There us now numbBucketsPerGolfer and numberOfTees

Extra parameters:
	Run "java DrivingRangeApp numGolfers stashSize bucketSize numbBucketsPerGolfer numberOfTees"
		where variables are integer values
		eg "java DrivingRangeApp 5 20 3 3 5"
		
numbBucketsPerGolfer is the total number of buckets that a Golfer can use throughout
the driving range day
numberOfTees is the golfing room that is need to start golfing
	