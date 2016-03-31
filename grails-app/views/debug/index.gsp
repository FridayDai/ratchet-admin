<!DOCTYPE html>
<g:set var="commonScriptPath" value="dist/commons.chunk.js"/>
<g:set var="scriptPath" value="dist/debug.bundle.js"/>
<g:set var="cssPath" value="debug"/>
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
        <div class="btn-group-vertical" id="btn-group-panel">
            <button type="button" id="debug-schedule" class="rc-line-space btn btn-warning"
                    data-toggle="modal" data-target="#change-time-modal">Debug Schedule</button>
            <button type="button" id="resend-confirmation" class="rc-line-space btn btn-warning"
                    data-toggle="modal">Trigger Now</button>

            <div class="btn-group" role="group">
                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Voice Call
                    <span class="caret"></span>
                </button>
                <ul class="dropdown-menu">
                    <li><a href="#"  id="debug-dataTime" data-toggle="modal" data-target="#change-dateTime-modal">Set DateTime</a></li>
                    <li><a href="#"  id="debug-random-hour" data-toggle="modal" data-target="#random-hour-modal">Random Hour</a></li>
                </ul>
            </div>

        </div>

    </div>

    %{-- Modal dialog --}%

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

    <div class="modal fade" id="random-hour-modal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">Random Hour</h4>
                </div>

                <div class="modal-body">
                    <div class="alert alert-danger rc-server-error" role="alert"></div>

                    <g:form action="changeRandomHour" method="POST" class="form form-horizontal">
                        <div class="form-group">
                            <label class="col-sm-5 control-label">Last Random Hour:</label>

                            <div class="col-sm-6">
                                <div id="lastRandomHour" class="label-text"></div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-5 control-label">* Random Hour:</label>

                            <div class="col-sm-6">
                                <div class="form-group">
                                    <select name="randomHour" id="randomHour">
                                        <g:each var="i" in="${(0..<25)}">
                                            <option value="${i}">${i}</option>
                                        </g:each>
                                    </select>
                                </div>

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

    <div class="modal fade" id="change-dateTime-modal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">Change DateTime</h4>
                </div>

                <div class="modal-body">
                    <div class="alert alert-danger rc-server-error" role="alert"></div>

                    <g:form action="changeScheduleDateTime" method="POST" class="form form-horizontal">
                        <div class="form-group">
                            <label class="col-sm-5 control-label">Last Debug DateTime:</label>

                            <div class="col-sm-6">
                                <div id="lastDebugDateTime" class="label-text"></div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="debugDate" class="col-sm-5 control-label">* Debug DateTime:</label>

                            <div class="col-sm-6">
                                <div class="form-group">
                                    <div class='input-group date' id="debugDateTime">
                                        <input name="debugDateTime" class="form-control"
                                               placeholder="DateTime" required/>
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-calendar"></span>
                                        </span>
                                    </div>
                                </div>

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
