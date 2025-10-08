def call(Map config) {
    def imageName = config.imageName
    def dockerfile = config.dockerfile ?: 'Dockerfile'
    def context = config.context ?: '.'
    def digestFileName = "image-digest.txt"

    container('kaniko') {
    sh '''
        /kaniko/executor --context=dir://app \
                         --dockerfile=app/Dockerfile \
                         --destination=zizoo1566/my-node-app:${IMAGE_TAG} \
                         --cleanup
    '''
}
    return digestFileName
}