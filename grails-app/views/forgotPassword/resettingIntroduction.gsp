<!DOCTYPE html>

<g:set var="cssPath" value="resetPassword"/>
<g:applyLayout name="form">

    <html>
    <head>
        <title>Welcome to Ratchet Health</title>
    </head>

    <body>

    <div class="site-wrapper">
        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <div class="panel panel-border">

                    <div class="panel-heading">
                        <div class="help-block">
                            <p class="center">
                                Please follow the instructions sent to
                                <label class="bolder">${email}</label> to reset your password.</p>
                        </div>
                    </div>

                    <div class="panel-body">
                        <div class="col-lg-12">
                            <div class="form-group">
                                <div class="row">
                                    <div class="col-sm-6 col-sm-offset-4">
                                        <g:link uri="/login" class="btn btn-primary">Back to Log In</g:link>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>

    </body>
    </html>
</g:applyLayout>
