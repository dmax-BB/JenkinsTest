def stringsToEcho = ["a", "b", "c", "d"]

def myBuilds = [:]

node {
  stage('MyParallel') {
    for (int i = 0; i < stringsToEcho.size(); i++) {
      def s = stringsToEcho.get(i)
      def stepName = "echoing ${s}"
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
