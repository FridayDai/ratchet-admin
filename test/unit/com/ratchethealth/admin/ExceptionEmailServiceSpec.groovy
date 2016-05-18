package com.ratchethealth.admin

import com.mashape.unirest.request.GetRequest
import grails.test.mixin.TestFor
import org.codehaus.groovy.grails.web.util.WebUtils
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ExceptionEmailService)
class ExceptionEmailServiceSpec extends Specification {
    def setupSpec() {
        WebUtils.metaClass.'static'.retrieveGrailsWebRequest = { ->
            return null
        }
    }

    void "test Send Uncaught Exception Email"() {
        given:
        GetRequest.metaClass.asString = { ->
            return [
                    status: 400,
                    body  : "body"
            ]
        }

        when:
        def result = service.sendExceptionEmail()

        then:
        result == true
    }
}
