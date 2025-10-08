// jenkins/vars/buildAndPush.groovy
def call(Map config) {
    def imageName = config.imageName
    def dockerfile = config.dockerfile ?: 'app/Dockerfile'
    def context = config.context ?: 'app'
    def digestFileName = "image-digest.txt"

    container('buildah') {
        // Buildah needs explicit login using credentials
        withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {

            try {
                echo "--- Logging in to Docker Hub with Buildah ---"
                sh "buildah login -u '${DOCKER_USER}' -p '${DOCKER_PASS}' docker.io"

                echo "--- Building image ${imageName} with Buildah ---"
                sh "buildah bud -t ${imageName} -f ${dockerfile} ${context}"

                echo "--- Pushing image ${imageName} with Buildah ---"
                // The 'docker://' prefix is how Buildah knows to push to a Docker registry
                sh "buildah push --digestfile ${digestFileName} ${imageName} docker://${imageName}"

            } finally {
                echo "--- Logging out from Docker Hub ---"
                sh "buildah logout docker.io"
            }
        }
    }
    return digestFileName
}