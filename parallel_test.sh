#!/bin/bash
# Just some basic bash script to executes concurrent requests on the API

for i in {1..100}
do
	curl 'http://localhost:8080/venues/search/rest?place=london&radius=50&limit=20' &
done
