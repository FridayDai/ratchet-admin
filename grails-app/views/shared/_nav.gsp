<%@ page import="com.ratchethealth.admin.RatchetConstants" %>
<div class="nav">
    <ul id="menu" class="list">
        <g:if test="${request.session.groups.contains(com.ratchethealth.admin.RatchetConstants.ROLE_MANAGER) || request.session.groups.contains(com.ratchethealth.admin.RatchetConstants.ROLE_QA)}">
            <li class="nav-li <g:if test="${controllerName == 'clients' || controllerName == 'home'}">active</g:if>">
                <g:link controller="clients" action="index">
                    <div class="ui-icon icon-client"></div>
                    <span>CLIENTS</span>
                </g:link>
            </li>
        </g:if>
        <g:if test="${request.session.groups.contains(com.ratchethealth.admin.RatchetConstants.ROLE_ADMIN)}">
            <li class="nav-li <g:if test="${controllerName == 'accounts'}">active</g:if>">
                <g:link controller="accounts" action="index">
                    <div class="ui-icon icon-account"></div>
                    <span>ACCOUNTS</span>
                </g:link>
            </li>
            <li class="nav-li <g:if test="${controllerName == 'announcements'}">active</g:if>">
                <g:link controller="announcements" action="index">
                    <div class="ui-icon icon-announcements"></div>
                    <span>ANNOUNCE</span>
                </g:link>
            </li>
        </g:if>
        <g:if test="${request.session.groups.contains(com.ratchethealth.admin.RatchetConstants.ROLE_QA)}">
            <li class="nav-li <g:if test="${controllerName == 'testdata'}">active</g:if>">
                <g:link controller="testData" action="index">
                    <div class="ui-icon icon-announcements"></div>
                    <span>TEST DATA</span>
                </g:link>
            </li>
            <g:if test="${Boolean.valueOf(grailsApplication.config.ratchetv2.server.debug)}">
                <li class="nav-li <g:if test="${controllerName == 'debug'}">active</g:if>">
                    <g:link controller="debug" action="index">
                        <div class="ui-icon icon-debug"></div>
                        <span>DEBUG</span>
                    </g:link>
                </li>
            </g:if>
        </g:if>
        <li class="nav-li <g:if test="${controllerName == 'authentication'}">active</g:if>">
            <g:link controller="profile" action="goToProfilePage">
                <div class="ui-icon icon-account"></div>
                <span>PROFILE</span>
            </g:link>
        </li>
    </ul>
</div>
