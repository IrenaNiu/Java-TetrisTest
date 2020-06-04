#!/bin/bash
#===============================================================================
# CPSC 5210-01
# Group 6: Khanh Phan, Yi Niu, Shaista Usman
# Professor McKee
# 3rd June 2020
#                              Milestone #2
#
# File: buildTestSuite.sh
#
# Description:
# This shell script builds the test suite for the Java Game Tetris
# using the J-Unit tests designed during Milestone 1.
#
#   DEPENDENCIES, LIMITATIONS, & DESIGN NOTES:
#       Dependencies :	
#           1. openjdk version "11.0.6" as available in CS1 at $JAVA_HOME/bin.
#	    2. junit-4.13.jar needs to be installed at JUNIT_HOME
#	    3. hamcrest-all-1.3.jar needs to be installed at JUNIT_HOME
#       Design Notes :
#           1. All source files and all J-Unit test files are built/compiled by this script.
#           2. Artifacts are placed in the $OUT_DIR/tetris directory
#	    3. run chmod +x buildTestSuite.sh before executing the script
#       Limitations :
#           1. Due to memory limitations on SU's CS1 server, the $CS1_HACK (-J-Xmx512m)
#              variable is used to limit the memory used by the JVM.
# 
#   Example Usage:
#   "./buildTestSuite.sh"
#===============================================================================



set -o nounset


#===============================================================================
# Constants
#===============================================================================
mydir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" > /dev/null && pwd )"
echo $mydir

OUT_DIR="bin"

#JUNIT_HOME=/home/st/niuyi/junit/Java-TetrisTest/lib
JUNIT_HOME="$mydir/lib"

# Hack required due to memory limitations on CS1
CS1_HACK="-J-Xmx512m"  # Limit heap to 512 MB

export CLASSPATH=.:$JUNIT_HOME/junit-4.13.jar:$JUNIT_HOME/hamcrest-all-1.3.jar
LOG_FILE="buildTestSuiteLog.txt"
#===============================================================================
# Script
#===============================================================================
echo "Building test suite..."

echo "Creating directory for build artifacts..."
mkdir -p $mydir/$OUT_DIR | tee -a $LOG_FILE

echo Removing stale artifacts...
rm -f -v $mydir/$OUT_DIR/*.class | tee -a $LOG_FILE

# Remove previous log files
echo Cleaning up stale log files...
rm -f -v $LOG_FILE

echo "Printing javac version..."
javac $CS1_HACK -version | tee -a $LOG_FILE

echo "Building source code..."
  # $JAVA_HOME/bin/javac -J-Xmx512m -d $mydir/$OUT_DIR/ -cp $JUNIT_HOME/junit-4.13.jar:$JUNIT_HOME/hamcrest-all-1.3.jar:. $mydir/src/*.java | tee -a $LOG_FILE

javac -J-Xmx512m -d $mydir/$OUT_DIR/ -cp $JUNIT_HOME/junit-4.13.jar:$JUNIT_HOME/hamcrest-all-1.3.jar:. $mydir/src/tested/*.java | tee -a $LOG_FILE

if [ $? != 0 ]; then
        echo "ERROR: Unable to build Aborting build..."
        exit 1
else
        echo "Build success!"
fi
exit 0
