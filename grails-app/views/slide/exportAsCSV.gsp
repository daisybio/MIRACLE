<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'sheet.label', default: 'Sheet')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="show" id="${slideInstance.id}">Back to slide</g:link></li>
    </ul>
</div>

<div class="content">
    <h1>Export Spots to CSV</h1>

    <div id="formDiv">
        <g:form action="processExport">
            <g:hiddenField name="id" value="${slideInstance.id}"></g:hiddenField>

            <ol class="property-list">
                <li class="fieldcontain">
                    <span class="property-label">Select a column separator: </span>
                    <span class="property-value"><g:select name="separator" optionKey="key" optionValue="value" value=";" from="${separatorMap}"/>
                    </span>
                </li>
            </ol>

            <fieldset class="buttons"><g:submitButton name="Export"/></fieldset>
        </g:form>
    </div>
</div>
</body>
</html>