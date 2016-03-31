package com.ratchethealth.admin

import com.mashape.unirest.request.GetRequest
import com.mashape.unirest.request.HttpRequestWithBody
import com.mashape.unirest.request.body.MultipartBody
import com.ratchethealth.admin.exceptions.ServerException
import grails.test.mixin.TestFor
import groovy.json.JsonBuilder
import spock.lang.Specification

@TestFor(ClientService)
class ClientServiceSpec extends Specification {
	def "test getClients with successful result"() {
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
		def result = service.getClients('token', queryOption)

		then:
		result.recordsTotal == 2
		result.recordsFiltered == 2
		result.data == [1, 2]
	}

	def "test getClients without successful result"() {
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
		service.getClients('token', queryOption)

		then:
		ServerException e = thrown()
		e.getMessage() == "body"
	}

	def "test getClient with successful result"() {
		given:
		def jBuilder = new JsonBuilder()
		jBuilder {
			id 2
			name 'name'
		}

		GetRequest.metaClass.asString = { ->
			return [
					status: 200,
					body: jBuilder.toString()
			]
		}

		when:
		def result = service.getClient('token', 1)

		then:
		result.id == 2
		result.name == 'name'
	}

	def "test getClient without successful result"() {
		given:
		GetRequest.metaClass.asString = { ->
			return [
					status: 400,
					body: "body"
			]
		}

		when:
		service.getClient('token', 1)

		then:
		ServerException e = thrown()
		e.getMessage() == "body"
	}

	def "test createClient with successful result"() {
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

		Client client = new Client()
		client.id = 1
		client.name = 'name'
		client.logo = 'logo'
		client.favIcon = 'favIcon'
		client.logoFileName = 'logoFileName'
		client.favIconFileName = 'favIconFileName'
		client.subDomain = 'subDomain'
		client.portalName = 'portalName'
		client.primaryColorHex = '111111'

		when:
		def result = service.createClient('token', client)

		then:
		result.id == 2
		result.name == 'name'
		result.logo == null
		result.favIcon == null
		result.logoFileName == 'logoFileName'
		result.favIconFileName == 'favIconFileName'
		result.subDomain == 'subDomain'
		result.portalName == 'portalName'
		result.primaryColorHex == '111111'
	}

	def "test createClient without successful result"() {
		given:
		MultipartBody.metaClass.asString = { ->
			return [
					status: 400,
					body: "body"
			]
		}

		Client client = new Client()

		when:
		service.createClient('token', client)

		then:
		ServerException e = thrown()
		e.getMessage() == "body"
	}

	def "test updateClient with successful result"() {
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

		Client client = new Client()
		client.id = 1
		client.name = 'name'
		client.logo = 'logo'
		client.favIcon = 'favIcon'
		client.logoFileName = 'logoFileName'
		client.favIconFileName = 'favIconFileName'
		client.subDomain = 'subDomain'
		client.portalName = 'portalName'
		client.primaryColorHex = '111111'


		when:
		def result = service.updateClient('token', client)

		then:
		result == true
	}

	def "test updateClient without successful result"() {
		given:
		MultipartBody.metaClass.asString = { ->
			return [
					status: 400,
					body: "body"
			]
		}

		Client client = new Client()

		when:
		service.updateClient('token', client)

		then:
		ServerException e = thrown()
		e.getMessage() == "body"
	}

	def "test addIP with successful result"() {
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

		ClientIP ip = new ClientIP()
		ip.name = 'name'
		ip.description = 'description'
		ip.ip = '0.0.0.0/0'

		when:
		def result = service.addIP('token', 1, ip)

		then:
		result.id == 2
		result.name == 'name'
		result.ip == '0.0.0.0/0'
		result.description == 'description'
	}

	def "test addIP without successful result"() {
		given:
		MultipartBody.metaClass.asString = { ->
			return [
				status: 400,
				body: "body"
			]
		}

		ClientIP ip = new ClientIP()

		when:
		service.addIP('token', 1, ip)

		then:
		ServerException e = thrown()
		e.getMessage() == "body"
	}

    def "test updateIP with successful result"() {
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

        ClientIP ip = new ClientIP()
        ip.name = 'name'
        ip.description = 'description'
        ip.ip = '0.0.0.0/0'


        when:
        def result = service.updateIP('token', 1, ip)

        then:
        result == true
    }

    def "test updateIP without successful result"() {
        given:
        MultipartBody.metaClass.asString = { ->
            return [
                status: 400,
                body: "body"
            ]
        }

        ClientIP ip = new ClientIP()

        when:
        service.updateIP('token', 1, ip)

        then:
        ServerException e = thrown()
        e.getMessage() == "body"
    }

    def "test deleteIP with successful result"() {
        given:
        HttpRequestWithBody.metaClass.asString = { ->
            return [
                status: 204,
                body: ''
            ]
        }

        ClientIP ip = new ClientIP()
        ip.id = 1


        when:
        def result = service.deleteIP('token', 1, ip)

        then:
        result == true
    }

    def "test deleteIP without successful result"() {
        given:
        HttpRequestWithBody.metaClass.asString = { ->
            return [
                status: 400,
                body: "body"
            ]
        }

        ClientIP ip = new ClientIP()
        ip.id = 1

        when:
        service.deleteIP('token', 1, ip)

        then:
        ServerException e = thrown()
        e.getMessage() == "body"
    }
}
