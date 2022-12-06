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

    
    stages {
        stage('increment new version') {
            steps {
                script {
                    echo "incrementing app version"
                    sh 'mvn build-helper:parse version versions:set \
                        -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} versions:commit'
                    def matcher = readFile(pom.xml) =~ '<version>(.+)</version>'
                    def version = matcher[0][1]
                    env.IMAGE_NAME = "$version-$BUILD_NUMBER"
                }
            }
        }
        stage('build JAR file') {
            steps {
                slackSend channel: '#jenkins-cicd',
                message: " ${env.JOB_NAME} build ${env.BUILD_NUMBER} started"
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
                    def shellCmd = "bash ./shell-cmds.sh ${env.IMAGE_NAME}"
                        sshagent(['EC2-server-key']) {
                            // some block
                            sh "scp shell-cmds.sh ec2-user@18.233.158.124:/home/ec2-user"
                            sh "scp docker-compose.yaml ec2-user@18.233.158.124:/home/ec2-user"
                            sh "ssh -o StrictHostKeyChecking=no ec2-user@18.233.158.124 ${shellCmd}"
                        }
                }
            }
        }

    }
    post {
        always {
            echo 'Slack Notifications.'
            slackSend channel: '#jenkins-cicd',
                message: " ${env.JOB_NAME} build ${env.BUILD_NUMBER} completed \n More info at: ${env.BUILD_URL}"
        }
    }
}
