pipeline {
    agent any
    stages {
        stage('Maven Clean Package') {
            steps {
                sh 'mvn clean package -DskipTests=true'
                echo "Maven Build Completed"
            }
        }
        stage('Build Docker Image') {
            steps {
                sh 'docker build -t ajayc20/selenium-and-docker:1.0.0 .'
                echo "Docker Image Build Completed"
            }
        }
        stage('Push Docker Image to Docker Hub') {
            environment {
                DOCKERHUB_CREDENTIALS = credentials('docker-hub-credentials')
            }
            steps {
                sh "echo \$DOCKERHUB_CREDENTIALS_PSW | docker login -u \$DOCKERHUB_CREDENTIALS_USR --password-stdin"
                sh 'docker push ajayc20/selenium-and-docker:1.0.0'
                echo "Docker Image Push Completed"
            }
        }
    }
    post {
        always {
            sh 'docker logout'
        }
    }
}