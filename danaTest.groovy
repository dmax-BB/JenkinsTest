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
      myBuilds[e.key] = syncTest()
    }
  }
}
try{
  stage('RunParallel'){
  myBuilds.failFast = true
  parallel myBuilds
  }
} catch (Exception e){
  throw e
}

def syncTest() {
  print "Starting syncTest"
  print "syncTest complete"
}
