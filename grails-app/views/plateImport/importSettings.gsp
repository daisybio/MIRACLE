<%@ page import="org.nanocan.rppa.layout.Dilution" %>
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

    <style>
    .myselectable li.ui-selecting  { background: #FECA40; }
    .myselectable li.ui-selected { background: #F39814; color: white; }
    .myselectable { list-style-type: none; margin: 0; padding: 0; width: 450px; }
    .myselectable li { margin: 3px; padding: 1px; float: left; width: 200px; height: 40px; font-size: 2em; text-align: center; }
    </style>
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
    <div class="message" role="status">${flash.message?:"Select extraction and define settings. Per default all extractions are used."}</div>
    <g:form name="importSettingsForm">
    <div id="accordion" style="margin: 25px; width: 90%;">

        <g:each in="${plates}" var="plate">
            <h3><a href="#">Exclude extractions in plate ${plate}</a></h3>
       <div>
           <ol class="myselectable" id="${plate}ExtractionFilter">
                    <g:each in="${1..8}" var="extraction">
                        <li id="Plate_${plate}|Extraction_${extraction}" class="ui-state-default">${extraction}</li>
                    </g:each>
                </ol>
            </div>
            <r:script>
                $(function() {
                    $("#${plate}ExtractionFilter").selectable();
                });
            </r:script>
        </g:each>

        <h3><a href="#">Import settings</a></h3>
        <div>
            <ol class="property-list"/>
                <li class="fieldcontain">
                    <span class="property-label">Traverse settings for Extractor:</span>
                    <span class="property-value"><g:select from="${['row-wise', 'column-wise']}" value="row-wise" name="extractorOperationMode"/></span>
                </li>
                <li class="fieldcontain">
                    <span class="property-label">Block spotting orientation: </span>
                    <span class="property-value">
                        <g:select from="${['top-to-bottom', 'left-to-right']}"
                                  value="left-to-right"
                                  name="spottingOrientation"
                                  onchange="${g.remoteFunction(action: 'blockSettings', update:'blockSettings', params:'\'&blockOrder=\'+this.value')}"
                        />
                    </span>
                </li>
                <div id="blockSettings">
                    <g:render template="blockSettings" model="${[rowWise: true]}"/>
                </div>
                <li class="fieldcontain">
                    <span class="property-label">Deposition pattern:</span>
                    <span class="property-value"><g:textField name="depositionPattern" value="[4,4,2,2,1,1]"/></span>
                </li>
                <li class="fieldcontain">
                    <span class="property-label">96-well plate conversion</span>
                    <span class="property-value">
                        <div style="width:240px;">
                            A single spot in a 96 well plate is converted into a square of four spots in different dilutions.
                            Please select the dilution pattern here.
                            <div class="circle">
                                <g:select style="width:65px; margin: 15px;" from="${Dilution.list()}" name="topLeftDilution"/>
                            </div>
                            <div class="circle">
                                <g:select style="width:65px; margin: 15px;" from="${Dilution.list()}" name="topRightDilution"/>
                            </div>
                            <div class="circle">
                                <g:select style="width:65px; margin: 15px;" from="${Dilution.list()}" name="bottomLeftDilution"/>
                            </div>
                            <div class="circle">
                                <g:select style="width:65px; margin: 15px;" from="${Dilution.list()}" name="bottomRightDilution"/>
                            </div>
                        </div>
                    </span>
                </li>
            </ol>

        </div>

    </div>

    <div class="buttons"><g:submitButton class="save" name="continue" value="Continue"/></div>
    </g:form>

</body>
</html>
