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
            <g:select from="${experiments}" name="experiment" value="${experiment}" onchange="${g.remoteFunction(action: 'filterPlates', params: '\'&experiment=\'+this.value', update: 'plateSelect')}"/>
            <br/><br/>
            Select plates by dragging them from left to right. Order them by drag and drop, then press check<br/><br/>
            <div style="padding-left:163px; padding-bottom: 10px; width:143px;">Selected plates:</div>
            <div id="plateSelect">
                <g:render template="plateSorting" model="${[plates: plates]}"/>
            </div>

            <input style="padding:10px;margin-top:10px;"
                   class="buttons" value="Check"
                   type="button" name="continue"
                   onclick="var plateOrder = $('#selectedPlates').sortable('serialize');${g.remoteFunction(action: 'platesOrdered', update:'selectedPlatesInOrder', params: '\'&\'+plateOrder')}"/>

            <div id="selectedPlatesInOrder" style="float:right;padding:5px;">
                No plates have been selected
            </div>
        </div>
    </div>



</div>
</body>
</html>
