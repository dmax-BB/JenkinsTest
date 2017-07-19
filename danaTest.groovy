defaultBranch='origin/master'
if (!env.RAILS_BRANCH){env.RAILS_BRANCH=defaultBranch}

node {
  // Run cloning
  try{
    stage('CloneRepos') {
      parallel (
        clone1: cloneCode("${env.RAILS_BRANCH}",'git@github.com:rails/rails.git','rails'),
        clone2: cloneCode('origin/master','git@github.com:tensorflow/tensorflow.git','tensorflow'),
        clone3: cloneCode('origin/master','git@github.com:twbs/bootstrap.git','bootstrap'),
        clone4: cloneCode('origin/master','git@github.com:freeCodeCamp/freeCodeCamp.git','freeCodeCamp'),
        clone5: cloneCode('origin/master','git@github.com:facebook/react.git','react'),
        clone6: cloneCode('origin/master','git@github.com:angular/angular.js.git','angular.js')
      )
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
