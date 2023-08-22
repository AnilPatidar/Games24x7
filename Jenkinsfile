pipeline {
    agent any
    //{label 'local' }

    parameters {
        string(name: 'platformType', description: 'mobile,web', defaultValue: 'mobile')
        string(name: 'platform', description: 'android,ios,CHROME', defaultValue: 'both')
    }

    environment {
            platformType = "${params.platformType}"
            platform = "${params.platform}"
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    def gitRepoUrl = 'https://github.com/AnilPatidar/Games24x7.git'
                    //def checkoutDir = "${env.WORKSPACE}/repo"
                    git branch: 'dynamic-testng', url: gitRepoUrl, credentialsId: 'github'
                }
            }
        }

        stage('Build and Test') {
            steps {
                script {
                  sh "ls -la" // Debug: List workspace content
                  sh "/var/gradle/bin/gradle clean test -Pparam1=${params.PARAMETER_1} -Pparam2=${params.PARAMETER_2}"
                }
            }
        }
    }

    post {
        always {
            junit '**/build/test-results/test/*.xml'
        }
    }
}