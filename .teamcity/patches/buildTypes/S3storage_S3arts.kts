package patches.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, create a buildType with id = 'S3storage_S3arts'
in the project with id = 'S3storage', and delete the patch script.
*/
create(RelativeId("S3storage"), BuildType({
    id("S3storage_S3arts")
    name = "s3arts"
}))

