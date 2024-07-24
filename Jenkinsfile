pipeline {
    agent any
    parameters {
        string(name: 'BRANCH_NAME', defaultValue: 'main', description: 'Branch to build' )
        choice(name: 'LOCATION', choices: ['uat-uber-datadb', 'uat-uber-service'])
    }
    environment {
        NEW_VERSION = '1.0 '
    }
    stages {
        stage ("Build Jar") {
            steps {
                echo 'This is the build stage' 
                sh 'mvn package'
            }
        }
        stage ("Build Image") {
            steps {
                echo 'Building Image'
                withCredentials([usernamePassword(credentialId: 'docker-hub', usernameVariable: 'USER', passwordVariable: 'PASS')])
                sh 'docker build -t kelzceana/demo-app:3.0 .'
                sh 'echo $PASS | docker login -u $USER --password-stdin'
                sh 'docker push kelzceana/demo-app:3.0'
            }
        }
        stage ("deploy") {
            steps {
                echo 'This is the deploy stage'
                echo "The location of the deployment is ${params.LOCATION}"
            }
        }
    }
}