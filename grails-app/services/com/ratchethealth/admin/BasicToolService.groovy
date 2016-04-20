package com.ratchethealth.admin

import grails.converters.JSON

class BasicToolService extends RatchetAPIService {
    def grailsApplication

    def getBasicToolTemplates(String token, queryOption) {
        log.info("Call backend service to get basic tool templates with offset and max, token: ${token}.")


        String templatesUrl = grailsApplication.config.ratchetv2.server.url.basicTool.templates

        withGet(token, templatesUrl) { req ->
            def resp = req
                .queryString("offset", queryOption.offset)
                .queryString("max", queryOption.max)
                .asString()

            if (resp.status == 200) {
                def result = JSON.parse(resp.body)
                log.info("Get basic tool templates success, token: ${token}")

                [
                    "recordsTotal": result.totalCount,
                    "recordsFiltered": result.totalCount,
                    "data": result.items,
                ]
            } else {
                handleError(resp)
            }
        }
    }

    def createBasicToolTemplate(String token, BasicToolTemplate basicTool) {
        log.info("Call backend service to create basic tool template, token: ${token}.")

        String templatesUrl = grailsApplication.config.ratchetv2.server.url.basicTool.templates

        withPost(token, templatesUrl) { req ->
            def resp = req
                .field("isTesting", basicTool.isTesting)
                .field("title", basicTool.title)
                .field("description", basicTool.description)
                .field("basicToolTitle", basicTool.questionnaireTitle)
                .field("basicToolContent", basicTool.questionnaireContent)
                .asString()

            if (resp.status == 201) {
                def result = JSON.parse(resp.body)
                log.info("Create client success, token: ${token}")

                basicTool.id = result.id

                basicTool
            } else {
                handleError(resp)
            }
        }
    }

    def updateBasicToolTemplate(String token, basicToolId, BasicToolTemplate basicTool) {
        log.info("Call backend service to update basic tool template, token: ${token}.")

        String templatesUrl = grailsApplication.config.ratchetv2.server.url.basicTool.oneTemplate
        def url = String.format(templatesUrl, basicToolId)

        withPost(token, url) { req ->
            def resp = req
                .field("isTesting", basicTool.isTesting)
                .field("title", basicTool.title)
                .field("description", basicTool.description)
                .field("basicToolTitle", basicTool.questionnaireTitle)
                .field("basicToolContent", basicTool.questionnaireContent)
                .asString()

            if (resp.status == 200) {
                log.info("Update client success, token: ${token}")

                true
            } else {
                handleError(resp)
            }
        }
    }
}
