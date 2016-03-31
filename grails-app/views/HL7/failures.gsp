<!DOCTYPE html>

<g:set var="commonScriptPath" value="dist/commons.chunk.js"/>
<g:set var="cssPath" value="hl7/failures"/>
<g:set var="scriptPath" value="dist/hl7Failures.bundle.js"/>
<g:applyLayout name="main">
    <html>
    <head>
        <title>Welcome to ratchet</title>
    </head>

    <body>
    <div class="content">
        <ul class="nav nav-tabs">
            <li role="presentation"><a href="/hl7/reporting">Reporting</a></li>
            <li role="presentation" class="active"><a>Failures</a></li>
        </ul>
        <table class="failures-table table table-hover">
            <thead>
            <tr>
                <td width="10%">Job ID</td>
                <td width="10%">Client</td>
                <td width="10%">File Name</td>
                <td width="15%">Hashed File Name</td>
                <td width="12%">Received Date</td>
                <td width="10%">Fail Reason</td>
                <td width="25%">Fail Desc</td>
                <td width="8%"></td>
            </tr>
            </thead>
            <tbody>
            <g:each var="errorItem" in="${errorList}">
            <tr>
                <td class="job-id">${errorItem.jobId}</td>
                <td>${errorItem.clientName}</td>
                <td>${errorItem.fileName}</td>
                <td class="hash-name-cell">${errorItem.hashedFileName}</td>
                <td>${errorItem.receivedDate}</td>
                <td>${errorItem.failReason}</td>
                <td>${errorItem.failDesc}</td>
                <td><g:if test="${!errorItem?.isProcessing}"><button>Retry</button></g:if><g:else>Processing...</g:else></td>
            </tr>
            </g:each>
            </tbody>
        </table>
    </div>
    </body>
    </html>
</g:applyLayout>
