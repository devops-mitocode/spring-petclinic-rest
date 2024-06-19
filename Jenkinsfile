pipeline {
    agent any
    tools{
        maven 'maven3.8.8'
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests -B -ntp'
            }
        }
        stage("DockerHub"){
            steps{
                script {
                    def pom = readMavenPom file: 'pom.xml'
                    def app = docker.build("danycenas/${pom.artifactId}:${pom.version}")

                    docker.withRegistry('https://registry.hub.docker.com/', 'dockerhub-credentials') {
                        app.push()
                        app.push('latest')
                    }
                }
            }
        }
    }
}