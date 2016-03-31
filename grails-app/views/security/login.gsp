<!DOCTYPE html>

%{--<g:set var="scriptPath" value="bundles/loginBundle"/>--}%
<g:set var="commonScriptPath" value="dist/commons.chunk.js"/>
<g:set var="scriptPath" value="dist/login.bundle.js"/>
<g:set var="cssPath" value="login"/>
<g:applyLayout name="form">
    <html>
    <head>
        <title>Welcome to Ratchet Admin Portal</title>
    </head>

    <body>
    <div class="content">
        <h1 class="title">Ratchet Admin Portal</h1>

        <div class="col-lg-12 login-form">
            <g:form class="form" uri="/login" method="post" name="loginForm"
                    novalidate="novalidate" autocomplete="off">
                <g:if test="${errorMsg}">
                    <p class="error" id="error_login" rateLimit="${rateLimit}">${errorMsg}</p>
                </g:if>

                <div class="form-group text-left">
                    <label for="email" class="control-label">Email</label>
                    <input type="email" class="form-control" name="email" id="email" placeholder="Email" required/>
                </div>

                <div class="form-group text-left">
                    <label for="password" class="control-label">Password</label>
                    <input type="password" class="form-control" name="password" id="password" placeholder="Password" required/>
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
                        <button type="submit" class="login-btn btn btn-primary">Sign in</button>
                        %{--<input type="button" class="login-btn btn btn-primary" value="Sign in"/>--}%
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
