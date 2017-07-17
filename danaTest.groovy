def stringsToEcho = ["a", "b", "c", "d"]
def myBuilds = [:]
def repoMap = [:]
repoMap.put('rails','git@github.com:rails/rails.git')
repoMap.put('tensorflow','git@github.com:tensorflow/tensorflow.git')
repoMap.put('bootstrap','git@github.com:twbs/bootstrap.git')
repoMap.put('freeCodeCamp','git@github.com:freeCodeCamp/freeCodeCamp.git')
repoMap.put('react','git@github.com:facebook/react.git')
repoMap.put('angular.js','git@github.com:angular/angular.js.git')
def repoNames = ["rails", "tensorflow", "bootstrap", "bootstrap", "freeCodeCamp", "react", "angular.js"]

node {
  stage('MyParallel') {
    def counter=1

    def keys = repoMap.keySet() as List
    for (key in keys) {
      def value = repoMap.get(key)
      def stepName = "[${key}]"
      myBuilds[stepName] = syncCode(key,value)
    }

    //for (int i = 0; i < repoNames.size(); i++) {
      //def targetDir=repoNames.get(i)
      //def targetURL=repoMap.get(targetDir)

      //def stepName = "[${targetDir}]"
      //print "key = ${targetDir}, value = ${targetURL}"
      //myBuilds[stepName] = syncCode(targetDir,targetURL)
    //}

    //for (int i = 0; i < stringsToEcho.size(); i++) {
      //def s = stringsToEcho.get(i)
      //def stepName = "Sync number [${i}]"
      //myBuilds[stepName] = syncCode(s,s)
    //}
  }

  try{
    stage('RunParallel') {
      parallel myBuilds
    }
  } catch (Exception e){
    throw e
  }
}
def syncCode(targetDir,targetURL) {
    return {
        node {
            echo targetDir
            echo targetURL
        }
    }
}
