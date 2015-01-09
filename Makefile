#
# Copyright (C)  2015  George Wong
# 

all: build run

build:
	javac Designer.java

jar: build
	jar cfm Designer.jar Manifest.txt *.class

run:
	java Designer

clean:
	rm -f *.class
