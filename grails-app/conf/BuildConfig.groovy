grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.8
grails.project.source.level = 1.8
grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.fork = [
        // configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
        //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

        // configure settings for the test-app JVM, uses the daemon by default
//        test   : [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon: true],
        // configure settings for the run-app JVM
//    run: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
// configure settings for the run-war JVM
        war    : [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve: false],
        // configure settings for the Console UI JVM
        console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    def gebVersion = "0.10.0"
    def seleniumVersion = "2.47.0"
    def ghostDriverVersion = "1.1.0"
    def webdriverVersion = "2.47.0" // Selenium version >= 2.44.0 won't work with ghostdriver until this issue is fixed https://github.com/detro/ghostdriver/issues/397

    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false
    // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        mavenLocal()
        grailsCentral()
        mavenCentral()
        mavenRepo 'http://dl.bintray.com/karman/karman'
        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.
        // runtime 'mysql:mysql-connector-java:5.1.29'
        // runtime 'org.postgresql:postgresql:9.3-1101-jdbc41'
        compile "com.mashape.unirest:unirest-java:1.3.27"
        test "org.grails:grails-datastore-test-support:1.0.2-grails-2.4"
//        test "org.gebish:geb-spock:$gebVersion"
//        test "org.seleniumhq.selenium:selenium-support:$seleniumVersion"
//        test "org.seleniumhq.selenium:selenium-safari-driver:$seleniumVersion"
//        test "org.seleniumhq.selenium:selenium-chrome-driver:$seleniumVersion"
//        test "org.seleniumhq.selenium:selenium-firefox-driver:$seleniumVersion"
//        test "org.seleniumhq.selenium:selenium-ie-driver:${webdriverVersion}"
//        test("com.github.detro.ghostdriver:phantomjsdriver:${ghostDriverVersion}") {
//            transitive = false
//        }
        runtime 'biz.paluch.logging:logstash-gelf:1.5.4'
    }

    plugins {
        // plugins for the build system only
        build ":tomcat:7.0.55"

        // plugins for the compile step
        compile ":scaffolding:2.1.2"
        compile ':cache:1.1.8'
        compile ":asset-pipeline:2.1.5"

        compile ":cookie-session:2.0.17"

        compile ":codenarc:0.23"

        test ':code-coverage:2.0.3-3'

//        test ":geb:$gebVersion"

        // plugins needed at runtime but not for compilation
//        runtime ":hibernate4:4.3.6.1" // or ":hibernate:3.6.10.18"
//        runtime ":database-migration:1.4.0"
//        runtime ":jquery:1.11.1"

        // Uncomment these to enable additional asset-pipeline capabilities
        //compile ":sass-asset-pipeline:1.9.0"
        //compile ":less-asset-pipeline:1.10.0"
        //compile ":coffee-asset-pipeline:1.8.0"
        //compile ":handlebars-asset-pipeline:1.3.0.3"
    }
}

codenarc {
    reports = {
        XmlReport('xml') {
            outputFile = 'target/CodeNarc-Report.xml'
            title = 'CodeNarc Report'
        }
        HtmlReport('html') {
            outputFile = 'target/CodeNarc-Report.html'
            title = 'CodeNarc Report'
        }
    }

    systemExitOnBuildException = true
    maxPriority1Violations = 0
    maxPriority2Violations = 2
    maxPriority3Violations = 5

    properties = {
        CatchException.enabled = false
        GrailsDomainReservedSqlKeywordName.enabled = false
    }
}

coverage {
    environments {
        development {
            enabledByDefault = true
        }
        production {
            enabledByDefault = true
        }
        test {
            enabledByDefault = false
        }
    }
    xml = true
    exclusions = [
            "**/QuartzConfig*",
            '**/Hstore*',
            "**/*changelog*/**",
            "**/*add_*/**",
            "**/*update_*/**",
            "**/*drop_*/**",
            "**/*remove_*/**",
            "**/*rename_*/**",
            "**/*schedule_job*/**",
            "**/*token_refactor*/**",
            "**/*user_add_*/**",
            "**/*BootStrap*",
            "Config*",
            "**/*DataSource*",
            "**/*CodeNarcRuleSet*",
            "**/*resources*",
            "**/*UrlMappings*",
            "**/*Tests*",
            "**/grails/test/**",
            "**/org/codehaus/groovy/grails/**",
            "**/PreInit*",
            "*GrailsPlugin*",
            "**/domain/**"
    ]
}
