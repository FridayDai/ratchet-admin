package com.xplusz.ratchet

import com.mashape.unirest.http.Unirest
import com.xplusz.ratchet.exceptions.ServerException
import grails.converters.JSON

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ClientService {
    // dependency injection for grailsApplication
    def grailsApplication

    /**
     * Get client list
     *
     * @param offset # page index from 0
     * @param max # page size
     * @return client list
     */
    def getClients(HttpServletRequest request, HttpServletResponse response, offset, max)
            throws ServerException {
        String clientsUrl = grailsApplication.config.ratchetv2.server.url.clients

        def resp = Unirest.get(clientsUrl)
                .queryString("offset", offset)
                .queryString("max", max)
                .asString()

        def result = JSON.parse(resp.body)

        if (resp.status == 200) {
            def map = [:]
            map.put("recordsTotal", result.totalCount)
            map.put("recordsFiltered", result.totalCount)
            map.put("data", result.items)
            log.info("Get clients success, token: ${request.session.token}")
            return map
        } else {
            String errorMessage = result?.errors?.message ?: result?.error?.errorMessage
            throw new ServerException(errorMessage)
        }
    }

    /**
     * Get one client by id
     *
     * @param clientId
     * @return client
     */
    def getClient(HttpServletRequest request, HttpServletResponse response, int clientId) throws ServerException {
        String oneClientUrl = grailsApplication.config.ratchetv2.server.url.oneClient

        def clientUrl = String.format(oneClientUrl, clientId)

        def resp = Unirest.get(clientUrl).asString()

        def result = JSON.parse(resp.body)

        if (resp.status == 200) {
            log.info("Get client success, token: ${request.session.token}")
            return result
        } else {
            String errorMessage = result?.errors?.message ?: result?.error?.errorMessage
            throw new ServerException(errorMessage)
        }
    }

    /**
     * Create new client
     *
     * @param client # new client instance
     * @return client   # created client
     */
    def createClient(HttpServletRequest request, HttpServletResponse response, Client client) throws ServerException {
        String clientsUrl = grailsApplication.config.ratchetv2.server.url.clients

        def resp = Unirest.post(clientsUrl)
                .field("name", client.name)
                .field("logo", client.logo)
                .field("favIcon", client.favIcon)
                .field("subDomain", client.subDomain)
                .field("portalName", client.portalName)
                .field("primaryColorHex", client.primaryColorHex)
                .asString()

        def result = JSON.parse(resp.body)

        if (resp.status == 201) {
            client.logo = null
            client.favIcon = null
            client.id = result.id
            log.info("Create client success, token: ${request.session.token}")
            return client
        } else {
            String errorMessage = result?.errors?.message ?: result?.error?.errorMessage
            throw new ServerException(errorMessage)
        }
    }

    /**
     * Update client
     *
     * @param client # updated client instance
     * @return isSuccess
     */
    def updateClient(HttpServletRequest request, HttpServletResponse response, Client client) throws ServerException {
        String oneClientUrl = grailsApplication.config.ratchetv2.server.url.oneClient

        def clientUrl = String.format(oneClientUrl, client.id)

        def resp = Unirest.post(clientUrl)
                .field("name", client.name)
                .field("subDomain", client.subDomain)
                .field("portalName", client.portalName)
                .field("primaryColorHex", client.primaryColorHex)
                .field("logo", client.logo)
                .field("favIcon", client.favIcon)
                .asString()

        if (resp.status == 200) {
            log.info("Update client success, token: ${request.session.token}")
            return true
        } else {
            def result = JSON.parse(resp.body)

            String errorMessage = result?.errors?.message ?: result?.error?.errorMessage
            throw new ServerException(errorMessage)
        }
    }
}
