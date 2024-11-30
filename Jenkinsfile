def COLOR_MAP = [
    'SUCCESS': 'good',
    'FAILURE': 'danger'
]
pipeline {
    agent any
    tools {
        maven 'maven3.9'
    }
    stages {
        stage('Init') {
            steps {
                script {
                    buildimage = load 'scripts/buildimage.groovy'
                    buildjar = load 'scripts/buildjar.groovy'
                }
            }
        }
        stage('Build Jar') {
            steps {
                script {
                    buildjar.execute()
                }
            }
        }
        stage('Build image') {
            steps {
                script {
                    buildimage.execute()
                }
            }
        }
        stage('Deploy') {
            steps {
                echo 'deploying application...'
                script {
                    def dockerCmd = 'docker run -p 3080:3080 -d kelzceana/my-webapp:1.0.0'
                    sshagent(['ec2-server-key']) {
                        sh """
                        ssh -o StrictHostKeyChecking=no ec2-user@18.212.101.1
                        echo $PASS | docker login -u $USER --password-stdin ${dockerCmd}
                        """
                    }
                }
            }
        }
    }
    post {
        always {
            slackSend(
                channel: '#automation-builds',
                color: COLOR_MAP[currentBuild.currentResult],
                message: "Build ${env.BUILD_NUMBER} was ${currentBuild.currentResult}. More details at ${env.BUILD_URL}"
            )
        }
    }
}
