pipeline {
    agent any
    environment {
        NEW_VERSION = '1.0 '
    }
    stages {
        stage ("build") {
            steps {
                echo 'This is the build stage'  
            }
        }
        stage ("test") {
            steps {
                echo 'This is the test stage'
            }
        }
        stage ("deploy") {
            steps {
                echo 'This is the deploy stage'
                echo "The commiter name is ${env.GIT_COMMITTER_NAME"}"
            }
        }
    }
}