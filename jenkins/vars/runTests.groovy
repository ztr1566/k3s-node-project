def call(Map config) {
    def appDir = config.appDir ?: '.'
    container('node') {
        dir(appDir) {
            echo "--- Running npm install and lint ---"
            sh 'npm install'
            sh 'npm run lint' // Runs ESLint as defined in package.json
        }
    }
}