package patches.projects

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.S3Storage
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.s3Storage
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, change the project with id = 'S3storage'
accordingly, and delete the patch script.
*/
changeProject(RelativeId("S3storage")) {
    features {
        val feature1 = find<S3Storage> {
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
        }
        feature1.apply {
            bucketPrefix = ""
            enablePresignedURLUpload = false
            param("aws.use.default.credential.provider.chain", "")
        }
    }
}
