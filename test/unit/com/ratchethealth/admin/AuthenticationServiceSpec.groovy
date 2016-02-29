package com.ratchethealth.admin

import com.mashape.unirest.request.GetRequest
import com.mashape.unirest.request.HttpRequestWithBody
import com.mashape.unirest.request.body.MultipartBody
import com.ratchethealth.admin.exceptions.AccountValidationException
import com.ratchethealth.admin.exceptions.ServerException
import grails.test.mixin.TestFor
import groovy.json.JsonBuilder
import spock.lang.Specification

@TestFor(AuthenticationService)
class AuthenticationServiceSpec extends Specification {
	def "test authenticate with successful result"() {
		given:
		def jBuilder = new JsonBuilder()
		jBuilder {
			token 'new token'
		}

		MultipartBody.metaClass.asString = { ->
			return [
					status: 200,
					body: jBuilder.toString()
			]
		}

		when:
		def result = service.authenticate('token', 'email', 'password')

		then:
		result.token == 'new token'
		result.authenticated == true
	}

	def "test authenticate with 403 result"() {
		given:
		service.messageSource = [getMessage: { key, args, locale -> return ("message " + args[0]) }]

		def jBuilder = new JsonBuilder()
		jBuilder {
			error {
				errorMessage 20
				errorID 403
			}
		}

		MultipartBody.metaClass.asString = { ->
			return [
					status: 401,
					body: jBuilder.toString()
			]
		}

		when:
		service.authenticate('token', 'email', 'password')

		then:
		AccountValidationException e = thrown()
		e.getMessage() == 'message 20'
	}

	def "test authenticate with 401 result"() {
		given:
		MultipartBody.metaClass.asString = { ->
			return [
					status: 401,
					body: 'body'
			]
		}

		when:
		service.authenticate('token', 'email', 'password')

		then:
		AccountValidationException e = thrown()
		e.getMessage() == 'body'
	}

	def "test logout without token"() {
		when:
		def result = service.logout()

		then:
		result == false
	}

	def "test logout with successful result"() {
		given:
		HttpRequestWithBody.metaClass.asString = { ->
			return [
					status: 200,
					body: ''
			]
		}

		when:
		def result = service.logout('token')

		then:
		result == true
	}

	def "test logout without successful result"() {
		given:
		HttpRequestWithBody.metaClass.asString = { ->
			return [
					status: 400,
					body: "body"
			]
		}

		when:
		service.logout('token')

		then:
		ServerException e = thrown()
		e.getMessage() == "body"
	}

	def "test askForResetPassword with successful result"() {
		given:
		MultipartBody.metaClass.asString = { ->
			return [
					status: 200,
					body: ''
			]
		}

		when:
		def result = service.askForResetPassword('token', 'email', 'client')

		then:
		result == true
	}

	def "test askForResetPassword without successful result"() {
		given:
		MultipartBody.metaClass.asString = { ->
			return [
					status: 400,
					body: "body"
			]
		}

		when:
		def result = service.askForResetPassword('token', 'email', 'client')

		then:
		result == true
	}

	def "test validPasswordCode with successful result"() {
		given:
		def jBuilder = new JsonBuilder()
		jBuilder {
			id 1
		}

		GetRequest.metaClass.asString = { ->
			return [
					status: 200,
					body: jBuilder.toString()
			]
		}

		when:
		def result = service.validPasswordCode('token', 'code')

		then:
		result == true
	}

	def "test validPasswordCode without successful result"() {
		given:
		GetRequest.metaClass.asString = { ->
			return [
					status: 400,
					body: "body"
			]
		}

		when:
		service.validPasswordCode('token', 'code')

		then:
		ServerException e = thrown()
		e.getMessage() == "body"
	}

	def "test resetPassword with successful result"() {
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
		def result = service.resetPassword('token', 'code', 'password', 'password')

		then:
		result == true
	}

	def "test resetPassword without successful result"() {
		given:
		MultipartBody.metaClass.asString = { ->
			return [
					status: 400,
					body: "body"
			]
		}

		when:
		service.resetPassword('token', 'code', 'password', 'password')

		then:
		ServerException e = thrown()
		e.getMessage() == "body"
	}

	def "test updatePassword with successful result"() {
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
		def result = service.updatePassword('token', 'oldPassword', 'password', 'password')

		then:
		result == true
	}

	def "test updatePassword without successful result"() {
		given:
		MultipartBody.metaClass.asString = { ->
			return [
					status: 400,
					body: "body"
			]
		}

		when:
		service.updatePassword('token', 'oldPassword', 'password', 'password')

		then:
		ServerException e = thrown()
		e.getMessage() == "body"
	}
}
