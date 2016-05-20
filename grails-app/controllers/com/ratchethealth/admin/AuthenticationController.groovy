package com.ratchethealth.admin

class AuthenticationController extends BaseController {

    static allowedMethods = [
            login: ['POST', 'GET'],
            logout: ['GET'],
            goToForgetPasswordPage: ['GET'],
            forgotPassword: ['POST'],
            resetPassword: ['GET', 'POST'],
            confirmResetPassword: ['GET', 'POST']
    ]

    def beforeInterceptor = [action: this.&auth, except: [
            'login',
            'beforeTFAVerify',
            'beforeTFA',
            'twoFactorAuthenticationVerify',
            'goToForgetPasswordPage',
            'forgotPassword',
            'resetPassword',
            'confirmResetPassword'
    ]]

    def authenticationService

    def login() {
        if (request.method == "GET") {
            render(view: '/security/login')
        } else if (request.method == "POST") {
            String token = request.session.token
            def email = params.email
            def password = params.password

            def resp = authenticationService.authenticate(token, email, password)

            if (resp.MFAValidationRequired) {
                request.session.MFAValidationRequired = true;

                if (resp.sessionId) {
                    request.session.sessionId = resp.sessionId
                }

                redirect( uri: '/login/two-factor-enabled')
            }else {

                if(resp.token) {
                    request.session.token = resp.token
                }

                if(resp.id) {
                    request.session.accountId = resp.id
                }

                if(resp.groups) {
                    request.session.groups = resp.groups
                }
                redirect(uri: '/')
            }
        }
    }


    def twoFactorAuthentication(){
        String token = request.session.token
        String id = request.session.accountId

        def enable = authenticationService.MFAuthenticationEnable(token, id)

        request.session.key = enable?.key;
        request.session.keyUrl = enable?.QRBarcodeURL;

        render view: '/security/beforeTFA'

    }

    def beforeTFAVerify(){
        render view:'/security/twoFactor'
    }

    def twoFactorAuthenticationVerify(){
        String token = request.session.token
        String sessionId = request.session.sessionId
        request.session.authen = true;

        def otpCode = params.otp

        def resp = authenticationService.TFAuthenticate(token, sessionId, otpCode)

        if(resp == null){
            request.session.authen = false;
            render view: '/security/twoFactor'
        }else {
            if(resp.token){
                request.session.token = resp.token
            }

            if(resp.groups){
                request.session.groups = resp.groups
            }

            if(resp.id){
                request.session.accountId = resp.id
            }
            redirect(uri: '/')
        }

    }

    def goToApp(){
//        String token = request.session.token
        String keyUrl = request.session.keyUrl

        request.session.way = 1;

        //def QRcode = authenticationService.getQRcode(token, keyUrl)

        render view: '/security/App', model:[QRcode: keyUrl]
    }

    def goToKey(){
        String keycode = request.session.key

        request.session.way = 2;

        render view: '/security/key', model: [key: keycode]
    }

    def enableTFA(){
        String token = request.session.token
        String id = request.session.accountId
        String keyUrl = request.session.keyUrl
        String keycode = request.session.key

        def otpCode = params.otp

        def validate = authenticationService.MFAValidate(token, id, otpCode)

        if(validate){
            request.session.MFAValidationRequired = true;
            render view: '/profile/Info', model:[ info: "Enable Two Factor Authentication successful"]
        }else {
            request.session.MFAValidationRequired = false;
            if(request.session.way == 1){
                render view: '/security/App', model:[ errorMsg: "Wrong Code, Can't enable Two Factor Authentication.", QRcode: keyUrl]

            }else if(request.session.way == 2){
                render view: '/security/key', model:[ errorMsg: "Wrong Code, Can't enable Two Factor Authentication.", key: keycode]

            }
        }
    }

    def disableTFA(){
        String token = request.session.token
        String id = request.session.accountId
        //String id = 287975
        def validate = authenticationService.MFAuthenticationDisable(token, id)

        if(validate == 204){
            request.session.MFAValidationRequired = false;
            request.session.recoveryCodes = false;
            request.session.way = 0


            render view: '/profile/Info', model:[ info: "Disable Two-Factor Authentication successful"]
        }else{
            render view: '/profile/Info', model:[ info: "Already Disabled Two-Factor Authentication"]
        }
    }

    def getRecoveryCodes(){
        String token = request.session.token
        String id = request.session.accountId

        def recoveryCodes = authenticationService.MFARecoveryCodes(token, id)

        def code = null

        if(recoveryCodes == null ){
            code = "Can't get Recovery Codes"
        }else {
            request.session.recoveryCodes = true;

            code = recoveryCodes.codes.toString();
            code = code.substring(1, code.length()-1);
            code = code.split(',');
            if( recoveryCodes.totalCount == 5){
                def firstPart = code[0..2]
                def secondPart = code[2..3]
                request.session.CODE1 = firstPart;
                request.session.CODE2 = secondPart;
            }else if(recoveryCodes.totalCount == 10){
                def firstPart = code[0..4]
                def secondPart = code[5..9]
                request.session.CODE1 = firstPart;
                request.session.CODE2 = secondPart;
            }else{
                request.session.CODE1 = code;
            }
        }
        render view: '/profile/Info', model: [info: code]
    }


    def logout() {
        def session = request.session
        String token = session.token

        def result = authenticationService.logout(token)
        if (result) {
            session.invalidate()

            redirect(uri: '/login')
        }
    }

    def goToForgetPasswordPage() {
        render view: '/forgotPassword/forgotPassword'
    }

    def forgotPassword() {
        String token = request.session.token
        def email = params.email

        authenticationService.askForResetPassword(token, email, RatchetConstants.CLIENT_TYPE)

        render view: '/forgotPassword/resettingIntroduction', model: [email: email]
    }

    def resetPassword() {
        String token = request.session.token
        def code = params?.code

        def resp = authenticationService.validPasswordCode(token, code)
        if (resp) {
            render view: '/forgotPassword/resetPassword', model: [code: code]
        }else {
            log.info("password has been reset")

            render view: '/forgotPassword/alreadyResetPassword'
        }
    }

    def confirmResetPassword() {
        String token = request.session.token
        def code = params.code
        def newPassword = params.newPassword
        def confirmPassword = params.confirmPassword

        def resp = authenticationService.resetPassword(token, code, newPassword, confirmPassword)

        if (resp) {
            redirect(uri: '/login')
        }
    }
}
