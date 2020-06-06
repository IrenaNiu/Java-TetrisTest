Tetris Game
======

The classic arcade game. Five shapes of blocks: Right_L, Left_L, Long, Tee, and Squre.

## **1. Requirements:**
* Eclipse IDE for Java
* JUnit 4 (built in Eclipse IDE)
* JDK

## **2. Project clone and import in Eclipse:**
* Open IDE of Eclipse and import the project into the IDE:
File →  Import → Git → Project from Git → Clone URL → URL: https://github.com/IrenaNiu/Java-TetrisTest.git → follow the next steps until finish.
You will see the project loaded in the left navigation bar with all subfolders.

Steps for running unit tests and code coverage:
Open the package of "tested" and right click the any of the three test java files
* Run as “JUnit Test” → You will see the tests successfully pass within a second.
* Right click package “tested”, click “Coverage as” → “JUnit test”. You will see code coverage results in the coverage panel.

## **3. Project Regression test and stress test on CS1:**
* Log in CS1 and cd any folder you want to git clone
* Use git clone to import the repo:
```
  git clone https://github.com/IrenaNiu/Java-TetrisTest.git
```
* Enter the repository of project:
```
  cd Java-TetrisTest
```
* Change permissions to make scripts executable: 
```
  chmod 777 *
```
* Run the build script
```
  ./buildTestSuite.sh
```
* Run the test suite
```
  ./runTestSuite.sh <num of iterations> [your email address to receive log]
```
  (e.g.: ./runTestSuite.sh 10 xxx@seattleu.edu)
* Perform stress test
```
  ./stressTest.sh <num of instances> <num of iteration> 
```
  (e.g.: ./stressTest.sh 2 3.  CS1 has limitation of instances, better choose num < 5)


