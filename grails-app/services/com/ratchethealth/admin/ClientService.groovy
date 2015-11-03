package com.ratchethealth.admin

import grails.converters.JSON

class ClientService extends RatchetAPIService {
    def grailsApplication

    def getClients(String token, queryOption) {
        log.info("Call backend service to get clients with offset and max, token: ${token}.")


        String clientsUrl = grailsApplication.config.ratchetv2.server.url.clients

        withGet(token, clientsUrl) { req ->
            def resp = req
                    .queryString("portalName", queryOption.portalNameSearch)
                    .queryString("offset", queryOption.offset)
                    .queryString("max", queryOption.max)
                    .asString()

            if (resp.status == 200) {
                def result = JSON.parse(resp.body)
                log.info("Get clients success, token: ${token}")

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

    def getClient(String token, clientId) {
        log.info("Call backend service to get client, token: ${token}.")

        String oneClientUrl = grailsApplication.config.ratchetv2.server.url.oneClient
        def clientUrl = String.format(oneClientUrl, clientId)

        withGet(token, clientUrl) { req ->
            def resp = req.asString()

            if (resp.status == 200) {
                log.info("Get client success, token: ${token}")

                JSON.parse(resp.body)
            } else {
                handleError(resp)
            }
        }
    }

    def createClient(String token, Client client) {
        log.info("Call backend service to create clients with name, logo, favIcon, subDomain, " +
                "portalName and primaryColorHex, token: ${token}.")

        String clientsUrl = grailsApplication.config.ratchetv2.server.url.clients

        withPost(token, clientsUrl) { req ->
            def resp = req
                    .field("name", client.name)
                    .field("logo", client.logo)
                    .field("logoFileName", client.logoFileName)
                    .field("favIcon", client.favIcon)
                    .field("favIconFileName", client.favIconFileName)
                    .field("subDomain", client.subDomain)
                    .field("portalName", client.portalName)
                    .field("primaryColorHex", client.primaryColorHex)
                    .field("isTesting", client.isTesting)
                    .asString()

            if (resp.status == 201) {
                def result = JSON.parse(resp.body)
                log.info("Create client success, token: ${token}")

                client.logo = null
                client.favIcon = null
                client.id = result.id

                client
            } else {
                handleError(resp)
            }
        }
    }

    def updateClient(String token, Client client) {
        log.info("Call backend service to update clients with name, subDomain, " +
                "protalName, logo and facIcon, token: ${token}.")

        String oneClientUrl = grailsApplication.config.ratchetv2.server.url.oneClient
        def clientUrl = String.format(oneClientUrl, client.id)

        withPost(token, clientUrl) { req ->
            def resp = req
                    .field("name", client.name)
                    .field("subDomain", client.subDomain)
                    .field("portalName", client.portalName)
                    .field("primaryColorHex", client.primaryColorHex)
                    .field("logo", client.logo)
                    .field("logoFileName", client.logoFileName)
                    .field("favIcon", client.favIcon)
                    .field("favIconFileName", client.favIconFileName)
                    .field("isTesting", client.isTesting)
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
