#!/bin/bash

sbt -Dhttp.port=9001 -Dakka.remote.artery.canonical.port=2552 -Dakka.cluster.seed-nodes.1="akka://application@127.0.0.1:2552" -Dakka.cluster.roles.2=backend-region -Dakka.cluster.roles.3=backend-summary -Dakka.management.http.port=8558 "runMain backend.Main"
