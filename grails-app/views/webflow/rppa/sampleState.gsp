<%@ page import="org.nanocan.rppa.rnai.Sample" %>
<%@ page import="org.nanocan.rppa.rnai.Identifier" %>

<!doctype html>
<html>
<head>
    <meta name="layout" content="webflow">
    <g:set var="entityName" value="${message(code: 'sample.label', default: 'Sample')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>
<a href="#create-sample" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                               default="Skip to content&hellip;"/></a>



<div id="create-sample" class="content scaffold-create" role="main">
    <h1><g:message code="default.create.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${sampleInstance}">
        <ul class="errors" role="alert">
            <g:eachError bean="${sampleInstance}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                        error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <g:form action="rppa">
        <fieldset class="form">
            <g:render template="sampleStateForm"/>
        </fieldset>
        <fieldset class="buttons">
            <g:submitButton name="save" class="save"
                            value="${message(code: 'default.button.create.label', default: 'Create')}"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
