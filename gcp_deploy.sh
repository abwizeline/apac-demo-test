eval "$(docker-machine env default)"

gcloud init

export $GOOGLE_CLOUD_PROJECT = apactestdeploy

## set default project name
gcloud config set project apactestdeploy

gcloud auth configure-docker

## Enable container registry
gcloud services enable containerregistry.googleapis.com

## ./mvnw clean package

kubectl config view

kubectl config use-context

## docker build -t gcr.io/apactestdeploy/apac-demo:v1 .

## docker push gcr.io/apactestdeploy/apac-demo:v1

./mvnw -DskipTests com.google.cloud.tools:jib-maven-plugin:build \
  -Dimage=gcr.io/apactestdeploy/apac-demo:v1

## kubectl run dataserver --image=gcr.io/apactestdeploy/apac-demo:latest --port 8080 --labels="app=apac,tier=backend"
## kubectl expose deployment userapi --type=LoadBalancer --port 80 --target-port 8080

gcloud services enable compute.googleapis.com container.googleapis.com
gcloud container clusters create apac-demo-cluster \
  --num-nodes 3 \
  --machine-type n1-standard-1 \
  --zone us-central1-b

## kubectl run dataserver --image=gcr.io/apactestdeploy/apac-demo:v3 --port 8080 --labels="app=apac-demo,tier=backend"

kubectl create deployment apac-demo \
  --image=gcr.io/apactestdeploy/apac-demo:v1

#### kubectl expose deployment userapi --type=LoadBalancer --port 80 --target-port 8080

kubectl get services
kubectl get pods
kubectl create service loadbalancer apac-demo-lb --tcp=8080:8080


## UPDATES
## kubectl set image deployment/hello-java \
##     hello-java=gcr.io/$GOOGLE_CLOUD_PROJECT/hello-java:v2

## gcloud container clusters create k8s-medium --num-nodes=3 --zone=us-central1-b