pipeline {
    agent any 
    stages {
        stage('Configure project') {
            steps {
                echo 'Configurando environment'
                def mvnHome = tool 'microservicios'
                env.PATH = "${mvnHome}/bin:${env.PATH}"
                echo "var mvnHome='${mvnHome}'" 
            }
        }
        stage('Compile application') {
            steps {
                echo 'Compilando código fuente'
                sh 'rm -rf *'
                checkout scm
                sh 'mvn clean compile'
            }
        }
        stage('Unit tests') {
            steps {
                echo 'Ejecutando tests'
                try {
                    sh 'mvn verify'
                    step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
                } catch(err) {
                    step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
                    if (currentBuild.result == 'UNSTABLE')
                        currentBuild.result = 'FAILURE'
                    throw err
                }
            }
        }
        stage('Install and deploy JAR') {
            steps {
                echo 'Instalar y guardar JAR'
                sh 'mvn install -Dmaven.test.skip = true'
                step([$class: 'ArtifactArchiver', artifacts: '**/target/*.jar, **/target/*.war', fingerprint: true])
            }
        }
        stage('Build Docker image') {
            steps {
                echo 'Build imagen Docker'
                dockerImage = docker.build("eblazquez/test-project:latest")
                echo 'Deplegar en DockerHub'
                withDockerRegistry([credentialsId: "dockerhub", url: ""]) {
                    dockerImage.push()
                } 
            }
        }
        stage('Launch Docker container') {
            steps {
                echo 'Ejectuar contenedores'
                sh 'docker-compose down && docker-compose up --build -d' 
            }
        }
    }
}