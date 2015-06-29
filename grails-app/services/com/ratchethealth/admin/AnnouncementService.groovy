package com.ratchethealth.admin

import com.mashape.unirest.http.Unirest
import com.mashape.unirest.http.exceptions.UnirestException
import com.ratchethealth.admin.exceptions.ApiAccessException
import com.ratchethealth.admin.exceptions.ServerException
import grails.converters.JSON

import javax.servlet.http.HttpServletRequest

class AnnouncementService {
	// dependency injection for grailsApplication
	def grailsApplication

	def getAnnouncements(HttpServletRequest request, offset, max)
			throws ServerException, ApiAccessException {
		try {
			String announcementsUrl = grailsApplication.config.ratchetv2.server.url.announcements

			log.info("Call backend service to get Announcements with offset and max, token: ${request.session.token}.")
			def resp = Unirest.get(announcementsUrl)
					.header("X-Auth-Token", request.session.token)
					.queryString("offset", offset)
					.queryString("max", max)
					.asString()

			def result = JSON.parse(resp.body)

			if (resp.status == 200) {
				def map = [:]
				map.put("recordsTotal", result.totalCount)
				map.put("recordsFiltered", result.totalCount)
				map.put("data", result.items)
				log.info("Get Announcements success, token: ${request.session.token}")

				return map
			} else {
				String errorMessage = result?.errors?.message ?: result?.error?.errorMessage
				throw new ServerException(errorMessage)
			}
		} catch (UnirestException e) {
			throw new ApiAccessException(e.message)
		}
	}

	def addAnnouncement(HttpServletRequest request, Announcement announcement)
			throws ServerException, ApiAccessException {
		try {
			String announcementsUrl = grailsApplication.config.ratchetv2.server.url.announcements

			log.info("Call backend service to add Announcement with status, content and colorHex, token: ${request.session.token}.")
			def resp = Unirest.post(announcementsUrl)
					.header("X-Auth-Token", request.session.token)
					.field("status", announcement.status)
					.field("content", announcement.content)
					.field("colorHex", announcement.colorHex)
					.asString()

			def result = JSON.parse(resp.body)

			if (resp.status == 201) {
				log.info("Create Announcements success, token: ${request.session.token}")

				announcement.id = result.id

				return announcement
			} else {
				String errorMessage = result?.errors?.message ?: result?.error?.errorMessage
				throw new ServerException(errorMessage)
			}
		} catch (UnirestException e) {
			throw new ApiAccessException(e.message)
		}
	}

	def editAnnouncement(HttpServletRequest request, Announcement announcement)
			throws ServerException, ApiAccessException {
		try {
			String announcementsUrl = grailsApplication.config.ratchetv2.server.url.oneAnnouncement

			def url = String.format(announcementsUrl, announcement.id)
			log.info("Call backend service to edit Announcement with status, content and colorHex, token: ${request.session.token}.")

			def resp = Unirest.post(url)
					.header("X-Auth-Token", request.session.token)
					.field("status", announcement.status)
					.field("content", announcement.content)
					.field("colorHex", announcement.colorHex)
					.asString()

			if (resp.status == 200) {
				log.info("Update Announcements success, token: ${request.session.token}")

				return announcement
			} else {
				def result = JSON.parse(resp.body)

				String errorMessage = result?.errors?.message ?: result?.error?.errorMessage
				throw new ServerException(errorMessage)
			}
		} catch (UnirestException e) {
			throw new ApiAccessException(e.message)
		}
	}

	def deleteAnnouncement(HttpServletRequest request, Announcement announcement)
			throws ServerException, ApiAccessException {
		try {
			String announcementsUrl = grailsApplication.config.ratchetv2.server.url.oneAnnouncement

			def url = String.format(announcementsUrl, announcement.id)
			log.info("Call backend service to delete Announcement, token: ${request.session.token}.")

			def resp = Unirest.delete(url)
					.header("X-Auth-Token", request.session.token)
					.asString()

			if (resp.status == 204) {
				log.info("Delete Announcement success, token: ${request.session.token}")
				return true
			} else {
				def result = JSON.parse(resp.body)

				String errorMessage = result?.errors?.message ?: result?.error?.errorMessage
				throw new ServerException(resp.status, errorMessage)
			}
		} catch (UnirestException e) {
			throw new ApiAccessException(e.message)
		}
	}
}