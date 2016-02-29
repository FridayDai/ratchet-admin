<%@ page import="com.ratchethealth.admin.RatchetConstants" %>
<div class="main-nav nav">
    <ul id="menu" class="list">
        <g:if test="${request.session.groups.contains(com.ratchethealth.admin.RatchetConstants.ROLE_MANAGER) || request.session.groups.contains(com.ratchethealth.admin.RatchetConstants.ROLE_QA)}">
            <li class="nav-li <g:if test="${controllerName == 'clients' || controllerName == 'home'}">active</g:if>">
                <g:link controller="clients" action="index">
                    <div class="ui-icon fa fa-h-square fa-2x"></div>
                    <span>CLIENTS</span>
                </g:link>
            </li>
        </g:if>
        <g:if test="${request.session.groups.contains(com.ratchethealth.admin.RatchetConstants.ROLE_ADMIN)}">
            <li class="nav-li <g:if test="${controllerName == 'accounts'}">active</g:if>">
                <g:link controller="accounts" action="index">
                    <div class="ui-icon fa fa-users fa-2x"></div>
                    <span>ACCOUNTS</span>
                </g:link>
            </li>
            <li class="nav-li <g:if test="${controllerName == 'announcements'}">active</g:if>">
                <g:link controller="announcements" action="index">
                    <div class="ui-icon fa fa-bullhorn fa-2x"></div>
                    <span>ANNOUNCE</span>
                </g:link>
            </li>
            <li class="nav-li <g:if test="${controllerName == 'HL7'}">active</g:if>">
                <g:link controller="HL7" action="index">
                    <div class="ui-icon fa fa-tachometer fa-2x"></div>
                    <span>HL7</span>
                </g:link>
            </li>
        </g:if>
        <g:if test="${request.session.groups.contains(com.ratchethealth.admin.RatchetConstants.ROLE_QA)}">
            <li class="nav-li <g:if test="${controllerName == 'testdata'}">active</g:if>">
                <g:link controller="testData" action="index">
                    <div class="ui-icon fa fa-database fa-2x"></div>
                    <span>TEST DATA</span>
                </g:link>
            </li>
            <g:if test="${Boolean.valueOf(grailsApplication.config.ratchetv2.server.debug)}">
                <li class="nav-li <g:if test="${controllerName == 'debug'}">active</g:if>">
                    <g:link controller="debug" action="index">
                        <div class="ui-icon fa fa-bug fa-2x"></div>
                        <span>DEBUG</span>
                    </g:link>
                </li>
            </g:if>
        </g:if>
        <li class="nav-li <g:if test="${controllerName == 'authentication'}">active</g:if>">
            <g:link controller="profile" action="goToProfilePage">
                <div class="ui-icon fa fa-user fa-2x"></div>
                <span>PROFILE</span>
            </g:link>
        </li>
    </ul>
</div>
