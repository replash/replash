package com.replash.build

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.maven.MavenDeployment
import org.gradle.api.tasks.bundling.Jar

class ReplashPublishingPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.apply(plugin: 'signing')
        project.apply(plugin: 'maven')

        project.task('javadocJar', type: Jar) {
            classifier = 'javadoc'
            from project.javadoc
        }

        project.task('sourcesJar', type: Jar) {
            classifier = 'sources'
            from project.sourceSets.main.allSource
        }

        project.artifacts {
            archives project.javadocJar, project.sourcesJar
        }

        project.signing {
            sign project.configurations.archives
        }

        project.group = "com.replash"
        project.archivesBaseName = "replash-${project.name}"
        project.version = project.replashVersion

        project.uploadArchives {
            repositories {
                mavenDeployer {
                    beforeDeployment { MavenDeployment deployment -> project.signing.signPom(deployment) }

                    repository(url: "https://oss.sonatype.org/service/local/staging/deploy/maven2/") {
                        authentication(userName: project.sonatypeUsername, password: project.sonatypePassword)
                    }

                    snapshotRepository(url: "https://oss.sonatype.org/content/repositories/snapshots/") {
                        authentication(userName: project.sonatypeUsername, password: project.sonatypePassword)
                    }

                    pom.project {
                        name 'REPLash'
                        packaging 'jar'
                        // optionally artifactId can be defined here
                        description 'REPLash is a lightweight and extensible command-line REPL for Java.'
                        url 'http://www.replash.com'

                        scm {
                            connection 'https://github.com/replash/replash.git'
                            developerConnection 'https://github.com/replash/replash.git'
                            url 'https://github.com/replash/replash'
                        }

                        licenses {
                            license {
                                name 'The Apache License, Version 2.0'
                                url 'http://www.apache.org/licenses/LICENSE-2.0.txt'
                            }
                        }

                        developers {
                            developer {
                                id 'spaceshaker'
                                name 'Curt Beattie'
                                email 'spaceshaker.geo@yahoo.com'
                            }
                        }
                    }
                }
            }
        }

    }
}
