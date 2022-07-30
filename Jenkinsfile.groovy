pipeline {
    agent any

    environment {
        registry = "javaeeapp"
        registryCredential = 'docker_ev'
        dockerImage = ''


    }

    stages {
        stage('Cloning Git') {
            steps {
                git branch: 'main', url: 'https://github.com/ShiftHash/javaeeapp.git'
            }
        }
        stage('build the project') {
            steps {
                sh "mvn clean install"
            }
        }
        stage('Building  image') {
            steps {
                script {
                    dockerImage = docker.build registry + ":$BUILD_NUMBER"
                }
            }
        }
        stage('Deploy image') {
            steps {
                script {
                    docker.withRegistry( '', registryCredential ) {
                        dockerImage.push("$BUILD_NUMBER")
                        dockerImage.push('latest')
                    }
                }
            }
        }
        stage('Docker Run') {
            steps{
                script {
                    dockerImage.run("-p 8000:8000 --name api_hello")
                }
            }
        }
        stage('Cleaning up'){
            steps {
                sh "docker rmi $registry:$BUILD_NUMBER"
            }
        }
    }
}
