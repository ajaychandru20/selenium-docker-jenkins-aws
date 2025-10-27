pipeline {
  agent none
  stages {

    stage('Build mvn Jar') {
      agent {
        docker {
          image 'maven:3.9.6-eclipse-temurin-21-alpine'
          args '-u root -v /tmp/v2:/root/m2'
        }
      }
      steps {
        sh 'mvn clean package -DskipTests=true'
        echo "Maven Build Completed"

      }

    }
    stage('Build Docker Image') {
      steps {
        script {
          app = docker.build('ajayc20/selenium-and-docker:latest','.')
          echo "Docker Image Build Completed"
        }

      }
    }
    stage('Push Docker Image to Docker Hub') {
      steps {
        script {
          docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credentials') {
            app.push('latest')
            // app.push("${env.BUILD_NUMBER}")
          }
          echo "Docker Image Push Completed"
        }

      }
    }
  }
}