package com.ratchethealth.admin

import grails.converters.JSON

class TreatmentService extends RatchetAPIService {
    def grailsApplication

    def getTreatments(String token, clientId, offset, max) {
        log.info("Call backend service to get treatments with offset and max, token: ${token}.")

        String treatmentsUrl = grailsApplication.config.ratchetv2.server.url.treatments
        def url = String.format(treatmentsUrl, clientId)

        withGet(token, url) { req ->
            def resp = req
                    .queryString("offset", offset)
                    .queryString("max", max)
                    .queryString("isClientPortal", true)
                    .asString()

            if (resp.status == 200) {
                def result = JSON.parse(resp.body)
                log.info("Get treatments success, token: ${token}")

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

    def createTreatment(String token, Treatment treatment) {
        log.info("Call backend service to create treatment with title, tmpTitle, " +
                "description and surgeryTimeRequired, token: ${token}.")

        String treatmentsUrl = grailsApplication.config.ratchetv2.server.url.treatments
        def url = String.format(treatmentsUrl, treatment.clientId)

        withPost(token, url) { req ->
            def resp = req
                    .field("title", treatment.title)
                    .field("tmpTitle", treatment.tmpTitle)
                    .field("description", treatment.description)
                    .field("archiveTime", treatment.archiveTime)
                    .field("surgeryTimeRequired", treatment.surgeryTimeRequired)
                    .asString()

            if (resp.status == 201) {
                def result = JSON.parse(resp.body)
                log.info("Create treatment success, token: ${token}")

                treatment.id = result.id

                treatment
            } else {
                handleError(resp)
            }
        }
    }

    def getTreatment(String token, long clientId, long treatmentId) {
        log.info("Call backend service to get treatment, token: ${token}.")

        String oneTreatmentUrl = grailsApplication.config.ratchetv2.server.url.oneTreatment
        def url = String.format(oneTreatmentUrl, clientId, treatmentId)

        withGet(token, url) { req ->
            def resp = req.asString()

            if (resp.status == 200) {
                log.info("Get treatment success, token: ${token}")

                JSON.parse(resp.body)
            } else {
                handleError(resp)
            }
        }
    }

    def updateTreatment(String token, Treatment treatment) {
        log.info("Call backend service to update treatment with title, tmpTitle, description, " +
                "surgeryTimeRequired, token: ${token}.")

        String oneTreatmentUrl = grailsApplication.config.ratchetv2.server.url.oneTreatment
        def url = String.format(oneTreatmentUrl, treatment.clientId, treatment.id)

        withPost(token, url) { req ->
            def resp = req
                    .field("title", treatment.title)
                    .field("tmpTitle", treatment.tmpTitle)
                    .field("description", treatment.description)
                    .field("archiveTime", treatment.archiveTime)
                    .field("surgeryTimeRequired", treatment.surgeryTimeRequired)
                    .asString()

            if (resp.status == 200) {
                log.info("Update treatment success, token: ${token}")

                true
            } else {
                handleError(resp)
            }
        }
    }

    def closeTreatment(String token, long clientId, long treatmentId) {
        log.info("Call backend service to close treatment, token: ${token}.")

        String oneTreatmentUrl = grailsApplication.config.ratchetv2.server.url.oneTreatment
        def url = String.format(oneTreatmentUrl, clientId, treatmentId)

        withDelete(token, url) { req ->
            def resp = req.asString()

            if (resp.status == 204) {
                log.info("Close treatment success, token: ${token}")

                true
            } else {
                handleError(resp)
            }
        }
    }

    def getTools(String token, long treatmentId, int offset, int max) {
        log.info("Call backend service to get tools with offset and max, token: ${token}.")

        String toolsUrl = grailsApplication.config.ratchetv2.server.url.treatment.tools
        String url = String.format(toolsUrl, treatmentId)

        withGet(token, url) { req ->
            def resp = req
                    .queryString("offset", offset)
                    .queryString("max", max)
                    .asString()

            if (resp.status == 200) {
                log.info("Get tools success, token: ${token}")

                def result = JSON.parse(resp.body)

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

    def getToolsInTreatment(String token, long treatmentId) {
        log.info("Call backend service to get tools in treatment, token: ${token}.")

        String allToolsUrl = grailsApplication.config.ratchetv2.server.url.treatment.allToolsOfTreatment
        String url = String.format(allToolsUrl, treatmentId)

        withGet(token, url) { req ->
            def resp = req
                .queryString("offset", 0)
                .queryString("max", RatchetConstants.DEFAULT_MAX)
                .asString()

            if (resp.status == 200) {
                log.info("Get tools in treatment success, token: ${token}")

                JSON.parse(resp.body)
            } else {
                handleError(resp)
            }
        }
    }

    def getPredefinedTools(String token) {
        log.info("Call backend service to get predefined tools, token: ${token}.")

        String allPredefinedToolsUrl = grailsApplication.config.ratchetv2.server.url.treatment.allToolsOfPredefined

        withGet(token, allPredefinedToolsUrl) { req ->
            def resp = req.asString()

            if (resp.status == 200) {
                log.info("Get predefined tools success, token: ${token}")

                JSON.parse(resp.body)
            } else {
                handleError(resp)
            }
        }
    }

    def addTool(String token, Tool tool) {
        log.info("Call backend service to add tool with id, title, description, requireCompletion, " +
                "defaultDueTime, reminder, detailedDescription and type, token: ${token}.")

        String toolsUrl = grailsApplication.config.ratchetv2.server.url.treatment.tools
        def url = String.format(toolsUrl, tool.treatmentId)

        withPost(token, url) { req ->
            def resp = req
                    .field("id", tool.id)
                    .field("title", tool.title)
                    .field("description", tool.description)
                    .field("requireCompletion", tool.requireCompletion)
                    .field("defaultDueTime", tool.defaultDueTime)
                    .field("defaultExpireTime", tool.defaultExpireTime)
                    .field("reminder", tool.reminder)
                    .field("detailedDescription", tool.detailedDescription)
                    .field("type", tool.type)
                    .asString()

            if (resp.status == 201) {
                log.info("Add tool success, token: ${token}")

                JSON.parse(resp.body)
            } else {
                handleError(resp)
            }
        }
    }

    def updateTool(String token, Tool tool) {
        log.info("Call backend service to update tool with id, title, description, requireCompletion, " +
                "defaultDueTime, reminder, detailedDescription and type, token: ${token}.")

        String oneToolUrl = grailsApplication.config.ratchetv2.server.url.treatment.oneTool
        def url = String.format(oneToolUrl, tool.treatmentId, tool.id)

        withPost(token, url) { req ->
            def resp = req
                    .field("title", tool.title)
                    .field("description", tool.description)
                    .field("requireCompletion", tool.requireCompletion)
                    .field("defaultDueTime", tool.defaultDueTime)
                    .field("defaultExpireTime", tool.defaultExpireTime)
                    .field("reminder", tool.reminder)
                    .field("detailedDescription", tool.detailedDescription)
                    .field("type", tool.type)
                    .asString()

            if (resp.status == 200) {
                log.info("Update tool success, token: ${token}")

                JSON.parse(resp.body)
            } else {
                handleError(resp)
            }
        }
    }

    def deleteTool(String token, long treatmentId, long toolId) {
        log.info("Call backend service to delete tool, token: ${token}.")

        String oneToolUrl = grailsApplication.config.ratchetv2.server.url.treatment.oneTool
        def url = String.format(oneToolUrl, treatmentId, toolId)

        withDelete(token, url) { req ->
            def resp = req.asString()

            if (resp.status == 204) {
                log.info("Delete tool success, token: ${token}")

                true
            } else {
                handleError(resp)
            }
        }
    }

    def getTasks(String token, long treatmentId, int offset, int max) {
        log.info("Call backend service to get tasks with offset and max, token: ${token}.")

        String tasksUrl = grailsApplication.config.ratchetv2.server.url.treatment.tasks
        String url = String.format(tasksUrl, treatmentId)

        withGet(token, url) { req ->
            def resp = req
                    .queryString("offset", offset)
                    .queryString("max", max)
                    .asString()

            if (resp.status == 200) {
                log.info("Get tasks success, token: ${token}")

                def result = JSON.parse(resp.body)

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

    def addTask(String token, Task task) {
        log.info("Call backend service to add task with toolId, " +
                "sendTimeOffset and immediate, token: ${token}.")

        String tasksUrl = grailsApplication.config.ratchetv2.server.url.treatment.tasks
        def url = String.format(tasksUrl, task.treatmentId)

        withPost(token, url) { req ->
            def resp = req
                    .field("toolId", task.toolId)
                    .field("sendTimeOffset", task.sendTimeOffset)
                    .field("immediate", task.immediate)
                    .asString()

            if (resp.status == 201) {
                log.info("Add task success, token: ${token}")

                JSON.parse(resp.body)
            } else {
                handleError(resp)
            }
        }
    }

    def updateTask(String token, Task task) {
        log.info("Call backend service to update task with toolId, " +
                "sendTimeOffset and immediate, token: ${token}.")

        String oneTaskUrl = grailsApplication.config.ratchetv2.server.url.treatment.oneTask
        def url = String.format(oneTaskUrl, task.treatmentId, task.id)

        withPost(token, url) { req ->
            def resp = req
                    .field("toolId", task.toolId)
                    .field("sendTimeOffset", task.sendTimeOffset)
                    .field("immediate", task.immediate)
                    .asString()

            if (resp.status == 200) {
                log.info("Update task success, token: ${token}")

                JSON.parse(resp.body)
            } else {
                handleError(resp)
            }
        }
    }

    def deleteTask(String token, long treatmentId, long taskId) {
        log.info("Call backend service to delete task, token: ${token}.")

        String oneTaskUrl = grailsApplication.config.ratchetv2.server.url.treatment.oneTask
        def url = String.format(oneTaskUrl, treatmentId, taskId)

        withDelete(token, url) { req ->
            def resp = req.asString()

            if (resp.status == 204) {
                log.info("Delete task success, token: ${token}")

                true
            } else {
                handleError(resp)
            }
        }
    }
}
