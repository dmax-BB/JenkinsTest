def myBuilds = [:]

def Closure getTest(String testFileName){
  print "${testFileName}\n"
}

node{
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
} catch (Exception e){
  print "I failed"
}
