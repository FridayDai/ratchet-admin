<!DOCTYPE html>
<g:set var="commonScriptPath" value="dist/commons.chunk.js"/>
<g:set var="scriptPath" value="dist/profile.bundle.js"/>
<g:set var="cssPath" value="profile"/>
<g:applyLayout name="main">
    <html>
    <head>
        <title>Welcome to ratchet</title>
    </head>
    <body>
    <div class="alert alert-success alert-dismissible" role="alert">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <strong>Triggered Success</strong>
    </div>
    <div class="content">
        <div class="btn-group-vertical">
            <button type="button" id="update-password" class="rc-line-space btn btn-primary"
                    data-toggle="modal" data-target="#change-password-modal">Change Password</button>
            <button type="button" id="debug-schedule" class="rc-line-space btn btn-warning"
                    data-toggle="modal" data-target="#change-time-modal">Debug Schedule</button>
            <button type="button" id="resend-confirmation" class="rc-line-space btn btn-warning"
                    data-toggle="modal">Trigger Now</button>

            <g:link controller="authentication" action="logout" class="btn btn-danger">Log Out</g:link>
        </div>

    </div>

    %{-- Modal dialog --}%
    <div class="modal fade" id="change-password-modal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">Change Password</h4>
                </div>

                <div class="modal-body">
                    <div class="alert alert-danger rc-server-error" role="alert"></div>

                    <g:form controller="profile" action="updatePassword" method="POST" class="form form-horizontal">
                        <div class="form-group">
                            <label for="old-password" class="col-sm-5 control-label">* OLD PASSWORD:</label>

                            <div class="col-sm-6">
                                <input id="old-password" name="old-password" type="password" class="form-control"
                                       placeholder="Enter old password" required/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="new-password" class="col-sm-5 control-label">* NEW PASSWORD:</label>

                            <div class="col-sm-6">
                                <input id="new-password" name="new-password" type="password" class="form-control"
                                       placeholder="Enter new password" required/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="confirm-password" class="col-sm-5 control-label">* CONFIRM PASSWORD:</label>

                            <div class="col-sm-6">
                                <input id="confirm-password" name="confirm-password" type="password" class="form-control"
                                       placeholder="Enter new password again" required/>
                            </div>
                        </div>

                        <div class="error-area error hide error-password" id="confirmPass-error">
                            Passwords do not match, please retype.
                        </div>
                    </g:form>


                </div>

                <div class="modal-footer">
                    <button class="btn btn-default" type="button" data-dismiss="modal">Cancel</button>
                    <button class="create-btn btn btn-primary" type="button"
                            data-loading-text="Creating">Create</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="change-time-modal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">Change Time</h4>
                </div>

                <div class="modal-body">
                    <div class="alert alert-danger rc-server-error" role="alert"></div>

                    <g:form controller="profile" action="changeScheduleTime" method="POST" class="form form-horizontal">
                        <div class="form-group">
                            <label class="col-sm-5 control-label">Last Debug Date:</label>

                            <div class="col-sm-6">
                                <div id="lastDebugDate" class="label-text"></div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="debugDate" class="col-sm-5 control-label">* Debug Date:</label>

                            <div class="col-sm-6">
                                <input id="debugDate" name="debugDate" class="form-control"
                                       placeholder="Enter Debug Date" required/>
                            </div>
                        </div>
                    </g:form>

                </div>

                <div class="modal-footer">
                    <button class="btn btn-default" type="button" data-dismiss="modal">Cancel</button>
                    <button class="create-btn btn btn-primary" type="button"
                            data-loading-text="Creating">Create</button>
                </div>
            </div>
        </div>
    </div>

    </body>
    </html>
</g:applyLayout>
