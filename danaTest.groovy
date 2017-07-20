@Library('jenkins-pipeline-libraries')
import com.danasoftware.jenkins.pipeline.libraries.SourceClone

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
        "rails clone": new SourceClone().cloneSourceCode('git@github.com:rails/rails.git','rails',"$env.DANA_TEST_CREDENTIALS","$env.RAILS_BRANCH"),
        "tensorflow clone": new SourceClone().cloneSourceCode('git@github.com:tensorflow/tensorflow.git','tensorflow',"$env.DANA_TEST_CREDENTIALS","$env.TENSORFLOW_BRANCH"),
        "bootstrap clone": new SourceClone().cloneSourceCode('git@github.com:twbs/bootstrap.git','bootstrap',"$env.DANA_TEST_CREDENTIALS","$env.BOOTSTRAP_BRANCH"),
        "freeCodeCamp clone": new SourceClone().cloneSourceCode('git@github.com:freeCodeCamp/freeCodeCamp.git','freeCodeCamp',"$env.DANA_TEST_CREDENTIALS","$env.FREECODECAMP_BRANCH"),
        "react clone": new SourceClone().cloneSourceCode('git@github.com:facebook/react.git','react',"$env.DANA_TEST_CREDENTIALS","$env.REACT_BRANCH"),
        "angular.js clone": new SourceClone().cloneSourceCode('git@github.com:angular/angular.js.git','angular.js',"$env.DANA_TEST_CREDENTIALS","$env.ANGULARJS_BRANCH")
      )
    }
  } catch (Exception e){
    throw e
  }

}

