/*
 * Copyright (C) 2014
 * Center for Excellence in Nanomedicine (NanoCAN)
 * Molecular Oncology
 * University of Southern Denmark
 * ###############################################
 * Written by:	Markus List
 * Contact: 	mlist'at'health'.'sdu'.'dk
 * Web:			http://www.nanocan.org/miracle/
 * ###########################################################################
 *	
 *	This file is part of MIRACLE.
 *
 *  MIRACLE is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with this program. It can be found at the root of the project page.
 *	If not, see <http://www.gnu.org/licenses/>.
 *
 * ############################################################################
 */
grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
grails.project.war.file = "target/${appName}.war"
grails.plugin.location.HtsBackend = "HtsBackend/"
grails.project.dependency.resolver = "maven"

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve

    repositories {
        inherits true // Whether to inherit repository definitions from plugins
        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenCentral()
        // uncomment these to enable remote dependency resolution from public Maven repositories
        //mavenCentral()
        mavenLocal()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
        mavenRepo "http://repo.grails.org/grails/repo/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

        //xls file support
        compile (group:'org.apache.poi', name:'poi', version:'3.9')
        //xlxs file support
        compile (group:'org.apache.poi', name:'poi-ooxml', version:'3.9')

        //jackson JSON parser
        compile 'org.codehaus.jackson:jackson-core-asl:1.9.13'
        compile 'org.codehaus.jackson:jackson-mapper-asl:1.9.13'

        //database
        runtime "net.sourceforge.jtds:jtds:1.3.1" //MS-SQL
        runtime 'mysql:mysql-connector-java:5.1.16'

        //https connection with self-signed certificates
        compile 'com.github.kevinsawicki:http-request:5.4.1'
    }

    plugins {
        compile ":open-seadragon:0.3"
        build   ":tomcat:7.0.52"
        runtime ':hibernate:3.6.10.14'
        compile ":jquery:1.11.1"
        compile ":jquery-ui:1.10.4"
        runtime ":resources:1.2.14"
        runtime ":database-migration:1.4.1"
        runtime ":webxml:1.4.1"

        //alternative to the flash scope, which is unreliable
        compile "org.grails.plugins:one-time-data:1.0"

        //security
        compile ":spring-security-core:1.2.7.4"
        compile ":spring-security-cas:1.0.5"
        //compile ":spring-security-eventlog:0.2"

        // Uncomment these (or add new ones) to enable additional resources capabilities
        //runtime ":zipped-resources:1.0"
        //runtime ":cached-resources:1.0"
        //runtime ":yui-minify-resources:0.1.4"
        compile ":searchable:0.6.9"
        compile ":webflow:2.1.0"
        compile "org.grails.plugins:scaffolding:2.1.2"
    }
}

//fixes a bug in grails 2.5.4
grails.war.resources = { stagingDir, args ->
    copy(todir: "${stagingDir}/WEB-INF/lib", flatten: "true") {
        fileset(dir: "${grailsHome}/lib", includes: "**/jline-*.jar, **/jansi-*.jar")
    }
}