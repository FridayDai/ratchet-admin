<!DOCTYPE html>
<g:set var="commonScriptPath" value="dist/commons.chunk.js"/>
<g:set var="scriptPath" value="dist/key.bundle.js"/>
<g:set var="cssPath" value="key"/>
<g:applyLayout name="form">

<html>
<head>
    <title>Key</title>
</head>

<body>
    <div class="content">
        <div class="container">
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
                    <input name="key" type="text" placeholder="Input Your Key" name="authentication">
                    <button type="submit">Enable two-factor authentication</button>
                </div>
            </g:form>

        </div>
    </div>
</body>
</html>
</g:applyLayout>
