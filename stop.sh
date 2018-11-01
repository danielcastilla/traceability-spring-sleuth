#!/usr/bin/env bash
kill -9 $(lsof -i:8081 -t)
kill -9 $(lsof -i:8082 -t)
kill -9 $(lsof -i:8083 -t)
kill -9 $(lsof -i:8084 -t)