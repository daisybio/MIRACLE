<%@ page import="org.nanocan.rppa.security.Person" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'person.label', default: 'Person')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-person" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                             default="Skip to content&hellip;"/></a>

<div class="navbar">
    <div class="navbar-inner">
        <div class="container">
            <ul class="nav">
                <g:render template="/templates/navmenu"></g:render>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
            </ul>
        </div>
    </div>
</div>

<div id="show-person" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list person">

        <g:if test="${personInstance?.username}">
            <li class="fieldcontain">
                <span id="username-label" class="property-label"><g:message code="person.username.label"
                                                                            default="Username"/></span>

                <span class="property-value" aria-labelledby="username-label"><g:fieldValue bean="${personInstance}"
                                                                                            field="username"/></span>

            </li>
        </g:if>

        <g:if test="${personInstance?.accountExpired}">
            <li class="fieldcontain">
                <span id="accountExpired-label" class="property-label"><g:message code="person.accountExpired.label"
                                                                                  default="Account Expired"/></span>

                <span class="property-value" aria-labelledby="accountExpired-label"><g:formatBoolean
                        boolean="${personInstance?.accountExpired}"/></span>

            </li>
        </g:if>

        <g:if test="${personInstance?.accountLocked}">
            <li class="fieldcontain">
                <span id="accountLocked-label" class="property-label"><g:message code="person.accountLocked.label"
                                                                                 default="Account Locked"/></span>

                <span class="property-value" aria-labelledby="accountLocked-label"><g:formatBoolean
                        boolean="${personInstance?.accountLocked}"/></span>

            </li>
        </g:if>

        <g:if test="${personInstance?.enabled}">
            <li class="fieldcontain">
                <span id="enabled-label" class="property-label"><g:message code="person.enabled.label"
                                                                           default="Enabled"/></span>

                <span class="property-value" aria-labelledby="enabled-label"><g:formatBoolean
                        boolean="${personInstance?.enabled}"/></span>

            </li>
        </g:if>

        <g:if test="${personInstance?.passwordExpired}">
            <li class="fieldcontain">
                <span id="passwordExpired-label" class="property-label"><g:message code="person.passwordExpired.label"
                                                                                   default="Password Expired"/></span>

                <span class="property-value" aria-labelledby="passwordExpired-label"><g:formatBoolean
                        boolean="${personInstance?.passwordExpired}"/></span>

            </li>
        </g:if>


        <li class="fieldcontain">
            <span id="person-isAdmin-label" class="property-label"><g:message code="person.isAdmin.label"
                                                                               default="Administrator"/></span>

            <span class="property-value" aria-labelledby="person-isAdmin-label"><g:formatBoolean
                    boolean="${isAdmin}"/></span>

        </li>

    </ol>
    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="${personInstance?.id}"/>
            <g:link class="edit" action="edit" id="${personInstance?.id}"><g:message code="default.button.edit.label"
                                                                                     default="Edit"/></g:link>
            <g:actionSubmit class="delete" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
