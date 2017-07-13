def myBuilds = [:]

node{
  stage('MyParallel') {
    [1,2].each {
      def a = it;
      myBuilds[a] = { print "${a}\n" }
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
