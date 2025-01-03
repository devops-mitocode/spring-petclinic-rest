pipeline {
    agent any
    tools{
        maven 'maven3.8.8'
    }
    stages {
        stage('Test') {
            steps {
                sh 'env | sort'
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
                    // sh 'mvn sonar:sonar -B -ntp'
                    script {
                        def branchName = GIT_BRANCH.replaceFirst('^origin/', '')
                        println "Branch name: ${branchName}"
                        sh "mvn sonar:sonar -B -ntp -Dsonar.branch.name=${branchName}"
                    }
                }
            }
        }
        stage("Quality Gate"){
            steps{
                timeout(time: 1, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }
    post{
        always{
            cleanWs()
        }
    }
}