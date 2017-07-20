@Library('jenkins-pipeline-libraries')

defaultBranch='master'
if (!env.RAILS_BRANCH){env.RAILS_BRANCH=defaultBranch}
if (!env.TENSORFLOW_BRANCH){env.TENSORFLOW_BRANCH=defaultBranch}
if (!env.BOOTSTRAP_BRANCH){env.BOOTSTRAP_BRANCH=defaultBranch}
if (!env.FREECODECAMP_BRANCH){env.FREECODECAMP_BRANCH=defaultBranch}
if (!env.REACT_BRANCH){env.REACT_BRANCH=defaultBranch}
if (!env.ANGULARJS_BRANCH){env.ANGULARJS_BRANCH=defaultBranch}

node {
  // Run cloning
  try{
    stage('CloneRepos') {
      parallel (
        "rails clone": cloneMyRepo("origin/${env.RAILS_BRANCH}",'git@github.com:rails/rails.git','rails'),
        "tensorflow clone": cloneMyRepo("origin/${env.TENSORFLOW_BRANCH}",'git@github.com:tensorflow/tensorflow.git','tensorflow'),
        "bootstrap clone": cloneMyRepo("origin/${env.BOOTSTRAP_BRANCH}",'git@github.com:twbs/bootstrap.git','bootstrap'),
        "freeCodeCamp clone": cloneMyRepo("origin/${env.FREECODECAMP_BRANCH}",'git@github.com:freeCodeCamp/freeCodeCamp.git','freeCodeCamp'),
        "react clone": cloneMyRepo("origin/${env.REACT_BRANCH}",'git@github.com:facebook/react.git','react'),
        "angular.js clone": cloneMyRepo("origin/${env.ANGULARJS_BRANCH}",'git@github.com:angular/angular.js.git','angular.js')
      )
    }
  } catch (Exception e){
    throw e
  }

}

// Method to run the actual clone of the repository
def cloneMyRepo(branchName,targetUrl,targetDir) {
  return {
    node {
      print "$targetDir $targetUrl"
      sh "echo START clone for ${targetDir}: `date`"
      checkout scm: [
                    $class: 'GitSCM',
                    branches: [[name: "$branchName"]],
                    userRemoteConfigs: [[credentialsId: "$env.DANA_TEST_CREDENTIALS", url: "${targetUrl}"]],
                    extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: "${targetDir}"]]
                    ]
      sh "echo END clone for ${targetDir}: `date`"
    }
  }
}
