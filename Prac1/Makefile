JAVAC = javac
JFLAGS = -g
SOURCES = Main.java \
	src/FileUtil.java \
	src/RecursiveThread.java \
	src/Serial.java 

.SUFFIXES: .java .class

CLS= $(SOURCES:.java=.class)

all:	$(CLS)

.java.class:
	$(JAVAC) $(JFLAGS) $<


clean:
	@rm -f Main*.class
	@rm -f $(SOURCES:.java=.class)