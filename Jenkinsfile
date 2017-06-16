def srvName = 'account-service'
def gitRepo = 'https://github.com/microservices-kata/petstore-account-service.git'
def devUser = 'scaleworks'
def devHost = '10.202.129.46'
def registryUrl = '10.202.129.203:5000'
def mvnImage = 'maven:3.5.0-jdk-8-alpine'
def mvnFolder = '/opt/m2'
node {
    stage('代码更新') {
        checkout scm: [$class: 'GitSCM', branches: [[name: '*/master']],
                      userRemoteConfigs: [[url: gitRepo]]]
    }
    docker.image("${mvnImage}").inside("-v ${mvnFolder}:/root/.m2") {
        stage('构建代码') {
            sh "mvn clean compile"
        }
        stage('单元测试和打包') {
            sh "mvn package"
        }
        stage('契约和集成测试') {
            withEnv(["PACT_BROKER_URL=${devHost}", "PACT_BROKER_PORT=2000"]) {
               sh "mvn verify"
            }
        }
    }
    stage('创建镜像') {
        sh "mv -f target/*.jar deployment/${srvName}.jar"
        sh "docker build -t ${registryUrl}/${srvName}:$BUILD_NUMBER deployment"
        sh "docker push ${registryUrl}/${srvName}:$BUILD_NUMBER"
        sh "docker rmi ${registryUrl}/${srvName}:$BUILD_NUMBER"
        sh "rm -f deployment/${srvName}.jar"
    }
    stage('部署Dev环境') {
        def devDockerDaemon = "tcp://${devHost}:2376"
        sh "docker -H ${devDockerDaemon} rm -f ${srvName} | true"
        sh "docker -H ${devDockerDaemon} run -d --name ${srvName} --net=host \
            ${registryUrl}/${srvName}:$BUILD_NUMBER"
        sh "docker -H ${devDockerDaemon} image prune --force --all \
            --filter until=`date -d '5 day ago' '+%F'`"
    }
}
