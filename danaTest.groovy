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
      myBuilds[e.key] = this.performSync(e.key, e.value)
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

def performSync(String repoName, String repoURL){
  checkout scm: [$class: 'GitSCM', branches: [[name: "origin/master"]], userRemoteConfigs: [[credentialsId: "$env.DANA_TEST_CREDENTIALS", url: "${repoURL}"]], extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: "${repoName}"]]]
}
