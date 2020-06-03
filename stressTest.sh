
LOG_FILE="stressTest.log"

#===============================================================================
# Script
#===============================================================================

# Check number of arguments
if [ "$#" != 2 ]; then
    echo "ERROR: Invalid number of command-line arguments!"
    echo "Usage:"
    echo "   ./stressTestApp <numInstances> <numLoop>"
    echo "     - <numInstances> - How many instances of runTestSuite"
    echo "     - <numLoop> - Number of loop for each instance to run"
    exit 1
fi

# Basic input validation for number of iterations
if [ "$1" -le "0" ]; then
    echo "ERROR: Caller must enter numInstances of 1 or more!"
    exit 2
fi

# Basic input validation for number of iterations
if [ "$2" -le "0" ]; then
    echo "ERROR: Caller must enter numLoop of 1 or more!"
    exit 3
fi

rm -f stressTest*.log

echo -e "
================================================================================
Start Stress Test
================================================================================
" | tee -a $LOG_FILE

numInstances=$1
numLoop=$2

# Capture START of test section
echo "Start time:" $(date) | tee -a $LOG_FILE
start=$SECONDS

for i in `seq 1 $numInstances`; do
    echo "Launching test suite instance $i of $numInstances..." | tee -a $LOG_FILE

    # Launch new test suite instance as background process with &
    (
        # Obtain a unique index for this block
        fileIdx=$i

        # Execute the test suite
        ./runTestSuite.sh $numLoop >> stressTest-$fileIdx.log
    ) &

    # Add block PID to list
    pidList[$i]=$!
done

# Block until all background tasks are complete with wait command
for i in `seq 1 $numInstances`; do
    echo "Waiting for test suite $i, (pid=${pidList[$i]}) to complete..." | tee -a $LOG_FILE
    wait ${pidList[$i]}
done

# Capture END of test section
stop=$SECONDS
echo "Finish time:" $(date) | tee -a $LOG_FILE

# Combine all output to one file for easier parsing
sleep 2  # Allow time for previous file writes to complete
for i in `seq 1 $numInstances`; do
    cat stressTest-$i.log >> stressTest_combinedResult.log
done

# Parse results and report PASS/FAIL
expectedPassCount=$numInstances
actualPassCount=$(grep PASS -c stressTest_combinedResult.log)

if [ "$actualPassCount" -eq "$expectedPassCount" ]; then
    result="PASS"
    retval=0
else
    result="FAIL"
    retval=4
fi

# Perform calculations 
duration=$(( $stop - $start ))
passRate=$(bc -l <<< "scale=2; $actualPassCount/$numInstances*100")

echo ""                                                                                 | tee -a $LOG_FILE
echo "================================================================================" | tee -a $LOG_FILE
echo "Stress Test Results"                                                             | tee -a $LOG_FILE
echo "================================================================================" | tee -a $LOG_FILE
echo "Overall stress test result:       $result"                                        | tee -a $LOG_FILE
echo "Execution time:                   $duration [seconds]"                            | tee -a $LOG_FILE
echo ""                                                                                 | tee -a $LOG_FILE
echo "Expected # of passing instances:  $expectedPassCount"                               | tee -a $LOG_FILE
echo "Actual # of passing instances:    $actualPassCount"                                 | tee -a $LOG_FILE
echo "Stress test passing rate:         $passRate%"                                     | tee -a $LOG_FILE
echo "================================================================================" | tee -a $LOG_FILE
echo "Please see the log file for the full console output: $LOG_FILE"
echo ""

# cleanup individual test file

rm -f stressTest-*.log

# Return status code
exit $retval


