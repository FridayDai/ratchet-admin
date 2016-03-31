<!DOCTYPE html>

<g:set var="commonScriptPath" value="dist/commons.chunk.js"/>
<g:set var="cssPath" value="hl7/reporting"/>
<g:applyLayout name="main">
    <html>
    <head>
        <title>Welcome to ratchet</title>
    </head>

    <body>
    <div class="content">
        <ul class="nav nav-tabs">
            <li role="presentation" class="active"><a>Reporting</a></li>
            <li role="presentation"><a href="/hl7/failures">Failures</a></li>
        </ul>
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Queue Status</h3>
            </div>
            <div class="panel-body">
                <div class="overview-heros message-in col-md-3 col-md-offset-3">
                    <div class="hero-text">${queueStatus?.data?.get(0)?.'approximateNumberOfMessages'}</div>
                    <div class="sub-text">Messages</div>
                </div>
                <div class="overview-heros message-delayed col-md-3">
                    <div class="hero-text">${queueStatus?.data?.get(0)?.'approximateNumberOfMessagesDelayed'}</div>
                    <div class="sub-text">Messages Delayed</div>
                </div>
            </div>
        </div>

        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Message Status</h3>
            </div>
            <div class="panel-body">
                <div class="panel-body">
                    <g:each var="item" in="${messageStatus?.data}">
                    <div class="overview-heros col-md-2">
                        <div class="hero-text">${item.count}</div>
                        <div class="sub-text">${item.status}</div>
                    </div>
                    </g:each>
                </div>
            </div>
        </div>
    </div>
    </body>
    </html>
</g:applyLayout>
