pipeline {
  agent none
  options {
    // Requires Workspace Cleanup Plugin
    // Ensures a clean slate before Git checkout
    skipDefaultCheckout true
  }
  stages { // <-- Single top-level 'stages' block

    stage('Prepare Workspace') {
      agent any
      steps {
        cleanWs() // Deletes the entire workspace directory
        // Use an explicit checkout step
        checkout scm
      }
    }

    // REMOVED 'stages {' here

    stage('Build mvn Jar') {
      agent {
        docker {
          image 'maven:3.9.6-eclipse-temurin-21-alpine'
          // Using the UID argument to prevent permission errors
          args "-u \$(id -u) -v /tmp/v2:/root/m2"
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
          app = docker.build('ajayc20/selenium-and-docker:latest', '.')
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
  } // <-- End of the single 'stages' block
}