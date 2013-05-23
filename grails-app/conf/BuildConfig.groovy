grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
grails.project.war.file = "target/${appName}.war"


grails.plugin.location.Rconnect = "../Rconnect/"

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
        //mavenLocal()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        compile "org.grails:grails-webflow:$grailsVersion"
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

        //xls file support
        compile (group:'org.apache.poi', name:'poi', version:'3.9')
        //xlxs file support
        compile (group:'org.apache.poi', name:'poi-ooxml', version:'3.9')


        // runtime 'mysql:mysql-connector-java:5.1.16'
    }

    plugins {
        runtime ":hibernate:$grailsVersion"
        runtime ":jquery:1.7.1"
        runtime ":resources:1.1.6"
        runtime ":database-migration:1.1"

        //security
        compile ":spring-security-core:1.2.7.3"
        compile ":spring-security-cas:1.0.5"
        compile ":spring-security-eventlog:0.2"

        // Uncomment these (or add new ones) to enable additional resources capabilities
        //runtime ":zipped-resources:1.0"
        //runtime ":cached-resources:1.0"
        //runtime ":yui-minify-resources:0.1.4"

        build ":tomcat:$grailsVersion"
        test(":spock:0.7") {
            exclude "spock-grails-support"
        }
        compile ":searchable:0.6.4"
        compile(":webflow:2.0.0") {
            exclude 'grails-webflow'
        }
		compile ":codenarc:0.18.1"
    }
	CodeNarc.properties = {
		GrailsPublicControllerMethod.enabled = false
		EmptyIfStatement.priority = 1
	}
}
