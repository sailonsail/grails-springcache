grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir	= "target/test-reports"

grails.project.dependency.resolution = {
	inherits("global") {
		excludes "ehcache"
	}
	log "warn"
	repositories {
        grailsHome()
        grailsPlugins()
		mavenLocal()
		mavenCentral()
	}
	dependencies {
		compile ("net.sf.ehcache:ehcache:1.7.2") {
			excludes "jms", "commons-logging", "servlet-api"
		}
		compile("net.sf.ehcache:ehcache-web:2.0.0") {
			excludes "ehcache-core"
		}
		test("org.gmock:gmock:0.8.0") {
			excludes "junit"
		}
		test "org.hamcrest:hamcrest-all:1.1"
	}
}
