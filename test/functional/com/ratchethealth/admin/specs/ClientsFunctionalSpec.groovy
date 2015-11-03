//package com.ratchethealth.admin.specs
//
//import com.ratchethealth.admin.pages.ClientsPage
//import com.ratchethealth.admin.pages.LoginPage
//import geb.spock.GebReportingSpec
//import org.openqa.selenium.Keys
//import spock.lang.Shared
//import spock.lang.Stepwise
//
//@Stepwise
//class ClientsFunctionalSpec extends GebReportingSpec {
//	@Shared ClientsPage clientsPage
//	@Shared IDENTIFY
//	@Shared CLIENT_NAME
//	@Shared CLIENT_NAME_SHORT
//	@Shared SUB_DOMAIN
//	@Shared PATIENT_PORTAL_NAME
//	@Shared PRIMARY_COLOR_HEX
//
//	static REQUIRE_ERROR_MESSAGE = 'This field is required.'
//	static PRIMARY_COLOR_HEX_ERROR_MESSAGE = 'The syntax of primary color hex should be \'#123afd\' or \'#abd\', numbers in 0-9, letters in a-f.'
//	static LOGO_RELATIVE_PATH = 'grails-app/assets/images/logo.png'
//	static FAVICON_RELATIVE_PATH = 'grails-app/assets/images/favicon.ico'
//
//	def setupSpec() {
//		IDENTIFY = System.currentTimeMillis()
//		CLIENT_NAME = "AFT${IDENTIFY} CN"
//		CLIENT_NAME_SHORT = "AFT${IDENTIFY}"
//		SUB_DOMAIN = "aft${IDENTIFY}sd"
//		PATIENT_PORTAL_NAME = "AFT${IDENTIFY} PPN"
//		PRIMARY_COLOR_HEX = "#9EFF9E"
//	}
//
//	def "login successfully"() {
//		when:
//		def loginPage = to(LoginPage)
//
//		report "Login page"
//
//		loginPage.login()
//
//		then:
//		at ClientsPage
//	}
//
//	def "check all contents displayed"() {
//		when:
//		clientsPage = to ClientsPage
//
//		then:
//		[
//			clientsPage.searchClientNameInput,
//			clientsPage.searchClientNameButton,
//			clientsPage.createClientButton,
//			clientsPage.clientsTable
//		].each {
//			it.displayed
//		}
//	}
//
//	def "show new client dialog and check contents in it and check close button"() {
//		when:
//		def newClientDialog = clientsPage.showNewClientDialog()
//
//		then:
//		waitFor(3, 1) {
//			newClientDialog.displayed
//		}
//
//		Thread.sleep(1000)
//
//		report "new client dialog opened"
//
//		newClientDialog.title.text() == 'New Client'
//		[
//			newClientDialog.closeButton,
//			newClientDialog.cancelButton,
//			newClientDialog.createButton
//		].each {
//			it.displayed
//		}
//
//		[
//			newClientDialog.clientName,
//			newClientDialog.subDomain,
//			newClientDialog.patientPortalName,
//			newClientDialog.primaryColorHex
//		].each {
//			it.displayed
//			it.text() == ''
//		}
//
//
//		when:
//		newClientDialog.closeButton.click()
//
//		then:
//		waitFor(3, 1) {
//			!clientsPage.newClientModel.displayed
//		}
//	}
//
//	def "click cancel button in new client dialog will close it"() {
//		when:
//		def newClientDialog = clientsPage.showNewClientDialog()
//
//		then:
//		waitFor(3, 1) {
//			newClientDialog.displayed
//		}
//
//		Thread.sleep(1000)
//		report "new client dialog opened"
//
//		when:
//		newClientDialog.closeButton.click()
//
//		then:
//		waitFor(3, 1) {
//			!newClientDialog.displayed
//		}
//	}
//
//	def "check required validation for all fields with clicking create button"() {
//		when:
//		def newClientDialog = clientsPage.showNewClientDialog()
//
//		then:
//		waitFor(3, 1) {
//			newClientDialog.displayed
//		}
//
//		when:
//		Thread.sleep(1000)
//
//		report "new client dialog opened, before create button clicked"
//
//		newClientDialog.createButton.click()
//
//		then:
//		waitFor(3, 1) {
//			newClientDialog.displayed
//		}
//
//		Thread.sleep(1000)
//		report "new client dialog opened, after create button clicked"
//
//		[
//			newClientDialog.clientName,
//			newClientDialog.subDomain,
//			newClientDialog.patientPortalName,
//			newClientDialog.primaryColorHex,
//			newClientDialog.logo,
//			newClientDialog.favicon
//		].each {
//			it.parents('.form-group').hasClass('has-error')
//			it.next().text() == REQUIRE_ERROR_MESSAGE
//		}
//
//		report "new client dialog, create button clicked"
//
//		when:
//		newClientDialog.cancelButton.click()
//
//		then:
//		waitFor(3, 1) {
//			!newClientDialog.displayed
//		}
//	}
//
//	def "check required validation for client name, subdomain, portal name and color hex with type and clean"() {
//		when:
//		def newClientDialog = clientsPage.showNewClientDialog()
//
//		then:
//		waitFor(3, 1) {
//			newClientDialog.displayed
//		}
//
//		when:
//		Thread.sleep(1000)
//		def inputs = [
//			newClientDialog.clientName,
//			newClientDialog.subDomain,
//			newClientDialog.patientPortalName,
//			newClientDialog.primaryColorHex,
//		]
//
//		inputs.each {
//			it << 'abc'
//
//			Thread.sleep(500)
//
//			it.value('')
//
//			Thread.sleep(500)
//
//			newClientDialog.title.click()
//
//			Thread.sleep(1000)
//		}
//
//		then:
//		inputs.each {
//			it.parents('.form-group').hasClass('has-error')
//			it.next().text() == REQUIRE_ERROR_MESSAGE
//		}
//
//		report "new client dialog, create button clicked"
//
//		when:
//		newClientDialog.cancelButton.click()
//
//		then:
//		waitFor(3, 1) {
//			!newClientDialog.displayed
//		}
//	}
//
//	def "check syntax validation for primary color hex field"() {
//		when:
//		def newClientDialog = clientsPage.showNewClientDialog()
//
//		then:
//		waitFor(3, 1) {
//			newClientDialog.displayed
//		}
//
//		when:
//		Thread.sleep(1000)
//		newClientDialog.primaryColorHex << '1'
//
//		then:
//		waitFor(3, 1) {
//			newClientDialog.primaryColorHex.parents('.form-group').hasClass('has-error')
//			newClientDialog.primaryColorHex.next().text() == PRIMARY_COLOR_HEX_ERROR_MESSAGE
//		}
//
//		report "new client dialog, check primary color hex field syntax validation"
//
//		when:
//		newClientDialog.cancelButton.click()
//
//		then:
//		waitFor(3, 1) {
//			!newClientDialog.displayed
//		}
//	}
//
//	def "create client successfully"() {
//		File logo = new File(LOGO_RELATIVE_PATH)
//		File favicon = new File(FAVICON_RELATIVE_PATH)
//
//		when:
//		Thread.sleep(1000)
//
//		def newClientDialog = clientsPage.showNewClientDialog()
//
//		then:
//		waitFor(3, 1) {
//			newClientDialog.displayed
//		}
//
//		when:
//		Thread.sleep(1000)
//		newClientDialog.clientName << CLIENT_NAME
//		newClientDialog.subDomain << SUB_DOMAIN
//		newClientDialog.patientPortalName << PATIENT_PORTAL_NAME
//		newClientDialog.primaryColorHex << PRIMARY_COLOR_HEX
//		newClientDialog.logo = logo.absolutePath
//		newClientDialog.favicon = favicon.absolutePath
//
//		newClientDialog.createButton.click()
//
//		waitFor(30, 1) { !newClientDialog.displayed }
//
//		then: "New client should display one the first line of table"
//		waitFor(90, 1) {
//			clientsPage.getClientNameInTable() == CLIENT_NAME
//			clientsPage.getActiveStaffCountInTable() == "0"
//			clientsPage.getActivePatientCountInTable() == "0"
//			clientsPage.getActiveTreatmentCountInTable() == "0"
//		}
//	}
//
//	def "search client name with click enter on keyboard"() {
//		when:
//		clientsPage.searchClientNameInput << CLIENT_NAME_SHORT
//		clientsPage.searchClientNameInput << Keys.ENTER
//
//		then:
//		waitFor(30, 1) {
//			clientsPage.getTableLineSize() == 1
//			clientsPage.getClientNameInTable() == CLIENT_NAME
//		}
//	}
//
////	def "search client name with click go button"() {
////		setup:
////		clientsPage.getAllClients()
////
////		when:
////		Thread.sleep(1000)
////		clientsPage.searchClientNameInput << CLIENT_NAME_SHORT
////
////		clientsPage.searchClientNameButton.click()
////
////		then:
////		waitFor(30, 1) {
////			clientsPage.getTableLineSize() == 1
////			clientsPage.getClientNameInTable() == CLIENT_NAME
////		}
////	}
//
////	def "click pagination button to load data"() {
////		setup:
////		clientsPage.getAllClients()
////
////		when:
////		Thread.sleep(1000)
////		clientsPage.clickPaginationButton(2)
////
////		then:
////		waitFor(30, 1) {
////			clientsPage.clientsTableInfo.text().startsWith("Showing 11 to 20 of")
////			clientsPage.getTableLineSize() >= 10
////		}
////
////		when:
////		Thread.sleep(1000)
////		clientsPage.clickPaginationButton(3)
////
////		then:
////		waitFor(30, 1) {
////			clientsPage.clientsTableInfo.text().startsWith("Showing 21 to 30 of")
////			clientsPage.getTableLineSize() >= 10
////		}
////	}
//}
