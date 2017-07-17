def stringsToEcho = ["a", "b", "c", "d"]

def stepsForParallel = [:]

for (int i = 0; i < stringsToEcho.size(); i++) {
    def s = stringsToEcho.get(i)

    def stepName = "echoing ${s}"
    
    stepsForParallel[stepName] = transformIntoStep(s)
}

parallel stepsForParallel

def transformIntoStep(inputString) {
    return {
        node {
            echo inputString
        }
    }
}
