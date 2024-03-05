pipeline {
    agent any

    stages {
        stage('Git Checkout') {
            steps {
              checkout scmGit(branches: [[name: '*/main']], extensions: [submodule(parentCredentials: true, recursiveSubmodules: true, reference: '', trackingSubmodules: true)], userRemoteConfigs: [[credentialsId: 'shineMeoseuk', url: 'https://github.com/shineMeoseuk/CarWash']])
              echo 'Git Checkout Success!'
            }
        }

        stage('Build&Test') {
            steps {
                sh './gradlew build test'
                echo 'test success!'
            }
        }

        stage('Deploy') {
            steps([$class: 'BapSshPromotionPublisherPlugin']) {
                sshPublisher(
                    continueOnError: false, failOnError: true,
                    publishers: [
                        sshPublisherDesc(
                            configName: "nginx-web-server",
                            verbose: true,
                            transfers: [
                                sshTransfer(
                                    sourceFiles: "build/libs/*.jar", // 전송할 파일
                                    removePrefix: "build/libs", // 파일에서 삭제할 경로
                                    remoteDirectory: "app", // 배포할 위치
                                    execCommand: "sh ~/app/deploy.sh" // 원격 서버에서 실행
                                )
                            ]
                        )
                    ]
                )
            }
        }
    }
}