node {
    stage('Test') {
        echo 'Lanzando tests'
        git 'https://github.com/eblazquezsierra/proyecto_jenkins.git'
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

    stage('Build') {
        echo 'Build'
        sh 'mvn clean package -DskipTests'
        archiveArtifacts artifacts: 'target/*.jar'
    }
    
    stage('Deploy') {
        echo 'Deploy imagen Docker'
        dockerImage = docker.build("eblazquez/test-project:latest")
        echo 'Deplegar en DockerHub'
        docker.withRegistry( '', "dockerhub") {
            dockerImage.push()
        }
    }

    stage('Launch Docker') {
        echo 'Ejectuar contenedores'
        sh 'docker-compose down && docker-compose up --build -d'
    }
}
