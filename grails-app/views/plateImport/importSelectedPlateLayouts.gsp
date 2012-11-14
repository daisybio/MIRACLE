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
    <h1>Property Assignment</h1>
    <div class="message" role="status">${flash.message?:"Assign SAVANAH properties to MIRACLE properties or create new ones."}</div>
    <g:set var="backParams" value="${params.toQueryString()}"/>
    <g:set var="backController" value="plateImport"/>
    <g:set var="backAction" value = "importSelectedPlateLayouts"/>

    <div id="accordion" style="margin: 25px; width: 90%;">
        <h3><a href="#">CellLine</a></h3>
        <div>
        <ol class="property-list">
            <g:each in="${cellLineList}" var="cellLine">
                <li class="fieldcontain">
                <span class="property-label">${cellLine}:</span>
                <span class="property-value"><g:select from="${CellLine.list()}" value="${CellLine.findByNameLike(cellLine.name)}" name="cellline_${cellLine}"/>
                    or <g:link controller="cellLine" action="create"
                               params="${[name: cellLine.name, color: cellLine.color, backParams: backParams, backController: backController, backAction: backAction]}">
                create</g:link></span>
                </li>
            </g:each>
        </ol>
        </div>
    </div>



</div>
</body>
</html>
