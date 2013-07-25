<%@ page import="org.nanocan.rppa.layout.Dilution" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="webflow">
    <g:set var="entityName" value="${message(code: 'dilution.label', default: 'Dilution')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>
<a href="#create-dilution" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                 default="Skip to content&hellip;"/></a>

<div id="create-dilution" class="content scaffold-create" role="main">
    <h1><g:message code="default.create.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${dilutionInstance}">
        <ul class="errors" role="alert">
            <g:eachError bean="${dilutionInstance}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                        error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <g:form action="rppa">
        <fieldset class="form">
            <g:render template="dilutionFactorStateForm"/>
        </fieldset>
        <fieldset class="buttons">
            <g:submitButton name="save" class="save"
                            value="${message(code: 'default.button.create.label', default: 'Create')}"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
