pipeline {
    agent none
    environment {
        S3_BUCKET = 'elasticbeanstalk-us-west-2-231784247281'
        EB_APP_NAME = 'petclinic1'
        EB_ENV_NAME = 'Petclinic1-env'
        AWS_REGION = 'us-west-2'
        AWS_ACCESS_KEY_ID = credentials('aws-access-key-id')
        AWS_SECRET_ACCESS_KEY = credentials('aws-secret-access-key')
    }    
    stages {
        stage('Build and Package') {
            agent {
                docker {
                    image 'maven:3.8.8-eclipse-temurin-17-alpine'
                }
            }
            steps {
                sh 'tar --version'
                sh 'mvn clean package -DskipTests -B -ntp'
                script {
                    def pom = readMavenPom file: 'pom.xml'
                    sh """
                        mkdir -p target/deployment
                        cp target/${pom.artifactId}-${pom.version}.jar target/deployment/
                        cp -r .ebextensions target/deployment/
                        cp -r .platform target/deployment/
                        tar -czvf target/${pom.artifactId}-${pom.version}.tar.gz -C target/deployment .
                    """
                }
            }
        }
        stage('Upload to S3') {
            agent {
                docker {
                    image 'amazon/aws-cli:2.24.8'
                    args '--entrypoint=""'
                }
            }
            options {
                skipDefaultCheckout()
            }
            steps {
                script {
                    def pom = readMavenPom file: 'pom.xml'
                    sh """
                        aws s3 cp target/${pom.artifactId}-${pom.version}.tar.gz s3://$S3_BUCKET/ --region $AWS_REGION
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
            options {
                skipDefaultCheckout()
            }
            steps {
                script {
                    def pom = readMavenPom file: 'pom.xml'
                    sh """
                        aws elasticbeanstalk create-application-version --application-name $EB_APP_NAME --version-label ${pom.version} --source-bundle S3Bucket="$S3_BUCKET",S3Key="${pom.artifactId}-${pom.version}.tar.gz" --region $AWS_REGION
                        
                        aws elasticbeanstalk update-environment --application-name $EB_APP_NAME --environment-name $EB_ENV_NAME --version-label ${pom.version} --region $AWS_REGION
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
