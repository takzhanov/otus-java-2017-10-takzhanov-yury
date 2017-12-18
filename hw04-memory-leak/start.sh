#!/usr/bin/env bash

OPTIONS_LIST=(
"-XX:+UseSerialGC"
"-XX:+UseG1GC"
"-XX:+UseParallelGC -XX:+UseParallelOldGC"
#"-XX:+UseParNewGC" нельзя так в 9
"-XX:+UseConcMarkSweepGC -XX:+UseParNewGC" #deprecated
#"-XX:+UseConcMarkSweepGC -XX:-UseParNewGC" нельзя так в 9
)

>mygc.log
for JAVA_OPTS in "${OPTIONS_LIST[@]}"
do
    JAVA_OPTS="-Xmx50m -Xlog:gc:file=rawgc.log $JAVA_OPTS"
    echo "Running: java $JAVA_OPTS -jar target/hw04-memory-leak.jar"
    echo $JAVA_OPTS >> mygc.log
    java $JAVA_OPTS -jar target/hw04-memory-leak.jar >> mygc.log
done
