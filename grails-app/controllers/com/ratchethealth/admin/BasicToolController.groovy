package com.ratchethealth.admin

import grails.converters.JSON

class BasicToolController extends BaseController {
    def beforeInterceptor = [action: this.&auth]

    def basicToolService

    def index() {
        def page = params.page ?: RatchetConstants.DEFAULT_PAGE_OFFSET
        def pageSize = params.pagesize ?: RatchetConstants.DEFAULT_PAGE_SIZE
        def isAjax = request.isXhr() ?: false
        def queryOption, basicToolTemplates

        String token = request.session.token

        if (isAjax) {
            queryOption = [
                    offset: params?.start ?: RatchetConstants.DEFAULT_PAGE_OFFSET,
                    max: params?.length ?: RatchetConstants.DEFAULT_PAGE_SIZE
            ]

            basicToolTemplates = basicToolService.getBasicToolTemplates(token, queryOption)
            render basicToolTemplates as JSON

        } else {
            queryOption = [
                    offset: page,
                    max: pageSize
            ]

            basicToolTemplates = basicToolService.getBasicToolTemplates(token, queryOption)

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
