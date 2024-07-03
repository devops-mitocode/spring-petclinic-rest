pipeline {
    agent none
    stages {
        stage('Build') {
            agent {
                docker {
                    image 'maven:3.8.8-eclipse-temurin-17-alpine'
                }
            }
            steps {
                sh 'mvn clean package -DskipTests -B -ntp'
            }
        }
        stage('Dockerhub') {
            agent any
            steps {
                script {
                    def pom = readMavenPom file: 'pom.xml'
                    println pom

                    // sh 'docker build -t spring-petclinic-rest .'

                    def app = docker.build("danycenas/${pom.artifactId}:${pom.version}")

                    docker.withRegistry('https://registry.hub.docker.com', 'dockerhub-credentials') {
                        app.push()
                        app.push('latest')
                    }
                }
            }
        }
    }
}
