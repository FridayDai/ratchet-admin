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

            if (resp.token) {
                request.session.token = resp.token
            }

            if (resp.groups) {
                request.session.groups = resp.groups
            }

            if (resp?.authenticated) {
                redirect(uri: '/')
            }
        }
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
