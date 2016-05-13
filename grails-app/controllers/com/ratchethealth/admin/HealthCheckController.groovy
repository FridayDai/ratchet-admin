package com.ratchethealth.admin

class HealthCheckController {

    def index() {
        render(contentType: 'application/json') {
            status = 'OK'
            memeory = {
                hello = 'wawa'
            }
        }
    }
}
