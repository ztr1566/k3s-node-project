def call(Map config) {
    def imageName = config.imageName
    def dockerfile = config.dockerfile
    def context = config.context
    def digestFileName = "image-digest.txt"

    container('kaniko') {
        sh """
            /kaniko/executor --context=${context} \\
                             --dockerfile=${dockerfile} \\
                             --destination=${imageName} \\
                             --digest-file=${digestFileName} \\
                             --cleanup
        """
    }
    return digestFileName
}