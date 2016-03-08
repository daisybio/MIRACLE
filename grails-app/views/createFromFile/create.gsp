<%@ page import="org.nanocan.layout.SlideLayout" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'slideLayout.label', default: 'SlideLayout')}" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>
</head>
<body>
<a href="#create-slideLayout" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="navbar">
    <div class="navbar-inner">
        <div class="container">
            <ul class="nav">
                <g:render template="/templates/navmenu"></g:render>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
    </div>
</div>

<div id="create-slideLayout" class="content scaffold-create" role="main">
    <h1><g:message code="default.create.label" args="[entityName]" /></h1>
    <div class="message" role="status">${flash.message?:"Important: Column means \"deposition blocks\" here, e.g. 6 different depositions spotted twice result in 2 columns."}</div>
    <g:hasErrors bean="${slideLayoutInstance}">
        <ul class="errors" role="alert">
            <g:eachError bean="${slideLayoutInstance}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <g:form name="uploadSheet" method="POST" enctype="multipart/form-data">
        <fieldset class="form">
            <g:render template="../slideLayout/form"/>
        </fieldset>
        <div class="fieldcontain ${hasErrors(bean: slideLayoutInstance, field: 'layoutFile', 'error')} ">
            <label for="layoutFile">
                <g:message code="slideLayout.layoutFile.label" default="Layout File" />
                <span class="required-indicator">*</span>
            </label>
            <input type="file" name="layoutFileInput"/>
        </div>

        <fieldset class="buttons">
            <g:submitButton name="upload" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
        </fieldset>
    </g:form>
</div>
</body>
</html>

