pipeline {
    agent {
        docker {
            image 'maven:3.8.8-eclipse-temurin-17-alpine'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests -B -ntp'
            }
        }
        stage('Artifactory') {
           steps {
               script{

                    sh 'env | sort'
                    env.MAVEN_HOME='/usr/share/maven'

                    def release = 'spring-petclinic-rest-release'
                    def snapshot = 'spring-petclinic-rest-snapshot'

                    def server = Artifactory.server 'artifactory'

                    // Forma 1
                    // def rtMaven = Artifactory.newMavenBuild()
                    // rtMaven.deployer server: server, releaseRepo: release, snapshotRepo: snapshot
                    // def buildInfo = rtMaven.run pom: 'pom.xml', goals: 'clean install -B -ntp -DskipTests'
                    // server.publishBuildInfo buildInfo

                    // Forma 2
                    def pom = readMavenPom file : 'pom.xml'
                    println pom
                    println env.GIT_BRANCH

                    def targetRepo
                    if (env.GIT_BRANCH == 'origin/master' || env.GIT_BRANCH.startsWith('release/')) {
                        targetRepo = 'spring-petclinic-rest-release'
                    } else {
                        targetRepo = 'spring-petclinic-rest-snapshot'
                    }
                    def uploadSpec = """
                        {
                            "files": [
                                {
                                    "pattern": "target/.*.jar",
                                    "target": "${targetRepo}/${pom.groupId}/${pom.artifactId}/${pom.version}/",
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
    // post{
    //     success {
    //         archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
    //         cleanWs()
    //     }
    // }
}
