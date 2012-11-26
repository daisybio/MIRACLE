<%@ page import="org.nanocan.rppa.layout.Dilution" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <title><g:message code="spotting.properties" /></title>

    <r:script>$(function() {
        $("#accordion").accordion({
            collapsible:true,
            autoHeight: false
        });

    });
    </r:script>

    <style>
    .myselectable li.ui-selecting  { background: #FECA40; }
    .myselectable li.ui-selected { background: #F39814; color: white; }
    .myselectable { list-style-type: none; margin: 0; padding: 0; width: 450px; }
    .myselectable li { margin: 3px; padding: 1px; float: left; width: 200px; height: 40px; font-size: 2em; text-align: center; }
    </style>
</head>
<body>
<div id="show-plateImport" class="content scaffold-show" role="main">
    <h1><g:message code="spotting.properties"/></h1>
    <div class="message" role="status">${message?:"Select extraction and define settings. Per default all extractions are used."}</div>
    <g:form>
    <g:set var="numOfExtractions" value="${8}"/>
    <g:hiddenField name="numOfExtractions" value="${numOfExtractions}"/>

    <div id="accordion" style="margin: 25px; width: 90%;">

        <h3><a href="#">Spotter settings</a></h3>
        <div>
            <ol class="property-list"/>
            <li class="fieldcontain">
                <span class="property-label">Title for Layout:</span>
                <span class="property-value"><g:textField name="title" value="${title}"/></span>
            </li>
            <li class="fieldcontain">
                <span class="property-label">Traverse settings for Extractor:</span>
                <span class="property-value"><g:select from="${['row-wise', 'column-wise']}" value="${extractorOperationMode?:'row-wise'}" name="extractorOperationMode"/></span>
            </li>
            <li class="fieldcontain">
                <span class="property-label">Block spotting orientation: </span>
                <span class="property-value">
                    <g:select from="${['top-to-bottom', 'left-to-right']}"
                              value="${spottingOrientation?:'left-to-right'}"
                              name="spottingOrientation"
                              onchange="${g.remoteFunction(action: 'blockSettings', update:'blockSettings', params:'\'&blockOrder=\'+this.value')}"
                    />
                </span>
            </li>
            <div id="blockSettings">
                <g:render template="blockSettings" model="${[rowWise: true, xPerBlock: xPerBlock?:2]}"/>
            </div>
            <li class="fieldcontain">
                <span class="property-label">Deposition pattern:</span>
                <span class="property-value"><g:textField name="depositionPattern" value="${depositionPattern?:'[4,4,2,2,1,1]'}"/></span>
            </li>
            <li class="fieldcontain">
                <span class="property-label">96-well plate conversion</span>
                <span class="property-value">
                    <div style="width:240px;">
                        A single spot in a 96 well plate is converted into a square of four spots in different dilutions.
                        Please select the dilution pattern here.
                        <div class="circle">
                            <g:select style="width:65px; margin: 15px;" value="${topLeftDilution?:Dilution.list().first()}" optionKey="id" from="${Dilution.list()}" name="topLeftDilution"/>
                        </div>
                        <div class="circle">
                            <g:select style="width:65px; margin: 15px;" value="${topRightDilution?:Dilution.list().first()}" optionKey="id" from="${Dilution.list()}" name="topRightDilution"/>
                        </div>
                        <div class="circle">
                            <g:select style="width:65px; margin: 15px;" value="${bottomLeftDilution?:Dilution.list().first()}" optionKey="id" from="${Dilution.list()}" name="bottomLeftDilution"/>
                        </div>
                        <div class="circle">
                            <g:select style="width:65px; margin: 15px;" value="${bottomRightDilution?:Dilution.list().first()}" optionKey="id" from="${Dilution.list()}" name="bottomRightDilution"/>
                        </div>
                    </div>
                </span>
            </li>
        </ol>

        </div>

        <g:each in="${layouts.entrySet()}" var="layout">
            <h3><a href="#">Exclude extractions in layout ${layout.value}</a></h3>
       <div>
           <ol class="myselectable" id="${layout.key}ExtractionFilter">
                    <g:hiddenField name="layouts" value="${layout.key}"/>
                    <g:each in="${1..numOfExtractions}" var="extraction">
                        <g:set var="extractionId" value="Plate_${layout.key}|Extraction_${extraction}"/>
                        <g:set var="extractionFieldId" value="${extractionId}|Field"/>
                        <g:if test="${excludedPlateExtractions?excludedPlateExtractions[extractionFieldId]:false == true}">
                            <g:set var="extractionValue" value="${true}"/>
                        </g:if>
                        <g:else>
                            <g:set var="extractionValue" value="${false}"/>
                        </g:else>
                        <li id="${extractionId}" class="ui-state-default${extractionValue?' ui-selected':''}">${extraction}</li>
                        <g:hiddenField name="${extractionFieldId}" value="${extractionValue}"/>
                    </g:each>
                </ol>
            </div>
            <r:script>
                $(function() {
                    $("#${layout.key}ExtractionFilter").selectable({
                            selected: function(event, ui){
                                var newId = "" + ui.selected.id + "|Field";
                                $(document.getElementById(newId)).attr("value", "true");
                            },
                            unselected: function(event, ui){
                                var newId = "" + ui.unselected.id + "|Field";
                                $(document.getElementById(newId)).attr("value", "false");
                            }
                    });
                });
            </r:script>
        </g:each>
    </div>

    <div class="buttons"><g:submitButton class="save" name="continue" value="Continue"/></div>
    </g:form>

</body>
</html>
