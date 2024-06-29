pipeline {
    agent {
        docker {
            image 'maven:3.8.8-eclipse-temurin-17-alpine'
        }
    }
    stages {
        // stage('Build') {
        //     steps {
        //         sh 'mvn clean package -DskipTests -B -ntp'
        //     }
        // }
        stage('Artifactory') {
           steps {
               script{

                    sh 'env | sort'

                    def release = 'spring-petclinic-rest-release'
                    def snapshot = 'spring-petclinic-rest-snapshot'

                    def server = Artifactory.server 'artifactory'

                    // Forma 1
                    def rtMaven = Artifactory.newMavenBuild()
                    rtMaven.deployer server: server, releaseRepo: release, snapshotRepo: snapshot
                    def buildInfo = rtMaven.run pom: 'pom.xml', goals: 'clean install -B -ntp -DskipTests'
                    server.publishBuildInfo buildInfo

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
