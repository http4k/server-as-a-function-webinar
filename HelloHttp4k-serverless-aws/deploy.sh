#!/bin/bash
set -e

./gradlew clean buildZip
pulumi up --yes --stack aws