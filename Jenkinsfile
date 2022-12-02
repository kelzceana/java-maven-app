#!/usr/bin/env groovy
/*
steps to deploy app from jenkins server to ec2 instance
1. build jar app
2. build docker image
3. login to docker hub
4. push image to docker hub
5. ssh to ec2 instace 
6. docker run with the image downloaded from docker hub




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
                    echo "Deploying the application..."
                    def dockerCmd = "docker run -p 3080:3080 -d ${IMAGE_NAME}"
                    sshagent(['EC2-server-key']) {
                          // run shell commands
                        sh "ssh -o StrictHostKeyChecking = no ec2-user@54.166.104.195 ${dockerCmd}"
                    }
                }
            }
        }
    }
}
