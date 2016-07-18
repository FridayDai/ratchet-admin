<!DOCTYPE html>
<g:set var="commonScriptPath" value="dist/commons.chunk.js"/>
<g:set var="scriptPath" value="dist/twoFactorAuthentication.bundle.js"/>
<g:set var="cssPath" value="app"/>
<g:applyLayout name="main">
    <html>
    <head>
        <title>App</title>
    </head>
    <body>
    <div class="content">
        <div class="container">
            <div class="show-app auth-form">
                <g:if test="${session.keyUrl}">
                    <p>1. Scan barcode</p>
                    <div class="app-barcode">
                        <qrcode:image text="${QRcode}"></qrcode:image>
                        <p>Scan the image above with the two-factor authentication app on your phone. </p>
                    </div>
                    <g:form method="post" url="[controller:'authentication', action:'enableTFA']">
                        <div id="authentication">
                            <p>2. Enter the code from the application</p>
                            <input id="otp" type="text" placeholder="Input QRcode from your App" name="otp" maxlength="6">
                            <g:if test="${request.session.MFAValidationRequired == false}">
                                <div class="text-danger">${errorMsg}</div>
                            </g:if>
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
    </body>
    </html>
</g:applyLayout>

