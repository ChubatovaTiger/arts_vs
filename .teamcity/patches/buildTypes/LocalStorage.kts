package patches.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.ScriptBuildStep
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, change the buildType with id = 'LocalStorage'
accordingly, and delete the patch script.
*/
changeBuildType(RelativeId("LocalStorage")) {
    expectSteps {
        script {
            name = "art1"
            scriptContent = """
                mkfile 3g a
                echo "##teamcity[publishArtifacts 'a']"
            """.trimIndent()
        }
        script {
            name = "art2"
            scriptContent = """
                mkfile 1g 1.txt
                mkfile 1g 2.txt
                
                echo "##teamcity[publishArtifacts '*.txt']"
                mkdir dir
                for j in ${'$'}(seq 1 1 10000)
                do
                	echo a > ${'$'}j.bat
                    echo a > dir/${'$'}j.cmd
                    
                done
                echo "##teamcity[publishArtifacts 'dir/**']"
                echo "##teamcity[publishArtifacts '*.bat']"
            """.trimIndent()
        }
    }
    steps {
        update<ScriptBuildStep>(1) {
            clearConditions()
            scriptContent = """
                for j in ${'$'}(seq 1 1 50)
                do
                	mkfile 1m file${'$'}j
                    echo "##teamcity[publishArtifacts 'file${'$'}j']"
                done
            """.trimIndent()
        }
        insert(2) {
            script {
                name = "art3"
                enabled = false
                scriptContent = """
                    mkfile 1g 1.txt
                    mkfile 1g 2.txt
                    
                    echo "##teamcity[publishArtifacts '*.txt']"
                    mkdir dir
                    for j in ${'$'}(seq 1 1 10000)
                    do
                    	echo a > ${'$'}j.bat
                        echo a > dir/${'$'}j.cmd
                        
                    done
                    echo "##teamcity[publishArtifacts 'dir/**']"
                    echo "##teamcity[publishArtifacts '*.bat']"
                """.trimIndent()
            }
        }
    }

    dependencies {
        remove(RelativeId("S3multipart_S3storage")) {
            snapshot {
                reuseBuilds = ReuseBuilds.NO
            }
        }

        remove(RelativeId("S3storage_S3storage")) {
            snapshot {
                reuseBuilds = ReuseBuilds.NO
            }
        }

    }
}
