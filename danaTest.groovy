@Library('jenkins-pipeline-libraries')
import com.danasoftware.jenkins.pipeline.libraries.SourceClone

node ('DanaNode') {
  // Run cloning
  try{
    stage('CloneRepos') {
      parallel (
        "rails clone": new SourceClone().cloneSourceCode('DanaNode','ssh://git@github.com/rails/rails.git','rails',"${env.DANA_TEST_CREDENTIALS}","${env.RAILS_BRANCH}"),
        "tensorflow clone": new SourceClone().cloneSourceCode('DanaNode','ssh://git@github.com/tensorflow/tensorflow.git','tensorflow',"${env.DANA_TEST_CREDENTIALS}","${env.TENSORFLOW_BRANCH}"),
        "bootstrap clone": new SourceClone().cloneSourceCode('DanaNode','ssh://git@github.com/twbs/bootstrap.git','bootstrap',"${env.DANA_TEST_CREDENTIALS}","${env.BOOTSTRAP_BRANCH}"),
        "freeCodeCamp clone": new SourceClone().cloneSourceCode('DanaNode','ssh://git@github.com/freeCodeCamp/freeCodeCamp.git','freeCodeCamp',"${env.DANA_TEST_CREDENTIALS}","${env.FREECODECAMP_BRANCH}"),
        "react clone": new SourceClone().cloneSourceCode('DanaNode','ssh://git@github.com/facebook/react.git','react',"${env.DANA_TEST_CREDENTIALS}","${env.REACT_BRANCH}"),
        "angular.js clone": new SourceClone().cloneSourceCode('DanaNode','ssh://git@github.com/angular/angular.js.git','angular.js',"${env.DANA_TEST_CREDENTIALS}","${env.ANGULARJS_BRANCH}")
      )
    }
  } catch (Exception e){
    throw e
  }

}

