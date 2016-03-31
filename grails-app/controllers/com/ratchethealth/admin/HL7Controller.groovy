package com.ratchethealth.admin

class HL7Controller extends BaseController {

    def HL7Service

    def beforeInterceptor = [action: this.&auth]

    def index() {
        redirect action: 'getReportingPage'
    }

    def getReportingPage() {
        String token = request.session.token
        def queueStatus = HL7Service.getQueueStatus(token)
        def messageStatus = HL7Service.getMessageStatus(token)
        render view: '/HL7/reporting', model: [queueStatus: queueStatus, messageStatus: messageStatus]
    }

    def getFailuresPage() {
        String token = request.session.token
        def errorList = HL7Service.getFailureList(token)
        render view: '/HL7/failures', model: [errorList: errorList.items]
    }

    def retryFailure() {
        String token = request.session.token

        HL7Service.retryFailure(token, params?.errorJobId)

        render status: 200
    }
}
