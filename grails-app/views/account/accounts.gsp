<%@ page import="com.ratchethealth.admin.RatchetConstants" %>
<!DOCTYPE html>

<g:set var="commonScriptPath" value="dist/commons.chunk.js"/>
<g:set var="scriptPath" value="dist/accounts.bundle.js"/>
<g:set var="cssPath" value="accounts"/>
<g:applyLayout name="main">
    <html>
    <head>
        <title>Welcome to ratchet</title>
    </head>

    <body>
    <div class="content">
        <div class="tool-bar clearfix">
            <button type="button" id="add-account" class="add-account pull-right rc-line-space btn btn-primary"
                    data-toggle="modal" data-target="#add-account-modal">New Account</button>
        </div>

        <div class="account-list-table">
            <table id="account-table" class="display" data-total="${accountList.recordsTotal}"
                   data-pagesize="${pagesize}">
                <thead>
                <tr>
                    <td>ID</td>
                    <td>Email Address</td>
                    <td>Status</td>
                    <td>Enabled</td>
                    <td></td>
                    <td></td>
                </tr>
                </thead>
                <tbody>
                <g:each var="account" in="${accountList.data}" status="i">
                    <tr data-is-dom-data="true">
                        <td>${account.id}</td>
                        <td>${account.email}</td>
                        <td>${account.status}</td>
                        <td>${account.enabled}</td>
                        <td>
                            <a href="#" class="btn-edit glyphicon glyphicon-pencil" aria-hidden="true" data-row="${i}"
                               data-toggle="modal" data-target="#edit-account-modal"
                               data-account-id="${account.id}"></a>
                        </td>
                        <td>
                            <a href="#" class="btn-remove glyphicon glyphicon-trash" aria-hidden="true" data-row="${i}"
                               data-toggle="modal" data-target="#delete-account-modal"
                               data-account-id="${account.id}"></a>
                        </td>
                    </tr>
                </g:each>
                </tbody>
            </table>
        </div>

        %{-- Modal dialog --}%
        <div class="modal fade" id="add-account-modal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title">New Account</h4>
                    </div>

                    <div class="modal-body">
                        <div class="alert alert-danger rc-server-error" role="alert"></div>
                        <g:uploadForm controller="accounts" method="post" name="tableForm"
                                      class="form form-horizontal" novalidate="novalidate">
                            <div class="form-group">
                                <label for="email" class="col-sm-5 control-label">*Email Address:</label>

                                <div class="col-sm-6">
                                    <input id="email" name="email" type="email" class="form-control" required/>
                                </div>
                            </div>
                        </g:uploadForm>
                    </div>

                    <div class="modal-footer">
                        <button class="btn btn-default" type="button" data-dismiss="modal">Cancel</button>
                        <button class="create-btn btn btn-primary" type="button"
                                data-loading-text="Creating">Create</button>
                    </div>
                </div>
            </div>
        </div>

        %{-- Modal dialog --}%
        <div class="modal fade" id="edit-account-modal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title">Edit Account</h4>
                    </div>

                    <div class="modal-body">
                        <div class="alert alert-danger rc-server-error" role="alert"></div>
                        <g:uploadForm controller="accounts" method="post" name="tableForm"
                                      class="form form-horizontal" novalidate="novalidate">
                            <div class="form-group">
                                <label for="email" class="col-sm-5 control-label">*Email Address:</label>

                                <div class="col-sm-6">
                                    <input id="account-email" name="email" type="email" class="form-control" readonly/>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <div class="checkbox">
                                        %{--<label>--}%
                                        %{--<input type="checkbox" id="isEnabled" name="enabled"> Enabled--}%
                                        %{--</label>--}%
                                        <g:checkBox name="enabled" id="isEnabled"/>Enabled
                                    </div>

                                </div>
                            </div>

                        </g:uploadForm>
                    </div>

                    <div class="modal-footer">
                        <button class="btn btn-default" type="button" data-dismiss="modal">Cancel</button>
                        <button class="update-btn btn btn-primary" type="button"
                                data-loading-text="Updating">Update</button>
                    </div>
                </div>
            </div>
        </div>

        <div id="delete-account-modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title">Delete Account</h4>
                    </div>

                    <div class="modal-body">
                        <div class="alert alert-danger rc-server-error" role="alert"></div>
                        <div>Are you sure to delete this account?</div>
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
