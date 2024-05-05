#!/bin/bash

# Compile PathfindingPartitions.java
javac PathfindingPartitions.java

# Compile Board.java
javac Board.java

# Compile DebuggingMethods.java
javac DebuggingMethods.java

# If all compilations were successful, display a success message
if [ $? -eq 0 ]; then
    echo "Compiled Classes"
else
    echo "Compilation Failed"
fi