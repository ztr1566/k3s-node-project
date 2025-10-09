def call(Map config) {
    def imageURI = config.imageURI
    def manifestPath = config.manifestPath

    echo "--- Updating deployment manifest locally ---"
    
    sh """
        echo "Updating Kubernetes manifest..."

        # Configure Git user for this commit
        git config --global user.name 'Jenkins CI Bot'
        git config --global user.email 'ci-bot@example.com'

        # Use sed to update the image tag in the deployment file
        sed -i 's|image: .*|image: ${imageURI}|g' ${manifestPath}

        # Add the modified file and commit it.
        # The '|| true' part prevents the build from failing if there are no changes to commit.
        git add ${manifestPath}
        git commit -m "Update image to ${imageURI}" || true
    """
    
    echo "--- Manifest updated and committed locally. Ready to be pushed. ---"
}