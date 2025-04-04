#!/bin/bash

echo "=== Dishcraft Application Startup Benchmark ==="
echo ""

# Number of runs for averaging
RUNS=3

# Function to measure startup time
measure_startup() {
  APP_TYPE=$1
  COMMAND=$2
  
  echo "Measuring $APP_TYPE startup time ($RUNS runs)..."
  total_time=0
  
  for i in $(seq 1 $RUNS); do
    echo "  Run $i/$RUNS..."
    start_time=$(date +%s.%N)
    
    # Run the command and capture its output
    OUTPUT=$($COMMAND 2>&1)
    END_STATUS=$?
    
    # Check for successful startup
    if [ $END_STATUS -ne 0 ]; then
      echo "ERROR: Application failed to start!"
      echo "$OUTPUT"
      exit 1
    fi
    
    # Extract the reported startup time if available
    STARTUP_MSG=$(echo "$OUTPUT" | grep -o "Started .* in [0-9.]* seconds")
    STARTUP_TIME=$(echo "$STARTUP_MSG" | grep -o "[0-9.]* seconds" | cut -d' ' -f1)
    
    end_time=$(date +%s.%N)
    elapsed=$(echo "$end_time - $start_time" | bc)
    echo "    External time: ${elapsed}s, Reported: ${STARTUP_TIME}s"
    total_time=$(echo "$total_time + $elapsed" | bc)
    
    # Wait for app to fully shutdown
    sleep 2
  done
  
  # Calculate average time
  avg_time=$(echo "scale=3; $total_time / $RUNS" | bc)
  echo "  Average startup time: ${avg_time}s"
  echo ""
}

# Ensure we're in the right directory
cd "$(dirname "$0")"

# Measure JAR startup time (make sure JAR exists)
if [ -f "target/dishcraft-0.0.1-SNAPSHOT.jar" ]; then
  measure_startup "JAR" "java -jar target/dishcraft-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod --server.port=0 --spring.jpa.hibernate.ddl-auto=update --spring.datasource.initialization-mode=never --spring.sql.init.mode=never"
else
  echo "JAR file not found! Build it with: ./mvnw clean package"
fi

# Measure Native Image startup time (make sure native executable exists)
if [ -f "target/dishcraft" ]; then
  measure_startup "Native Image" "./target/dishcraft --spring.profiles.active=prod --server.port=0 --spring.jpa.hibernate.ddl-auto=update --spring.datasource.initialization-mode=never --spring.sql.init.mode=never"
else
  echo "Native executable not found! Build it with: ./mvnw -Pnative clean package"
fi

echo "Benchmark complete!"