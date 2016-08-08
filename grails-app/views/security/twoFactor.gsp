<!DOCTYPE html>
<g:set var="commonScriptPath" value="dist/commons.chunk.js"/>
<g:set var="scriptPath" value="dist/twoFactorAuthentication.bundle.js"/>
<g:set var="cssPath" value="login"/>
<g:applyLayout name="form">
<html>
<head>
    <title>Two Factor Authentication</title>
</head>
<body>
    <div class="content">
        <div class="container" id="AuthenticationContent">
            <div class="auth-form" id="login">

                    <div class="auth-form-header">
                        <h1>Two-factor Authentication</h1>
                    </div>
                <g:form url="[controller:'Authentication', action:'twoFactorAuthenticationVerify']" method="POST" name="TFAVerify">
                    <div class="auth-form-body">
                        <label for="otp">Authentication code</label>
                        <input type="text" id="otp" name="otp" class="form-control" maxlength="8">
                        <g:if test="${request.session.authen == false}">
                            <div class="sms-error">Wrong authentication codes, Please try again.</div>
                        </g:if>

                        <button class="btn btn-lg btn-primary" type="submit">Verify</button>
                    </div>
                </g:form>

            </div>
        </div>
    </div>
    <div class="twofooter">
        <span class="copyright-year">© 2016</span>
        <div class="reserved">All rights reserved.</div>
    </div>
</body>
</html>
</g:applyLayout>
