pipeline {
    agent any

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
                sh 'docker version'
            }
        }
        stage('Build') {
            agent {
                docker {
                    image 'gitlab-ci-java8'
                    args '-u root:root'
                }
            }
            // agent { dockerfile true }
            steps {
                sh './gradlew assembleDebug'
            }
        }
        stage('Static Analysis') {
            agent {
                docker {
                    image 'gitlab-ci-java8'
                    args '-u root:root'
                }
            }
            steps {
                sh './gradlew detekt'
                sh './gradlew lintDebug'
            }
        }
        stage('Test') {
            agent {
                docker {
                    image 'gitlab-ci-java8'
                    args '-u root:root'
                }
            }
            steps {
                sh './gradlew testDebugUnitTest'
                sh './gradlew jacocoTestDebugUnitTestReport'
            }
        }
        stage('Signing APK') {
            agent {
                docker {
                    image 'gitlab-ci-java8'
                    args '-u root:root'
                }
            }
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
