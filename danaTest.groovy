def myBuilds = [:]

def getTest(testFileName){
  return {
    node {
      echo testFileName
    }
  }
}

node{
  print "In node"

  stage('MyParallel') {
    print "In MyParallel"
    [1,2].each {
      def a = it;
      def stepName = "echoing ${a}"
      //myBuilds[a] = { print "${a}\n" }
      myBuilds[stepName] = a
    }
    print "Out MyParallel"
  }

 print "Out node"

  stage ('RunParallel') {
    print "In RunParallel"
    try{
      print "In try"
      parallel myBuilds
      print "Out try"
    } catch (Exception e){
      print "I failed"
      print e
    }
    print "Out RunParallel"
  }
}
