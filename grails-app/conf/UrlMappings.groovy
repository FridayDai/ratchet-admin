class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        // Root
        "/"(controller: "home", action: "index")

        // Account
        "/login"(controller: "authentication", action: "login")
        "/logout"(controller: "authentication", action: 'logout')

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

        // Treatment
        "/clients/$clientId/treatments"(controller: "treatments") {
            action = [POST: "addTreatment"]
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
        "/accounts"(controller: "accounts", action: "index")

        // Error
        "500"(view: '/error/error')
    }
}
