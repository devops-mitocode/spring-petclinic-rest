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
    }
    post {
        success {
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            cleanWs()
        }
    }
}
