<%@ page import="org.nanocan.savanah.plates.PlateLayout" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'plateLayout.label', default: 'PlateLayout')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-plateLayout" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                  default="Skip to content&hellip;"/></a>

<div class="navbar">
    <div class="navbar-inner">
        <div class="container">
            <ul class="nav">
                <g:render template="/templates/navmenu"></g:render>
                <li><g:link class="list" action="list"><g:message code="default.list.label"
                                                                  args="[entityName]"/></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                                      args="[entityName]"/></g:link></li>
            </ul>
        </div>
    </div>
</div>

<div id="show-plateLayout" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list plateLayout">

        <g:if test="${plateLayoutInstance?.name}">
            <li class="fieldcontain">
                <span id="name-label" class="property-label"><g:message code="plateLayout.name.label"
                                                                        default="Name"/></span>

                <span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${plateLayoutInstance}"
                                                                                    field="name"/></span>

            </li>
        </g:if>

        <g:if test="${plateLayoutInstance?.format}">
            <li class="fieldcontain">
                <span id="format-label" class="property-label"><g:message code="plateLayout.format.label"
                                                                          default="Format"/></span>

                <span class="property-value" aria-labelledby="format-label"><g:fieldValue bean="${plateLayoutInstance}"
                                                                                          field="format"/></span>

            </li>
        </g:if>

    </ol>
    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="${plateLayoutInstance?.id}"/>
            <g:link class="edit" action="edit" id="${plateLayoutInstance?.id}"><g:message
                    code="default.button.edit.label" default="Edit"/></g:link>
            <g:actionSubmit class="delete" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
