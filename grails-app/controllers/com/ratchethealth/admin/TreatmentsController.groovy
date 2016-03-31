package com.ratchethealth.admin

import grails.converters.JSON

class TreatmentsController extends BaseController {

    def beforeInterceptor = [action: this.&auth]

    def treatmentService

    def getTreatments() {
        String token = request.session.token
        def offset = params?.start
        def max = params?.length
        long clientId = params.clientId as long

        def resp = treatmentService.getTreatments(token, clientId, offset, max)

        render resp as JSON
    }

    def addTreatment(Treatment treatment) {
        String token = request.session.token
        treatment.clientId = params.clientId as long

        if (treatment.archiveDay || treatment.archiveWeek) {
            treatment.archiveTime = (treatment.archiveWeek * 7 + treatment.archiveDay) * 24 * 3600000
        }

        treatment = treatmentService.createTreatment(token, treatment)

        if (treatment.id) {

            treatment.lastUpdated = System.currentTimeMillis()

            render treatment as JSON
        }
    }

    def treatmentDetail() {
        String token = request.session.token
        long clientId = params.clientId as long
        long treatmentId = params.treatmentId as long

        def treatment = treatmentService.getTreatment(token, clientId, treatmentId)
        def tools = treatmentService.getToolsInTreatment(token, treatmentId)
        def predefinedTools = treatmentService.getPredefinedTools(token)
        def outcomeTools = [], voiceTools = []

        for (tool in predefinedTools) {
            //4.voice, 2:outcome
            if (tool.type == 4) {
                voiceTools.add(tool)
            } else {
                outcomeTools.add(tool)
            }
        }
        render view: '/treatment/treatmentDetail', model: [clientId    : clientId,
                                                           treatment   : treatment,
                                                           tools       : tools,
                                                           voiceTools  : voiceTools,
                                                           outcomeTools: outcomeTools]
    }

    def editTreatment(Treatment treatment) {
        String token = request.session.token
        treatment.clientId = params.clientId as long
        treatment.id = params.treatmentId as long

        if (treatment.archiveDay || treatment.archiveWeek) {
            treatment.archiveTime = (treatment.archiveWeek * 7 + treatment.archiveDay) * 24 * 3600000
        }

        def success = treatmentService.updateTreatment(token, treatment)

        if (success) {
            render status: 200
        }
    }

    def closeTreatment() {
        String token = request.session.token
        long clientId = params.clientId as long
        long treatmentId = params.treatmentId as long

        def success = treatmentService.closeTreatment(token, clientId, treatmentId)

        if (success) {
            render status: 204
        }
    }

    def getTools() {
        String token = request.session.token
        def page = params.page ?: RatchetConstants.DEFAULT_PAGE_OFFSET
        def pageSize = params.length ?: RatchetConstants.DEFAULT_PAGE_SIZE

        long treatmentId = params.treatmentId as long

        def toolList = treatmentService.getTools(token, treatmentId, page as int, pageSize as int)

        if (toolList) {
            render toolList as JSON
        }
    }

    def getTasks() {
        String token = request.session.token
        def page = params.page ?: RatchetConstants.DEFAULT_PAGE_OFFSET
        def pageSize = params.length ?: RatchetConstants.DEFAULT_PAGE_SIZE

        long treatmentId = params.treatmentId as long

        def taskList = treatmentService.getTasks(token, treatmentId, page as int, pageSize as int)

        if (taskList) {
            render taskList as JSON
        }
    }

    def addTool(Tool tool) {
        String token = request.session.token
        tool.treatmentId = params.treatmentId as long
        tool.defaultDueTime = (tool.defaultDueTimeHour + tool.defaultDueTimeDay * 24) * 3600000
        tool.defaultExpireTime = (tool.defaultExpireTimeHour + tool.defaultExpireTimeDay * 24) * 3600000

        def result = treatmentService.addTool(token, tool)

        if (result) {
            render result as JSON
        }
    }

    def editTool(Tool tool) {
        String token = request.session.token
        tool.treatmentId = params.treatmentId as long
        tool.id = params.toolId as long
        tool.defaultDueTime = (tool.defaultDueTimeHour + tool.defaultDueTimeDay * 24) * 3600000
        tool.defaultExpireTime = (tool.defaultExpireTimeHour + tool.defaultExpireTimeDay * 24) * 3600000

        def result = treatmentService.updateTool(token, tool)

        if (result) {
            render result as JSON
        }
    }

    def deleteTool() {
        String token = request.session.token
        long treatmentId = params.treatmentId as long
        long toolId = params.toolId as long

        def success = treatmentService.deleteTool(token, treatmentId, toolId)

        if (success) {
            render status: 204
        }
    }

    def addTask(Task task) {
        String token = request.session.token
        task.treatmentId = params.treatmentId as long

        task.sendTimeOffset = task.sendTimeDirection *
                (task.sendTimeWeeks * 7 * 24 * 60 * 60
                        + task.sendTimeDays * 24 * 60 * 60
                        + task.sendTimeHours * 60 * 60
                        + task.sendTimeMinutes * 60) * 1000

        task.immediate = task.sendTimeDirection == 0

        def result = treatmentService.addTask(token, task)

        if (result) {
            render result as JSON
        }
    }

    def editTask(Task task) {
        String token = request.session.token
        task.treatmentId = params.treatmentId as long
        task.id = params.taskId as long

        task.sendTimeOffset = task.sendTimeDirection *
                (task.sendTimeWeeks * 7 * 24 * 60 * 60
                        + task.sendTimeDays * 24 * 60 * 60
                        + task.sendTimeHours * 60 * 60
                        + task.sendTimeMinutes * 60) * 1000

        task.immediate = task.sendTimeDirection == 0

        def result = treatmentService.updateTask(token, task)

        if (result) {
            render result as JSON
        }
    }

    def deleteTask() {
        String token = request.session.token
        long treatmentId = params.treatmentId as long
        long taskId = params.taskId as long

        def success = treatmentService.deleteTask(token, treatmentId, taskId)

        if (success) {
            render status: 204
        }
    }
}
