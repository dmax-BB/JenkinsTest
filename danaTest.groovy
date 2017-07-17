def stringsToEcho = ["a", "b", "c", "d"]
def myBuilds = [:]
def repoMap = [:]
repoMap.put('rails','git@github.com:rails/rails.git')
repoMap.put('tensorflow','git@github.com:tensorflow/tensorflow.git')
repoMap.put('bootstrap','git@github.com:twbs/bootstrap.git')
repoMap.put('freeCodeCamp','git@github.com:freeCodeCamp/freeCodeCamp.git')
repoMap.put('react','git@github.com:facebook/react.git')
repoMap.put('angular.js','git@github.com:angular/angular.js.git')

node {
  stage('MyParallel') {
    for (int i = 0; i < stringsToEcho.size(); i++) {
      def s = stringsToEcho.get(i)
      def stepName = "Sync number [${i}]"
      myBuilds[stepName] = syncCode(s)
    }
  }

  try{
    stage('RunParallel') {
      parallel myBuilds
    }
  } catch (Exception e){
    throw e
  }
}
def syncCode(inputString) {
    return {
        node {
            echo inputString
        }
    }
}
