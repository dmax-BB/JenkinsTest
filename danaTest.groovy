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
repositoryMapping.rails=cloneDetails
repositoryMapping.rails.url='git@github.com:rails/rails.git'

repositoryMapping.tensorflow=cloneDetails
repositoryMapping.tensorflow.url='git@github.com:tensorflow/tensorflow.git'

repositoryMapping.bootstrap=cloneDetails
repositoryMapping.bootstrap.url='git@github.com:twbs/bootstrap.git'

repositoryMapping.freeCodeCamp=cloneDetails
repositoryMapping.freeCodeCamp.url='git@github.com:freeCodeCamp/freeCodeCamp.git'

repositoryMapping.react=cloneDetails
repositoryMapping.react.url='git@github.com:facebook/react.git'

repositoryMapping.angularjs=cloneDetails
repositoryMapping.angularjs.url='git@github.com:angular/angular.js.git'
repositoryMapping.angularjs.tarDir='angular.js'

node {
  // 1st stage to set up the clone step
  stage('clonePreparation') {
    processReposToClone2(repositoryMapping,reposToClone)
  }

  try{
    stage('RunCloning') {
      // Execute the cloning in parallel
      //parallel reposToClone
    print "Hello"
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
  return {
    node {
      sh "echo START: `date`"
      checkout scm: [$class: cloneDetails.scmClass, branches: [[name: cloneDetails.branch]], userRemoteConfigs: [[credentialsId: cloneDetails.credentials, url: cloneDetails.url]], extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: cloneDetails.tarDir]]]
      sh "echo END: `date`"
    }
  }
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

def processReposToClone2(Map userDataMap, Map userCloneMap) {
  def repoMapKeys = userDataMap.keySet() as List
  for (repoName in repoMapKeys) {
    def stepName = "[Cloning for: ${repoName}]"
    print "$stepName"
    //print "${userDataMap.stepName.tarDir
    if ("userDataMap.${stepName}.tarDir"){
      print "${userDataMap}.${stepName}.tarDir.toString()"
      //userDataMap.stepName.tarDir=userDataMap.stepName
    }
    userCloneMap[stepName] = cloneCode2(userDataMap.get(repoName))
    print "Added to map: ${repoName}"
  }
}
