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
        stage ('Init') {
            steps {
                scripts {
                    buildimage = load 'scripts/buildimage.groovy'
                    buildjar = load 'scripts/buildjar.groovy'
                }
            }
        }
        //
        stage ('Build Jar') {
            steps {
                scripts {
                    buildjar.execute()
                }
            }
        }
        stage ('Build image') {
            steps {
                scripts {
                    buildimage.execute()
                }
            }
        }
        stage ('Deploy') {
            steps {
                echo 'deploying application...'
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