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
    options {
        timeout(time: 5, unit: 'MINUTES')
    }
    triggers {
        // pollSCM("* * * * *")
        githubPush()
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
                // sh 'mvn test -B -ntp'
                sh 'mvn test -Dmaven.test.failure.ignore=true -B -ntp'
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
                sh 'mvn package -B -ntp -DskipTests'
            }
        }
        // stage('Sonarqube') {
        //     steps {
        //         withSonarQubeEnv('sonarqube') {
        //             sh 'mvn sonar:sonar -B -ntp'
        //         }
        //     }
        // }
        // stage("Quality Gate"){
        //     steps{
        //         timeout(time: 2, unit: 'MINUTES') {
        //             waitForQualityGate abortPipeline: true
        //         }
        //     }
        // }
        stage('SonarQube') {
            steps {
                withSonarQubeEnv('sonarqube'){
                    sh 'env | sort'
                    script {
                        if (env.CHANGE_ID) {
                            sh """
                                mvn sonar:sonar -B -ntp \
                                -Dsonar.pullrequest.key=${env.CHANGE_ID} \
                                -Dsonar.pullrequest.branch=${env.CHANGE_BRANCH} \
                                -Dsonar.pullrequest.base=${env.CHANGE_TARGET}
                            """
                        } else {
                            def branchName = GIT_BRANCH.replaceFirst('^origin/', '')
                            println "Branch name: ${branchName}"
                            sh "mvn sonar:sonar -B -ntp -Dsonar.branch.name=${branchName} -Dsonar.branch.target=${branchName}"
                        }
                    }
                }
            }
        }
    }
    post {
        success {
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }
        cleanup {
            cleanWs()
        }
    }
}