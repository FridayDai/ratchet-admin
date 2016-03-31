package com.ratchethealth.admin

import grails.converters.JSON

class DebugController extends BaseController {

    def schedulerService
    def beforeInterceptor = [action: this.&auth]

    def index() {
        render(view: '/debug/index')
    }

    def changeScheduleDateTime() {
        String token = request.session.token
        String date = params.debugDateTime

        def resp = schedulerService.changeDateTimeForSchedule(token, date)
        def result = [resp: resp]

        render result as JSON
    }

    def setDateTime() {
        String token = request.session.token
        def result = schedulerService.getDateTimeForSchedule(token)
        render result as JSON
    }

    def changeRandomHour() {
        String token = request.session.token
        int hour = params.randomHour.toInteger()

        def resp = schedulerService.changeRandomHourForSchedule(token, hour)
        def result = [resp: resp]

        render result as JSON
    }

    def setRandomHour() {
        String token = request.session.token
        def result = schedulerService.getRandomHourForSchedule(token)
        render result as JSON
    }

}
