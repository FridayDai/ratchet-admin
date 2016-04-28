<!DOCTYPE html>
<g:set var="commonScriptPath" value="dist/commons.chunk.js"/>
<g:set var="scriptPath" value="dist/key.bundle.js"/>
<g:set var="cssPath" value="key"/>
<g:applyLayout name="main">
    <html>
    <head>
        <title>Key</title>
    </head>
    <body>
    <div class="content">
        <div class="container">
            <g:if test="${session.key}">
                <div class="show-key">
                    <p>1. Get your key</p>
                    <div class="sms-key">
                        <p>This is your Key to enable two-factor authentication</p>
                        <div>${key}</div>
                    </div>
                </div>
                <g:form name="loginForm" method="post" url="[controller:'authentication', action:'enableTFA']">
                    <div id="authentication">
                        <p>2. Enter the Key</p>
                        <input name="otp" type="text" placeholder="Input Your Key" name="authentication">
                        <button class="btn btn-lg btn-primary" type="submit">Enable two-factor authentication</button>
                    </div>
                </g:form>
            </g:if>
            <g:if test="${!session.key}">
                <p id="AlreadyEnabled"> Already Enabled!</p>
            </g:if>

        </div>

    </div>
    </body>
    </html>
</g:applyLayout>



