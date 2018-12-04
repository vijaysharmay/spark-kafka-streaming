#!/usr/bin/env bash
curl -fsSL https://get.docker.com -o get-docker.sh
sh get-docker.sh

docker build
docker run -d -p 9192:9192 --name kafka