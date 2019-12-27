#!/bin/bash

set -e
echo ${HOME}

docker build -t gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME}:$TRAVIS_COMMIT .

GCLOUD_CONFIG_FILE=gcloud-service-key.json.enc
echo $GCLOUD_CONFIG >> $GCLOUD_CONFIG_FILE

echo $GCLOUD_SERVICE_KEY_PRD | base64 --decode -i > $GCLOUD_CONFIG_FILE
gcloud auth activate-service-account --key-file $GCLOUD_CONFIG_FILE

gcloud --quiet config set project $PROJECT_NAME_PRD
gcloud --quiet config set container/cluster $CLUSTER_NAME_PRD
gcloud --quiet config set compute/zone ${CLOUDSDK_COMPUTE_ZONE}

## -- In case if want to create cluster from scratch, but in real world it has to be created already
##  gcloud container clusters create $CLUSTER_NAME_PRD \
##      --num-nodes $NODES_NUM \
##      --machine-type $GCLOUD_MACHINE_TYPE \
##      --zone $CLOUDSDK_COMPUTE_ZONE

gcloud config set container/use_client_certificate True

gcloud --quiet container clusters get-credentials $CLUSTER_NAME_PRD
gcloud docker -- push gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME}:$TRAVIS_COMMIT

yes | gcloud beta container images add-tag gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME}:$TRAVIS_COMMIT gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME}:latest

kubectl config view
kubectl config current-context

kubectl apply -f deployment.yaml
kubectl apply -f loadbalancer.yaml
kubectl apply -f nodeport.yaml
