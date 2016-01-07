package com.ratchethealth.admin

import grails.util.Holders

class TestDataController extends BaseController {

    def beforeInterceptor = [action: this.&auth]

    def testDataService

    static final IS_DEBUG = Boolean.valueOf(Holders.config.ratchetv2.server.debug)

    def index() {
        def (anonyData, nonAnonyData) = getTestData()
        render(view: '/testData/index', model: [anonyLinks: anonyData, nonAnonyLinks:nonAnonyData, isDebug: IS_DEBUG])
    }

    def generateTestData() {
        String token = request.session.token
        def resp = testDataService.generateTestData(token, params.boolean('isDataAnonymized'))
        if (resp == true) {
            def (anonyData, nonAnonyData) = getTestData()
            render(view: '/testData/index', model: [anonyLinks: anonyData, nonAnonyLinks:nonAnonyData, isDebug: IS_DEBUG])
        }
    }

    def getTestData() {
        String token = request.session.token
        def testDataMax = RatchetConstants.DEFAULT_TEST_DATA_SIZE
        def testData = testDataService.getTestData(token, testDataMax)

        [testData?.anonyData, testData?.nonAnonyData]
    }
}
