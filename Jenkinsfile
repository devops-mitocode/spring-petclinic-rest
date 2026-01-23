pipeline {
    // agent any
    // tools {
    //     maven 'maven3.9.11'
    // }
    agent {
        docker {
            image 'maven:3.9.12-eclipse-temurin-17'
        }
    }
    trigger {
        pollSCM("* * * * *")
    }
    stages {
        /*stage('Checkout SCM') {
            steps {
                git branch: 'master', url: 'https://github.com/devops-mitocode/spring-petclinic-rest.git'
            }
        }*/   
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
                sh 'mvn package -B -ntp -DskipTests'
            }
        }        
    }
}