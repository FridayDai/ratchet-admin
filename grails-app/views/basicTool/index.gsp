<!DOCTYPE html>

<g:set var="commonScriptPath" value="dist/commons.chunk.js"/>
<g:set var="scriptPath" value="dist/basicTool.bundle.js"/>
<g:set var="cssPath" value="basicTool"/>
<g:applyLayout name="main">
    <html>
    <head>
        <title>Welcome to ratchet</title>
    </head>

    <body>
    <div class="content">
        <div id="basic-tool-tool-bar" class="tool-bar clearfix row">
            <div class="col-lg-6 pull-right">
                <button type="button" id="add-basic-tool" class="pull-right rc-line-space btn btn-primary"
                        data-toggle="modal">New Basic Tool</button>
            </div>
        </div>

        <div>
            <table id="basic-tool-table" class="display" data-total="${basicToolTemplates.recordsTotal}" data-pagesize="${pagesize}">
                <thead>
                <tr>
                    <td>ID</td>
                    <td>Title</td>
                    <td>Description</td>
                    <td>Testing</td>
                </tr>
                </thead>
                <tbody>
                <g:each var="template" in="${basicToolTemplates.data}" status="i">
                    <tr data-questionnaire-title="${template.basicToolTitle}"
                        data-questionnaire-content="${template.basicToolContent}"
                    >
                        <td>${template.id}</td>
                        <td>${template.title}</td>
                        <td>${template.description}</td>
                        <td>${template.isTesting}</td>
                    </tr>
                </g:each>
                </tbody>
            </table>
        </div>

        %{-- Modal dialog --}%
        <div class="modal fade" id="basic-tool-modal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title">New Basic Tool</h4>
                    </div>
                    <div class="modal-body">
                        <div class="alert alert-danger rc-server-error" role="alert"></div>
                            <form action="/basic_tool" method="post" class="form form-horizontal"
                                enctype="multipart/form-data" novalidate="novalidate">
                                <div class="form-group">
                                    <label for="isTesting" class="col-sm-3 control-label">Testing:</label>

                                    <div class="col-sm-9">
                                        <input id="isTesting" name="isTesting" type="checkbox" checked/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="title" class="col-sm-3 control-label">* Title:</label>

                                    <div class="col-sm-9">
                                        <input id="title" name="title" type="text" class="form-control" required/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="description" class="col-sm-3 control-label">* Description:</label>

                                    <div class="col-sm-9">
                                        <input id="description" name="description" type="text" class="form-control" required/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="questionnaireTitle" class="col-sm-3 control-label">* Questionnaire Title:</label>

                                    <div class="col-sm-9">
                                        <input id="questionnaireTitle" name="questionnaireTitle" type="text" class="form-control" required/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="questionnaireContent" class="col-sm-3 control-label">* Questionnaire Content:</label>

                                    <div class="col-sm-9">
                                        <textarea class="form-control" name="questionnaireContent" id="questionnaireContent" cols="30"
                                                  rows="30" required></textarea>
                                    </div>
                                </div>
                            </form>
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
