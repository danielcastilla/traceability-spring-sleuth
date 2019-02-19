#!/usr/bin/env groovy
//Continuos Integration - Declarative Pipeline

final PROPERTIES_FILE_PATH = 'jenkinsfile.properties'



pipeline{

    agent any

    tools {
        maven "MAVEN"
    }

    stages{

        stage("Prepare execution"){
            steps{
                script{
                    //readProperties(PROPERTIES_FIELD_PATH)
                    def pomModel = readMavenPom file: "pom.xml"
                    ARTIFACTID = pomModel.getArtifactId()
                    GROUPID = pomModel.getGroupId()
                }

            }

        }

        stage("Publish API"){
            steps{
                echo "Publish API"
            }
        }

        stage("Compile and Package"){
            steps{
                echo "Compile and Package"
                sh "mvn clean install"
            }
        }

        stage("Start App"){
            steps{
                echo "Start App"

            }
        }

    }

}
