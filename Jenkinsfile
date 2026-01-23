pipeline {
    agent any
    tools {
        maven 'maven3.9.11'
    }
    stages {
        /*stage('Checkout SCM') {
            steps {
                git branch: 'master', url: 'https://github.com/devops-mitocode/spring-petclinic-rest.git'
            }
        }*/   
        stage('Build') {
            steps {
                sh 'ls -la'
                sh 'mvn clean package -DskipTests'
            }
        }
        stage('Test') {
            steps {
                sh 'cat /etc/os-release'
            }
        }        
    }
}