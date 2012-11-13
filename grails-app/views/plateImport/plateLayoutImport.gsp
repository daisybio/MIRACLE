<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'from.savanah', default: 'from SAVANAH')}" />
    <title><g:message code="default.import.label" args="[entityName]" /></title>

    <r:script>$(function() {
        $("#accordion").accordion({
            collapsible:true,
            autoHeight: false
        });

    });</r:script>
</head>
<body>
<a href="#show-plateImport" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="navbar">
    <div class="navbar-inner">
        <div class="container">
            <ul class="nav">
                <g:render template="/templates/navmenu"></g:render>
            </ul>
        </div>
    </div>
</div>
<div id="show-plateImport" class="content scaffold-show" role="main">
    <h1><g:message code="default.import.label" args="[entityName]" /></h1>
    <div class="message" role="status">${flash.message?:"Plates and experiments need to be created in SAVANAH first"}</div>

    <div id="accordion" style="margin: 25px; width: 90%;">
        <h3><a href="#">Select plates for import</a></h3>
        <div>

            Select an experiment:
            <g:select from="${experiments}" name="experiment" value="${experiment}" onchange="${g.remoteFunction(action: 'filterPlateLayouts', params: '\'&experiment=\'+this.value', update: 'plateSelect')}"/>
            <br/><br/>
            <g:form name="importPlateLayouts" action="importSelectedPlateLayouts" controller="plateImport">
            <div id="plateSelect">
                <g:select name="platesSelected" optionKey="id" optionValue="name" multiple="true" size="20" from="${plateLayouts}"/>
            </div>         <br/><br/>
            <g:submitButton name="import selected layouts"/>
            </g:form>
        </div>
    </div>



</div>
</body>
</html>
