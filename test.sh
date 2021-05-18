#!/bin/bash

BASEDIR="$( cd "$( dirname "$0" )" && pwd )"
sudo docker run -it --rm -v ${BASEDIR}/conf:/opt/gatling/conf \
-v ${BASEDIR}/user-files:/gatling-charts-highcharts-bundle-3.5.1/user-files \
-v ${BASEDIR}/results:/gatling-charts-highcharts-bundle-3.5.1/gatling/results \
gatling_kafka:java8