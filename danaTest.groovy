// Map to be used to pass to parallel task
def reposToClone = [:]

// Map which defines each of the repos we want to clone
def repositoryMapping = [:]
repositoryMapping.rails='git@github.com:rails/rails.git'
repositoryMapping.tensorflow='git@github.com:tensorflow/tensorflow.git'
repositoryMapping.bootstrap='git@github.com:twbs/bootstrap.git'
repositoryMapping.freeCodeCamp='git@github.com:freeCodeCamp/freeCodeCamp.git'
repositoryMapping.react='git@github.com:facebook/react.git'
repositoryMapping.put('angular.js','git@github.com:angular/angular.js.git')

node {
  // 1st stage to set up the clone step
  //stage('clonePreparation') {
    //processReposToClone(repositoryMapping,reposToClone)
  //}
  stage('CloneRepos') {
    parallel (
      clone1: cloneCode('origin/master','git@github.com:rails/rails.git','rails'),
      clone2: cloneCode('origin/master','git@github.com:tensorflow/tensorflow.git','tensorflow'),
      clone3: cloneCode('origin/master','git@github.com:twbs/bootstrap.git','bootstrap'),
      clone4: cloneCode('origin/master','git@github.com:freeCodeCamp/freeCodeCamp.git','freeCodeCamp'),
      clone5: cloneCode('origin/master','git@github.com:facebook/react.git','react'),
      clone6: cloneCode('origin/master','git@github.com:angular/angular.js.git','angular.js')
    )
  }

  try{
    stage('RunCloning') {
      // Execute the cloning in parallel
      //parallel reposToClone
      print "$env.WORKSPACE"
    }
  } catch (Exception e){
    throw e
  }
}

// Method to run the actual clone of the repository
def cloneCode(branchName,targetUrl,targetDir) {
    return {
        node {
            print "$targetDir $targetUrl"
            sh "echo START: `date`"
            checkout scm: [
                          $class: 'GitSCM',
                          branches: [[name: "$branchName"]],
                          userRemoteConfigs: [[credentialsId: "$env.DANA_TEST_CREDENTIALS", url: "${targetUrl}"]],
                          extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: "${targetDir}"]]
                          ]
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
      userCloneMap[stepName] = cloneCode('origin/master',repoUrl,repoName)
      print "Adding to map: ${repoName} ${repoUrl}"
    }
}
