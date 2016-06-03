package com.ratchethealth.admin

import com.ratchethealth.admin.exceptions.AccountValidationException
import grails.converters.JSON


class AuthenticationService extends RatchetAPIService {
    def grailsApplication

    def messageSource

    def authenticate(String token, email, password)
            throws AccountValidationException {

        log.info("Call backend service to login with email, password, clientPlatform and clientType.")

        def url = grailsApplication.config.ratchetv2.server.url.login

        withPost(url) { req ->
            def resp = req
                    .field("email", email)
                    .field("password", password)
                    .field("clientPlatform", RatchetConstants.CLIENT_PLATFORM)
                    .field("clientType", RatchetConstants.CLIENT_TYPE)
                    .asString()

            def result = null
            if (resp?.body) {
                try {
                    result = JSON.parse(resp.body)
                } catch (Exception e) {
                    log.error("JSON parse failed" + e)
                    throw new AccountValidationException('');
                }
            }

            if (resp.status == 200) {
                log.info("login Authenticate success, token: ${token}")
                def groupList = result?.groups ?  result.groups.split(',') : []

                return [
                        token                : result?.token,
                        id                   : result?.id,
                        groups               : groupList,
                        account              : result?.account,
                        sessionId            : result?.sessionId,
                        MFAValidationRequired: result?.MFAValidationRequired,
                        authenticated        : true
                ]
            } else if (resp.status == 401 && result?.error?.errorID == 403) {
                log.info("login Authenticate forbidden")

                def rateLimit = result?.error?.errorMessage?.toString() ?: '10'

                String[] args = [rateLimit]
                def errorMessage = messageSource.getMessage("security.errors.login.rateLimit", args, Locale.ENGLISH)

                throw new AccountValidationException(errorMessage, rateLimit)
            } else {
                def errorMessage = result?.error?.errorMessage ?: resp.body
                throw new AccountValidationException(errorMessage)
            }
        }

    }

    def TFAuthenticate(String token, id, otpCode) throws AccountValidationException {
        log.info("doing Two-Factor Authentication")

        def url = grailsApplication.config.ratchetv2.server.url.login

        withPost( url ) { req ->
            def resp = req
                    .field("sessionId", id)
                    .field("otpCode", otpCode)
                    .field("clientPlatform", RatchetConstants.CLIENT_PLATFORM)
                    .field("clientType", RatchetConstants.CLIENT_TYPE)
                    .asString()

            def result = null
            if (resp?.body) {
                try {
                    result = JSON.parse(resp.body)
                } catch (Exception e) {
                    log.error("JSON parse failed" + e)
                    throw new AccountValidationException('');
                }
            }

            if (resp.status == 200) {
                log.info("login TFAuthenticate success, token: ${token}")
                def groupList = result?.groups ? result.groups.split(',') : []

                return [
                        id                   : result?.id,
                        token                : result?.token,
                        groups               : groupList
                ]
            } else if (resp.status == 417) {
                log.info(" invalid ")
            }
        }
    }

    def MFARecoveryCodes(String token, id) throws AccountValidationException{
        if (!token) {
            log.error("There is no token.")
            return false
        }
        String MFARecoveryCodesUrl = grailsApplication.config.ratchetv2.server.url.MFA + "/${id}/mfa/codes"

        withGet(token, MFARecoveryCodesUrl) { req ->
            def resp = req.asString()

            if(resp.status == 200){
                def result = JSON.parse(resp.body)

                return [
                        totalCount: result?.totalCount,
                        codes: result?.codes
                ]
            }else if ( resp.status == 400 || resp.status == 404){
                log.info("Can't get Recovery Codes");
            }

        }

    }


    def MFAuthenticationDisable(String token, id) throws AccountValidationException{
        if (!token) {
            log.error("There is no token.")
            return false
        }

        String MFAdisableUrl = grailsApplication.config.ratchetv2.server.url.MFA +"/${id}/mfa/disable"

        withDelete(token, MFAdisableUrl) { req ->
            def resp = req.asString()

            if(resp.status == 204 ){
                log.info("MFA is disabled");
                return 204;
            }else if ( resp.status == 400 || resp.status == 404){
                log.info("MFA Disable error");
            }
        }
    }

    def getQRcode(String token, url) throws AccountValidationException{

        String MFAEnableUrl = grailsApplication.config.ratchetv2.server.url.MFAText + url

        withGet(token, MFAEnableUrl) { req ->
            def resp = req.asString()
            def data = resp.body
            if( resp.status == 200 ){

                int start = data.indexOf(",");
                def img = data.substring(start + 1);

                return  [data: img, status: 200];

            }else if( resp.status == 404 || resp.status == 400 ){
                log.info("get QRcode error");
            }


        }

    }

    def MFAuthenticationEnable(String token, id)
            throws AccountValidationException{

        log.info("Call back-end service to login with Multi-Factor authentication")

        String MFAEnableUrl = grailsApplication.config.ratchetv2.server.url.MFA + "/${id}/mfa/enable"

        withPost(token, MFAEnableUrl) { req ->
            def resp = req.asString()

            def result = null;

            if(resp?.body){
                try {
                    result = JSON.parse(resp.body)
                } catch (Exception e) {
                    log.error("JSON parse failed" + e)
                    throw new AccountValidationException('');
                }
            }

            if( resp.status == 200 ){
                log.info("Enable MFA for admin account")
                return [
                        QRBarcodeURL: result?.QRBarcodeURL,
                        key: result?.key
                ];
            }else if( resp.status == 404 || resp.status == 400 ){
                log.info("MFA enable error");
            }
        }

    }

    def MFAValidate(String token, id, otpCode) throws AccountValidationException{

        String validateUrl = grailsApplication.config.ratchetv2.server.url.MFA+ "/${id}/mfa/validate"

        withPost(token, validateUrl) { req ->
            def resp = req
                    .field("code", otpCode)
                    .asString()

            def result = null;

            if (resp?.body) {
                try {
                    result = JSON.parse(resp.body)
                } catch (Exception e) {
                    log.error("JSON parse failed" + e)
                    throw new AccountValidationException('');
                }
            }

            if(resp.status == 200){
                log.info("Validate the OTP code generated from MFA devices")

                return [
                        totalCount: result?.totalCount,
                        codes: result?.codes,
                        MFAValidationRequired: true
                ]
            }else if(resp.status == 404 || resp.status == 400){
                log.info("validate failed")
            }
        }

    }


    def logout(String token) {
        if (!token) {
            log.error("There is no token.")
            return false
        }

        log.info("Call backend service to logout, token: ${token}.")

        String url = grailsApplication.config.ratchetv2.server.url.logout

        withPost(token, url) { req ->
            def resp = req.asString()

            if (resp.status == 200) {
                log.info("Logout success, token: ${token}")

                true
            } else {
                handleError(resp)
            }
        }
    }

    def askForResetPassword(String token, email, clientType) {
        log.info("Call backend service to ask for reset password with email and client type, token: ${token}.")

        String url = grailsApplication.config.ratchetv2.server.url.password.reset

        withPost(url) { req ->
            def resp = req
                    .field("email", email)
                    .field("clientType", clientType)
                    .asString()

            if (resp.status == 200) {
                log.info("Ask for reset password success, token: ${token}.")
            }

            true
        }
    }

    def validPasswordCode(String token, code) {
        log.info("Call backend service to valid password code, token: ${token}.")

        String url = grailsApplication.config.ratchetv2.server.url.password.restCheck

        withGet(url) { req ->
            def resp = req
                    .queryString("code", code)
                    .asString()

            if (resp.status == 200) {
                log.info("Valid password code success, token: ${token}.")

                true
            } else if(resp.status == 412) {
                return false
            }else {
                handleError(resp)
            }

        }
    }

    def resetPassword(String token, code, newPassword, confirmPassword) {
        log.info("Call backend service to reset password with code and password, token: ${token}.")

        String url = grailsApplication.config.ratchetv2.server.url.password.confirm

        withPost(url) { req ->
            def resp = req
                    .field("code", code)
                    .field("password", newPassword)
                    .field("confirmPassword", confirmPassword)
                    .asString()

            if (resp.status == 200) {
                log.info("Reset password success, token: ${token}.")

                true
            } else {
                handleError(resp)
            }
        }
    }

    def updatePassword(String token, oldPassword, newPassword, confirmPassword) {
        log.info("Call backend service to update password with old and new password, token: ${token}.")

        String url = grailsApplication.config.ratchetv2.server.url.updatePassword

        withPost(token, url) { req ->
            def resp = req
                    .field("oldPassword", oldPassword)
                    .field("password", newPassword)
                    .field("confirmPassword", confirmPassword)
                    .asString()

            if (resp.status == 200) {
                log.info("Update password success, token: ${token}.")

                true
            } else {
                handleError(resp)
            }
        }
    }
}
