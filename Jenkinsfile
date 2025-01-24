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
                sh 'mvn clean package -DskipTests -B -ntp'
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
