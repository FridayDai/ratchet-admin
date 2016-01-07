package com.ratchethealth.admin

import grails.converters.JSON


class TestDataService extends RatchetAPIService {

    def grailsApplication

    def getTestData(String token, max) {
        log.info("Call backend service to get test data, token: ${token}.")

        String testDataURL = grailsApplication.config.ratchetv2.server.url.testdata

        withGet(token, testDataURL) { req ->
            def resp = req
                    .queryString("max", max)
                    .asString()

            if (resp.status == 200) {
                def result = JSON.parse(resp.body)

                log.info("Get test data success, token: ${token}")
                return result
            } else {
                handleError(resp)
            }
        }
    }

    def generateTestData(String token, Boolean isDataAnonymized) {
        log.info("Call backend service to generate test data, token: ${token}.")

        String testDataURL = grailsApplication.config.ratchetv2.server.url.testdata

        withPost(token, testDataURL) { req ->
            def resp = req
                    .field("isDataAnonymized", isDataAnonymized)
                    .asString()

            if (resp.status == 200) {
                log.info("Generate test data success, token: ${token}")

                return true
            } else {
                handleError(resp)
            }
        }
    }
}
