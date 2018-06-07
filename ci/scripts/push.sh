#!/usr/bin/env bash

#--- set script options
#    e - exit with non-zero error if any command fails
#    u - treat undefined variables as errors
#    x - print command before running them (trace)

set -e -u

export ROOT_FOLDER=$( pwd )

pushd repo

. ci/scripts/generate-settings.sh

./mvnw clean package

popd

cp -R repo/application/target/*-SNAPSHOT.jar artifacts/application.jar
cp repo/ci/manifest_${ENVIRONMENT}.yml artifacts/