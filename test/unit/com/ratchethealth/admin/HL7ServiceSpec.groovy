package com.ratchethealth.admin

import com.mashape.unirest.request.GetRequest
import com.mashape.unirest.request.HttpRequestWithBody
import com.ratchethealth.admin.exceptions.ServerException
import grails.test.mixin.TestFor
import groovy.json.JsonBuilder
import spock.lang.Specification

@TestFor(HL7Service)
class HL7ServiceSpec extends Specification {
    def "test getQueueStatus with successful result"() {
        given:
        def jBuilder = new JsonBuilder()
        jBuilder {
            totalCount 2
            items 1, 2
        }

        GetRequest.metaClass.asString = { ->
            return [
                status: 200,
                body: jBuilder.toString()
            ]
        }

        when:
        def result = service.getQueueStatus('token')

        then:
        result.totalCount == 2
        result.items == [1, 2]
    }

    def "test getQueueStatus without successful result"() {
        given:
        GetRequest.metaClass.asString = { ->
            return [
                status: 400,
                body: "body"
            ]
        }

        when:
        service.getQueueStatus('token')

        then:
        ServerException e = thrown()
        e.getMessage() == "body"
    }

    def "test getMessageStatus with successful result"() {
        given:
        def jBuilder = new JsonBuilder()
        jBuilder {
            totalCount 2
            items 1, 2
        }

        GetRequest.metaClass.asString = { ->
            return [
                status: 200,
                body: jBuilder.toString()
            ]
        }

        when:
        def result = service.getMessageStatus('token')

        then:
        result.totalCount == 2
        result.items == [1, 2]
    }

    def "test getMessageStatus without successful result"() {
        given:
        GetRequest.metaClass.asString = { ->
            return [
                status: 400,
                body: "body"
            ]
        }

        when:
        service.getMessageStatus('token')

        then:
        ServerException e = thrown()
        e.getMessage() == "body"
    }

    def "test getFailureList with successful result"() {
        given:
        def jBuilder = new JsonBuilder()
        jBuilder {
            totalCount 2
            items 1, 2
        }

        GetRequest.metaClass.asString = { ->
            return [
                status: 200,
                body: jBuilder.toString()
            ]
        }

        when:
        def result = service.getFailureList('token')

        then:
        result.totalCount == 2
        result.items == [1, 2]
    }

    def "test getFailureList without successful result"() {
        given:
        GetRequest.metaClass.asString = { ->
            return [
                status: 400,
                body: "body"
            ]
        }

        when:
        service.getFailureList('token')

        then:
        ServerException e = thrown()
        e.getMessage() == "body"
    }

    def "test retryFailure with successful result"() {
        given:
        def jBuilder = new JsonBuilder()
        jBuilder {
            status 'ok'
        }

        HttpRequestWithBody.metaClass.asString = { ->
            return [
                status: 200,
                body: jBuilder.toString()
            ]
        }

        when:
        def result = service.retryFailure('token', 1)

        then:
        result == true
    }

    def "test retryFailure without successful result"() {
        given:
        HttpRequestWithBody.metaClass.asString = { ->
            return [
                status: 400,
                body: "body"
            ]
        }

        when:
        service.retryFailure('token', 1)

        then:
        ServerException e = thrown()
        e.getMessage() == "body"
    }
}
