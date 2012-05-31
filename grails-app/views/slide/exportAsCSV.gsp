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
        <g:form name="exportToCSVform" action ="processExport">
            <g:hiddenField name="id" value="${slideInstance.id}"></g:hiddenField>

            <ol class="property-list">
                <li class="fieldcontain">
                    <span class="property-label">Select a column separator: </span>
                    <span class="property-value"><g:select name="separator" optionKey="key" optionValue="value" value=";" from="${separatorMap}"/>
                    </span>
                </li>
                <li class="fieldcontain">
                    <span class="property-label">Exclude spots with flags != 0</span>
                    <span class="property-value"><g:checkBox name="excludeBadFlags" value="${true}"/></span>
                </li>
                <li class="fieldcontain">
                    <span class="property-label">Exclude spots with<br/> diameter >= 250</span>
                    <span class="property-value"><g:checkBox name="excludeBadDiameter" value="${true}"/></span>
                </li>
                <li class="fieldcontain">
                    <span class="property-label">Exclude spots with negative signal (FG-BG) values</span>
                    <span class="property-value"><g:checkBox name="excludeBadSignal" value="${true}"/></span>
                </li>
                <li class="fieldcontain">
                    <span class="property-label">Decimal separator</span>
                    <span class="property-value"><g:select name="decimalSeparator" value="," from="${[',', '.']}"/></span>
                </li>
                <li class="fieldcontain">
                    <span class="property-label">Decimal precision</span>
                    <span class="property-value"><g:select name="decimalPrecision" value="3" from="${1..10}"/></span>
                </li>
                <li class="fieldcontain">
                    <span class="property-label">Select properties for export</span>
                    <span class="property-value">
                        <g:select name="selectedProperties" value="${slideProperties}" from="${slideProperties}" multiple="${true}" size="${10}"/>
                    </span>
                </li>

                <li class="fieldcontain">
                    <span class="property-label">Include block shifts (columns hshift and vshift)</span>
                    <span class="property-value"><g:checkBox name="includeBlockShifts" value="${true}"/></span>
                </li>
            </ol>


            <fieldset class="buttons"><g:submitButton onclick="outsideFunction();" name="Export"/></fieldset>
        </g:form>
    </div>
</div>
</body>
</html>