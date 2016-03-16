// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

if (System.properties["${appName}.config.location"]) {
	grails.config.locations << "file:" + System.properties["${appName}.config.location"]
}

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination

// The ACCEPT header will not be used for content negotiation for user agents containing the following strings (defaults to the 4 major rendering engines)
grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [ // the first one is the default format
					  all          : '*/*', // 'all' maps to '*' or the first available format in withFormat
					  atom         : 'application/atom+xml',
					  css          : 'text/css',
					  csv          : 'text/csv',
					  form         : 'application/x-www-form-urlencoded',
					  html         : ['text/html', 'application/xhtml+xml'],
					  js           : 'text/javascript',
					  json         : ['application/json', 'text/json'],
					  multipartForm: 'multipart/form-data',
					  rss          : 'application/rss+xml',
					  text         : 'text/plain',
					  hal          : ['application/hal+json', 'application/hal+xml'],
					  xml          : ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'singleton'

// GSP settings
grails {
	views {
		gsp {
			encoding = 'UTF-8'
			htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
			codecs {
				expression = 'html' // escapes values inside ${}
				scriptlet = 'html' // escapes output from scriptlets in GSPs
				taglib = 'none' // escapes output from taglibs
				staticparts = 'none' // escapes output from static template parts
			}
		}
		// escapes all not-encoded output at final stage of outputting
		// filteringCodecForContentType.'text/html' = 'html'
	}
}


grails.converters.encoding = "UTF-8"
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart = false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

// configure passing transaction's read-only attribute to Hibernate session, queries and criterias
// set "singleSession = false" OSIV mode in hibernate configuration after enabling
grails.hibernate.pass.readonly = false
// configure passing read-only to OSIV session by default, requires "singleSession = false" OSIV mode
grails.hibernate.osiv.readonly = false

environments {
	development {
		grails.logging.jul.usebridge = true
	}
	production {
		grails.logging.jul.usebridge = false
		// TODO: grails.serverURL = "http://www.changeme.com"
	}
}

grails.assets.excludes = [
		'bower_components/**',
		'.sass-cache/**',
		'sass/**',
		'components/**',
		'constants/**',
		'pages/**',
		'utils/**',
		'config.rb'
]

grails.assets.plugin."resources".excludes =["**"]
grails.assets.plugin."cookie-session".excludes =["**"]
grails.plugin.cookiesession.httponly = true
grails.assets.minifyJs = false
grails.plugin.cookiesession.setsecure = !System.getProperty("NOT_SUPPORT_HTTPS")?.toBoolean()

if (System.getProperty("CDN_ENABLE")?.toBoolean()) {
	cdn_domain = System.getProperty("CDN_ASSET_DOMAIN_ADMIN") ?: "https://d1gdqclzwn7f9.cloudfront.net"
	grails.assets.url = "${cdn_domain}/assets/"
}

// log4j configuration
log4j.main = {
	// Example of changing the log pattern for the default console appender:
	//
	//appenders {
	//    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
	//}

    if (System.getProperty("ELK_TCP_ADDR")) {
        appenders {
            console name: 'stdout', layout: pattern(conversionPattern: '%c{2} %m%n')
            appender new biz.paluch.logging.gelf.log4j.GelfLogAppender(name: 'central',
                    host: System.getProperty("ELK_TCP_ADDR"), port: 12201, additionalFields: "app_type= admin")
        }

		root { info "central", "stdout", "stacktrace" }
	}

	info 'com.ratchethealth.admin',
			'grails.app.domain',
			'grails.app.services',
			'grails.app.controllers',
			'grails.app.filters.com.ratchethealth.admin.LoggingFilters'

	error 'org.codehaus.groovy.grails.web.servlet',        // controllers
			'org.codehaus.groovy.grails.web.pages',          // GSP
			'org.codehaus.groovy.grails.web.sitemesh',       // layouts
			'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
			'org.codehaus.groovy.grails.web.mapping',        // URL mapping
			'org.codehaus.groovy.grails.commons',            // core / classloading
			'org.codehaus.groovy.grails.plugins',            // plugins
			'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
			'org.springframework',
			'org.hibernate',
			'net.sf.ehcache.hibernate',
            'com.granicus.grails.plugins.cookiesession.CookieSessionFilter',
            'com.granicus.grails.plugins.cookiesession.SessionRepositoryRequestWrapper',
            'com.granicus.grails.plugins.cookiesession.SessionRepositoryResponseWrapper',
            'com.granicus.grails.plugins.cookiesession.CookieSessionRepository'

	environments {
		development {
			debug 'com.ratchethealth.admin',
					'grails.app.domain',
					'grails.app.services',
					'grails.app.controllers'
		}
	}
}

grails.resources.resourceLocatorEnabled = false
grails.plugin.cookiesession.enabled = true
grails.plugin.cookiesession.cookiename = "ratchet-session"
grails.plugin.cookiesession.secret = "3xgyUhjKb7%w=3NBXznmXyF$H".bytes.encodeBase64(false).toString()

// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format
String defaultOverrideLocation = "classpath:resources/noredist/override.properties"
String systemOverrideLocation = System.getProperty("override")
String overrideLocation = systemOverrideLocation ? "file:${systemOverrideLocation}" : defaultOverrideLocation
grails.config.locations = [
		overrideLocation
]

grails {
	mail {
		host = "smtp.gmail.com"
		port = 587
		username = "test@gmail.com"
		password = "password"
		props = ["mail.smtp.auth"                  : "true",
				 "mail.smtp.socketFactory.port"    : "465",
				 "mail.smtp.socketFactory.class"   : "javax.net.ssl.SSLSocketFactory",
				 "mail.smtp.socketFactory.fallback": "false"]
	}
}

hmac.access.secret = System.getProperty("HMAC_ACCESS_SECRET") ?: "Fk5R5udHYU0CWRXnPAdZtlfDIRQsscgvPIUEwd0c"
hmac.access.token = System.getProperty("HMAC_ACCESS_TOKEN") ?: "rpZL8RQj2R65fzOPtJ22"

grails.mail.default.from = "no-reply@ratchet.com"
ratchet.api.anonymous.token = System.getProperty("ANONYMOUS_API_TOKEN") ?: "FkvVedyg9hT\$dvkUGhNVqj"

ratchetv2 {
	server {
		debug = System.getProperty("IS_DEBUG") ?: false
		url {
			host = System.getProperty("SERVER_URL") ?: "http://api.develop.ratchethealth.com"

			// Authentication
			login = "${ratchetv2.server.url.host}/api/v1/login"
			logout = "${ratchetv2.server.url.host}/api/v1/logout"
			validateSessionId = "${ratchetv2.server.url.host}/api/v1/check_token"

            //forgotPassword
            password.reset = "${ratchetv2.server.url.host}/api/v1/password/reset"
            password.restCheck = "${ratchetv2.server.url.host}/api/v1/password/reset/check"
            password.confirm = "${ratchetv2.server.url.host}/api/v1/password/confirm"
            updatePassword = "${ratchetv2.server.url.host}/api/v1/password/update"

			// Client URL
			clients = "${ratchetv2.server.url.host}/api/v1/clients"
			oneClient = "${ratchetv2.server.url.host}/api/v1/clients/%d"
			ips = "${ratchetv2.server.url.host}/api/v1/clients/%d/ips"
			oneIP = "${ratchetv2.server.url.host}/api/v1/clients/%d/ips/%d"

			//Admin URL
			admins = "${ratchetv2.server.url.host}/api/v1/admins"
			oneAdmin = "${ratchetv2.server.url.host}/api/v1/admins/%d"
			admin.confirm = "${ratchetv2.server.url.host}/api/v1/admin/confirm"
			admin.validateCode = "${ratchetv2.server.url.host}/api/v1/admin/validation/%s"

			// Announcement URL
			announcements = "${ratchetv2.server.url.host}/api/v1/announcements"
			oneAnnouncement = "${ratchetv2.server.url.host}/api/v1/announcements/%d"

			// Staff URL
			staffs = "${ratchetv2.server.url.host}/api/v1/staffs"
			oneStaff = "${ratchetv2.server.url.host}/api/v1/staffs/%d"

			// Treatment URL
			treatments = "${ratchetv2.server.url.host}/api/v1/clients/%d/treatments"
			oneTreatment = "${ratchetv2.server.url.host}/api/v1/clients/%d/treatments/%d"

			treatment {
				tools = "${ratchetv2.server.url.host}/api/v1/treatments/%d/tools"
				oneTool = "${ratchetv2.server.url.host}/api/v1/treatments/%d/tools/%d"
				allToolsOfTreatment = "${ratchetv2.server.url.host}/api/v1/treatments/%d/tools/loadToolByTreatment"
				allToolsOfPredefined = "${ratchetv2.server.url.host}/api/v1/tools/allPredefinedTools"

				tasks = "${ratchetv2.server.url.host}/api/v1/treatments/%d/tasks"
				oneTask = "${ratchetv2.server.url.host}/api/v1/treatments/%d/tasks/%d"

			}
            // Debug Schedule URL
            scheduleTime = "${ratchetv2.server.url.host}/api/v1/admin/debug/set-date"
			trigger.now = "${ratchetv2.server.url.host}/api/v1/admin/quartz/trigger/triggerNow"

			// Db backup URL
			testdata = "${ratchetv2.server.url.host}/api/v1/db/backups"

			ratchetv2.server.client_platform = ancient
			ratchetv2.server.client_type = admin

			// HL7
			HL7 {
				queueStatusURI = "/api/v2/hl7/metrics/queue"
				messageStatusURI = "/api/v2/hl7/metrics/status"
				failuresListURI = "/api/v2/hl7/error"
				failureRetryURI = "/api/v2/hl7/error/%s/reprocess"
			}
		}
	}
}
