<!DOCTYPE html>

<g:set var="scriptPath" value="bundles/loginBundle"/>
<g:set var="cssPath" value="login"/>
<g:applyLayout name="form">
    <html>
    <head>
        <title>Welcome to Ratchet Admin Portal</title>
    </head>

    <body>
    <div class="content">
        <h1 class="title">Ratchet Admin Portal</h1>

        <div class="col-lg-12">
            <g:form class="form" controller="authentication" method="post" action="login" name="loginForm"
                    novalidate="novalidate">
                <g:if test="${errorMsg}">
                    <p class="error" id="error_login" rateLimit="${rateLimit}">${errorMsg}</p>
                </g:if>

                <div class="form-group text-left">
                    <label for="email">Email</label>
                    <input type="email" class="form-control" name="email" id="email" placeholder="Email">
                </div>

                <div class="form-group text-left">
                    <label for="password" class="control-label">Password</label>
                    <input type="password" class="form-control" name="password" id="password" placeholder="Password">
                </div>
            %{--<input type="hidden" name="back" value="${backUrl}"/>--}%
            %{--<div class="form-group">--}%
            %{--<div class="col-sm-offset-2 col-sm-10">--}%
            %{--<div class="checkbox">--}%
            %{--<label>--}%
            %{--<input type="checkbox"> Remember me--}%
            %{--</label>--}%
            %{--</div>--}%
            %{--</div>--}%
            %{--</div>--}%
                <div class="form-group row">
                    <div class="col-sm-offset-2 col-sm-8">
                        <button type="submit" class="btn btn-primary">Sign in</button>
                    </div>
                </div>

                <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-8">
                                <g:link uri="/forgot-password" tabindex="5"
                                        class="forgot-password">Forgot Password?</g:link>
                        </div>
                </div>

            </g:form>
        </div>

    </div>
    </body>
    </html>
</g:applyLayout>
