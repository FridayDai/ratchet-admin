<!DOCTYPE html>

<g:set var="commonScriptPath" value="dist/commons.chunk.js"/>
<g:set var="scriptPath" value="dist/clients.bundle.js"/>
<g:set var="cssPath" value="clients"/>
<g:applyLayout name="main">
	<html>
	<head>
		<title>Welcome to ratchet</title>
	</head>

	<body>
	<div class="content">
		<div class="clients-tool-bar tool-bar clearfix row">
			<div class="col-lg-4">
				<div class="input-group">
					<input type="text" id="client-search-input" class="form-control" placeholder="Search with Client Name">
					<span class="input-group-btn">
						<button id="client-search-btn" class="btn btn-default" type="button">Go!</button>
					</span>
				</div>
			</div>
			<div class="col-lg-6 pull-right">
				<button type="button" id="add-client" class="add-client pull-right rc-line-space btn btn-primary"
					data-toggle="modal" data-target="#client-modal">New Client</button>
			</div>
		</div>

		<div>
			<table id="client-table" class="display" data-total="${clientList.recordsTotal}" data-pagesize="${pagesize}">
				<thead>
					<tr>
						<td>ID</td>
						<td>Client</td>
						<td>Active Staff</td>
						<td>Active Patient</td>
						<td>Active Treatment</td>
						<td></td>
					</tr>
				</thead>
				<tbody>
				<g:each var="client" in="${clientList.data}" status="i">
					<tr data-is-dom-data="true"
						data-sub-domain="${client.subDomain}"
						data-portal-name="${client.portalName}"
						data-primary-color-hex="${client.primaryColorHex}"
						data-client-staff='{
							"id": ${client.clientStaff?.id ?: 0},
							"email":"${client.clientStaff?.email}",
							"firstName": "${client.clientStaff?.firstName}",
							"lastName": "${client.clientStaff?.lastName}"
						}'
					>
						<td>${client.id}</td>
						<td>${client.name}</td>
						<td>${client.activeStaffCount}</td>
						<td>${client.activePatientCount}</td>
						<td>${client.activeTreatmentCount}</td>
						<td><span class="copy-btn glyphicon glyphicon-copy" aria-hidden="true" data-row="${i}"></span></td>
					</tr>
				</g:each>
				</tbody>
			</table>
			<!-- <div id="records-total" style="display:none;">${clientList.recordsTotal}</div> -->
		</div>

		%{-- Modal dialog --}%
		<div class="modal fade" id="client-modal" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button class="close" type="button" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">New Client</h4>
					</div>
					<div class="modal-body">
						<div class="alert alert-danger rc-server-error" role="alert"></div>
						<g:uploadForm controller="clients" method="post" name="tableForm"
								class="form form-horizontal" novalidate="novalidate">
							<div class="form-group">
								<label for="isTesting" class="col-sm-5 control-label">Testing:</label>

								<div class="col-sm-6">
									<input id="isTesting" name="isTesting" type="checkbox" checked/>
								</div>
							</div>

							<div class="form-group">
								<label for="name" class="col-sm-5 control-label">* Client Name:</label>

								<div class="col-sm-6">
									<input id="name" name="name" type="text" class="form-control" required/>
								</div>
							</div>

							<div class="form-group">
								<label for="subDomain" class="col-sm-5 control-label">* Subdomain:</label>

								<div class="col-sm-6">
									<input id="subDomain" name="subDomain" type="text" class="form-control" required/>
								</div>
							</div>

							<div class="form-group">
								<label for="portalName" class="col-sm-5 control-label">* Patient Portal Name:</label>

								<div class="col-sm-6">
									<input id="portalName" name="portalName" type="text" class="form-control" required/>
								</div>
							</div>

							<div class="form-group">
								<label for="primaryColorHex" class="col-sm-5 control-label">* Primary Color Hex:</label>

								<div class="col-sm-6">
									<input id="primaryColorHex" name="primaryColorHex" type="text" class="form-control" required/>
								</div>
							</div>

							<div class="form-group">
								<label for="sessionTimeout" class="col-sm-5 control-label">Session Timeout(in minutes):</label>

								<div class="col-sm-6">
									<input id="sessionTimeout" name="sessionTimeout" type="number" class="form-control" min="0"/>
								</div>
							</div>

							<div class="form-group">
								<label for="logo" class="col-sm-5 control-label">* Logo:</label>

								<div class="col-sm-6">
									<input type="file" id="logo" name="logo" required/>
								</div>
							</div>

							<div class="form-group">
								<label for="favIcon" class="col-sm-5 control-label">* Favicon:</label>

								<div class="col-sm-6">
									<input type="file" id="favIcon" name="favIcon" required/>
								</div>
							</div>
						</g:uploadForm>
					</div>
					<div class="modal-footer">
						<button class="btn btn-default" type="button" data-dismiss="modal">Cancel</button>
						<button class="create-btn btn btn-primary" type="button" data-loading-text="Creating">Create</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	</body>
	</html>
</g:applyLayout>
