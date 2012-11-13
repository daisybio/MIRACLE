<%@ page import="org.nanocan.rppa.layout.CellLine" %>
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
    <h1>The following properties could not be found in MIRACLE. Please check the assignments</h1>
    <div class="message" role="status">${flash.message}</div>

    <div id="accordion" style="margin: 25px; width: 90%;">
        <h3><a href="#">CellLine</a></h3>
        <div>
            <g:each in="${cellLineList}" var="cellLine">
                <g:select from="${CellLine.list()}" value="${CellLine.findByNameLike(cellLine.name)}" name="cellline_${cellLine}"/>
            </g:each>
        </div>
    </div>



</div>
</body>
</html>
