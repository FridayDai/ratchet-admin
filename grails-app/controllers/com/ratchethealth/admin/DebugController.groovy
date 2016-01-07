package com.ratchethealth.admin

class DebugController extends BaseController {

    def beforeInterceptor = [action: this.&auth]


    def index() {
        render(view: '/debug/index')
    }
}
