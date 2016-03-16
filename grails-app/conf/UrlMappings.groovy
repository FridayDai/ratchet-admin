class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        // Root
        "/"(controller: "home", action: "index")

        //Health check
        "/healthcheck"(controller: "healthCheck", action: "index")

        // Account
        "/login"(controller: "authentication", action: "login")
        "/logout"(controller: "authentication", action: 'logout')
        "/forgot-password"(controller: "authentication") {
            action = [GET: "goToForgetPasswordPage", POST: "forgotPassword"]
        }

        "/reset-password/$code?"(controller: "authentication", action: "resetPassword")

        "/confirm-reset-password"(controller: "authentication", action: "confirmResetPassword")

        // Client
        "/getClients"(controller: "clients", action: "getClients")
        "/clients"(controller: "clients") {
            action = [GET: "index", POST: "addClient"]
        }
        "/clients/$id/$clientName?"(controller: "clients") {
            action = [GET: "clientDetail", POST: "editClient"]
        }
        "/clients/$clientId/agents"(controller: "clients") {
            action = [POST: "addAgent"]
        }
        "/clients/$clientId/agents/$agentId"(controller: "clients") {
            action = [POST: "editAgent", DELETE: "deleteAgent"]
        }
        "/clients/$clientId/ips"(controller: "clients") {
            action = [POST: "addIP"]
        }
        "/clients/$clientId/ips/$ipId"(controller: "clients") {
            action = [POST: "editIP", DELETE: "deleteIP"]
        }

        // Announcement
        "/getAnnouncements"(controller: "announcements", action: "getAnnouncements")
        "/announcements"(controller: "announcements") {
            action = [GET: "index", POST: "addAnnouncement"]
        }
        "/announcements/$announcementId"(controller: "announcements") {
            action = [POST: "closeAnnouncement", PUT: "editAnnouncement", DELETE: "deleteAnnouncement"]
        }

        // Treatment
        "/clients/$clientId/treatments"(controller: "treatments") {
            action = [GET: "getTreatments", POST: "addTreatment"]
        }
        "/clients/$clientId/treatments/$treatmentId/$treatmentName?"(controller: "treatments") {
            action = [GET: "treatmentDetail", POST: "editTreatment", DELETE: "closeTreatment"]
        }
        "/clients/$clientId/treatments/$treatmentId/tools"(controller: "treatments") {
            action = [GET: "getTools", POST: "addTool"]
        }
        "/clients/$clientId/treatments/$treatmentId/tools/$toolId"(controller: "treatments") {
            action = [POST: "editTool", DELETE: "deleteTool"]
        }
        "/clients/$clientId/treatments/$treatmentId/tasks"(controller: "treatments") {
            action = [GET: "getTasks", POST: "addTask"]
        }
        "/clients/$clientId/treatments/$treatmentId/tasks/$taskId"(controller: "treatments") {
            action = [POST: "editTask", DELETE: "deleteTask"]
        }

        // Account
        "/getAccounts"(controller: "accounts", action: "getAccounts")
        "/accounts"(controller: "accounts") {
            action = [GET: "index", POST: "addAccount"]
        }
        "/accounts/$accountId?/update"(controller: "accounts", action: "updateAccount")
        "/accounts/$accountId?/delete"(controller: "accounts", action: "deleteAccount")
        "/confirm-password"(controller: "accounts", action: "confirmAccountPassword")

        //Account profile
        "/profile"(controller: "profile", action: "goToProfilePage")
        "/profile/update-password"(controller: "profile", action: "updatePassword")
        "/profile/debug-time"(controller: "profile", action: "getLastScheduleTime")

        //email confirm to activate account
        "/email/confirmation/$code?"(controller: "accounts", action: "activateAccount")

        //data backup
        "/testdata"(controller: "testData"){
            action = [GET: "index", POST: "generateTestData"]
        }

        // QA Debug Functions
        "/debug"(controller: "dubug"){
            action = [GET: "index"]
        }

        // HL7
        "/hl7"(controller: "HL7", action: "index")
        "/hl7/reporting"(controller: "HL7", action: "getReportingPage")
        "/hl7/failures"(controller: "HL7", action: "getFailuresPage")
        "/hl7/error/$errorJobId/reprocess"(controller: "HL7", action: "retryFailure")

        // Error
        "500"(view: '/error/503')
    }
}
