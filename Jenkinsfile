pipeline {
    agent any
    tools {
        maven 'maven3.8.8'
    }
    stages {
        // stage('Checkout SCM') {
        //     steps {
        //         git branch: 'master', url: 'https://github.com/devops-mitocode/spring-petclinic-rest.git'
        //     }
        // }
        stage('Build') {
            steps {
                sh 'mvn clean package -B -ntp'
            }
        }
    }
}