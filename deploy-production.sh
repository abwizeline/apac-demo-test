#!/bin/bash

set -e
echo ${HOME}

docker build -t gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME}:$TRAVIS_COMMIT .

echo $GCLOUD_SERVICE_KEY_PRD | base64 --decode -i > ${GOOGLE_APPLICATION_CREDENTIALS}
gcloud auth activate-service-account --key-file ${GOOGLE_APPLICATION_CREDENTIALS}

gcloud --quiet config set project $PROJECT_NAME_PRD
gcloud --quiet config set container/cluster $CLUSTER_NAME_PRD
gcloud --quiet config set compute/zone ${CLOUDSDK_COMPUTE_ZONE}
gcloud --quiet container clusters get-credentials $CLUSTER_NAME_PRD

gcloud docker push gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME}

yes | gcloud beta container images add-tag gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME}:$TRAVIS_COMMIT gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME}:latest

kubectl config view
kubectl config current-context

gcloud container clusters create ${CLUSTER_NAME_PRD} \
  --num-nodes ${NODES_NUM} \
  --machine-type ${GCLOUD_MACHINE_TYPE} \
  --zone ${CLOUDSDK_COMPUTE_ZONE}

## kubectl set image deployment/${KUBE_DEPLOYMENT_NAME} ${KUBE_DEPLOYMENT_CONTAINER_NAME}=gcr.io/${PROJECT_NAME_PRD}/${DOCKER_IMAGE_NAME}:$TRAVIS_COMMIT
echo "start deployment.yaml"
kubectl apply -f deployment.yaml
kubectl apply -f loadbalancer.yaml
kubectl apply -f nodeport.yaml