def myBuilds = [:]

node{
  checkout scm: [$class: 'GitSCM', branches: [[name: "origin/master"]], userRemoteConfigs: [[credentialsId: "$env.DANA_TEST_CREDENTIALS", url: 'git@github.com:rails/rails.git']], extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'rails']]]
  checkout scm: [$class: 'GitSCM', branches: [[name: "origin/master"]], userRemoteConfigs: [[credentialsId: "$env.DANA_TEST_CREDENTIALS", url: 'git@github.com:tensorflow/tensorflow.git']], extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'tensorflow']]]
  checkout scm: [$class: 'GitSCM', branches: [[name: "origin/master"]], userRemoteConfigs: [[credentialsId: "$env.DANA_TEST_CREDENTIALS", url: 'git@github.com:twbs/bootstrap.git']], extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'bootstrap']]]
  checkout scm: [$class: 'GitSCM', branches: [[name: "origin/master"]], userRemoteConfigs: [[credentialsId: "$env.DANA_TEST_CREDENTIALS", url: 'git@github.com:freeCodeCamp/freeCodeCamp.git']], extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'freeCodeCamp']]]
  checkout scm: [$class: 'GitSCM', branches: [[name: "origin/master"]], userRemoteConfigs: [[credentialsId: "$env.DANA_TEST_CREDENTIALS", url: 'git@github.com:facebook/react.git']], extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'react']]]
  checkout scm: [$class: 'GitSCM', branches: [[name: "origin/master"]], userRemoteConfigs: [[credentialsId: "$env.DANA_TEST_CREDENTIALS", url: 'git@github.com:angular/angular.js.git']], extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'angular.js']]]

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
