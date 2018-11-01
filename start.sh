#!/usr/bin/env bash

nohup java -jar ./microservice-1/target/microservice-1-0.0.1-SNAPSHOT.jar &
nohup java -jar ./microservice-2/target/microservice-2-0.0.1-SNAPSHOT.jar &
nohup java -jar ./microservice-3/target/microservice-3-0.0.1-SNAPSHOT.jar &
nohup java -jar ./microservice-4/target/microservice-4-0.0.1-SNAPSHOT.jar &