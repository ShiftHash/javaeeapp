pipeline {
    agent any

    environment {
        registry = ""
        registryCredential = 'docker_akshay'
        dockerImage = ''

    }


    stages {
        stage('Cloning Git') {
            steps {
                git branch: 'master', url: 'https://github.com/ShiftHash/javaeeapp.git'
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
