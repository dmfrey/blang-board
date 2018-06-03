#!/bin/bash

#--- set script options
#    e - exit with non-zero error if any command fails
#    u - treat undefined variables as errors
#    x - print command before running them (trace)
set -e -u

pushd repo

export ROOT_FOLDER=$( pwd )
. ci/scripts/generate-settings.sh

./gradlew :application:build

popd
