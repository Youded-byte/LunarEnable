plugins {
    java
}

group = "me.onils.lunarenable"
version = "1.0"

java {
    sourceCompatibility = JavaVersion.VERSION_16
    targetCompatibility = JavaVersion.VERSION_16
}

repositories {
    mavenCentral()
}

val embed by configurations.creating
configurations.implementation.get().extendsFrom(embed)

dependencies {
    embed("org.ow2.asm:asm:9.2")
    embed("org.ow2.asm:asm-tree:9.2")
}

tasks.jar {
    from(embed.map { if(it.isDirectory) it else zipTree(it) }){
        exclude("**/module-info.class")
    }

    manifest {
        attributes["Agent-Class"] = "me.onils.lunarenable.Agent"
        attributes["Premain-Class"] = "me.onils.lunarenable.Agent"
        attributes["Can-Retransform-Classes"] = true
    }

    archiveFileName.set("${project.name}.jar")
}