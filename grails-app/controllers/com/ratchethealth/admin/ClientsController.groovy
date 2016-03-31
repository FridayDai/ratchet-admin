package com.ratchethealth.admin

import grails.converters.JSON

class ClientsController extends BaseController {
    def beforeInterceptor = [action: this.&auth]

    def clientService
    def staffService
    def treatmentService

    def index() {
        def page = params.page ?: RatchetConstants.DEFAULT_PAGE_OFFSET
        def pageSize = params.pagesize ?: RatchetConstants.DEFAULT_PAGE_SIZE
        def isAjax = params.ajax ?: false
        String token = request.session.token

        def queryOption = [
                offset: page,
                max: pageSize
        ]

        def clientList = clientService.getClients(token, queryOption)

        if (isAjax) {
            render clientList as JSON
        } else {
            render view: '/client/clients', model: [clientList: clientList, pagesize: pageSize]
        }
    }

    def addClient(Client client) {
        String token = request.session.token

        // Client information
        def logoFile = params.logo
        def favIconFile = params.favIcon

        // Transfer logo file to Base64 string
        client.logo = Base64.encoder.encodeToString(logoFile?.getBytes()).encodeAsURL()
        client.favIcon = Base64.encoder.encodeToString(favIconFile?.getBytes()).encodeAsURL()
        client.logoFileName = logoFile.fileItem.fileName
        client.favIconFileName = favIconFile.fileItem.fileName

        client = clientService.createClient(token, client)

        if (client.id) {
            render client as JSON
        }
    }

    def getClients() {
        String token = request.session.token

        def queryOption = [
                offset: params?.start ?: RatchetConstants.DEFAULT_PAGE_OFFSET,
                max: params?.length ?: RatchetConstants.DEFAULT_PAGE_SIZE,
                portalNameSearch: params?.portalNameS
        ]

        def resp = clientService.getClients(token, queryOption)

        render resp as JSON
    }

    def clientDetail() {
        def page = params.page ?: RatchetConstants.DEFAULT_PAGE_OFFSET
        def pageSize = params.pagesize ?: RatchetConstants.DEFAULT_PAGE_SIZE

        long clientId = params?.id as long

        String token = request.session.token

        def client = clientService.getClient(token, clientId)
        def treatmentList = treatmentService.getTreatments(token, client.id, page, pageSize)

        render view: '/client/clientDetail',
                model: [
                        client: client,
                        treatmentList: treatmentList,
                        pagesize: pageSize
                ]
    }

    def editClient(Client client) {
        // Client information
        def logoFile = params.logo
        def favIconFile = params.favIcon
        String token = request.session.token

        // Transfer logo file to Base64 string
        client.logo = Base64.encoder.encodeToString(logoFile?.getBytes()).encodeAsURL()
        client.favIcon = Base64.encoder.encodeToString(favIconFile?.getBytes()).encodeAsURL()
        client.logoFileName = logoFile.fileItem.fileName
        client.favIconFileName = favIconFile.fileItem.fileName

        client.id = params.id as long

        def success = clientService.updateClient(token, client)

        if (success) {
            render client as JSON
        }
    }

    def editAgent(Staff agent) {
        agent.clientId = params.clientId as long
        agent.id = params.agentId as long
        String token = request.session.token

        def success = staffService.updateAgent(token, agent)

        if (success) {
            render agent as JSON
        }
    }

    def addAgent(Staff agent) {
        String token = request.session.token
        agent.clientId = params.clientId as long

        agent = staffService.addAgent(token, agent)

        if (agent.id) {
            render agent as JSON
        }
    }

    def deleteAgent() {
        String token = request.session.token
        long agentId = params.agentId as long

        def success = staffService.deleteAgent(token, agentId)

        if (success) {
            render status: 204
        }
    }

    def addIP(ClientIP ip) {
        String token = request.session.token
        def clientId = params.clientId as long

        def success = clientService.addIP(token, clientId, ip)

        if (success) {
            render ip as JSON
        }
    }

    def editIP(ClientIP ip) {
        String token = request.session.token
        def clientId = params.clientId as long
        ip.id = params.ipId as long

        def success = clientService.updateIP(token, clientId, ip)

        if (success) {
            render ip as JSON
        }
    }

    def deleteIP(ClientIP ip) {
        String token = request.session.token
        def clientId = params.clientId as long
        ip.id = params.ipId as long

        def success = clientService.deleteIP(token, clientId, ip)

        if (success) {
            render ip as JSON
        }
    }
}
