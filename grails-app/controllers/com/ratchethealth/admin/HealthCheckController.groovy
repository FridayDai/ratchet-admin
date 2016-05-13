package com.ratchethealth.admin

class HealthCheckController {

    def index() {
        render status: 200, text: "OK"
    }
}
