# apac-demo-test

[![Build Status](https://travis-ci.org/abwizeline/apac-demo-test.svg?branch=master)](https://travis-ci.org/abwizeline/apac-demo-test)


- In TravisCI you have to setup GCLOUD_SERVICE_KEY_PRD variable
Please, follow this guide https://medium.com/google-cloud/continuous-delivery-in-a-microservice-infrastructure-with-google-container-engine-docker-and-fb9772e81da7
section @Environment Variables

- Replace gcloud-service-key.json with your account, follow this guide:
https://cloud.google.com/iam/docs/creating-managing-service-account-keys

- In GCP be sure you have production cluster, named with $CLUSTER_NAME_PRD variable(.travis.yml) OR uncomment cluster creation section in gcp-deploy.sh 