package com.ratchethealth.admin

import grails.converters.JSON

class BasicToolController extends BaseController {
    def beforeInterceptor = [action: this.&auth]

    def basicToolService

    def index() {
        def page = params.page ?: RatchetConstants.DEFAULT_PAGE_OFFSET
        def pageSize = params.pagesize ?: RatchetConstants.DEFAULT_PAGE_SIZE
        def isAjax = request.isXhr() ?: false
        String token = request.session.token

        def queryOption = [
            offset: page,
            max: pageSize
        ]

        def basicToolTemplates = basicToolService.getBasicToolTemplates(token, queryOption)

        if (isAjax) {
            render basicToolTemplates as JSON
        } else {
            render view: '/basicTool/index', model: [basicToolTemplates: basicToolTemplates, pagesize: pageSize]
        }
    }

    def addBasicToolTemplate(BasicToolTemplate template) {
        String token = request.session.token

        template = basicToolService.createBasicToolTemplate(token, template)

        if (template.id) {
            render template as JSON
        }
    }

    def editBasicToolTemplate(BasicToolTemplate template) {
        String token = request.session.token

        def resp = basicToolService.updateBasicToolTemplate(token, params.basicToolId, template)

        if (resp) {
            render template as JSON
        }
    }
}
