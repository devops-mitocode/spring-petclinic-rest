pipeline {
    // agent any
    // tools {
    //     maven 'maven3.9.11'
    // }
    agent {
        docker {
            image 'maven:3.9.11-eclipse-temurin-17-alpine'
        }
    }
    options {
        timeout(time: 10, unit: 'MINUTES')
        // ansiColor('xterm')
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
                sh 'mvn test -Dmaven.test.failure.ignore=true -B -ntp'
            }
            post { 
                success { 
                    junit 'target/surefire-reports/*.xml'
                    // junit testResults: 'target/surefire-reports/*.xml', skipMarkingBuildUnstable: true
                }
            }
        } 
        stage('Coverage') {
            steps {
                sh 'mvn jacoco:report -B -ntp'
            }
            post {
                success {
                    recordCoverage(tools: [[parser: 'JACOCO']])
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