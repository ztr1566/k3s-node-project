def call(Map config) {
    def imageURI = config.imageURI
    def manifestPath = config.manifestPath
    def gitRepoUrl = config.gitRepoUrl

    echo "--- Preparing to update deployment manifest in Git ---"
    withCredentials([usernamePassword(credentialsId: 'github-pat', usernameVariable: 'GIT_USER', passwordVariable: 'GIT_TOKEN')]) {
        sh """
            echo "Updating Kubernetes manifest..."

            # Configure Git user for this commit
            git config --global user.email "jenkins-ci@example.com"
            git config --global user.name "Jenkins CI Bot"

            # Use sed to replace the image line in the deployment file
            sed -i 's|image: .*|image: ${imageURI}|g' ${manifestPath}

            # Commit and push the change
            git add ${manifestPath}
            # [skip ci] is important to prevent this commit from triggering another build
            git commit -m "chore(release): Update image to ${imageURI} [skip ci]"
            git push https://${GIT_USER}:${GIT_TOKEN}@${gitRepoUrl} HEAD:main
        """
    }
    echo "--- Manifest updated in Git. ArgoCD will sync the new version. ---"
}