package com.ratchethealth.admin

import com.mashape.unirest.request.GetRequest
import com.mashape.unirest.request.HttpRequestWithBody
import com.mashape.unirest.request.body.MultipartBody
import com.ratchethealth.admin.exceptions.ServerException
import grails.test.mixin.TestFor
import groovy.json.JsonBuilder
import spock.lang.Specification

@TestFor(AccountService)
class AccountsServiceSpec extends Specification {

	def "test getAccounts with successful result"() {
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
		def result = service.getAccounts('token', 1, 1)

		then:
		result.recordsTotal == 2
		result.recordsFiltered == 2
		result.data == [1, 2]
	}

	def "test getAccounts without successful result"() {
		given:
		GetRequest.metaClass.asString = { ->
			return [
					status: 400,
					body: "body"
			]
		}

		when:
		service.getAccounts('token', 1, 1)

		then:
		ServerException e = thrown()
		e.getMessage() == "body"
	}

	def "test createAccount with successful result"() {
		given:
		def jBuilder = new JsonBuilder()
		jBuilder {
			id 1
		}

		MultipartBody.metaClass.asString = { ->
			return [
					status: 201,
					body: jBuilder.toString()
			]
		}

		Account account = new Account()

		when:
		def result = service.createAccount('token', account)

		then:
		result.id == 1
	}

	def "test createAccount without successful result"() {
		given:
		MultipartBody.metaClass.asString = { ->
			return [
					status: 400,
					body: "body"
			]
		}

		Account account = new Account()

		when:
		service.createAccount('token', account)

		then:
		ServerException e = thrown()
		e.getMessage() == "body"
	}

	def "test deleteAccount with successful result"() {
		given:
		HttpRequestWithBody.metaClass.asString = { ->
			return [
					status: 204,
					body: ''
			]
		}

		when:
		def result = service.deleteAccount('token', 1)

		then:
		result == true
	}

	def "test deleteAccount without successful result"() {
		given:
		HttpRequestWithBody.metaClass.asString = { ->
			return [
					status: 400,
					body: "body"
			]
		}

		when:
		service.deleteAccount('token', 1)

		then:
		ServerException e = thrown()
		e.getMessage() == "body"
	}

	def "test updateAccount with successful result"() {
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

		when:
		def result = service.updateAccount('token', 1, 'email', true, 'ROLE_ADMIN')

		then:
		result.id == 1
	}

	def "test updateAccount without successful result"() {
		given:
		MultipartBody.metaClass.asString = { ->
			return [
					status: 400,
					body: "body"
			]
		}

		when:
		service.updateAccount('token', 1, 'email', true, 'ROLE_ADMIN')

		then:
		ServerException e = thrown()
		e.getMessage() == "body"
	}

	def "test activateAccount with successful result"() {
		given:
		MultipartBody.metaClass.asString = { ->
			return [
					status: 200,
					body: ''
			]
		}

		when:
		def result = service.activateAccount('token', 'code', 'password', 'password')

		then:
		result == true
	}

	def "test activateAccount without successful result"() {
		given:
		MultipartBody.metaClass.asString = { ->
			return [
					status: 400,
					body: "body"
			]
		}

		when:
		service.activateAccount('token', 'code', 'password', 'password')

		then:
		ServerException e = thrown()
		e.getMessage() == "body"
	}

	def "test validateCode with successful result"() {
		given:
		HttpRequestWithBody.metaClass.asString = { ->
			return [
					status: 200,
					body: ''
			]
		}

		when:
		def result = service.validateCode('token', 'code')

		then:
		result == true
	}

	def "test validateCode without successful result"() {
		given:
		HttpRequestWithBody.metaClass.asString = { ->
			return [
					status: 400,
					body: "body"
			]
		}

		when:
		service.validateCode('token', 'code')

		then:
		ServerException e = thrown()
		e.getMessage() == "body"
	}
}
