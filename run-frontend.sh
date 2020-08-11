#!/bin/bash

sbt 'run -Dhttp.port=9000 -Dakka.remote.artery.canonical.port=2551 -Dakka.management.http.port=8559 -jvm-debug 5005'
