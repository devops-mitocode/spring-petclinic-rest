pipeline {
    // agent any
    // tools {
    //     maven 'maven3.9.10'
    // }
    agent {
        docker {
            image 'maven:3.9.9-eclipse-temurin-17-alpine'
        }
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
                // sh 'mvn test -B -ntp'
                // junit 'target/surefire-reports/*.xml'

                sh 'mvn test -Dmaven.test.failure.ignore=true -B -ntp'
            }
            post {
                success {
                    junit testResults: 'target/surefire-reports/*.xml', skipMarkingBuildUnstable: true
                }
            } 
        }
        stage('Coverage') {
            steps {
                sh 'mvn jacoco:report -B -ntp'
            }
            post {
                success {
                    // recordCoverage(tools: [[parser: 'JACOCO']])

                    recordCoverage(
                        tools: [[parser: 'JACOCO']],
                        sourceCodeRetention: 'EVERY_BUILD',
                        qualityGates: [
                                [threshold: 80.0, metric: 'LINE', criticality: 'FAILURE'],
                                [threshold: 80.0, metric: 'BRANCH', criticality: 'FAILURE']
                        ]
                    )
                }
            }
        }
        stage('Package') {
            steps {
                sh 'mvn package -DskipTests -B -ntp'
            }
        }
        stage('Sonarqube') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh 'mvn sonar:sonar -B -ntp'
                }
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