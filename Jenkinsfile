pipeline {
    agent none
    triggers {
        githubPush()
    }
    stages {
        stage('Package') {
            agent {
                docker {
                    image 'maven:3.8.8-eclipse-temurin-17-alpine'
                }
            }       
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
    //    stage('Artifactory') {
    //        steps {
    //            script {
    //                 // Forma 1

    //                 sh 'env | sort'
    //                 env.MAVEN_HOME = '/usr/share/maven'

    //                 def releases = 'spring-petclinic-rest-release'
    //                 def snapshots = 'spring-petclinic-rest-snapshot'

    //                 def server = Artifactory.server 'artifactory'
    //                 def rtMaven = Artifactory.newMavenBuild()
    //                 rtMaven.deployer server: server, releaseRepo: releases, snapshotRepo: snapshots
    //                 def buildInfo = rtMaven.run pom: 'pom.xml', goals: 'clean install -B -ntp -DskipTests'

    //                 server.publishBuildInfo buildInfo

    //                 // Forma 2: File Spec

    //                 def pom = readMavenPom file : 'pom.xml'
    //                 println pom

    //                 def targetRepo
    //                 def branchName = env.GIT_BRANCH.startsWith('origin/') ? env.GIT_BRANCH.replace('origin/', '') : env.GIT_BRANCH
    //                 println env.GIT_BRANCH
    //                 println branchName

    //                 if (branchName == 'master') {
    //                     targetRepo = 'spring-petclinic-rest-release'
    //                 } else if (branchName == 'develop' || branchName.startsWith('feature/') || branchName.startsWith('hotfix/') || branchName.startsWith('release/')) {
    //                     targetRepo = 'spring-petclinic-rest-snapshot'
    //                 }

    //                 def server = Artifactory.server 'artifactory'
    //                 println pom.groupId
    //                 def groupIdPath = pom.groupId.replaceAll("\\.", "/")
    //                 println groupIdPath

    //                 def uploadSpec = """
    //                     {
    //                         "files": [
    //                             {
    //                                 "pattern": "target/.*.jar",
    //                                 "target": "${targetRepo}/${groupIdPath}/${pom.artifactId}/${pom.version}/",
    //                                 "regexp": "true",
    //                                 "props": "build.url=${RUN_DISPLAY_URL};build.user=${USER}"
    //                             }
    //                         ]
    //                     }
    //                 """
    //                 server.upload spec: uploadSpec
    //            }
    //        }
    //    }
        // stage('Nexus') {
        //     steps {
        //         script {
        //             def pom = readMavenPom file: 'pom.xml'
        //             println pom

        //             nexusPublisher nexusInstanceId: 'nexus',
        //             nexusRepositoryId: 'spring-petclinic-rest-releases',
        //             packages: [[$class: 'MavenPackage',
        //             mavenAssetList: [[classifier: '', extension: '', filePath: "target/${pom.artifactId}-${pom.version}.${pom.packaging}"]],
        //             mavenCoordinate: [
        //             groupId: "${pom.groupId}",
        //             artifactId: "${pom.artifactId}",
        //             packaging: "${pom.packaging}",
        //             version: "${pom.version}-${BUILD_NUMBER}"]]]
        //         }
        //     }
        // }
        // stage('Dockerhub') {
        //     agent any
        //     steps {
        //         script {
        //             def pom = readMavenPom file: 'pom.xml'

        //             def app = docker.build("danycenas/${pom.artifactId}:${pom.version}")

        //             docker.withRegistry('https://registry.hub.docker.com/', 'dockerhub-credentials') {
        //                 app.push()
        //                 app.push('latest')
        //             }
        //         }
        //     }
        // }        
    }
    post {
        success {
            node() {
                cleanWs()
            }
        }
    }
}
