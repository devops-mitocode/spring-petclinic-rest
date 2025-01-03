pipeline {
    agent any
    tools{
        maven 'maven3.8.8'
    }
    stages {
        stage('Test') {
            steps {
                sh 'mvn clean test -B -ntp'  
            }
            post {
                success {
                    junit 'target/surefire-reports/*.xml'
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
        stage('SonarQube') {
            steps {
                withSonarQubeEnv('sonarqube'){
                    sh 'mvn sonar:sonar -B -ntp'
                }
            }
        }
        // stage("Quality Gate"){
        //     steps{
        //         timeout(time: 1, unit: 'MINUTES') {
        //             waitForQualityGate abortPipeline: true
        //         }
        //     }
        // }
        // stage('sonarqube') {
        //     steps {
        //         sh 'env | sort'
        //         withSonarQubeEnv('sonarqube') {
        //             // sh './mvnw sonar:sonar'
        //             // sh './mvnw sonar:sonar -Dsonar.branch.name=master'


        //             script {
        //                 def branchName = GIT_BRANCH.split('/').last()
        //                 sh "./mvnw sonar:sonar -Dsonar.branch.name=${branchName}"
        //             }
        //         }
        //     }
        // }
    }
}