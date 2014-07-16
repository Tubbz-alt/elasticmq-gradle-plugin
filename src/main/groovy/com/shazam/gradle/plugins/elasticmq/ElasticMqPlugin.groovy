package com.shazam.gradle.plugins.elasticmq

import org.elasticmq.NodeAddress
import org.elasticmq.rest.sqs.SQSRestServerBuilder
import org.gradle.api.Plugin
import org.gradle.api.Project

class ElasticMq implements Plugin<Project> {

    // figure out how to configure with a configuration task
    // instead of hardcoding the port number
    void apply(Project project) {
        def server = null

        project.task('startElasticMq') << {
            println "Starting ElasticMQ"
            server = SQSRestServerBuilder
                    .withPort(9320)
                    .withServerAddress(new NodeAddress("http", "localhost", 9320, ""))
                    .start()

            // replace with debug statements
            println("waiting for server to start")
            server.waitUntilStarted()
            println("started")
        }

        project.task('runElasticMq', dependsOn: 'startElasticMq') << {
            Thread.sleep(Integer.MAX_VALUE)
        }

        project.gradle.buildFinished {
            if (server != null) {
                println("Stopping ElasticMQ")
                server.stopAndWait()
                println("ElasticMQ Stopped")
            }
        }
    }
}

