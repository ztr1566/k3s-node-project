// ده ملف buildAndPush.groovy بتاعك
def call(Map config) {
    def imageName = config.imageName
    def dockerfile = config.dockerfile ?: 'Dockerfile'
    def context = config.context ?: '.' // Kaniko بيستخدم dir://app، لكن buildah بياخد المسار عادي
    def digestFileName = "image-digest.txt"

    container('buildah') {
        sh "buildah bud --format docker -t ${imageName} -f ${dockerfile} ./app"
        sh "buildah push ${imageName}"
    }
    return digestFileName
}