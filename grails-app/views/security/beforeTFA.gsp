
<!DOCTYPE html>
<g:set var="commonScriptPath" value="dist/commons.chunk.js"/>
<g:set var="scriptPath" value="dist/beforeTFA.bundle.js"/>
<g:set var="cssPath" value="beforeTFA"/>
<g:applyLayout name="form">
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
                        <button type="submit">Set up using an APP</button>
                    </g:form>
                </div>
                <div id="key">
                    <div class="context-key">
                        Use a key for two-factor authentication code when prompted.
                    </div>
                    <g:form name="key" url="[controller: 'authentication', action: 'goToKey']">
                        <button type="submit">Set up using a key</button>
                    </g:form>
                </div>
            </div>
            %{--<div id="disable">--}%
                %{--<g:form name="disableForm" url="[controller: 'authentication', action:'disableTFA']">--}%
                    %{--<button type="submit">Disable</button>--}%
                %{--</g:form>--}%

            %{--</div>--}%
        </div>
    </div>
</body>
</html>
</g:applyLayout>
