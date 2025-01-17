pipeline {
    agent any
    tools{
        jdk 'java21'
        maven 'maven3.8.8'
    }
    triggers {
        githubPush()
    }
    stages {
        // stage('Checkout SCM') {
        //     steps {
        //         git branch: 'master', url: 'https://github.com/devops-mitocode/spring-petclinic-rest.git'
        //     }
        // }
        stage('Build') {
            steps {
                sh 'java -version'
                sh 'mvn clean package -DskipTests -B -ntp'
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