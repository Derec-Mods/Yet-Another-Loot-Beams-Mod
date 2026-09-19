plugins {
    id("dev.kikugie.stonecutter")
}
stonecutter active "1.21.1-neoforge"

// Stonecutter 0.7+ dropped chiseled tasks: `./gradlew build` already builds every target.
// Each jar still lands in versions/<target>/build/libs, so gather them in one place.
tasks.register<Copy>("collectJars") {
    group = "project"
    dependsOn(stonecutter.tasks.named("build"))
    from(fileTree("versions") {
        include("*/build/libs/*.jar")
        exclude("**/*-sources.jar", "**/*-dev.jar", "**/*-dev-shadow.jar", "**/*-slim.jar")
    })
    into(layout.buildDirectory.dir("collected-jars"))
    eachFile { path = name }
    includeEmptyDirs = false
}


allprojects {
    repositories {
        mavenCentral()
        mavenLocal()
        maven("https://maven.neoforged.net/releases")
        maven("https://maven.fabricmc.net/")
        maven("https://repo.spongepowered.org/repository/maven-public/")
        maven("https://maven.blamejared.com")


        maven("https://cursemaven.com")
        maven("https://oss.sonatype.org/content/repositories/snapshots")

        maven("https://maven.parchmentmc.org")

        maven("https://api.modrinth.com/maven")

        maven("https://maven.fzzyhmstrs.me/")

        maven("https://thedarkcolour.github.io/KotlinForForge/")

        maven("https://maven.terraformersmc.com/")

        maven("https://maven.ladysnake.org/releases")

        maven("https://maven.theillusivec4.top/")

        maven ("https://maven.shedaniel.me/")
        maven("https://maven.bawnorton.com/releases")
    }
}