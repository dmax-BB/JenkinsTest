def myBuilds = [:]
def repoMap = [:]
repoMap.put('rails','git@github.com:rails/rails.git')
repoMap.put('tensorflow','git@github.com:tensorflow/tensorflow.git')
repoMap.put('bootstrap','git@github.com:twbs/bootstrap.git')
repoMap.put('freeCodeCamp','git@github.com:freeCodeCamp/freeCodeCamp.git')
repoMap.put('react','git@github.com:facebook/react.git')
repoMap.put('angular.js','git@github.com:angular/angular.js.git')

node{

  stage('MyParallel') {
    for ( e in repoMap ) {
      //myBuilds[e.key] = this.syncTest()
      print "$e.key $e.value"
      def a = e.key
      def b = e.value
      print "$a $b"
      print "${a}  ${b}\n"
      myBuilds[a] = { print "${a}  ${b}\n" }
    }
  }
}
try{
  stage('RunParallel'){
  myBuilds.failFast = true
  //parallel myBuilds
  }
} catch (Exception e){
  print "I failed"
}

def syncTest() {
  print "Starting syncTest"
  sleep 30
  checkout scm: [$class: 'GitSCM', branches: [[name: "origin/master"]], userRemoteConfigs: [[credentialsId: "$env.DANA_TEST_CREDENTIALS", url: 'git@github.com:rails/rails.git']], extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'rails']]]
  print "syncTest complete"
}
