package com.ratchethealth.admin

class AuthenticationController extends BaseController {
    def MFAValidationRequired = false;

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

            if(resp.sessionId) {
                request.session.sessionId = resp.sessionId
            }

            if(resp.MFAValidationRequired){
                redirect(uri: '/login/two-factor-enabled')
            }else{
                //redirect(uri: '/')
                redirect(uri: '/login/two-factor')
            }

        }
    }

    def beforeTFA(){
        render view:'/security/beforeTFA'
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

        def otpCode = params.otp // input 6

        def resp = authenticationService.TFAuthenticate(token, sessionId, otpCode)

        if(resp.token){
            request.session.token = resp.token
        }

        if(resp.groups){
            request.session.groups = resp.groups
        }

        if(resp.id){
            request.session.accountId = resp.id
        }

        if(resp.authenticated == true){
            redirect(uri: '/')
        }
    }

    def goToApp(){
        String token = request.session.token
        String keyUrl = request.session.keyUrl

        def QRcode = authenticationService.getQRcode(token, keyUrl)

        if( !QRcode ){
            render view: '/security/app', model:[png: "Already Enable!!"]
        }else{
            render view: '/security/app', model:[png: QRcode]
        }
    }

    def goToKey(){
        String keycode = request.session.key
        if( !keycode ){
            render view: '/security/key', model: [key: "Already Enable!!"]
        }else {
            render view: '/security/key', model: [key: keycode]
        }

    }

    def enableTFA(){
        String token = request.session.token
        String id = request.session.accountId
        def otpCode = params.key

        def validate = authenticationService.MFAValidate(token, id, otpCode)

        request.session.MFAValidationRequired = true;

        if(validate.MFAValidationRequired){
            redirect(uri: '/')
        }
    }

    def disableTFA(){
        String token = request.session.token
        String id = request.session.accountId

        authenticationService.MFAuthenticationDisable(token, id)

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
