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
        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests -B -ntp'
            }
        }        
    }
    post { 
        always { 
            archiveArtifacts artifacts: 'target/*.jar'
            cleanWs()
        }
    }    
}