pipeline {
    agent any
    tools {
        maven 'maven3.9.10'
    }
    stages {
        // stage('Checkout SCM') {
        //     steps {
        //         git branch: 'master', url: 'https://github.com/devops-mitocode/spring-petclinic-rest.git'
        //     }
        // }
        stage('Compile') {
            steps {
                sh 'mvn clean compile -B -ntp'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test -B -ntp'
            }
        }
        stage('Package') {
            steps {
                sh 'mvn package -B -ntp'
            }
        }                
    }
    post {
        success {
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            cleanWs()
        }
    } 
}