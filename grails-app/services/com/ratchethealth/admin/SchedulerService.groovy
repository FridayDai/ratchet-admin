package com.ratchethealth.admin

import grails.converters.JSON

class SchedulerService extends RatchetAPIService {
    def grailsApplication

    def changeTimeForSchedule(String token, String debugDate) {
        log.info("Call backend service to set date for debug schedule with debug time, token: ${token}.")

        String timeUrl = grailsApplication.config.ratchetv2.server.url.scheduleTime

        withPost(token, timeUrl) { req ->
            def resp = req
                    .field("dateForDebug",debugDate)
                    .asString()

            if (resp.status == 200) {
                log.info("set date for debug schedule successfully, token: ${token}")

                true
            } else {
                handleError(resp)
            }
        }
    }

    def getTimeForSchedule(String token) {
        log.info("Call backend service to get last date for debug schedule with debug time, token: ${token}.")

        String timeUrl = grailsApplication.config.ratchetv2.server.url.scheduleTime

        withGet(token, timeUrl) { req ->
            def resp = req.asString()

            if (resp.status == 200) {
                log.info("get last date for debug schedule successfully, token: ${token}")
                return JSON.parse(resp.body)
            } else {
                handleError(resp)
            }
        }
    }

    def triggerExpired(String token) {
        log.info("Call backend service to triggering expried job, token: ${token}.")
        String timeUrl = grailsApplication.config.ratchetv2.server.url.trigger.expired

        withPost(token, timeUrl) { req ->
            def resp = req
                    .asString()

            if (resp.status == 200) {
                log.info("Trigger successfully, token: ${token}")

                true
            } else {
                handleError(resp)
            }
        }
    }

    def triggerNotification(String token) {
        log.info("Call backend service to triggering send task job, token: ${token}.")
        String timeUrl = grailsApplication.config.ratchetv2.server.url.trigger.notification

        withPost(token, timeUrl) { req ->
            def resp = req
                    .asString()

            if (resp.status == 200) {
                log.info("Trigger successfully, token: ${token}")

                true
            } else {
                handleError(resp)
            }
        }
    }

    def triggerResendConfirm(String token) {
        log.info("Call backend service to triggering resend confirmation job, token: ${token}.")
        String timeUrl = grailsApplication.config.ratchetv2.server.url.trigger.resend.confirmation

        withPost(token, timeUrl) { req ->
            def resp = req
                    .asString()

            if (resp.status == 200) {
                log.info("Trigger successfully, token: ${token}")

                true
            } else {
                handleError(resp)
            }
        }
    }
}
