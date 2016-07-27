package com.ratchethealth.admin

class Client {
	long id
	String logo
	String favIcon
	String logoFileName
	String favIconFileName
	String shortName
	String name
	String legalName
	String subDomain
	String portalName
	String primaryColorHex
	int sessionTimeout
	int activeStaffCount = 0
	int activePatientCount = 0
	int activeTreatmentCount = 0
	Staff clientStaff
	boolean isTesting
}
