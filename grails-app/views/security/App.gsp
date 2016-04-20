<!DOCTYPE html>
<g:set var="commonScriptPath" value="dist/commons.chunk.js"/>
<g:set var="scriptPath" value="dist/app.bundle.js"/>
<g:set var="cssPath" value="app"/>
<g:applyLayout name="main">
    <html>
    <head>
        <title>App</title>
    </head>
    <body>
    <div class="content">
        <div class="container">
            <div class="show-app">
                <g:if test="${session.keyUrl}">
                    <p>1. Scan barcode</p>
                    <div class="app-barcode">
                        <qrcode:image text="${QRcode}"></qrcode:image>
                        <p>Scan the image above with the two-factor authentication app on your phone. </p>
                    </div>
                    <g:form method="post" url="[controller:'authentication', action:'enableTFA']">
                        <div id="authentication">
                            <p>2. Enter the code from the application</p>
                            <input type="text" placeholder="Input QRcode from your App" name="otp">
                            <button class="btn btn-lg btn-primary" type="submit">Enable two-factor Authentication</button>
                        </div>
                    </g:form>
                </g:if>
                <g:if test="${!session.keyUrl}">
                    <p id="AlreadyEnabled">Already Enabled!</p>
                </g:if>
            </div>
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

