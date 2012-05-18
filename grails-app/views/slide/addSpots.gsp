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
    <h1>Add Spots</h1>

        <div id="updateDiv" class="message">Select a sheet and configuration</div>
        <g:jprogressDialog message="Adding spots to database..." progressId="excelimport" trigger="buttonAddSpots" />
        <div id="formDiv">
        <g:form>

            <g:hiddenField name="id" value="${slideInstance.id}"></g:hiddenField>

            <ol class="property-list">
            <li class="fieldcontain">
                <span class="property-label">Select the sheet with the scanner results of the slide: </span>
                <span class="property-value"><g:select name="sheet" from="${sheets}" optionKey="value" optionValue="value"></g:select>
                </span>
            </li>
            <li class="fieldcontain">
                <span class="property-label">Select a suitable mapping of column names to spot properties:</span>
                <span class="property-value"><g:select name="config" from="${configs}" optionKey="id"></g:select></span>
            </li>
            </ol>

            <fieldset class="buttons"><g:submitToRemote onLoading="\$('#formDiv').hide() ; \$('#updateDiv').hide();" onSuccess="\$('#updateDiv').show();" update="updateDiv" action="processResultFile" name="buttonAddSpots" value="Add spots from selected sheet to slide"/></fieldset>
        </g:form>
        </div>
    </div>
    </body>
</html>