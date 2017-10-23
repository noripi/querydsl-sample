import org.gradle.api.AntBuilder
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper

configurations.create("querydsl")

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

    val querydsl = "querydsl"
    querydsl("com.querydsl:querydsl-sql-codegen:$querydslVersion")
    querydsl("mysql:mysql-connector-java:5.1.36")

    compileOnly(gradleApi())
    compile("mysql:mysql-connector-java:5.1.36")
    compile("com.querydsl:querydsl-sql:$querydslVersion")
    compile("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    compile("org.jetbrains.kotlinx:kotlinx-support-jdk8:0.3")
}

configure<JavaPluginConvention> {
    sourceSets.getByName("main").java.srcDirs("./generated/src/java")
}

configure<ApplicationPluginConvention> {
    mainClassName = "io.github.noripi.querydsl_sample.main.MainKt"
    group = "io.github.noripi"
    version = "0.1"
}

val generateQueryClass = task("generateQueryClass") {
    doLast {
        val dbHost = "localhost"
        val dbUser = System.getenv("DB_USER")
        val dbPass = System.getenv("DB_PASSWORD")
        val dbName = "test"
        val tableNames = "restaurant"

        ant.taskdef(
                name = "export",
                classname = "com.querydsl.sql.codegen.ant.AntMetaDataExporter",
                classpath = configurations.getByName("querydsl").asPath
        )
        ant.invokeMethod("export", mapOf(
                "jdbcUrl" to "jdbc:mysql://$dbHost/$dbName",
                "jdbcUser" to dbUser,
                "jdbcPassword" to dbPass,
                "jdbcDriver" to "com.mysql.jdbc.Driver",
                "packageName" to "io.github.noripi.querydsl_sample.query",
                "targetFolder" to "./generated/src/java",
                "tableNamePattern" to tableNames
        ))

    }
}

tasks.getByName("compileKotlin").dependsOn(generateQueryClass)

// custom extension function for build.gradle
fun AntBuilder.taskdef(name: String, classname: String, classpath: String) = this.invokeMethod(
        "taskdef", mapOf("name" to name, "classname" to classname, "classpath" to classpath))
