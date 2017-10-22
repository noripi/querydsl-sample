import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper

buildscript {
    var kotlinVersion: String? by extra; kotlinVersion = "1.1.51"
    var querydslVersion: String? by extra; querydslVersion = "4.1.4"

    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

apply {
    plugin<KotlinPluginWrapper>()
    plugin<ApplicationPlugin>()
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    val kotlinVersion: String? by extra
    val querydslVersion: String? by extra

    compileOnly(gradleApi())
    compile("mysql:mysql-connector-java:5.1.36")
    compile("com.querydsl:querydsl-sql:$querydslVersion")
    compile("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    compile("org.jetbrains.kotlinx:kotlinx-support-jdk8:0.3")
}

configure<ApplicationPluginConvention> {
    mainClassName = "io.github.noripi.querydsl_sample.main.MainKt"
    group = "io.github.noripi"
    version = "0.1"
}
