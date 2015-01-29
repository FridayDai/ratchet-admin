package com.xplusz.ratchet

import com.mashape.unirest.http.Unirest
import com.xplusz.ratchet.exceptions.AccountValidationException
import grails.converters.JSON

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationService {

    // dependency injection for grailsApplication
    def grailsApplication

    def messageSource

    /**
     * Authenticate against backend. when login, authenticate will be needed. It used username and password to call
     * ratchet-v2-server restAPI login.
     *
     * @param request
     * @param response
     * @param cmd
     *
     * @return the authenticated status and errorMessage which restAPI returned.
     */

    def authenticate(HttpServletRequest request, HttpServletResponse response, params) throws AccountValidationException {

        def email = params.email
        def password = params.password

        /**
         * Call backend login api
         *
         * @param username
         * @param password
         * @param client_platform
         * @param client_type
         *
         * @requestMethod post
         *
         * @return
         */
        def url = grailsApplication.config.ratchetv2.server.url.login
        def resp = Unirest.post(url)
                .field("email", email)
                .field("password", password)
                .field("clientPlatform", grailsApplication.config.ratchetv2.server.client_platform)
                .field("clientType", grailsApplication.config.ratchetv2.server.client_type)
                .asString()
        def result = JSON.parse(resp.body)

        if (resp.status == 200) {
            request.session.token = result.token
            def data = [
                    authenticated: true,
            ]
            return data
        }

        if (resp.status == 403) {
            def rateLimit = result?.error?.errorMessage
            Integer[] args = [rateLimit]
            def errorMessage = messageSource.getMessage("security.errors.login.rateLimit", args, Locale.default)
            throw new AccountValidationException(errorMessage, rateLimit)


        } else {
            def errorMessage = result?.error?.errorMessage
            throw new AccountValidationException(errorMessage)

        }


    }

    /**
     * Logout user, Here is two step. Step one is call ratchet-v2-server restAPI logout and check returned status .
     * Step two ,session in local needs to be invalidate.
     *
     * @param request
     * @param response
     */
    def logout(request, response) {
        def session = request.session
        def token = session?.token
        /**
         * Call backend logout api
         *
         * @param token
         *
         * @requestMethod get
         */
        if (!token) {
            log.error("There is no token.")
            return false
        }

        def url = grailsApplication.config.ratchetv2.server.url.logout
        def resp = Unirest.get(url)
                .header("X-Auth-Token", "${token}")
                .asString()
        if (resp.status != 200) {
            log.warn("No user login in the session.")
            return false
        }

        log.info("Logout ${token}")
        session.invalidate()
        return true
    }
}