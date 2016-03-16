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
                    .field("sessionTimeout", client.sessionTimeout)
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
                    .field("sessionTimeout", client.sessionTimeout)
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

    def addIP(String token, clientId, ip) {
        log.info("Call backend service to add ip limit for client, token: ${token}.")

        String ipsUrl = grailsApplication.config.ratchetv2.server.url.ips
        def url = String.format(ipsUrl, clientId)

        withPost(token, url) { req ->
            def resp = req
                .field("ip", ip.ip)
                .field("name", ip.name)
                .field("description", ip.description)
                .asString()

            if (resp.status == 201) {
                def result = JSON.parse(resp.body)
                log.info("Create limit ip fir client success, token: ${token}")

                ip.id = result.id

                ip
            } else {
                handleError(resp)
            }
        }
    }

    def updateIP(String token, clientId, ip) {
        log.info("Call backend service to update ip limit for client, token: ${token}.")

        String oneIPUrl = grailsApplication.config.ratchetv2.server.url.oneIP
        def url = String.format(oneIPUrl, clientId, ip.id)

        withPost(token, url) { req ->
            def resp = req
                .field("ip", ip.ip)
                .field("name", ip.name)
                .field("description", ip.description)
                .asString()

            if (resp.status == 200) {
                log.info("Update limit ip fir client success, token: ${token}")

                true
            } else {
                handleError(resp)
            }
        }
    }

    def deleteIP(String token, clientId, ip) {
        log.info("Call backend service to delete ip limit for client, token: ${token}.")

        String oneIPUrl = grailsApplication.config.ratchetv2.server.url.oneIP
        def url = String.format(oneIPUrl, clientId, ip.id)

        withDelete(token, url) { req ->
            def resp = req.asString()

            if (resp.status == 204) {
                log.info("Delete ip limit for client success, token: ${token}")

                true
            } else {
                handleError(resp)
            }
        }
    }
}
