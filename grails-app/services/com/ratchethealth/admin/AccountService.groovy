package com.ratchethealth.admin

import grails.converters.JSON


class AccountService extends RatchetAPIService {

    def grailsApplication

    def getAccounts(String token, offset, max) {
        log.info("Call backend service to get accounts with offset and max, token: ${token}.")

        String adminsUrl = grailsApplication.config.ratchetv2.server.url.admins

        withGet(token, adminsUrl) { req ->
            def resp = req
                    .queryString("max", max)
                    .queryString("offset", offset)
                    .asString()

            if (resp.status == 200) {
                def result = JSON.parse(resp.body)

                log.info("Get accounts success, token: ${token}")

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

    def createAccount(String token, Account account) {
        log.info("Call backend service to create account,token: ${token}.")

        String adminsUrl = grailsApplication.config.ratchetv2.server.url.admins

        withPost(token, adminsUrl) { req ->
            def resp = req
                    .field("email", account?.email)
                    .field("groups", account?.groups)
                    .asString()

            if (resp.status == 201) {
                def result = JSON.parse(resp.body)

                log.info("Create account success, token: ${token}")

                account.id = result.id

                account
            } else {
                handleError(resp)
            }
        }
    }

    def deleteAccount(String token, long accountId) {
        log.info("Call backend service to delete account,token: ${token}.")

        String adminUrl = grailsApplication.config.ratchetv2.server.url.oneAdmin
        def url = String.format(adminUrl, accountId)

        withDelete(token, url) { req ->
            def resp = req.asString()

            if (resp.status == 204) {
                log.info("Delete account success, token: ${token}")

                true
            } else {
                handleError(resp)
            }
        }
    }

    def updateAccount(String token, long accountId, email, enabled, groups) {
        log.info("Call backend service to update account,token: ${token}.")

        String adminUrl = grailsApplication.config.ratchetv2.server.url.oneAdmin
        def url = String.format(adminUrl, accountId)

        withPost(token, url) { req ->
            def resp = req
                    .field("email", email)
                    .field("enabled", enabled)
                    .field("groups", groups)
                    .asString()

            if (resp.status == 200) {
                log.info("Update account success, token: ${token}")

                JSON.parse(resp.body)
            } else {
                handleError(resp)
            }
        }
    }

    def activateAccount(String token, code, newPassword, confirmPassword) {
        log.info("Call backend service to activate account,token: ${token}.")

        String confirmAdminUrl = grailsApplication.config.ratchetv2.server.url.admin.confirm

        withPost(token, confirmAdminUrl) { req ->
            def resp = req
                    .field("code", code)
                    .field("password", newPassword)
                    .field("confirmPassword", confirmPassword)
                    .asString()

            if (resp.status == 200) {
                log.info("Activate account success, token: ${token}")

                true
            } else {
                handleError(resp)
            }
        }
    }

    def validateCode(String token, String code) {
        log.info("Call backend service to validate code,token: ${token}.")

        String validateCodeUrl = grailsApplication.config.ratchetv2.server.url.admin.validateCode
        def url = String.format(validateCodeUrl, code)

        withPost(token, url) { req ->
            def resp = req.asString()

            if (resp.status == 200) {
                log.info("Validate code success, token: ${token}")

                true
            } else {
                handleError(resp)
            }
        }
    }
}
