JAVAC = javac
JFLAGS = -g
SOURCES = DrivingRangeApp.java \
	golfGame/BallStash.java \
	golfGame/Bollie.java \
	golfGame/Golfer.java \
	golfGame/Range.java \
	golfGame/golfBall.java 

.SUFFIXES: .java .class

CLS= $(SOURCES:.java=.class)

all:	$(CLS)

.java.class:
	$(JAVAC) $(JFLAGS) $<


clean:
	@rm -f DrivingRangeApp*.class
	@rm -f $(SOURCES:.java=.class)