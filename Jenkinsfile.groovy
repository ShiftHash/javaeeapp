pipeline {
    agent any

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

    }
}
