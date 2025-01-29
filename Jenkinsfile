pipeline {
    agent {
        docker {
            image 'maven:3.8.8-eclipse-temurin-17-alpine'
        }
    }
    triggers {
        githubPush()
    }
    stages {
        stage('Package') {
            steps {
                // sh 'git branch -a'
                // sh 'git show-ref'
                sh 'env | sort'
                sh 'mvn clean package -DskipTests -B -ntp'
            }
        }
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
       stage('Artifactory') {
           steps {
               script {
                    // Forma 1
                    // sh 'env | sort'
                    // env.MAVEN_HOME = '/usr/share/maven'

                    // def releases = 'spring-petclinic-rest-release'
                    // def snapshots = 'spring-petclinic-rest-snapshot'

                    // def server = Artifactory.server 'artifactory'
                    // def rtMaven = Artifactory.newMavenBuild()
                    // rtMaven.deployer server: server, releaseRepo: releases, snapshotRepo: snapshots
                    // def buildInfo = rtMaven.run pom: 'pom.xml', goals: 'clean install -B -ntp -DskipTests'

                    // server.publishBuildInfo buildInfo

                    // Forma 2: File Spec

                    def pom = readMavenPom file : 'pom.xml'
                    println pom

                    def targetRepo
                    def branchName = env.GIT_BRANCH.startsWith('origin/') ? env.GIT_BRANCH.replace('origin/', '') : env.GIT_BRANCH
                    println env.GIT_BRANCH
                    println branchName

                    if (branchName == 'master') {
                        targetRepo = 'spring-petclinic-rest-release'
                    } else if (branchName == 'develop' || branchName.startsWith('feature/') || branchName.startsWith('hotfix/') || branchName.startsWith('release/')) {
                        targetRepo = 'spring-petclinic-rest-snapshot'
                    }

                    def server = Artifactory.server 'artifactory'
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
