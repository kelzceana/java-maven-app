def execute() {
    echo 'Building docker image'
    withCredentials([usernamePassword(credentialsId: 'dockerhub',
                                      passwordVariable: 'PASS',
                                      usernameVariable: 'USER')]) {
        sh '''
                docker build -t kelzceana/my-webapp:latest .
                echo $PASS | docker login -u $USER --password-stdin
                docker push kelzceana/my-webapp:latest
                '''
                                                  }
}
return this
