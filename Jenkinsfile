pipeline {
    agent none
    // environment {
    //     CONTAINER_IP = '192.168.1.100'
    // }
    options {
        timeout(time: 10, unit: 'MINUTES')
        ansiColor('xterm')
        // buildDiscarder(logRotator(numToKeepStr: '7'))
    }
    triggers {
        cron('H/5 * * * *')
    }
    stages {
        stage('Build') {
            agent {
                docker {
                    image 'maven:3.9.6-eclipse-temurin-17-alpine'
                }
            }
            options {
                skipDefaultCheckout()
                // ansiColor('xterm')
            }
            steps {
                // ansiColor('xterm') {
                    sh 'mvn clean package -Dstyle.color=always -DskipTests -B -ntp'
                // }
            }
        }
        stage('Prepare environment') {
            agent any
            steps {
                sh "docker compose --project-name ${BUILD_TAG} up -d"
                sh 'docker ps -a'
                sh 'docker network ls'
                script {
                    CONTAINER_IP = sh(script: "docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' ${BUILD_TAG}-backend-1", returnStdout: true).trim()
                    print "CONTAINER_IP: ${CONTAINER_IP}"
                }
                // sh 'env | sort'
            }
        }        
        stage('Integration Test') {
            agent {
                docker {
                    image 'maven:3.9.6-eclipse-temurin-8-alpine'
                    args "--network ${BUILD_TAG}_default -e CONTAINER_IP=${CONTAINER_IP}"
                }
            }
            options {
                skipDefaultCheckout()
                // ansiColor('xterm')
            }
            steps {
                sh 'ls -la'
                // sh 'docker compose --project-name petclinic-integration-tests up -d'

                // sh 'docker ps -a'
                sh 'sleep 1m'

                // sh 'pwd'
                // sh 'ls -la'
                sh "curl http://${CONTAINER_IP}:9966/petclinic/api/pettypes"

                // sh 'env | sort'

                dir('acceptance-it'){
                    git branch: 'master',
                    url: 'https://github.com/devops-mitocode/acceptance-it.git'
                }

                // sh 'ls -la'
                // sh 'cd acceptance-it && ls -la'

                sh 'mvn clean verify -Dstyle.color=always -f acceptance-it/pom.xml -B -ntp'

                publishHTML(
                    target: [
                        reportName : 'Serenity Report',
                        reportDir:   'acceptance-it/target/site/serenity',
                        reportFiles: 'index.html',
                        keepAll:     true,
                        alwaysLinkToLastBuild: true,
                        allowMissing: false
                    ]
                )
            }
        }
        stage('Destroy environment') {
            agent any
            options {
                skipDefaultCheckout()
            }            
            steps {
                sh "docker compose --project-name ${BUILD_TAG} down --rmi all --volumes"
            }
        }
    }
    // post {
    //     always {
    //         sh '''
    //             echo "BUILD_TAG: ${BUILD_TAG}"
    //             echo "CONTAINER_IP: ${CONTAINER_IP}"
    //         '''
    //         sh "docker compose --project-name ${BUILD_TAG} down --rmi all --volumes"
    //     }
    // }
}