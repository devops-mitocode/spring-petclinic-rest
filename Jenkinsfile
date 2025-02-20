pipeline {
    agent none
    stages {
        stage('Build & Deploy') {
            agent any
            steps {
                script {
                    def pom = readMavenPom file: 'pom.xml'
                     echo "pom: ${pom}"
                    
                    sh 'docker run --privileged --rm tonistiigi/binfmt --install all'
                    sh 'docker buildx create --use'
                    sh 'docker buildx inspect --bootstrap'

                    withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                        sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'
                        sh """
                            docker buildx build \
                                -t danycenas/${pom.artifactId}:${pom.version} \
                                -t danycenas/${pom.artifactId}:latest \
                                --platform linux/amd64,linux/arm64 --push .
                        """
                    }
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
