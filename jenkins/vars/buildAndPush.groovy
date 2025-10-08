def call(Map config) {
    def imageName = config.imageName
    def dockerfile = config.dockerfile ?: 'Dockerfile'
    def context = config.context ?: '.'
    def digestFileName = "image-digest.txt"

    container('kaniko') {
        echo "Building and pushing image: ${imageName}"
        sh """
            /kaniko/executor --dockerfile=${dockerfile} \
                             --context=${context} \
                             --destination=${imageName} \
                             --cache=true \
                             --cache-dir=/cache \
                             --digest-file=${digestFileName}
        """
    }
    return digestFileName
}