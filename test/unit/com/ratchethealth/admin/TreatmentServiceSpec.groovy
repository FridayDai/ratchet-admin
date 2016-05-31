package com.ratchethealth.admin

import com.mashape.unirest.request.GetRequest
import com.mashape.unirest.request.HttpRequestWithBody
import com.mashape.unirest.request.body.MultipartBody
import com.ratchethealth.admin.exceptions.ServerException
import grails.test.mixin.TestFor
import groovy.json.JsonBuilder
import spock.lang.Specification

@TestFor(TreatmentService)
class TreatmentServiceSpec extends Specification {
	def "test getTreatments with successful result"() {
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
		def result = service.getTreatments('token', 1, 1, 1)

		then:
		result.recordsTotal == 2
		result.recordsFiltered == 2
		result.data == [1, 2]
	}

	def "test getTreatments without successful result"() {
		given:
		GetRequest.metaClass.asString = { ->
			return [
					status: 400,
					body: "body"
			]
		}

		when:
		service.getTreatments('token', 1, 1, 1)

		then:
		ServerException e = thrown()
		e.getMessage() == "body"
	}

	def "test createTreatment with successful result"() {
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

		Treatment treatment = new Treatment()
		treatment.clientId = 1
		treatment.id = 1
		treatment.activePatient = 0
		treatment.title = 'title'
		treatment.tmpTitle = 'tmpTitle'
		treatment.active = true
		treatment.absoluteEventType = 'SURGERY'
		treatment.description = 'description'
		treatment.status = 'status'
		treatment.lastUpdated = 111111111

		when:
		def result = service.createTreatment('token', treatment)

		then:
		result.id == 2
		result.clientId == 1
		result.activePatient == 0
		result.title == 'title'
		result.tmpTitle == 'tmpTitle'
		result.active == true
		result.absoluteEventType == 'SURGERY'
		result.description == 'description'
		result.status == 'status'
		result.lastUpdated == 111111111
	}

	def "test createTreatment without successful result"() {
		given:
		MultipartBody.metaClass.asString = { ->
			return [
					status: 400,
					body: "body"
			]
		}

		Treatment treatment = new Treatment()
		treatment.clientId = 1
		treatment.id = 1
		treatment.activePatient = 0
		treatment.title = 'title'
		treatment.tmpTitle = 'tmpTitle'
		treatment.active = true
		treatment.absoluteEventType = 'SURGERY'
		treatment.description = 'description'
		treatment.status = 'status'
		treatment.lastUpdated = 111111111

		when:
		service.createTreatment('token', treatment)

		then:
		ServerException e = thrown()
		e.getMessage() == "body"
	}

	def "test getTreatment with successful result"() {
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
		def result = service.getTreatment('token', 1, 1)

		then:
		result.id == 2
		result.name == 'name'
	}

	def "test getTreatment without successful result"() {
		given:
		GetRequest.metaClass.asString = { ->
			return [
					status: 400,
					body: "body"
			]
		}

		when:
		service.getTreatment('token', 1, 1)

		then:
		ServerException e = thrown()
		e.getMessage() == "body"
	}

	def "test updateTreatment with successful result"() {
		given:
		def jBuilder = new JsonBuilder()
		jBuilder {
			id 2
		}

		MultipartBody.metaClass.asString = { ->
			return [
					status: 200,
					body: jBuilder.toString()
			]
		}

		Treatment treatment = new Treatment()
		treatment.clientId = 1
		treatment.id = 1
		treatment.activePatient = 0
		treatment.title = 'title'
		treatment.tmpTitle = 'tmpTitle'
		treatment.active = true
		treatment.absoluteEventType = 'SURGERY'
		treatment.description = 'description'
		treatment.status = 'status'
		treatment.lastUpdated = 111111111


		when:
		def result = service.updateTreatment('token', treatment)

		then:
		result == true
	}

	def "test updateTreatment without successful result"() {
		given:
		MultipartBody.metaClass.asString = { ->
			return [
					status: 400,
					body: "body"
			]
		}

		Treatment treatment = new Treatment()
		treatment.clientId = 1
		treatment.id = 1
		treatment.activePatient = 0
		treatment.title = 'title'
		treatment.tmpTitle = 'tmpTitle'
		treatment.active = true
		treatment.absoluteEventType = 'SURGERY'
		treatment.description = 'description'
		treatment.status = 'status'
		treatment.lastUpdated = 111111111

		when:
		service.updateTreatment('token', treatment)

		then:
		ServerException e = thrown()
		e.getMessage() == "body"
	}

	def "test closeTreatment with successful result"() {
		given:
		HttpRequestWithBody.metaClass.asString = { ->
			return [
					status: 204,
					body: ''
			]
		}

		when:
		def result = service.closeTreatment('token', 1, 1)

		then:
		result == true
	}

	def "test closeTreatment without successful result"() {
		given:
		HttpRequestWithBody.metaClass.asString = { ->
			return [
					status: 400,
					body: "body"
			]
		}

		when:
		service.closeTreatment('token', 1, 1)

		then:
		ServerException e = thrown()
		e.getMessage() == "body"
	}

	def "test getTools with successful result"() {
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
		def result = service.getTools('token', 1, 1, 1)

		then:
		result.recordsTotal == 2
		result.recordsFiltered == 2
		result.data == [1, 2]
	}

	def "test getTools without successful result"() {
		given:
		GetRequest.metaClass.asString = { ->
			return [
					status: 400,
					body: "body"
			]
		}

		when:
		service.getTools('token', 1, 1, 1)

		then:
		ServerException e = thrown()
		e.getMessage() == "body"
	}

	def "test getToolsInTreatment with successful result"() {
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
		def result = service.getToolsInTreatment('token', 1)

		then:
		result.totalCount == 2
		result.items == [1, 2]
	}

	def "test getToolsInTreatment without successful result"() {
		given:
		GetRequest.metaClass.asString = { ->
			return [
					status: 400,
					body: "body"
			]
		}

		when:
		service.getToolsInTreatment('token', 1)

		then:
		ServerException e = thrown()
		e.getMessage() == "body"
	}

	def "test getPredefinedTools with successful result"() {
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
		def result = service.getPredefinedTools('token', 1)

		then:
		result.totalCount == 2
		result.items == [1, 2]
	}

	def "test getPredefinedTools without successful result"() {
		given:
		GetRequest.metaClass.asString = { ->
			return [
					status: 400,
					body: "body"
			]
		}

		when:
		service.getPredefinedTools('token', 1)

		then:
		ServerException e = thrown()
		e.getMessage() == "body"
	}

	def "test addTool with successful result"() {
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

		Tool tool = new Tool()
		tool.id = 1
		tool.treatmentId = 1
		tool.title = 'title'
		tool.description = 'description'
		tool.defaultDueTime = 111111
		tool.defaultDueTimeDay = 111111
		tool.defaultDueTimeHour = 111111
		tool.reminder = 'reminder'
		tool.detailedDescription = 'detailedDescription'
		tool.requireCompletion = true
		tool.type = 10
		tool.lastUpdated = 111111111

		when:
		def result = service.addTool('token', tool)

		then:
		result.id == 2
	}

	def "test addTool without successful result"() {
		given:
		MultipartBody.metaClass.asString = { ->
			return [
					status: 400,
					body: "body"
			]
		}

		Tool tool = new Tool()
		tool.id = 1
		tool.treatmentId = 1
		tool.title = 'title'
		tool.description = 'description'
		tool.defaultDueTime = 111111
		tool.defaultDueTimeDay = 111111
		tool.defaultDueTimeHour = 111111
		tool.reminder = 'reminder'
		tool.detailedDescription = 'detailedDescription'
		tool.requireCompletion = true
		tool.type = 10
		tool.lastUpdated = 111111111

		when:
		service.addTool('token', tool)

		then:
		ServerException e = thrown()
		e.getMessage() == "body"
	}

	def "test updateTool with successful result"() {
		given:
		def jBuilder = new JsonBuilder()
		jBuilder {
			id 2
			name 'name'
		}

		MultipartBody.metaClass.asString = { ->
			return [
					status: 200,
					body: jBuilder.toString()
			]
		}

		Tool tool = new Tool()
		tool.id = 1
		tool.treatmentId = 1
		tool.title = 'title'
		tool.description = 'description'
		tool.defaultDueTime = 111111
		tool.defaultDueTimeDay = 111111
		tool.defaultDueTimeHour = 111111
		tool.reminder = 'reminder'
		tool.detailedDescription = 'detailedDescription'
		tool.requireCompletion = true
		tool.type = 10
		tool.lastUpdated = 111111111


		when:
		def result = service.updateTool('token', tool)

		then:
		result.id == 2
		result.name == 'name'
	}

	def "test updateTool without successful result"() {
		given:
		MultipartBody.metaClass.asString = { ->
			return [
					status: 400,
					body: "body"
			]
		}

		Tool tool = new Tool()
		tool.id = 1
		tool.treatmentId = 1
		tool.title = 'title'
		tool.description = 'description'
		tool.defaultDueTime = 111111
		tool.defaultDueTimeDay = 111111
		tool.defaultDueTimeHour = 111111
		tool.reminder = 'reminder'
		tool.detailedDescription = 'detailedDescription'
		tool.requireCompletion = true
		tool.type = 10
		tool.lastUpdated = 111111111

		when:
		service.updateTool('token', tool)

		then:
		ServerException e = thrown()
		e.getMessage() == "body"
	}

	def "test deleteTool with successful result"() {
		given:
		HttpRequestWithBody.metaClass.asString = { ->
			return [
					status: 204,
					body: ''
			]
		}

		when:
		def result = service.deleteTool('token', 1, 1)

		then:
		result == true
	}

	def "test deleteTool without successful result"() {
		given:
		HttpRequestWithBody.metaClass.asString = { ->
			return [
					status: 400,
					body: "body"
			]
		}

		when:
		service.deleteTool('token', 1, 1)

		then:
		ServerException e = thrown()
		e.getMessage() == "body"
	}

	def "test getTasks with successful result"() {
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
		def result = service.getTasks('token', 1, 1, 1)

		then:
		result.recordsTotal == 2
		result.recordsFiltered == 2
		result.data == [1, 2]
	}

	def "test getTasks without successful result"() {
		given:
		GetRequest.metaClass.asString = { ->
			return [
					status: 400,
					body: "body"
			]
		}

		when:
		service.getTasks('token', 1, 1, 1)

		then:
		ServerException e = thrown()
		e.getMessage() == "body"
	}

	def "test addTask with successful result"() {
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

		Task task = new Task()
		task.id = 1
		task.treatmentId = 1
		task.toolId = 1
		task.immediate = true
		task.sendTimeOffset = 111111
		task.sendTimeDirection = 111111
		task.sendTimeWeeks = 111111
		task.sendTimeDays = 111111
		task.sendTimeHours = 111111
		task.sendTimeMinutes = 111111
		task.toolTitle = 'toolTitle'
		task.toolDescription = 'toolDescription'
		task.lastUpdated = 111111111

		when:
		def result = service.addTask('token', task)

		then:
		result.id == 2
	}

	def "test addTask without successful result"() {
		given:
		MultipartBody.metaClass.asString = { ->
			return [
					status: 400,
					body: "body"
			]
		}

		Task task = new Task()
		task.id = 1
		task.treatmentId = 1
		task.toolId = 1
		task.immediate = true
		task.sendTimeOffset = 111111
		task.sendTimeDirection = 111111
		task.sendTimeWeeks = 111111
		task.sendTimeDays = 111111
		task.sendTimeHours = 111111
		task.sendTimeMinutes = 111111
		task.toolTitle = 'toolTitle'
		task.toolDescription = 'toolDescription'
		task.lastUpdated = 111111111

		when:
		service.addTask('token', task)

		then:
		ServerException e = thrown()
		e.getMessage() == "body"
	}

	def "test updateTask with successful result"() {
		given:
		def jBuilder = new JsonBuilder()
		jBuilder {
			id 2
			name 'name'
		}

		MultipartBody.metaClass.asString = { ->
			return [
					status: 200,
					body: jBuilder.toString()
			]
		}

		Task task = new Task()
		task.id = 1
		task.treatmentId = 1
		task.toolId = 1
		task.immediate = true
		task.sendTimeOffset = 111111
		task.sendTimeDirection = 111111
		task.sendTimeWeeks = 111111
		task.sendTimeDays = 111111
		task.sendTimeHours = 111111
		task.sendTimeMinutes = 111111
		task.toolTitle = 'toolTitle'
		task.toolDescription = 'toolDescription'
		task.lastUpdated = 111111111


		when:
		def result = service.updateTask('token', task)

		then:
		result.id == 2
		result.name == 'name'
	}

	def "test updateTask without successful result"() {
		given:
		MultipartBody.metaClass.asString = { ->
			return [
					status: 400,
					body: "body"
			]
		}

		Task task = new Task()
		task.id = 1
		task.treatmentId = 1
		task.toolId = 1
		task.immediate = true
		task.sendTimeOffset = 111111
		task.sendTimeDirection = 111111
		task.sendTimeWeeks = 111111
		task.sendTimeDays = 111111
		task.sendTimeHours = 111111
		task.sendTimeMinutes = 111111
		task.toolTitle = 'toolTitle'
		task.toolDescription = 'toolDescription'
		task.lastUpdated = 111111111

		when:
		service.updateTask('token', task)

		then:
		ServerException e = thrown()
		e.getMessage() == "body"
	}

	def "test deleteTask with successful result"() {
		given:
		HttpRequestWithBody.metaClass.asString = { ->
			return [
					status: 204,
					body: ''
			]
		}

		when:
		def result = service.deleteTask('token', 1, 1)

		then:
		result == true
	}

	def "test deleteTask without successful result"() {
		given:
		HttpRequestWithBody.metaClass.asString = { ->
			return [
					status: 400,
					body: "body"
			]
		}

		when:
		service.deleteTask('token', 1, 1)

		then:
		ServerException e = thrown()
		e.getMessage() == "body"
	}
}
