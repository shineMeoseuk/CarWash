pipeline {
    agent any

    stages {
        stage('Git Checkout') {
            steps {
              checkout scmGit(branches: [[name: '*/main']], extensions: [submodule(parentCredentials: true, recursiveSubmodules: true, reference: '', trackingSubmodules: true)], userRemoteConfigs: [[credentialsId: 'shineMeoseuk', url: 'https://github.com/shineMeoseuk/CarWash']])
              echo 'Git Checkout Success!'
            }
        }

        stage('Test') {
            steps {
                sh './gradlew test'
                echo 'test success'
            }
        }

        stage('Build') {
            steps {
                sh './gradlew clean build'
                echo 'build success'
            }
        }
    }
}