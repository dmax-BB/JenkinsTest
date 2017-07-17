def myBuilds = [:]

node{
  //checkout scm: [$class: 'GitSCM', branches: [[name: "origin/master"]], userRemoteConfigs: [[credentialsId: "$env.DANA_TEST_CREDENTIALS", url: 'git@github.com:dmax-BB/JenkinsTest.git']], extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'JenkinsTest1']]]
  //checkout scm: [$class: 'GitSCM', branches: [[name: "origin/master"]], userRemoteConfigs: [[credentialsId: "$env.DANA_TEST_CREDENTIALS", url: 'git@github.com:dmax-BB/JenkinsTest.git']], extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'JenkinsTest2']]]
  //checkout scm: [$class: 'GitSCM', branches: [[name: "origin/master"]], userRemoteConfigs: [[credentialsId: "$env.DANA_TEST_CREDENTIALS", url: 'git@github.com:dmax-BB/JenkinsTest.git']], extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'JenkinsTest3']]]
  //checkout scm: [$class: 'GitSCM', branches: [[name: "origin/master"]], userRemoteConfigs: [[credentialsId: "$env.DANA_TEST_CREDENTIALS", url: 'git@github.com:dmax-BB/JenkinsTest.git']], extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'JenkinsTest4']]]
  //checkout scm: [$class: 'GitSCM', branches: [[name: "origin/master"]], userRemoteConfigs: [[credentialsId: "$env.DANA_TEST_CREDENTIALS", url: 'git@github.com:dmax-BB/JenkinsTest.git']], extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'JenkinsTest5']]]
  //checkout scm: [$class: 'GitSCM', branches: [[name: "origin/master"]], userRemoteConfigs: [[credentialsId: "$env.DANA_TEST_CREDENTIALS", url: 'git@github.com:dmax-BB/JenkinsTest.git']], extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'JenkinsTest6']]]

  stage('MyParallel') {
    [1,2].each {
      def a = it;
      //myBuilds[a] = { print "${a}\n" }
      myBuilds[a] = this.getTest(a)
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

def getTest(String testFileName){
        def doStuffClosure = {
		print "${testFileName}\n"
	}
}
