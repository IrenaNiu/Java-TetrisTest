#!/bin/bash
#===============================================================================
# 3rd June 2020
#                              Milestone #2
#
# File: runTestSuite.sh
#
# Description:
# This shell script executes all compiled J-Unit test cases in the $OUT_DIR directory
# a specificed number of times and e-mails the results to an e-mail address, if
# provided.
#
#   DEPENDENCIES, LIMITATIONS, & DESIGN NOTES:
#       Dependencies :
#           1. openjdk version "11.0.6" as available in CS1 at $JAVA_HOME/bin.
#	    2. junit-4.13.jar needs to be installed at JUNIT_HOME
#	    3. hamcrest-all-1.3.jar needs to be installed at JUNIT_HOME
#       Design Notes :
#           1. All J-Unit test cases in the $OUT_DIR directory are run.
#           2. Console output is logged to $LOG_FILE.
#           3. Caller can specify the number of test iterations.
#           4. Caller can optionally specify an e-mail address.
#           5. If an e-mail is provided, the test results are set and the
#              console output is attached to the e-mail.
#           6. Test results are displayed.
#           7. Run chmod +x runTestSuite.sh before executing the script
#       Limitations :
#           1. Due to memory limitations on SU's CS1 server, the $CS1_HACK (-Xms250m -Xmx512m)
#              variable is used to limit the memory used by the JVM.
#
#   Example Usage:
#   1. Executes the test suite one time
#         ./runTestSuite.sh 1
#   2. Executes the test suite twice and e-mails results to test@gmail.com.
#         ./runTestSuite.sh 2 test@gmail.com
#===============================================================================

#set -o errexit
set -o pipefail
set -o nounset
#set -o xtrace

#===============================================================================
# Constants
#===============================================================================
MIN_ARGS=1
MAX_ARGS=2

mydir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" > /dev/null && pwd )"
echo $mydir

OUT_DIR="bin"

JUNIT_HOME="$mydir/lib"

# Hack required due to memory limitations on CS1
CS1_HACK="-Xms250m -Xmx512m"  # Limit heap to 512 MB

# Setting the CLASSPATH for JUnit and HCrest jars
export CLASSPATH=.:$JUNIT_HOME/junit-4.13.jar:$JUNIT_HOME/hamcrest-all-1.3.jar

# Setting up the log File
LOG_FILE="runTestSuiteLog.txt"

# Setting the current Date
NOW=$(date +"%m-%d-%Y")

#===============================================================================
# Script
#===============================================================================

# Check number of arguments
if [ "$#" -lt "$MIN_ARGS" ] || [ "$#" -gt "$MAX_ARGS" ]; then
    echo "ERROR: Invalid number of command-line arguments!"
    echo "Usage:"
    echo "   ./runTestSuite <numIter> [emailRecipient]"
    echo "     - <numIter> - Number of times to run the test suite. Range: [1,10000)"
    echo "     - [emailRecipient] - Optional e-mail address to notify with test results"
    exit 1
fi

# Basic input validation for number of iterations
if [ "$1" -le "0" ]; then
    echo "ERROR: Caller must enter numIter of 1 or more!"
    exit 2
fi

# Remove previous log files
echo Cleaning up stale log files...
rm -f -v $LOG_FILE

# Capture system-level information for future debug-ability
echo -e "
================================================================================
Environment Variables
================================================================================
$(env)" >> $LOG_FILE

echo -e "
================================================================================
Java Version
================================================================================
" | tee -a $LOG_FILE
echo $(java -Xms250m -Xmx512m -version 2>&1) | tee -a $LOG_FILE
echo JUnit jar file: $JUNIT_HOME/junit-4.13.jar | tee -a $LOG_FILE

echo -e "
================================================================================
Test Suite Execution
================================================================================
" | tee -a $LOG_FILE

# Run the test suite
numIter=$1
numPass=0
numFail=0
echo "Start date/time:" $(date)  | tee -a $LOG_FILE
start=$SECONDS
for i in `seq 1 $numIter`; do
    echo "**************************************************" | tee -a $LOG_FILE
    echo "Executing test run $i of $1..." | tee -a $LOG_FILE
    echo "**************************************************" | tee -a $LOG_FILE
    # Running unit tests written for TetrisGrid class 
    java -Xms250m -Xmx512m -cp $mydir/$OUT_DIR/:$JUNIT_HOME/junit-4.13.jar:$JUNIT_HOME/hamcrest-all-1.3.jar org.junit.runner.JUnitCore tested.TetrisGridTest | tee -a $LOG_FILE
    rv=$?
    echo "Tests completed with return code: $rv" | tee -a $LOG_FILE
    if [ $rv == 0 ]; then
        let "numPass++"
    else
        let "numFail++"
    fi
    # Running unit tests written for TetrisBlock class
    java -Xms250m -Xmx512m -cp $mydir/$OUT_DIR/:$JUNIT_HOME/junit-4.13.jar:$JUNIT_HOME/hamcrest-all-1.3.jar org.junit.runner.JUnitCore tested.TetrisBlockTest | tee -a $LOG_FILE
    rv=$?
    echo "Tests completed with return code: $rv" | tee -a $LOG_FILE
    if [ $rv == 0 ]; then
        let "numPass++"
    else
        let "numFail++"
    fi
    # Running unit tests written for BlockGrid class
    java -Xms250m -Xmx512m -cp $mydir/$OUT_DIR/:$JUNIT_HOME/junit-4.13.jar:$JUNIT_HOME/hamcrest-all-1.3.jar org.junit.runner.JUnitCore tested.BlockGridTest | tee -a $LOG_FILE
    rv=$?
    echo "Tests completed with return code: $rv" | tee -a $LOG_FILE
    if [ $rv == 0 ]; then
        let "numPass++"
    else
        let "numFail++"
    fi
done
stop=$SECONDS
echo "Stop date/time:" $(date) | tee -a $LOG_FILE

# Calculate statistics
  # Total execution time
duration=$(( $stop - $start ))
  # Search the log file for the number of failing test cases
numCaseFail=$(grep -i -c "AssertionFailedError" $LOG_FILE)
  # Calculate the test suite passing rate
testSuitePassRate=$(bc -l <<< "scale=2; $numPass/$numIter*100"/3 )

 # Determine overall result
rval=3
status="FAIL"
if  ((numPass == numIter*3)); then
    rval=0
    status="PASS"
fi
  
# Report statistics
echo ""                                                                                 | tee -a $LOG_FILE
echo "================================================================================" | tee -a $LOG_FILE
echo "Results & Statistics"                                                             | tee -a $LOG_FILE
echo "================================================================================" | tee -a $LOG_FILE
echo "Overall test suite result:  $status"                                              | tee -a $LOG_FILE
echo "Execution time:             $duration [seconds]"                                  | tee -a $LOG_FILE
echo "# of test suite runs:       $numIter"                                             | tee -a $LOG_FILE
echo "# of passing runs:          $numPass"                                             | tee -a $LOG_FILE
echo "# of failing runs:          $numFail"                                             | tee -a $LOG_FILE
echo "Test suite passing rate:    $testSuitePassRate%"                                  | tee -a $LOG_FILE
echo "# of failing test cases:    $numCaseFail"                                         | tee -a $LOG_FILE
echo "================================================================================" | tee -a $LOG_FILE
echo "Please see the log file for the full console output: $LOG_FILE"
echo ""

# Notify e-mail recipient if one was provided by caller
if [ $# == $MAX_ARGS ]; then
    echo "Sending e-mail report to $2..."
    messageBody="Please find attached the log file outlining the output and results of the test run as on $NOW."
    echo $messageBody | mail -s "[$USER] Tetris Test Suite Run - $status!" -a $LOG_FILE $2
    if [ $? != 0 ]; then
        echo "ERROR: Unable to send e-mail! rv = $?"
        exit 4
    fi
fi

# Return status code
exit $rval

