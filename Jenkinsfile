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
        timeout(time: 5, unit: 'MINUTES')
        buildDiscarder(logRotator(numToKeepStr: '3'))
    }
    triggers {
        // pollSCM('* * * * *')
        githubPush()
    }
    stages {
        // stage('Checkout SCM') {
        //     steps {
        //         git branch: 'master', url: 'https://github.com/devops-mitocode/spring-petclinic-rest.git'
        //     }
        // }
        // stage('Compile') {
        //     steps {
        //         sh 'mvn clean compile -B -ntp'
        //     }
        // }
        // stage('Test') {
        //     steps {
        //         // sh 'mvn test -B -ntp'
        //         sh 'mvn test -Dmaven.test.failure.ignore=true -B -ntp'
        //     }
        //     post { 
        //         success { 
        //             // junit 'target/surefire-reports/*.xml'
        //             junit testResults: 'target/surefire-reports/*.xml', skipMarkingBuildUnstable: true
        //         }
        //     }
        // } 
        // stage('Coverage') {
        //     steps {
        //         sh 'mvn jacoco:report -B -ntp'
        //     }
        //     post {
        //         success {
        //             recordCoverage(tools: [[parser: 'JACOCO']])
        //         }
        //     }
        // }
        // stage('Package') {
        //     steps {
        //         sh 'mvn package -DskipTests -B -ntp'
        //     }
        // }
        // stage('Sonarqube') {
        //     steps {
        //         withSonarQubeEnv('sonarqube') {
        //             // sh 'mvn sonar:sonar -B -ntp'

        //             sh 'env | sort'

        //             script {
        //                 def branchName = GIT_BRANCH.replaceFirst('^origin/', '')
        //                 println "Branch Name: ${branchName}"

        //                 sh "mvn sonar:sonar -B -ntp -Dsonar.branch.name=${branchName}"
        //             }

        //         }
        //     }
        // }
        // stage("Quality Gate"){
        //     steps{
        //         timeout(time: 1, unit: 'MINUTES') {
        //             waitForQualityGate abortPipeline: true
        //         }
        //     }
        // }
        stage("Build and Package") {
            steps{
                sh 'mvn clean package -DskipTests -B -ntp'
            }
        }
        stage("Publish Artifacts") {
            steps{
                script{

                    sh 'env | sort'

                    // Forma 1: Usando RtMaven

                    def releases = 'spring-petclinic-rest-release'
                    def snapshots = 'spring-petclinic-rest-snapshot'
                    
                    def server = Artifactory.server 'artifactory'
                    def rtMaven = Artifactory.newMavenBuild()
                    rtMaven.deployer server: server, releaseRepo: releases, snapshotRepo: snapshots
                    def buildInfo = rtMaven.run pom: 'pom.xml', goals: 'clean install -B -ntp -DskipTests'


                    server.publishBuildInfo buildInfo

                }
            }
    }
    }
    // post { 
    //     success { 
    //         archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
    //     }
    //     cleanup {
    //         cleanWs()
    //     }
    // }
}