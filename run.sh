#!/bin/bash

./gradlew clean && ./gradlew build

cd build/libs && spark-submit --class org.sustain.Application --master spark://lattice-150:8079 --jars \
/s/chopin/b/grad/cacaleb/regression-transfer-learning/build/libs/scala-library-2.12.11.jar,\
/s/chopin/b/grad/cacaleb/regression-transfer-learning/build/libs/scala-collection-compat_2.12-2.1.1.jar,\
/s/chopin/b/grad/cacaleb/regression-transfer-learning/build/libs/spark-mllib_2.12-3.0.1.jar,\
/s/chopin/b/grad/cacaleb/regression-transfer-learning/build/libs/spark-mllib-local_2.12-3.0.1.jar,\
/s/chopin/b/grad/cacaleb/regression-transfer-learning/build/libs/spark-sql_2.12-3.0.1.jar,\
/s/chopin/b/grad/cacaleb/regression-transfer-learning/build/libs/mongodb-driver-core-4.0.5.jar,\
/s/chopin/b/grad/cacaleb/regression-transfer-learning/build/libs/mongodb-driver-sync-4.0.5.jar,\
/s/chopin/b/grad/cacaleb/regression-transfer-learning/build/libs/mongo-spark-connector_2.12-3.0.1.jar,\
/s/chopin/b/grad/cacaleb/regression-transfer-learning/build/libs/bson-4.0.5.jar,\
/s/chopin/b/grad/cacaleb/regression-transfer-learning/build/libs/log4j-1.2.17.jar,\
/s/chopin/b/grad/cacaleb/regression-transfer-learning/build/libs/log4j-api-2.8.jar,\
/s/chopin/b/grad/cacaleb/regression-transfer-learning/build/libs/log4j-core-2.8.jar,\
/s/chopin/b/grad/cacaleb/regression-transfer-learning/build/libs/spark-unsafe_2.12-3.0.1.jar --files /s/chopin/b/grad/cacaleb/regression-transfer-learning/src/main/resources/log4j.properties regression-transfer-learning-0.1.jar

cd ../../