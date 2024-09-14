pipeline {
    // agent any
    // tools {
    //     maven 'maven3.8.8'
    // }
    agent {
        docker {
            image 'maven:3.8.8-eclipse-temurin-17-alpine'
        }
    }
    stages {
        /*
        stage('Checkout SCM') {
            steps {
                git branch: 'master', url: 'https://github.com/devops-mitocode/spring-petclinic-rest.git'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn clean test -B -ntp'
            }
            post {
                success {
                    junit 'target/surefire-reports/*.xml'
                    jacoco(execPattern: 'target/jacoco.exec')
                }
            }

        }
        */
        stage('Package') {
            steps {
                sh 'mvn clean package -DskipTests -B -ntp'
            }
        }
        // stage('Sonarqube') {
        //     steps {
        //         sh 'env | sort'
        //         script {

        //             def branch = env.GIT_BRANCH.split('/').last()
        //             echo "branch: ${branch}"


        //             withSonarQubeEnv('sonarqube') {
        //                 sh "mvn sonar:sonar -Dsonar.branch.name=${branch} -B -ntp"
        //             }
        //         }
        //     }
        // }
        // stage("Quality Gate"){
        //     steps{
        //         timeout(time: 5, unit: 'MINUTES') {
        //             waitForQualityGate abortPipeline: true
        //         }
        //     }
        // }
        stage('Artifactory') {
            steps {
                script{

                    sh 'env | sort'

                    // Forma 1

                    env.MAVEN_HOME = '/usr/share/maven'

                    def releases = 'spring-petclinic-rest-release'
                    def snapshots = 'spring-petclinic-rest-snapshot'

                    def server = Artifactory.server 'artifactory'
                    def rtMaven = Artifactory.newMavenBuild()
                    rtMaven.deployer server: server, releaseRepo: releases, snapshotRepo: snapshots
                    def buildInfo = rtMaven.run pom: 'pom.xml', goals: 'clean install -B -ntp -DskipTests'

                    server.publishBuildInfo buildInfo

                    // Forma 2 - File Spec

                    // def pom = readMavenPom file : 'pom.xml'
                    // println pom

                    // def targetRepo
                    // def branchName = env.GIT_BRANCH.startsWith('origin/') ? env.GIT_BRANCH.replace('origin/', '') : env.GIT_BRANCH
                    // println env.GIT_BRANCH
                    // println branchName

                    // if (branchName == 'master') {
                    //     targetRepo = 'spring-petclinic-rest-release'
                    // } else if (branchName == 'develop' || branchName.startsWith('feature/') || branchName.startsWith('hotfix/') || branchName.startsWith('release/')) {
                    //     targetRepo = 'spring-petclinic-rest-snapshot'
                    // }

                    // def server = Artifactory.server 'artifactory'
                    // def uploadSpec = """
                    //     {
                    //         "files": [
                    //             {
                    //                 "pattern": "target/.*.jar",
                    //                 "target": "${targetRepo}/${pom.groupId}/${pom.artifactId}/${pom.version}/",
                    //                 "regexp": "true",
                    //                 "props": "build.url=${RUN_DISPLAY_URL};build.user=${USER}"
                    //             }
                    //         ]
                    //     }
                    // """
                    // server.upload spec: uploadSpec
                }
            }
        }
        // stage('Nexus') {
        //     steps {
        //         script{

        //             def pom = readMavenPom file: 'pom.xml'
        //             println pom

        //             nexusPublisher nexusInstanceId: 'nexus',
        //             nexusRepositoryId: 'spring-petclinic-rest-release',
        //             packages: [[$class: 'MavenPackage',
        //             mavenAssetList: [[classifier: '', extension: '', filePath: "target/${pom.artifactId}-${pom.version}.jar"]],
        //             mavenCoordinate: [
        //             groupId: "${pom.groupId}",
        //             artifactId: "${pom.artifactId}",
        //             packaging: 'jar',
        //             version: "${pom.version}-${BUILD_NUMBER}"]]]

        //         }
        //     }
        // }   
    }
    post {
        success {
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            cleanWs()
        }
    }
    
}