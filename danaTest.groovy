def myBuilds = [:]
def repoMap = [:]
repoMap.put('rails','git@github.com:rails/rails.git')
repoMap.put('tensorflow','git@github.com:tensorflow/tensorflow.git')
repoMap.put('bootstrap','git@github.com:twbs/bootstrap.git')
repoMap.put('freeCodeCamp','git@github.com:freeCodeCamp/freeCodeCamp.git')
repoMap.put('react','git@github.com:facebook/react.git')
repoMap.put('angular.js','git@github.com:angular/angular.js.git')

node{
  for ( e in repoMap ) {
    print "key = ${e.key}, value = ${e.value}"
  }

  stage('MyParallel') {
    for ( e in repoMap ) {
      myBuilds[e.key] = syncTest()
    }
  }

  echo "${myBuilds.toString()}"
}
try{
  stage('RunParallel'){
  parallel myBuilds
  }
} catch (Exception e){
  throw e
}

def performSync(String repoName, String repoURL){
  print "repoName = ${repoName}, repoURL = ${repoURL}"
  checkout scm: [$class: 'GitSCM', branches: [[name: "origin/master"]], userRemoteConfigs: [[credentialsId: "$env.DANA_TEST_CREDENTIALS", url: 'git@github.com:rails/rails.git']], extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'rails']]]
}

def syncTest() {
  print "Starting syncTest"
  checkout scm: [$class: 'GitSCM', branches: [[name: "origin/master"]], userRemoteConfigs: [[credentialsId: "$env.DANA_TEST_CREDENTIALS", url: 'git@github.com:rails/rails.git']], extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'rails']]]
  print "syncTest complete"
}
