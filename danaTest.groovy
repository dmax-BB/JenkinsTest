// Map to be used to pass to parallel task
def reposToClone = [:]

// Map which defines each of the repos we want to clone
def cloneDetails = [:]
cloneDetails.scmClass='GitSCM'
cloneDetails.branch='origin/master'
cloneDetails.credentials="$env.DANA_TEST_CREDENTIALS"
cloneDetails.url=''
cloneDetails.tarDir=''

def repositoryMapping = [:]
repositoryMapping.rails='git@github.com:rails/rails.git'
repositoryMapping.tensorflow='git@github.com:tensorflow/tensorflow.git'
repositoryMapping.bootstrap='git@github.com:twbs/bootstrap.git'
repositoryMapping.freeCodeCamp='git@github.com:freeCodeCamp/freeCodeCamp.git'
repositoryMapping.react='git@github.com:facebook/react.git'
repositoryMapping.angularjs='git@github.com:angular/angular.js.git'

node {
  // 1st stage to set up the clone step
  stage('clonePreparation') {
    processReposToClone(repositoryMapping,reposToClone)
  }

  try{
    stage('RunCloning') {
      // Execute the cloning in parallel
      parallel reposToClone
      //cloneCode2(cloneDetails)
      repositoryMapping.danaTest=cloneDetails
      echo "${repositoryMapping.toString()}"
      repositoryMapping.danaTest.url='BLAHBLAHBLAH'
      echo "${repositoryMapping.toString()}"
    }
  } catch (Exception e){
    throw e
  }
}

// Method to run the actual clone of the repository
def cloneCode(targetDir,targetURL) {
    return {
        node {
            print "$targetDir $targetURL"
            sh "echo START: `date`"
            checkout scm: [$class: 'GitSCM', branches: [[name: 'origin/master']], userRemoteConfigs: [[credentialsId: "$env.DANA_TEST_CREDENTIALS", url: "${targetURL}"]], extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: "${targetDir}"]]]
            sh "echo END: `date`"
        }
    }
}

def cloneCode2(Map cloneDetails) {
    sh "echo START: `date`"
    checkout scm: [$class: cloneDetails.scmClass, branches: [[name: cloneDetails.branch]], userRemoteConfigs: [[credentialsId: cloneDetails.credentials, url: cloneDetails.url]], extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: cloneDetails.tarDir]]]
    sh "echo END: `date`"
}

// Test method for future dev
def processReposToClone(Map userDataMap, Map userCloneMap) {
    def repoMapKeys = userDataMap.keySet() as List
    for (repoName in repoMapKeys) {
      def repoUrl = userDataMap.get(repoName)
      def stepName = "[Cloning for: ${repoName}]"
      userCloneMap[stepName] = cloneCode(repoName,repoUrl)
      print "Adding to map: ${repoName} ${repoUrl}"
    }
}
