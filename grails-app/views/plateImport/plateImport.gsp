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

    <div id="accordion" style="margin: 25px; width: 90%;">
        <h3><a href="#">Hints</a></h3>
        <div>
            <div class="message" role="status">${flash.message?:"Select plate layouts by dragging them to the middle list."}</div>
            <div class="message">Order list elements by drag and drop, then press continue</div>
            <div class="message">Double click on a list element in order to open a preview window of the layout.</div>
            <div class="message">To remove elements from the selection drag them to the trash bin.</div>
            <div class="message">You can spot a plate layout multiple times. Just drag it to the selection list several times.</div>
            <div class="message">You can mix layout plates from MIRACLE and SAVANAH, e.g. in order to include control plates.</div>

        </div>
        <h3><a href="#">Select plate layouts for new slide layout</a></h3>
        <div>
            <div id="plateSelect">
                <g:render template="plateSorting"/>
            </div>
        </div>
    </div>

    <div class="buttons">
        <input
                value="Continue"
                type="button" name="continue"
                onclick="var plateOrder = $('#selectedPlates').sortable('serialize');${g.remoteFunction(action: 'platesOrdered', update:'show-plateImport', params: '\'&\'+plateOrder')}"/>
    </div>

</div>
</body>
</html>
