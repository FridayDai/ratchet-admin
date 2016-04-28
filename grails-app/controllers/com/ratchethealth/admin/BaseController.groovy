package com.ratchethealth.admin

import com.ratchethealth.admin.exceptions.AccountValidationException
import com.ratchethealth.admin.exceptions.ApiAccessException
import com.ratchethealth.admin.exceptions.ServerException
import grails.converters.JSON

import javax.servlet.http.HttpServletResponse

/**
 * Base Controller.
 */
class BaseController {

    /**
     *  Verify Permissions. Quick check to see if the current user is logged in. If not, it will redirect to login.
     *
     * @return
     */
    protected auth() {
        if (!session.token) {
            def back

            if (request.isXhr()) {
                render status: 403
            } else {
                back = request.forwardURI

                redirect(uri: "/login", params: ["back": back])
            }

            return false
        }
    }

    protected renderErrorResponse(errorCode, errorMessage) {
        log.error("Error response : ${errorMessage}, token: ${session.token}")
        response.status = errorCode
        Map data = [
                errorId     : errorCode,
                errorMessage: errorMessage
        ]

        JSON json = new JSON([error: data])
        render(json)
    }

    def handleAccountValidationException(AccountValidationException e) {
        def time

        if (e.limitSeconds) {
            time = e.limitSeconds
        } else {
            time = null
        }
        log.error("Account validation exception : ${e.message}, stack trace: ${e.getStackTrace()}, token: ${session.token}")
        def msg = e.getMessage()
        render(view: '/security/login', model: [errorMsg: msg, rateLimit: time])

    }

    def handleServerException(ServerException e) {
        log.error("Server exception : ${e.message}, stack trace: ${e.getStackTrace()}, token: ${session.token}")
        if (request.isXhr()){
            renderErrorResponse(e.statusId, e.message)
        } else if (e.statusId == 403 || e.statusId == 401) {
            render view: '/security/login', status: e?.statusId
        } else {
            render view: '/error/404', status: e?.statusId
        }
        return false
    }

    def handleApiAccessException(ApiAccessException e) {
        log.error("API Access exception: ${e.message},stack trace: ${e.getCause() ?: e.getStackTrace()}, token: ${session.token}.")
        def status = 503

        if (request.isXhr()) {
            render status: status, text: e.message
        } else {
            render view: '/error/503', status: status
        }
    }

    def handleException(Exception e) {
        log.error("Exception: ${e.message}, stack trace: ${e.getStackTrace()}, token: ${session.token}.")
        if (request.isXhr()) {
            render status: 400, text: e.message
        } else {
            render view: '/error/400', status: 400
        }
    }
}
