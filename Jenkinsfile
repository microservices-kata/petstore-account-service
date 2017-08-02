def srvName = 'account-service'
def gitRepo = 'https://github.com/microservices-kata/petstore-account-service.git'
def devUser = 'scaleworks'
def devHost = '10.202.129.46'
def registryUrl = '10.202.129.203:5000'
def mvnImage = 'maven:3.5.0-jdk-8-alpine'
def mvnFolder = '/opt/m2'
def devDockerDaemon = "tcp://${devHost}:2376"

pipeline {
    agent any
    stages {
        stage('代码更新') {
            steps {
                checkout scm: [$class: 'GitSCM', branches: [[name: '*/master']],
                              userRemoteConfigs: [[url: gitRepo]]]
            }
        }
        stage('构建代码') {
            agent {
                docker {
                    reuseNode true
                    image "${mvnImage}"
                    args "-v ${mvnFolder}:/root/.m2"
                }
            }
            steps {
                sh "mvn clean compile"
            }
        }
        stage('单元测试和打包') {
            agent {
                docker {
                    reuseNode true
                    image "${mvnImage}"
                    args "-v ${mvnFolder}:/root/.m2"
                }
            }
            steps {
                sh "mvn package"
            }
        }
        stage('契约和集成测试') {
            agent {
                docker {
                    reuseNode true
                    image "${mvnImage}"
                    args "-v ${mvnFolder}:/root/.m2"
                }
            }
            steps {
                withEnv(["PACT_BROKER_URL=${devHost}", 
                         "PACT_BROKER_PORT=2000"]) {
                    sh "mvn verify"
                }
            }
        }
        stage('创建镜像') {
            steps {
                sh """
                    mv -f target/*.jar deployment/${srvName}.jar
                    docker build -t ${registryUrl}/${srvName}:$BUILD_NUMBER deployment
                    docker push ${registryUrl}/${srvName}:$BUILD_NUMBER
                    docker rmi ${registryUrl}/${srvName}:$BUILD_NUMBER
                    rm -f deployment/${srvName}.jar
                """
            }
        }
        stage('部署Dev环境') {
            steps {
                sh """
                    docker -H ${devDockerDaemon} rm -f ${srvName} | true
                    docker -H ${devDockerDaemon} run -d --name ${srvName} --net=host ${registryUrl}/${srvName}:$BUILD_NUMBER
                    docker -H ${devDockerDaemon} image prune --force --all --filter until=`date -d '5 day ago' '+%F'`
                """
            }
        }
    }
}
