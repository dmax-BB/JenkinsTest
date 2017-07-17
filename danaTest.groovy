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
      def value = repositoryMapping.get(repoName)
      def stepName = "[Cloning for: ${repoName}]"
      reposToClone[stepName] = cloneCode(repoName,value)
    }

  }

  try{
    stage('RunCloning') {
      // Execute the cloning in parallel
      parallel reposToClone
    }
  } catch (Exception e){
    throw e
  }
}

// Method to run the actual clone of the repository
def cloneCode(targetDir,targetURL) {
    return {
        node {
            print "$targetDir\n"
            echo targetURL
            //checkout scm: [$class: 'GitSCM', branches: [[name: "${env.RESTBACKEND_COMMITHASH}"]], userRemoteConfigs: [[credentialsId: "$env.SERV_BUILDER_GIT_CREDENTIALS", url: 'ssh://git@bitbucket.dc1.lan:7999/hub/rest-backend.git']], extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'rest-backend']]]
        }
    }
}
