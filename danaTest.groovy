def myBuilds = [:]

node{
  //checkout scm: [$class: 'GitSCM', branches: [[name: "origin/master"]], userRemoteConfigs: [[credentialsId: "$env.DANA_TEST_CREDENTIALS", url: 'git@github.com:dmax-BB/JenkinsTest.git']], extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'JenkinsTest1']]]
  //checkout scm: [$class: 'GitSCM', branches: [[name: "origin/master"]], userRemoteConfigs: [[credentialsId: "$env.DANA_TEST_CREDENTIALS", url: 'git@github.com:dmax-BB/JenkinsTest.git']], extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'JenkinsTest2']]]
  //checkout scm: [$class: 'GitSCM', branches: [[name: "origin/master"]], userRemoteConfigs: [[credentialsId: "$env.DANA_TEST_CREDENTIALS", url: 'git@github.com:dmax-BB/JenkinsTest.git']], extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'JenkinsTest3']]]
  //checkout scm: [$class: 'GitSCM', branches: [[name: "origin/master"]], userRemoteConfigs: [[credentialsId: "$env.DANA_TEST_CREDENTIALS", url: 'git@github.com:dmax-BB/JenkinsTest.git']], extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'JenkinsTest4']]]
  //checkout scm: [$class: 'GitSCM', branches: [[name: "origin/master"]], userRemoteConfigs: [[credentialsId: "$env.DANA_TEST_CREDENTIALS", url: 'git@github.com:dmax-BB/JenkinsTest.git']], extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'JenkinsTest5']]]
  //checkout scm: [$class: 'GitSCM', branches: [[name: "origin/master"]], userRemoteConfigs: [[credentialsId: "$env.DANA_TEST_CREDENTIALS", url: 'git@github.com:dmax-BB/JenkinsTest.git']], extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'JenkinsTest6']]]
 print "In node"
  stage('MyParallel') {
   print "In MyParallel"
    [1,2].each {
      def a = it;
      //myBuilds[a] = { print "${a}\n" }
      myBuilds[a] = { getTest(a) }
    }
   print "Out MyParallel"
  }
 print "Out Note"
}
try{
 print "In try"
  stage('RunParallel'){
   print "In RunParallel"
  parallel myBuilds
  }
} catch (Exception e){
  print "I failed"
}

def getTest(String testFileName){
  print "${testFileName}\n"
}
