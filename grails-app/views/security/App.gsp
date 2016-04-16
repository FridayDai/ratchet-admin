<!DOCTYPE html>
<g:set var="commonScriptPath" value="dist/commons.chunk.js"/>
<g:set var="scriptPath" value="dist/app.bundle.js"/>
<g:set var="cssPath" value="app"/>
<g:applyLayout name="form">

<html>
<head>
    <title>App</title>
</head>

<body>
    <div class="content">
        <div class="container">
            <div class="show-app">
                <p>1. Scan barcode</p>
                <div class="app-barcode">
                    <div>${png}</div>
                    <img src="${png}" alt="wrong!">
                </div>
                <p>Scan the image above with the two-factor authentication app on your phone. </p>
            </div>
            <g:form method="post" url="[controller:'authentication', action:'enableTFA']">
                <div id="authentication">
                    <p>2. Enter the code from the application</p>
                    <input type="text" placeholder="Input QRcode from your App" name="authentication">
                    <button type="submit">Enable two-factor Authentication</button>
                </div>
            </g:form>

        </div>
    </div>
</body>
</html>
</g:applyLayout>
