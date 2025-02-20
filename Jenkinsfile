pipeline {
    agent none
    environment {
        S3_BUCKET = 'elasticbeanstalk-us-west-2-231784247281'
        EB_APP_NAME = 'petclinic25'
        EB_ENV_NAME = 'Petclinic25-env'
        AWS_REGION = 'us-west-2'
        AWS_ACCESS_KEY_ID = credentials('aws-access-key-id')
        AWS_SECRET_ACCESS_KEY = credentials('aws-secret-access-key')
    }    
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
        // stage('Docker Build and Tag') {
        //     agent any
        //     steps {
        //         script {
        //             def pom = readMavenPom file: 'pom.xml'
                    
        //             sh 'docker run --privileged --rm tonistiigi/binfmt --install all'
        //             sh 'docker buildx create --use'
        //             sh 'docker buildx inspect --bootstrap'

        //             withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
        //                 sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'
        //                 sh """
        //                     docker buildx build \
        //                         -t danycenas/${pom.artifactId}:${pom.version} \
        //                         -t danycenas/${pom.artifactId}:latest \
        //                         --platform linux/amd64,linux/arm64 --push .
        //                 """
        //             }
        //         }
        //     }
        // }
        stage('Upload to S3') {
            agent {
                docker {
                    image 'amazon/aws-cli:2.24.8'
                    args '--entrypoint=""'
                }
            }
            steps {
                script {
                    def pom = readMavenPom file: 'pom.xml'
                    sh """
                        aws configure set aws_access_key_id $AWS_ACCESS_KEY_ID
                        aws configure set aws_secret_access_key $AWS_SECRET_ACCESS_KEY
                        aws configure set default.region $AWS_REGION
                        
                        aws s3 cp target/${pom.artifactId}-${pom.version}.jar s3://$S3_BUCKET/${pom.artifactId}-${pom.version}.jar
                    """
                }
            }
        }
        stage('Deploy to Elastic Beanstalk') {
            agent {
                docker {
                    image 'amazon/aws-cli:2.24.8'
                    args '--entrypoint=""'
                }
            }
            steps {
                script {
                    def pom = readMavenPom file: 'pom.xml'
                    sh """
                        aws configure set aws_access_key_id $AWS_ACCESS_KEY_ID
                        aws configure set aws_secret_access_key $AWS_SECRET_ACCESS_KEY
                        aws configure set default.region $AWS_REGION
                        
                        aws elasticbeanstalk create-application-version --application-name $EB_APP_NAME --version-label ${pom.version} --source-bundle S3Bucket="$S3_BUCKET",S3Key="${pom.artifactId}-${pom.version}.jar"
                        
                        aws elasticbeanstalk update-environment --application-name $EB_APP_NAME --environment-name $EB_ENV_NAME --version-label ${pom.version}
                    """
                }
            }
        }    
    }
    post {
        success {
            node('built-in'){
                cleanWs()
            }
        }
    }
}
