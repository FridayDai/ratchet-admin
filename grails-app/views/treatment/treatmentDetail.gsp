<!DOCTYPE html>
<%@ page import="com.ratchethealth.admin.Utility" %>
<g:set var="commonScriptPath" value="dist/commons.chunk.js"/>
<g:set var="scriptPath" value="dist/treatmentDetail.bundle.js"/>
<g:set var="cssPath" value="treatmentDetail"/>
<g:applyLayout name="main">
	<html>
	<head>
		<title>Welcome to ratchet treatment detail</title>
	</head>

	<body>
	<div class="content">
		<div id="treatment-info-panel" class="panel row" data-client-id="${clientId}"
			 data-treatment-id="${treatment.id}">
			<div class="main-info col-sm-3">
				<div class="id"><strong>ID:</strong> ${treatment.id}</div>

				<div class="title rc-line-space"><strong>${treatment.title}</strong></div>

				<div class="last-update rc-line-space"><strong>Last Update:</strong><br><g:formatDate
						date="${new java.util.Date(treatment.lastUpdated)}"
						timeZone="${TimeZone.getTimeZone('America/Vancouver')}"
						format="MMM d, yyyy h:mm a"></g:formatDate></div>

				<div class="template-title rc-line-space"><strong>Template Title:</strong> <span
						class="text">${treatment.tmpTitle}</span></div>

				<div class="surgery-time-required rc-line-space"><strong>Surgery Time Required:</strong> <span
						class="text">${treatment.surgeryTimeRequired ? 'Yes' : 'No'}</span></div>

				<div class="status rc-line-space"><strong>Status:</strong> <span
						class="text">${treatment.active ? 'Active' : 'Closed'}</span></div>

				<div class="active-patient rc-line-space"><strong>Active Patient:</strong> ${treatment.activePatient}</div>
                <div class="auto-archive rc-line-space" data-auto-archive="${treatment.archiveTime}"><strong>Auto Archive:</strong>
                    <br><span>${Utility.getAutoArchiveStr(treatment.archiveTime)}</span>
                </div>
			</div>

			<div class="description col-sm-6">${treatment.description}</div>

			<div class="action col-sm-3">
				<div class="clearfix">
					<button type="button" class="btn btn-default pull-right" data-toggle="modal"
							data-target="#edit-treatment-modal">
						<span class="edit-btn glyphicon glyphicon-edit"></span>
					</button>
				</div>

				<div class="rc-line-space clearfix">
					<button type="button" class="btn btn-default pull-right" data-toggle="modal"
							data-target="#close-treatment-modal">
						Close Treatment
					</button>
				</div>
			</div>
		</div>

		<div id="edit-treatment-modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">Edit Treatment</h4>
					</div>

					<div class="modal-body">
						<div class="alert alert-danger rc-server-error" role="alert"></div>
						<form action="/clients/${clientId}/treatments/${treatment.id}" method="post"
							  class="form form-horizontal" enctype="multipart/form-data" novalidate="novalidate">
							<div class="form-group">
								<label for="edit-treatment-title"
									   class="col-sm-5 control-label">* Treatment Title:</label>

								<div class="col-sm-6">
									<input id="edit-treatment-title" name="title" type="text" class="form-control"
										   required/>
								</div>
							</div>

							<div class="form-group">
								<label for="edit-treatment-tmpTitle"
									   class="col-sm-5 control-label">* Template Title:</label>

								<div class="col-sm-6">
									<input id="edit-treatment-tmpTitle" name="tmpTitle" type="text" class="form-control"
										   required/>
								</div>
							</div>

							<div class="form-group">
								<label for="edit-treatment-surgeryTimeRequired"
									   class="col-sm-5 control-label">* Surgery Time Required:</label>

								<div class="col-sm-6">
									<select name="surgeryTimeRequired" id="edit-treatment-surgeryTimeRequired">
										<option value="false">No</option>
										<option value="true">Yes</option>
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
								<label for="edit-treatment-description"
									   class="col-sm-5 control-label">* Description:</label>

								<div class="col-sm-6">
									<textarea class="form-control" name="description" id="edit-treatment-description"
											  cols="30" rows="10" required></textarea>
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

		<div id="close-treatment-modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">Close Treatment</h4>
					</div>

					<div class="modal-body">
						<div class="alert alert-danger rc-server-error" role="alert"></div>
						<div>Are you sure to close this treatment?</div>
					</div>

					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
						<button type="button" class="delete-btn btn btn-primary" data-loading-text="Deleting"
								data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>

		<div id="tool-pool-list-panel" class="panel">
			<div class="tool-bar clearfix">
				<h3 class="title sub-title pull-left">Tool Pool</h3>

				<div class="action pull-right">
					<div class="btn-group">
						<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown"
								aria-expanded="false">
							Add Tool <span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#" class="basic" data-toggle="modal"
								   data-target="#add-basic-tool-modal">Basic</a></li>
							<li><a href="#" id="add-outcome-tool-btn" class="defined" data-type="OUTCOME">Outcome</a></li>
                            <li><a href="#" id="add-voice-tool-btn" class="defined" data-type="VOICE">Voice</a></li>
						</ul>
					</div>
				</div>
			</div>

			<div class="table-container">
				<table id="tool-pool-table" class="display"></table>
			</div>
		</div>

		<div id="add-defined-tool-modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">Add Tool</h4>
					</div>

					<div class="modal-body">
						<div class="alert alert-danger rc-server-error" role="alert"></div>
						<form action="/clients/${clientId}/treatments/${treatment.id}/tools" method="post"
							  class="form form-horizontal" enctype="multipart/form-data" novalidate="novalidate">
							<input type="hidden" name="type" value="2"/>
							<div class="form-group">
								<label class="col-sm-5 control-label">* Tool:</label>

								<div class="col-sm-6">
									<select name="id" id="outcome-tool-type" class="form-control defined-tool-type" required>
										<g:each var="tool" in="${outcomeTools}">
											<option value="${tool.id}">${tool.title}</option>
										</g:each>
									</select>

                                    <select name="idle" id="voice-tool-type" class="form-control defined-tool-type" required>
                                        <g:each var="tool" in="${voiceTools}">
                                            <option value="${tool.id}">${tool.title}</option>
                                        </g:each>
                                    </select>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-5 control-label">Require Completion:</label>

								<div class="col-sm-6">
									<p class="form-control-static">YES</p>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-5 control-label">* Default Due Time:</label>

								<div class="col-sm-7 default-due-time">
									<select name="defaultDueTimeDay" class="form-control inline-select" required>
										<g:each var="i" in="${(1..<51)}">
											<option value="${i}">${i}</option>
										</g:each>
									</select>
									<span>days</span>
									<select name="defaultDueTimeHour" class="form-control inline-select" required>
										<g:each var="i" in="${(0..<24)}">
											<option value="${i}">${i}</option>
										</g:each>
									</select>
									<span>hours</span>
									<span>upon receiving</span>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-5 control-label">Default Expire Time:</label>

								<div class="col-sm-7 default-expire-time">
									<select name="defaultExpireTimeDay" class="form-control inline-select">
										<option value=""></option>
										<g:each var="i" in="${(0..<100)}">
											<option value="${i}">${i}</option>
										</g:each>
									</select>
									<span>days</span>
									<select name="defaultExpireTimeHour" class="form-control inline-select">
										<option value=""></option>
										<g:each var="i" in="${(0..<24)}">
											<option value="${i}">${i}</option>
										</g:each>
									</select>
									<span>hours</span>
									<span>upon receiving</span>
								</div>
							</div>

							<div class="form-group">
								<label for="defined-tool-reminder" class="col-sm-5 control-label">* Reminder:</label>

								<div class="col-sm-6">
									<input type="text" name="reminder" id="defined-tool-reminder" class="form-control"
										   required/>
								</div>
							</div>
							<input type="hidden" name="requireCompletion" value="1"/>
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

		<div id="delete-tool-modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">Delete Tool</h4>
					</div>

					<div class="modal-body">
						<div class="alert alert-danger rc-server-error" role="alert"></div>
						<div>Are you sure to delete this tool?</div>
						<div class="item-description">
							<input type="hidden" class="row-index"/>
							<dl class="dl-horizontal">
								<dt>ID:</dt>
								<dd class="id"></dd>
								<dt>Tool Title:</dt>
								<dd class="tool-title"></dd>
								<dt>Tool Type:</dt>
								<dd class="tool-type"></dd>
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

		<div id="task-list-panel" class="panel">
			<div class="tool-bar clearfix">
				<h3 class="title sub-title pull-left">Task</h3>

				<div class="action pull-right">
					<button type="button" id="add-item-btn" class="btn btn-primary">Add</button>
				</div>
			</div>

			<div class="table-container">
				<table id="task-table" class="display"></table>
			</div>
		</div>

		<div id="add-item-modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">Add item</h4>
					</div>

					<div class="modal-body">
						<div class="alert alert-danger rc-server-error" role="alert"></div>
						<form action="/clients/${clientId}/treatments/${treatment.id}/tasks" method="post"
							  class="form form-horizontal"
							  enctype="multipart/form-data" novalidate="novalidate">
							<input type="hidden" name="toolId" />
							<div class="form-group">
								<label for="add-item-tool-id" class="col-sm-5 control-label">* Tool:</label>

								<div class="col-sm-6">
									<select name="toolId" id="add-item-tool-id" class="form-control" required>
										<g:each var="tool" in="${tools}" status="i">
											<option value="${tool.id}">${tool.title}</option>
										</g:each>
									</select>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-5 control-label">* Send Time:</label>

								<div class="col-sm-6">
									<select name="sendTimeDirection" class="form-control inline-long-select">
										<option value="0">Immediate</option>
										<option value="-1">Before</option>
										<option value="1">After</option>
									</select>
								</div>
							</div>

							<div class="form-group">
								<div class="col-sm-offset-5 col-sm-6">
									<select name="sendTimeWeeks" class="form-control inline-select">
										<g:each var="i" in="${(0..<1001)}">
											<option value="${i}">${i}</option>
										</g:each>
									</select>
									<span>weeks</span>
								</div>
							</div>

							<div class="form-group">
								<div class="col-sm-offset-5 col-sm-6">
									<select name="sendTimeDays" class="form-control inline-select">
										<g:each var="i" in="${(0..<7)}">
											<option value="${i}">${i}</option>
										</g:each>
									</select>
									<span>days</span>
								</div>
							</div>

							<div class="form-group">
								<div class="col-sm-offset-5 col-sm-6">
									<select name="sendTimeHours" class="form-control inline-select">
										<g:each var="i" in="${(0..<24)}">
											<option value="${i}">${i}</option>
										</g:each>
									</select>
									<span>hours</span>
								</div>
							</div>

							<div class="form-group">
								<div class="col-sm-offset-5 col-sm-6">
									<select name="sendTimeMinutes" class="form-control inline-select">
										<g:each var="i" in="${(0..<60)}">
											<option value="${i}">${i}</option>
										</g:each>
									</select>
									<span>minutes</span>
								</div>
							</div>
						</form>
					</div>

					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
						<button type="button" class="create-btn btn btn-primary"
								data-loading-text="Creating...">Create</button>
					</div>
				</div>
			</div>
		</div>

		<div id="delete-item-modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">Delete Task</h4>
					</div>

					<div class="modal-body">
						<div class="alert alert-danger rc-server-error" role="alert"></div>
						<div>Are you sure to delete this item?</div>
						<div class="item-description">
							<input type="hidden" class="row-index"/>
							<dl class="dl-horizontal">
								<dt>ID:</dt>
								<dd class="id"></dd>
								<dt>Tool Title:</dt>
								<dd class="tool-title"></dd>
								<dt>Tool Type:</dt>
								<dd class="tool-type"></dd>
								<dt>Send Time:</dt>
								<dd class="send-time"></dd>
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
	</div>
	</body>
	</html>
</g:applyLayout>
