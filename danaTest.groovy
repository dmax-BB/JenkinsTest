// Map to be used to pass to parallel task
def reposToClone = [:]

// Map which defines each of the repos we want to clone
def repositoryMapping = [:]
repositoryMapping.put('rails','git@github.com:rails/rails.git')
repositoryMapping.put('tensorflow','git@github.com:tensorflow/tensorflow.git')
repositoryMapping.put('bootstrap','git@github.com:twbs/bootstrap.git')
repositoryMapping.put('freeCodeCamp','git@github.com:freeCodeCamp/freeCodeCamp.git')
repositoryMapping.put('react','git@github.com:facebook/react.git')
repositoryMapping.put('angular.js','git@github.com:angular/angular.js.git')

def cloneDetails = [:]
cloneDetails.put('scmClass','GitSCM')
cloneDetails.put('branch','origin/master')
cloneDetails.put('credentials','$env.DANA_TEST_CREDENTIALS')
cloneDetails.put('url','git@github.com:rails/rails.git')
cloneDetails.put('tarDir','rails')

node {
  // 1st stage to set up the clone step
  stage('clonePreparation') {
    processReposToClone(repositoryMapping,reposToClone)
  }

  try{
    stage('RunCloning') {
      // Execute the cloning in parallel
      parallel reposToClone
      cloneCode2(cloneDetails)
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
    checkout scm: [$class: cloneDetails.scmClass, branches: [[name: 'origin/master']], userRemoteConfigs: [[credentialsId: "$env.DANA_TEST_CREDENTIALS", url: "${targetURL}"]], extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: "${targetDir}"]]]
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
