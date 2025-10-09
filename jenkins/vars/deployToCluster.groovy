def call(Map config) {
    def imageURI = config.imageURI
    def manifestPath = config.manifestPath

    echo "--- Updating deployment manifest locally ---"
    
    // We will only modify the file and commit it.
    // The actual push will be handled by the 'Push automation updates' stage.
    sh """
        echo "Updating Kubernetes manifest..."

        # Configure Git user for this commit
        git config --global user.name 'Jenkins Bot'
        git config --global user.email 'ci-bot@example.com'

        # Use sed to update the image tag in the deployment file
        sed -i 's|image: .*|image: ${imageURI}|g' ${manifestPath}

        # Add the modified file and commit it with the correct skip message
        git add ${manifestPath}
        git commit -m "Update image to ${imageURI} [ci skip]" || echo "No changes to commit"
    """
    
    echo "--- Manifest updated and committed locally. ---"
}