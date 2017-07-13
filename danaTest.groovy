def myBuilds = [:]

node{
  checkout scm: [$class: 'GitSCM', branches: [[name: "origin/master"]], userRemoteConfigs: [[credentialsId: "$env.DANA_TEST_CREDENTIALS", url: 'git@github.com:dmax-BB/JenkinsTest.git']], extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'JenkinsTest']]]

  stage('MyParallel') {
    [1,2].each {
      def a = it;
      myBuilds[a] = { print "${a}\n" }
    }   
  }    
}
try{
  stage('RunParallel'){
  parallel myBuilds
  }
} catch (Exception e){
  throw e
}
