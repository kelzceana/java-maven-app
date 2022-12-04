#!/usr/bin/env groovy
/*
steps to deploy app from jenkins server to ec2 instance
1. build jar app
2. build docker image
3. login to docker hub
4. push image to docker hub
5. ssh to ec2 instace 
6. docker run with the image downloaded from docker hub

*******
run docker-compose on ec2 instance from jenkins server
*/



//import shared library to jenkinsfile
@Library('jenkins-shared-library@master')_

pipeline {
    agent any
    tools{
        maven 'maven3'
    }

    environment {
        IMAGE_NAME = 'kelzceana/demo-app:1.4'
    }
    stages {
        stage('build JAR file') {
            steps {
                script {
                    echo "Building JAR file"
                    buildJar()
                }
            }
        }
        stage('build and push App') {
            steps {
                script {
                    echo "Building the application..."
                    buildImage(env.IMAGE_NAME)
                    dockerLogin()
                    echo "Pushing application to dockerhub..."
                    dockerPush(env.IMAGE_NAME)
                }
            }
        }
        stage('deploy to AWS EC2 instance') {
            steps {
                script {
                    def dockerCmd = "docker-compose -f docker-compose.yaml up --detach"
                        sshagent(['EC2-server-key']) {
                            // some block
                            sh "scp -o StrictHostKeyChecking=no docker-compose.yaml ec2-user@18.233.158.124:/home/ec2-user"
                            sh "ssh ec2-user@18.233.158.124 ${dockerCmd}"
                        }
                }
            }
        }
    }
}
