pipeline {
    agent {
        docker {
            image 'gitlab-ci-java8'
        }
    }
    stages {
        stage('Init') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/homework_proguard']],
                    doGenerateSubmoduleConfigurations: false,
                    extensions: [[$class: 'CleanCheckout']],
                    submoduleCfg: [],
                    userRemoteConfigs: [[
                        url: 'https://github.com/MrOzOn/HealthDiary',
                        credentialsId: 'CRED_FOR_GITHUB'
                        ]]])
            }
        }
        stage('Static Analysis') {
            failFast true
            parallel {
                stage('lint') {
                    steps {
                        echo './gradlew lintDebug'
                    }
                }
                stage('detekt') {
                    steps {
                        sh './gradlew detekt'
                    }
                }
            }
        }
        stage('Test') {
            failFast true
            parallel {
                stage('default') {
                    steps {
                        echo './gradlew testDebugUnitTest'
                    }
                }
                stage('jacoco') {
                    steps {
                        sh './gradlew jacocoTestDebugUnitTestReport'
                    }
                }
            }
        }
        stage('Signing APK') {
            steps {
                prepareProperties()
                sh './gradlew assembleRelease'
            }
        }
    }
}

def prepareProperties() {
    def propertiesPath = "/.keystore"
    sh "mkdir -p ${env.HOME}${propertiesPath}"
    withCredentials([file(credentialsId: 'KEYSTORE_PROPERTIES', variable: 'propertiesFile')]) {
        sh "cp -f ${propertiesFile} ${env.HOME}${propertiesPath}/keystore.properties"
    }
    withCredentials([file(credentialsId: 'KEYSTORE_JKS', variable: 'keystore')]) {
        sh "cp -f ${keystore} ${env.HOME}${propertiesPath}/learn_otus.jks"
    }
}
