
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.activeStorage
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.s3Storage

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2021.1"

project {

    buildType(LocalStorage)

    features {
        s3Storage {
            id = "PROJECT_EXT_72"
            storageName = "s3multipart"
            bucketName = "chubatovatransferacceleration"
            bucketPrefix = "multipart_test"
            multipartThreshold = "200MB"
            multipartChunksize = "200MB"
            accessKey = "credentialsJSON:e5ec9cb3-17dc-4be1-85fe-6d3b98bc961a"
            awsEnvironment = default {
                awsRegionName = "eu-central-1"
            }
            accessKeyID = "AKIA5JH2VERVHVMPJQJI"
        }
        activeStorage {
            id = "PROJECT_EXT_74"
            activeStorageID = "DefaultStorage"
        }
    }

    subProject(S3storage)
    subProject(S3multipart)
}

object LocalStorage : BuildType({
    name = "localStorage"

    vcs {
        cleanCheckout = true
    }

    steps {
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

    dependencies {
        snapshot(S3multipart_S3storage) {
            reuseBuilds = ReuseBuilds.NO
        }
        snapshot(S3storage_S3storage) {
            reuseBuilds = ReuseBuilds.NO
        }
    }
})


object S3multipart : Project({
    name = "s3multipart"

    buildType(S3multipart_S3storage)

    features {
        activeStorage {
            id = "PROJECT_EXT_76"
            activeStorageID = "PROJECT_EXT_72"
        }
    }
})

object S3multipart_S3storage : BuildType({
    name = "s3storage"

    steps {
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
                mkfile 1g 2.txt
                echo "##teamcity[publishArtifacts '*.txt']"
            """.trimIndent()
        }
    }
})


object S3storage : Project({
    name = "s3storage"

    buildType(S3storage_S3storage)

    features {
        s3Storage {
            id = "PROJECT_EXT_71"
            storageName = "s3storagechubatova"
            bucketName = "n.chubatova-test"
            bucketPrefix = "pref_chubatova"
            accessKey = "credentialsJSON:e5ec9cb3-17dc-4be1-85fe-6d3b98bc961a"
            awsEnvironment = default {
                awsRegionName = "eu-central-1"
            }
            accessKeyID = "AKIA5JH2VERVHVMPJQJI"
        }
        activeStorage {
            id = "PROJECT_EXT_75"
            activeStorageID = "PROJECT_EXT_71"
        }
    }
})

object S3storage_S3storage : BuildType({
    name = "s3storage"

    steps {
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
                mkfile 1g 2.txt
                echo "##teamcity[publishArtifacts '*.txt']"
            """.trimIndent()
        }
    }
})
