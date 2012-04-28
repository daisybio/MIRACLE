<%@ page contentType="text/html;charset=UTF-8" %>

<html>
	<head>
		<meta name="layout" content="main">
        <g:set var="entityName" value="${message(code: 'sheet.label', default: 'Sheet')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>

        <g:form action="processResultFile">

            <g:hiddenField name="id" value="${slideInstance.id}"></g:hiddenField>


            Select the sheet with the scanner results of the slide:
            <g:select name="sheet" from="${sheets}" optionKey="value" optionValue="value"></g:select></br>

            Select a suitable mapping of column names to spot properties:
            <g:select name="config" from="${configs}" optionKey="id"></g:select><br/>

            <g:submitButton name="Add spots from selected sheet to slide"/>
        </g:form>
    </body>
</html>