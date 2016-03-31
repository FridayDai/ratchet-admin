<!DOCTYPE html>

<g:set var="commonScriptPath" value="dist/commons.chunk.js"/>
<g:set var="scriptPath" value="dist/clientDetail.bundle.js"/>
<g:set var="cssPath" value="clientDetail"/>
<g:applyLayout name="main">
	<html>
	<head>
		<title>Welcome to ratchet client detail</title>
	</head>

	<body>
	<div class="content">
		<div id="client-info-panel">
			<div class="client-profile panel row" data-id="${client.id}">
				<div class="logo col-sm-3">
					<img src="${client.logo}" alt="logo"/>
					<div class="client-name rc-line-space"><strong>${client.name}</strong></div>
				</div>

				<div class="main-info col-sm-5">
					<dl class="testing dl-horizontal">
						<dt>Testing:</dt>
						<dd><strong>${client.isTesting}</strong></dd>
					</dl>
					<dl class="sub-domain dl-horizontal">
						<dt>Domain:</dt>
						<dd>${client.subDomain}</dd>
					</dl>
					<dl class="portal-name dl-horizontal">
						<dt>Patient Portal Name:</dt>
						<dd>${client.portalName}</dd>
					</dl>
					<dl class="primary-color dl-horizontal">
						<dt>Primary Color:</dt>
						<dd>${client.primaryColorHex}</dd>
					</dl>
					<dl class="favicon dl-horizontal">
						<dt>Favicon:</dt>
						<dd><img class="favicon" src="${client.favIcon}" alt="favicon"/></dd>
					</dl>
					<dl class="dl-horizontal">
						<dt>Secret:</dt>
						<dd>${client.secret}</dd>
					</dl>
					<dl class="dl-horizontal">
						<dt>Token:</dt>
						<dd>${client.token}</dd>
					</dl>

					<dl class="session-time-out dl-horizontal">
						<dt>Session Timeout:</dt>
					    <g:if test="${client?.sessionTimeout && client?.sessionTimeout > 0}">
						<dd>in <span>${client.sessionTimeout}</span> minutes</dd>
					    </g:if>
					    <g:else>
						<dd>never</dd>
					    </g:else>
					</dl>
				</div>

				<div class="edit col-sm-4">
					<button type="button" class="btn btn-default pull-right" data-toggle="modal"
							data-target="#client-modal">
						<span class="edit-btn glyphicon glyphicon-edit"></span>
					</button>
				</div>
			</div>

			<div class="resource panel row">
				<div class="content-status-info col-sm-3">
					<dl class="active-staff dl-horizontal">
						<dt>Active Staff:</dt>
						<dd>${client.activeStaffCount}<dt>
					</dl>

					<dl class="active-patient dl-horizontal">
						<dt>Active Patient:</dt>
						<dd>${client.activePatientCount}</dd>
					</dl>

					<dl class="active-treatment dl-horizontal">
						<dt>Active Treatment:</dt>
						<dd>${client.activeTreatmentCount}</dd>
					</dl>
				</div>

				<div class="agent col-sm-9" data-agent-id="${client.clientStaff?.id}" data-client-id="${client.id}">
					<div class="pull-left">
						<dl class="email dl-horizontal">
							<dt>Agent Email:</dt>
							<dd>${client.clientStaff?.email}</dd>
						</dl>

						<dl class="first-name dl-horizontal">
							<dt>Agent First Name:</dt>
							<dd>${client.clientStaff?.firstName}</dd>
						</dl>

						<dl class="last-name dl-horizontal">
							<dt>Agent Last Name:</dt>
							<dd>${client.clientStaff?.lastName}</dd>
						</dl>
					</div>
					<div class="edit pull-right">
						<button type="button" class="trigger btn btn-default" data-toggle="modal" data-target="#agent-modal">
							<span class="edit-btn glyphicon glyphicon-edit"></span>
						</button>
					</div>
				</div>
			</div>
		</div>

        <div id="ip-list-panel" class="panel">
            <div class="tool-bar">
                <button id="add-ip" type="button" class="btn btn-primary pull-right" data-toggle="modal" data-target="#ip-modal">Add IP</button>
            </div>

            <div class="table-container">
                <table id="ip-table" class="display" data-total="${treatmentList.recordsTotal}" data-pagesize="${pagesize}" data-client-id="${client.id}">
                    <thead>
                    <tr>
                        <td>ID</td>
                        <td>IP</td>
                        <td>Name</td>
                        <td>Description</td>
                        <td></td>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each var="ipItem" in="${client?.ips}" status="i">
                        <tr>
                            <td>${ipItem.id}</td>
                            <td>${ipItem.ip}</td>
                            <td>${ipItem.name}</td>
                            <td>${ipItem.description}</td>
                            <td><span class="copy-btn glyphicon glyphicon-copy" aria-hidden="true"
                                      data-row="${i}"></span></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>

		<div id="treatment-list-panel" class="panel">
			<div class="tool-bar">
				<button id="add-treatment" type="button" class="btn btn-primary pull-right" data-toggle="modal" data-target="#treatment-modal">Add Treatment</button>
			</div>

			<div class="table-container">
				<table id="treatment-table" class="display" data-total="${treatmentList.recordsTotal}" data-pagesize="${pagesize}" data-client-id="${client.id}">
					<thead>
					<tr>
						<td>ID</td>
						<td>Treatment Title</td>
						<td>Template Title</td>
						<td>Active</td>
						<td>Description</td>
						<td>Status</td>
						<td>Last Updated</td>
						<td></td>
					</tr>
					</thead>
					<tbody>
					<g:each var="treatment" in="${treatmentList.data}" status="i">
						<tr>
							<td>${treatment.id}</td>
							<td>${treatment.title}</td>
							<td>${treatment.tmpTitle}</td>
							<td>${treatment.activePatient}</td>
							<td>${treatment.description}</td>
							<td>${treatment.active}</td>
							<td>${treatment.lastUpdated}</td>
							<td><span class="copy-btn glyphicon glyphicon-copy" aria-hidden="true"
									  data-row="${i}"></span></td>
						</tr>
					</g:each>
					</tbody>
				</table>
			</div>
		</div>

		<div id="client-modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">Edit Client</h4>
					</div>

					<div class="modal-body">
						<div class="alert alert-danger rc-server-error" role="alert"></div>
						<form action="/clients/${client.id}" method="post" class="form form-horizontal"
							  enctype="multipart/form-data" novalidate="novalidate">
							<div class="form-group">
								<label for="isTesting" class="col-sm-5 control-label">Testing:</label>

								<div class="col-sm-6">
									<input id="isTesting" name="isTesting" type="checkbox"/>
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
									<input id="primaryColorHex" name="primaryColorHex" type="text" class="form-control"
										   required/>
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
									<input type="file" id="logo" name="logo" class="form-control" required/>
								</div>
							</div>

							<div class="form-group">
								<label for="favIcon" class="col-sm-5 control-label">* Favicon:</label>

								<div class="col-sm-6">
									<input type="file" id="favIcon" name="favIcon" class="form-control" required/>
								</div>
							</div>
						</form>
					</div>

					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
						<button type="button" class="update-btn btn btn-primary"
								data-loading-text="Updating">Update</button>
					</div>
				</div>
			</div>
		</div>

		<div id="agent-modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">Edit Agent</h4>
					</div>

					<div class="modal-body">
						<div class="alert alert-danger rc-server-error" role="alert"></div>
						<form action="/clients/${client.id}/agents/${client.clientStaff?.id}" method="post"
							  class="form form-horizontal" enctype="multipart/form-data" novalidate="novalidate">
							<div class="form-group">
								<label for="email" class="col-sm-5 control-label">* Agent Email:</label>

								<div class="col-sm-6">
									<input type="email" id="email" name="email" class="form-control" required/>
								</div>
							</div>

							<div class="form-group">
								<label for="firstName" class="col-sm-5 control-label">* Agent First Name:</label>

								<div class="col-sm-6">
									<input type="text" id="firstName" name="firstName" class="form-control" required/>
								</div>
							</div>

							<div class="form-group">
								<label for="lastName" class="col-sm-5 control-label">* Agent Last Name:</label>

								<div class="col-sm-6">
									<input type="text" id="lastName" name="lastName" class="form-control" required/>
								</div>
							</div>
						</form>
					</div>

					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
						<button type="button" class="update-btn btn btn-primary" data-loading-text="Updating"
								data-creating-text="Creating">Update</button>
					</div>
				</div>
			</div>
		</div>

        <div id="ip-modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" data-client-id="${client.id}">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title">Add IP</h4>
                    </div>

                    <div class="modal-body">
                        <div class="alert alert-danger rc-server-error" role="alert"></div>
                        <form action="/clients/${client.id}/agents/${client.clientStaff?.id}" method="post"
                              class="form form-horizontal" enctype="multipart/form-data" novalidate="novalidate">
                            <div class="form-group">
                                <label class="col-sm-5 control-label">* Type:</label>

                                <div class="col-sm-6">
                                    <select name="type">
                                        <option value="anywhere">Anywhere</option>
                                        <option value="custom">Custom</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-5 control-label">* IP:</label>

                                <div class="col-sm-6">
                                    <input type="text" name="ip" class="form-control" required/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-5 control-label">* Name:</label>

                                <div class="col-sm-6">
                                    <input type="text" name="name" class="form-control" required/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-5 control-label">* description:</label>

                                <div class="col-sm-6">
                                    <textarea class="form-control" name="description" cols="30"
                                              rows="10" required></textarea>
                                </div>
                            </div>
                        </form>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                        <button type="button" class="update-btn btn btn-primary" data-loading-text="Updating"
                                data-creating-text="Creating">Update</button>
                    </div>
                </div>
            </div>
        </div>

        <div id="delete-ip-modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" data-client-id="${client.id}">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title">Delete IP</h4>
                    </div>

                    <div class="modal-body">
                        <div class="alert alert-danger rc-server-error" role="alert"></div>
                        <div>Are you sure to delete this IP?</div>
                        <div class="item-description">
                            <input type="hidden" class="row-index"/>
                            <dl class="dl-horizontal">
                                <dt>ID:</dt>
                                <dd class="id"></dd>
                                <dt>IP:</dt>
                                <dd class="ip"></dd>
                                <dt>Name:</dt>
                                <dd class="name"></dd>
                                <dt>Description:</dt>
                                <dd class="description"></dd>
                            </dl>
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                        <button type="button" class="delete-btn btn btn-primary"
                                data-loading-text="Deleting">Delete</button>
                    </div>
                </div>
            </div>
        </div>

		<div id="treatment-modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">Add Treatment</h4>
					</div>

					<div class="modal-body">
						<div class="alert alert-danger rc-server-error" role="alert"></div>
						<form action="/clients/${client.id}/treatments" method="post" class="form form-horizontal"
							  enctype="multipart/form-data" novalidate="novalidate">
							<div class="form-group">
								<label for="title" class="col-sm-5 control-label">* Treatment Title:</label>

								<div class="col-sm-6">
									<input id="title" name="title" type="text" class="form-control" required/>
								</div>
							</div>

							<div class="form-group">
								<label for="tmpTitle" class="col-sm-5 control-label">* Template Title:</label>

								<div class="col-sm-6">
									<input id="tmpTitle" name="tmpTitle" type="text" class="form-control" required/>
								</div>
							</div>

							<div class="form-group">
								<label for="surgeryTimeRequired"
									   class="col-sm-5 control-label">* Surgery Time Required:</label>

								<div class="col-sm-6">
									<select name="surgeryTimeRequired" id="surgeryTimeRequired" class="form-control">
										<option value="true">Yes</option>
										<option value="false">No</option>
									</select>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-5 control-label">Auto Archive:</label>

								<div class="col-sm-7">
									<select name="archiveWeek" class="form-control inline-select">
										<g:each var="i" in="${(0..<1001)}">
											<option value="${i}">${i}</option>
										</g:each>
									</select>
									<span>weeks</span>
									<select name="archiveDay" class="form-control inline-select">
										<g:each var="i" in="${(0..<7)}">
											<option value="${i}">${i}</option>
										</g:each>
									</select>
									<span>days</span>
									<span>after surgery</span>
								</div>
							</div>

							<div class="form-group">
								<label for="description" class="col-sm-5 control-label">* Description:</label>

								<div class="col-sm-6">
									<textarea class="form-control" name="description" id="description" cols="30"
											  rows="10" required></textarea>
								</div>
							</div>
						</form>
					</div>

					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
						<button type="button" class="create-btn btn btn-primary"
								data-loading-text="Creating">Create</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	</body>
	</html>
</g:applyLayout>
