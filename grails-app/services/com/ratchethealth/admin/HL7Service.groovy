package com.ratchethealth.admin

import grails.converters.JSON

class HL7Service extends RatchetAPIService {
    def grailsApplication

    def getQueueStatus(String token) {
        log.info("Call backend service to get HL7 Queue status, token: ${token}.")

        String host = grailsApplication.config.ratchetv2.server.url.host
        String queueStatusURI = grailsApplication.config.ratchetv2.server.url.HL7.queueStatusURI
        String clientSecret = grailsApplication.config.hmac.access.secret

        withGet(token, host + queueStatusURI) { req ->
            def resp = req
                .headers(HMACUtils.generateAuthenticationInfo(clientSecret, "GET", queueStatusURI))
                .asString()

            if (resp.status == 200) {
                log.info("Get HL7 Queue status success, token: ${token}")

                JSON.parse(resp.body)
            } else {
                handleError(resp)
            }
        }
    }

    def getMessageStatus(String token) {
        log.info("Call backend service to get HL7 message status, token: ${token}.")

        String host = grailsApplication.config.ratchetv2.server.url.host
        String messageStatusURI = grailsApplication.config.ratchetv2.server.url.HL7.messageStatusURI

        withGet(token, host + messageStatusURI) { req ->
            def resp = req
                .headers(HMACUtils.generateAuthenticationInfo(grailsApplication.config.hmac.access.secret, 'GET', messageStatusURI))
                .asString()

            if (resp.status == 200) {
                log.info("Get HL7 message status success, token: ${token}")

                JSON.parse(resp.body)
            } else {
                handleError(resp)
            }
        }
    }

    def getFailureList(String token) {
        log.info("Call backend service to get HL7 failure list, token: ${token}.")

        String host = grailsApplication.config.ratchetv2.server.url.host
        String failuresListURI = grailsApplication.config.ratchetv2.server.url.HL7.failuresListURI

        withGet(token, host + failuresListURI) { req ->
            def resp = req
                .headers(HMACUtils.generateAuthenticationInfo(grailsApplication.config.hmac.access.secret, 'GET', failuresListURI))
                .queryString("max", 5000)
                .queryString("offset", 0)
                .asString()

            if (resp.status == 200) {
                log.info("Get HL7 failure list success, token: ${token}")

                JSON.parse(resp.body)
            } else {
                handleError(resp)
            }
        }
    }

    def retryFailure(String token, errorJobId) {
        log.info("Call backend service to let HL7 failure item reprocess, token: ${token}.")

        String host = grailsApplication.config.ratchetv2.server.url.host
        String failureRetryURI = grailsApplication.config.ratchetv2.server.url.HL7.failureRetryURI

        failureRetryURI = String.format(failureRetryURI, errorJobId)

        withPost(token, host + failureRetryURI) { req ->
            def resp = req
                .headers(HMACUtils.generateAuthenticationInfo(grailsApplication.config.hmac.access.secret, 'POST', failureRetryURI))
                .headers(['content-type': 'application/json'])
                .asString()

            if (resp.status == 200) {
                log.info("HL7 failure item reprocess success, token: ${token}")

                return true
            } else {
                handleError(resp)
            }
        }
    }
}
