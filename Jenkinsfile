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
        stage('Compile') {
            steps {
                sh 'mvn clean compile -B -ntp'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn testt -B -ntp'
            }
            post { 
                success { 
                    echo 'Tests passed!'
                }
                failure { 
                    echo 'Tests failed!'
                }
            }  
        }
        stage('Package') {
            steps {
                sh 'mvn package -DskipTests -B -ntp'
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