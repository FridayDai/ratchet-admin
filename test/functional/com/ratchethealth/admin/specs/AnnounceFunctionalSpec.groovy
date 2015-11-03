//package com.ratchethealth.admin.specs
//
//import com.ratchethealth.admin.pages.AnnouncePage
//import com.ratchethealth.admin.pages.ClientsPage
//import com.ratchethealth.admin.pages.LoginPage
//import geb.spock.GebReportingSpec
//import spock.lang.Shared
//import spock.lang.Stepwise
//
//@Stepwise
//class AnnounceFunctionalSpec extends GebReportingSpec {
//    @Shared
//    ClientsPage clientsPage
//    @Shared
//    AnnouncePage announcePage
//
//    def "login successfully"() {
//        when:
//        def loginPage = to(LoginPage)
//
//        report "Login page"
//
//        loginPage.login()
//
//        then:
//        waitFor {
//            at ClientsPage
//        }
//
//    }
//
//    def "direct to announce page"() {
//        when:
//        clientsPage = to ClientsPage
//        clientsPage.navSide.announceIcon.click()
//
//        then:
//        at AnnouncePage
//
//    }
//
//    def "check announce table info"() {
//        when:
//        announcePage = to AnnouncePage
//
//        then:
//        waitFor {
//            announcePage.announceTable.tbody.displayed
//        }
//        //TODO: CHECK TABLE MORE INFO
//    }
//
//    def "open and close announcementModal successfully"() {
//        given:
//        def announceModal = announcePage.newAnnounceModal
//
//        when:
//        announcePage.addAnnounceBtn.click()
//
//        then:
//        waitFor(5, 1) {
//            announceModal.displayed
//        }
//
//        Thread.sleep(2*1000)
//        and:
//        announceModal.title == "New Announcement"
//        [
//                announceModal.closeButton,
//                announceModal.cancelButton,
//                announceModal.createButton
//        ].each {
//            it.displayed
//        }
//
//        when:
//        announceModal.closeButton.click()
//
//        then:
//        waitFor(3, 1) {
//            !announceModal.displayed
//        }
//    }
//
//    def "validate error message correctly"() {
//        given:
//        def announceModal = announcePage.newAnnounceModal
//
//        when:
//        announcePage.addAnnounceBtn.click()
//        waitFor { announceModal.displayed }
//        announceModal.createButton.click()
//
//        then:
//        announceModal.AnnouncementErrorMessage.displayed
//        Thread.sleep(1000)
//
//        when:
//        announceModal.cancelButton.click()
//
//        then:
//        waitFor(3, 1) {
//            !announceModal.displayed
//        }
//
//    }
//
//    def "add announcement successfully"() {
//        given:
//        def announceModal = announcePage.newAnnounceModal
//
//        when:
//        announcePage.addAnnounceBtn.click()
//        waitFor { announceModal.displayed }
//        announceModal.AnnouncementContent << "TEST CONTENT FOR ANNOUNCEMENT"
//        announceModal.createButton.click()
//
//        then:
//        waitFor { !announceModal.displayed }
//    }
//}
