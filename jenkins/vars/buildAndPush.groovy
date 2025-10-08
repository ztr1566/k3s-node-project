def call(String imageName, String dockerhubCredentialsId) {
    script {
        echo "Starting Docker build for ${imageName}..."

        docker.withRegistry('https://index.docker.io/v1/', dockerhubCredentialsId) {
            def customImage = docker.build(imageName, 'app')

            echo "Pushing Docker image..."
            customImage.push()
        }

        echo "Image ${imageName} pushed successfully!"
    }
}