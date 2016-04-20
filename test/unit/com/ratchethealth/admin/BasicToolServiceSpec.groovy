package com.ratchethealth.admin

import com.mashape.unirest.request.GetRequest
import com.mashape.unirest.request.body.MultipartBody
import com.ratchethealth.admin.exceptions.ServerException
import grails.test.mixin.TestFor
import groovy.json.JsonBuilder
import spock.lang.Specification

@TestFor(BasicToolService)
class BasicToolServiceSpec extends Specification {
    def "test getBasicToolTemplates with successful result"() {
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

        def queryOption = [
            offset: 0,
            max: 10
        ]

        when:
        def result = service.getBasicToolTemplates('token', queryOption)

        then:
        result.recordsTotal == 2
        result.recordsFiltered == 2
        result.data == [1, 2]
    }

    def "test getBasicToolTemplates without successful result"() {
        given:
        GetRequest.metaClass.asString = { ->
            return [
                status: 400,
                body: "body"
            ]
        }

        def queryOption = [
            offset: 0,
            max: 10
        ]

        when:
        service.getBasicToolTemplates('token', queryOption)

        then:
        ServerException e = thrown()
        e.getMessage() == "body"
    }

    def "test createBasicToolTemplate with successful result"() {
        given:
        def jBuilder = new JsonBuilder()
        jBuilder {
            id 2
        }

        MultipartBody.metaClass.asString = { ->
            return [
                status: 201,
                body: jBuilder.toString()
            ]
        }

        BasicToolTemplate template = new BasicToolTemplate()
        template.title = 'title'
        template.description = 'description'
        template.questionnaireContent = 'questionnaireContent'
        template.questionnaireTitle = 'questionnaireTitle'

        when:
        def result = service.createBasicToolTemplate('token', template)

        then:
        result.id == 2
        result.title == 'title'
        result.description == 'description'
        result.questionnaireContent == 'questionnaireContent'
        result.questionnaireTitle == 'questionnaireTitle'
    }

    def "test createBasicToolTemplate without successful result"() {
        given:
        MultipartBody.metaClass.asString = { ->
            return [
                status: 400,
                body: "body"
            ]
        }

        BasicToolTemplate template = new BasicToolTemplate()

        when:
        service.createBasicToolTemplate('token', template)

        then:
        ServerException e = thrown()
        e.getMessage() == "body"
    }

    def "test updateBasicToolTemplate with successful result"() {
        given:
        def jBuilder = new JsonBuilder()
        jBuilder {
            id 1
        }

        MultipartBody.metaClass.asString = { ->
            return [
                status: 200,
                body: jBuilder.toString()
            ]
        }

        BasicToolTemplate template = new BasicToolTemplate()
        template.id = 2
        template.title = 'title'
        template.description = 'description'
        template.questionnaireContent = 'questionnaireContent'
        template.questionnaireTitle = 'questionnaireTitle'


        when:
        def result = service.updateBasicToolTemplate('token', 2, template)

        then:
        result == true
    }

    def "test updateBasicToolTemplate without successful result"() {
        given:
        MultipartBody.metaClass.asString = { ->
            return [
                status: 400,
                body: "body"
            ]
        }

        BasicToolTemplate template = new BasicToolTemplate()

        when:
        service.updateBasicToolTemplate('token', 3, template)
s
        then:
        ServerException e = thrown()
        e.getMessage() == "body"
    }
}
