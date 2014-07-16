ElasticMQ Gradle Plugin
=======================

This is a Gradle plugin that enables the starting/stopping of an ElasticMQ instance for the purposes of testing

Usage
-----

<pre>
buildscript {
	repositories {
		mavenCentral()
	}

	dependencies {
		classpath "com.shazam.elasticmq-plugin:0.1"
	}
}

apply plugin: 'elasticmq'
</pre>

The ElasticMQ server will be automatically terminated upon exit of the Gradle script.

Tasks
-----

This plugin adds two tasks:
<pre>
startElasticMq
runElasticMq
</pre>

startElasticMq will bring up the server in the background.
runElasticMq will bring up the server and wait until the script is killed.  This is useful when you just want to run the server, perhaps while running tests in your IDE.

Configuration
-----
TBD
