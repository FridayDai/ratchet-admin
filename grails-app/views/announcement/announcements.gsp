<g:set var="commonScriptPath" value="dist/commons.chunk.js"/>
<g:set var="scriptPath" value="dist/announcements.bundle.js"/>
<g:set var="cssPath" value="announcements"/>
<g:applyLayout name="main">
	<html>
	<head>
		<title>Welcome to ratchet</title>
	</head>

	<body>
	<div class="content">
		<div class="tool-bar clearfix">
			<button type="button" id="add-announcement-btn" class="add-client pull-right rc-line-space btn btn-primary"
					data-toggle="modal" data-target="#announcement-add-modal">New Annoucement</button>
		</div>

		<div class="announcements-list-table">
			<table id="announcements-table" class="display" data-total="${annouceList.recordsTotal}"
				   data-pagesize="${pagesize}">
				<thead>
				<tr>
					<td>ID</td>
					<td>Announcement</td>
					<td>Status</td>
					<td>Background</td>
					<td></td>
				</tr>
				</thead>
				<tbody>
				<g:each var="annouce" in="${annouceList.data}" status="i">
					<tr data-is-dom-data="true">
						<td>${annouce.id}</td>
						<td>${annouce.content}</td>
						<td>${annouce.status}</td>
						<td>${annouce.colorHex}</td>
						<td>${annouce.id}</td>
					</tr>
				</g:each>
				</tbody>
			</table>
		</div>
		<div class="modal fade" id="announcement-add-modal" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button class="close" type="button" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">New Announcement</h4>
					</div>

					<div class="modal-body">
						<div class="alert alert-danger alert-dismissible rc-server-error" role="alert">
							<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
							Sorry, Ratchet has experienced an internal error. Try again later.
							<span class="sr-only">Error:</span>
						</div>

						<form action="/announcements" method="post" class="form form-horizontal"
							  enctype="multipart/form-data" novalidate="novalidate">
							<input type="hidden" name="id"/>
							<input type="hidden" name="status"/>
							<div class="form-group">
								<label for="content" class="col-sm-5 control-label">* Announcement:</label>

								<div class="col-sm-6">
									<textarea class="form-control" name="content" id="content" cols="30"
											  rows="10" required></textarea>
								</div>
							</div>

							<div class="form-group">
								<label for="colorHex" class="col-sm-5 control-label">* Background:</label>

								<div class="col-sm-6">
									<select name="colorHex" id="colorHex" class="form-control">
										<option value="#fdddde">Red</option>
									</select>
								</div>
							</div>

							%{--<div class="form-group">--}%
								%{--<label for="status" class="col-sm-5 control-label">* Status:</label>--}%

								%{--<div class="col-sm-6">--}%
									%{--<select name="status" id="status" class="form-control">--}%
										%{--<option value="0">Inactive</option>--}%
										%{--<option value="1">Active</option>--}%
									%{--</select>--}%
								%{--</div>--}%
							%{--</div>--}%
						</form>
					</div>

					<div class="modal-footer">
						<button class="btn btn-default" type="button" data-dismiss="modal">Cancel</button>
						<button class="create-btn btn btn-primary" type="button"
								data-loading-text="Creating">Announce</button>
					</div>
				</div>
			</div>
		</div>
		<div id="announcement-delete-modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">Close Announcement</h4>
					</div>

					<div class="modal-body">
						<div class="alert alert-danger rc-server-error" role="alert"></div>
						<div>Are you sure to close this announcement?</div>
						%{--<div class="item-description">--}%
							%{--<input type="hidden" class="row-index"/>--}%
							%{--<dl class="dl-horizontal">--}%
								%{--<dt>ID:</dt>--}%
								%{--<dd class="announcement-id"></dd>--}%
								%{--<dt>Content:</dt>--}%
								%{--<dd class="announcement-content"></dd>--}%
								%{--<dt>Background:</dt>--}%
								%{--<dd class="announcement-background"></dd>--}%
								%{--<dt>Status:</dt>--}%
								%{--<dd class="announcement-status"></dd>--}%
							%{--</dl>--}%
						%{--</div>--}%
					</div>

					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
						<button type="button" class="delete-btn btn btn-primary"
								data-loading-text="Deleting">Close</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	</body>
	</html>
</g:applyLayout>
