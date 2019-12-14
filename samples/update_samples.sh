#!/bin/bash

for i in $(cat domains); do 
 echo $i
 output="$(echo $i | egrep -o '[a-z]+\.ro')"
 curl $i -o $output.xml
done
