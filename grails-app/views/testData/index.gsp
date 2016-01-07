<!DOCTYPE html>
<g:set var="cssPath" value="testData"/>
<g:applyLayout name="main">
    <html>
    <head>
        <title>Welcome to ratchet</title>
    </head>

    <body>

    <div class="content">

        <div class="row">
            <div class="col-lg-4">
                <g:if test="${anonyLinks}">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <td>Anonymized Data</td>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${anonyLinks}" var="link">
                            <% def anonyPattern = ~/(\d{4}\-\d{2}\-\d{2})/ %>
                            <% def anonyLinkDate = anonyPattern.matcher(link) %>
                            <tr>
                                <td class="col-md-4">
                                    <a href="${link}" target="_blank">${anonyLinkDate[0][0]}</a>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </g:if>
                <g:else>
                    <p> <strong>There is no anonymized data.</strong></p>
                </g:else>
            </div>
            <div class="col-lg-4">
                <g:if test="${nonAnonyLinks}">
                    <table class="table table-striped" >
                        <thead>
                        <tr>
                            <td >Non-Anonymized Data</td>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${nonAnonyLinks}" var="link">
                            <% def pattern = ~/(\d{4}\-\d{2}\-\d{2})/ %>
                            <% def linkDate = pattern.matcher(link) %>
                            <tr>
                                <td class="col-md-4">
                                    <a href="${link}" target="_blank">${linkDate[0][0]}</a>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </g:if>
                <g:else>
                    <p> <strong>There is no non-anonymized data.</strong></p>
                </g:else>
            </div>
        </div>

        <form method="post">
            <button class="btn btn-default btn-primary" type="submit">Generate anonymized snapshot</button>
            <input type="hidden" name="isDataAnonymized" value="true" />
        </form>

        <g:if test="${isDebug}">
            <form method="post">
                <button class="btn btn-default btn-primary" type="submit">Generate snapshot</button>
                <input type="hidden" name="isDataAnonymized" value="false" />
            </form>
        </g:if>

    </div>
    </body>
    </html>
</g:applyLayout>
