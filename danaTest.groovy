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

node {
  // 1st stage to set up the clone step
  stage('clonePreparation') {

    // Iterate acrosss the map, adding the step to the map
    def repoMapKeys = repositoryMapping.keySet() as List
    for (repoName in repoMapKeys) {
      def repoUrl = repositoryMapping.get(repoName)
      def stepName = "[Cloning for: ${repoName}]"
      reposToClone[stepName] = cloneCode(repoName,repoUrl)
    }

  }

  try{
    stage('RunCloning') {
      // Execute the cloning in parallel
      parallel reposToClone
      testUsingMap(repositoryMapping)
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
            checkout scm: [$class: 'GitSCM', branches: [[name: 'origin/master']], userRemoteConfigs: [[credentialsId: "$env.DANA_TEST_CREDENTIALS", url: "${targetURL}"]], extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: "${targetDir}"]]]
        }
    }
}

// Test method for future dev
def testUsingMap(Map userMap) {
  def mapKeys = userMap.keySet() as List
  for (key in mapKeys) {
    def value = userMap.get(key)
    print "KEY: ${key} VALUE: ${value}"
  }
}
