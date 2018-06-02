#!/usr/bin/env bash

#--- set script options
#    e - exit with non-zero error if any command fails
#    u - treat undefined variables as errors
#    x - print command before running them (trace)

set -e -u

pushd repo

./gradlew :application:build

popd

cp -R repo/application/build/libs*-SNAPSHOT.jar artifacts/application.jar

cp repo/ci/manifest_${ENVIRONMENT}.yml artifacts/