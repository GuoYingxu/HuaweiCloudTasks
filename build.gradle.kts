import org.jetbrains.kotlin.ir.backend.js.compile

plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.12.0"
}

group = "com.qst.extensions"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://mirrors.huaweicloud.com/repository/maven/huaweicloudsdk/")
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2022.1.4")
    type.set("IC") // Target IDE Platform
    plugins.set(listOf(/* Plugin Dependencies */))
}
dependencies {
    implementation("com.alibaba:fastjson:2.0.21");
    implementation("com.huaweicloud.sdk:huaweicloud-sdk-core:3.1.49"){
        exclude(group = "org.slf4j", module = "slf4j-log4j")
    }
    implementation("com.huaweicloud.sdk:huaweicloud-sdk-projectman:3.1.49"){
        exclude(group = "org.slf4j", module = "slf4j-log4j")
    }
}
tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
        options.encoding= "UTF-8"
    }

    patchPluginXml {
        sinceBuild.set("221")
        untilBuild.set("231.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
