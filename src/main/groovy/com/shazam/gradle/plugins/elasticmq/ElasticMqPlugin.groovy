/**
 * Copyright 2014 Shazam Entertainment Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License
 *
**/
package com.shazam.gradle.plugins.elasticmq

import org.elasticmq.NodeAddress
import org.elasticmq.rest.sqs.SQSRestServerBuilder
import org.gradle.api.Plugin
import org.gradle.api.Project

class ElasticMq implements Plugin<Project> {

    ElasticMqExtension exten;
    public ElasticMq() {
        exten = new ElasticMqExtension()
    }

    // figure out how to configure with a configuration task
    // instead of hardcoding the port number

    // add in task descriptions as well
    void apply(Project project) {
        project.extensions.create("elasticmq", ElasticMqExtension)
        def server = null

        project.task('startElasticMq') << {
            println "Starting ElasticMQ"
            server = SQSRestServerBuilder
                    .withPort(project.elasticmq.port)
                    .withServerAddress(new NodeAddress("http", "localhost", project.elasticmq.port, ""))
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

