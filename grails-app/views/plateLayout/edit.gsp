<%@ page import="org.nanocan.rppa.layout.PlateLayout" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'plateLayout.label', default: 'PlateLayout')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
</head>

<body>
<a href="#edit-plateLayout" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
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

<div id="edit-plateLayout" class="content scaffold-edit" role="main">
    <h1><g:message code="default.edit.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${plateLayoutInstance}">
        <ul class="errors" role="alert">
            <g:eachError bean="${plateLayoutInstance}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                        error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <g:form method="post">
        <g:hiddenField name="id" value="${plateLayoutInstance?.id}"/>
        <g:hiddenField name="version" value="${plateLayoutInstance?.version}"/>
        <fieldset class="form">
            <div class="fieldcontain ${hasErrors(bean: plateLayoutInstance, field: 'name', 'error')} required">
                <label for="name">
                    <g:message code="plateLayout.name.label" default="Name" />
                    <span class="required-indicator">*</span>
                </label>
                <g:textField name="name" required="" value="${plateLayoutInstance?.name}"/>
            </div>
        </fieldset>
        <fieldset class="buttons">
            <g:actionSubmit class="save" action="update"
                            value="${message(code: 'default.button.update.label', default: 'Update')}"/>
            <g:actionSubmit class="delete" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate=""
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
