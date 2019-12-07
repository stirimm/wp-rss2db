#!/usr/bin/env bash

set -eux

NAME="wp-rss2db"

mvn clean package

docker build -t ${NAME} .
