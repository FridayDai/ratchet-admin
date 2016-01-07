package com.ratchethealth.admin

class HomeController extends BaseController {

    def beforeInterceptor = [action: this.&auth]

    def index() {
        def controllerName
        def actionName
        if (request.session.groups.contains(com.ratchethealth.admin.RatchetConstants.ROLE_MANAGER)) {
            controllerName = 'clients'
            actionName = 'index'
        } else if (request.session.groups.contains(com.ratchethealth.admin.RatchetConstants.ROLE_ADMIN)) {
            controllerName = 'accounts'
            actionName = 'index'
        } else  {
            controllerName = 'profile'
            actionName = 'goToProfilePage'
        }

        redirect(controller: controllerName, action: actionName)
    }
}
