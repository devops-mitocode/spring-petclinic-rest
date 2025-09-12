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
                                [threshold: 60.0, metric: 'BRANCH', criticality: 'FAILURE']
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
        // stage('Sonarqube') {
        //     steps {
        //         withSonarQubeEnv('sonarqube') {
        //             sh 'mvn sonar:sonar -B -ntp'
        //         }
        //     }
        // }
        // stage('SonarQube') {
        //     steps {
        //         withSonarQubeEnv('sonarqube'){
        //             sh 'env | sort'
        //             script {
        //                 if (env.CHANGE_ID) {
        //                     sh """
        //                         mvn sonar:sonar -B -ntp \
        //                         -Dsonar.pullrequest.key=${env.CHANGE_ID} \
        //                         -Dsonar.pullrequest.branch=${env.CHANGE_BRANCH} \
        //                         -Dsonar.pullrequest.base=${env.CHANGE_TARGET}
        //                     """
        //                 } else {
        //                     def branchName = GIT_BRANCH.replaceFirst('^origin/', '')
        //                     println "Branch name: ${branchName}"
        //                     sh "mvn sonar:sonar -B -ntp -Dsonar.branch.name=${branchName} -Dsonar.branch.target=${branchName}"
        //                 }
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
        // stage('Artifactory') {
        //     steps {
        //         script{
        //             // Forma 1: rtMaven

        //             // env.MAVEN_HOME = '/usr/share/maven'

        //             // def releases = 'spring-petclinic-rest-release'
        //             // def snapshots = 'spring-petclinic-rest-snapshot'

        //             // def server = Artifactory.server 'artifactory'
        //             // def rtMaven = Artifactory.newMavenBuild()

        //             // rtMaven.deployer server: server, releaseRepo: releases, snapshotRepo: snapshots
        //             // def buildInfo = rtMaven.run pom: 'pom.xml', goals: 'clean install -B -ntp -DskipTests'

        //             // server.publishBuildInfo buildInfo


        //             // Forma 2 - File Spec
        //             def server = Artifactory.server 'artifactory'
        //             def targetRepo = 'spring-petclinic-rest-release'

        //             def pom = readMavenPom file: 'pom.xml'
        //             println pom.groupId
        //             def groupIdPath = pom.groupId.replaceAll("\\.", "/")
        //             println groupIdPath

        //             def uploadSpec = """
        //                 {
        //                     "files": [
        //                         {
        //                             "pattern": "target/.*.jar",
        //                             "target": "${targetRepo}/${groupIdPath}/${pom.artifactId}/${pom.version}/",
        //                             "regexp": "true",
        //                             "props": "build.url=${RUN_DISPLAY_URL};build.user=${USER}"
        //                         }
        //                     ]
        //                 }
        //             """
        //             server.upload spec: uploadSpec

        //         }
        //     }
        // }
        // stage('Nexus') {
        //     steps {
        //         script{
        //             def pom = readMavenPom file: 'pom.xml'
        //             println pom

        //             nexusPublisher nexusInstanceId: 'nexus',
        //             nexusRepositoryId: 'spring-petclinic-rest-release',
        //             packages: [[$class: 'MavenPackage',
        //             mavenAssetList: [[classifier: '', extension: '', filePath: "target/${pom.artifactId}-${pom.version}.${pom.packaging}"]],
        //             mavenCoordinate: [
        //             groupId: "${pom.groupId}",
        //             artifactId: "${pom.artifactId}",
        //             packaging: "${pom.packaging}",
        //             version: "${pom.version}-${BUILD_NUMBER}"]]]

        //             // nexus with curl -- revisar docs
        //             sh """
        //                curl -X POST -u ${NEXUS_USERNAME}:${NEXUS_PASSWORD} \
        //                -H "Content-Type: application/json" \
        //                -d '{
        //                    "mavenCoordinate": {
        //                        "groupId": "${pom.groupId}",
        //                        "artifactId": "${pom.artifactId}",
        //                        "version": "${pom.version}-${BUILD_NUMBER}",
        //                        "packaging": "${pom.packaging}"
        //                    },
        //                    "files": [
        //                        {
        //                            "path": "target/${pom.artifactId}-${pom.version}.${pom.packaging}"
        //                        }
        //                    ]
        //                }' \
        //                http://nexus:8081/service/rest/v1/components?repository=spring-petclinic-rest-release
        //             """
        //         }
        //     }
        // }
        stage('Artifactory') {
            steps {
                script{
                    // Forma 2 - File Spec
                    def server = Artifactory.server 'artifactory'
                    def targetRepo = 'spring-petclinic-rest-snapshot'

                    def pom = readMavenPom file: 'pom.xml'
                    println pom.groupId
                    def groupIdPath = pom.groupId.replaceAll("\\.", "/")
                    println groupIdPath

                    def uploadSpec = """
                        {
                            "files": [
                                {
                                    "pattern": "target/.*.jar",
                                    "target": "${targetRepo}/${groupIdPath}/${pom.artifactId}/${pom.version}/",
                                    "regexp": "true",
                                    "props": "build.url=${RUN_DISPLAY_URL};build.user=${USER}"
                                }
                            ]
                        }
                    """
                    server.upload spec: uploadSpec
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