<!DOCTYPE html>
<g:set var="commonScriptPath" value="dist/commons.chunk.js"/>
<g:set var="scriptPath" value="dist/beforeTFA.bundle.js"/>
<g:set var="cssPath" value="beforeTFA"/>
<g:applyLayout name="main">
    <html>
    <head>
        <title>Before Two-Factor Authentication</title>
    </head>
    <body>
    <div class="content">
        <div class="container">
            <div class="app-or-key">
                <div id="app">
                    <div class="context-app">
                        Use an application on your phone to get two-factor authentication codes when prompted.
                    </div>
                    <g:form name="app" url="[controller:'authentication', action:'goToApp']">
                        <button class="btn btn-lg btn-primary" type="submit">Set up using an APP</button>
                    </g:form>
                </div>
                <div id="key">
                    <div class="context-key">
                        Use a key for two-factor authentication code when prompted.
                    </div>
                    <g:form name="key" url="[controller: 'authentication', action: 'goToKey']">
                        <button class="btn btn-lg btn-primary" type="submit">Set up using a key</button>
                    </g:form>
                </div>
            </div>
        </div>

    </div>
    </body>
    </html>
</g:applyLayout>




