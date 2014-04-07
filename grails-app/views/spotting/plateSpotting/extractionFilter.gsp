<%@ page import="org.nanocan.layout.ExtractionHead; org.nanocan.layout.LysisBuffer; org.nanocan.layout.SpotType; org.nanocan.layout.Dilution" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <title><g:message code="spotting.properties" /></title>

    <style>
    .myselectable li.ui-selecting  { background: #FECA40; }
    .myselectable li.ui-selected { background: #F39814; color: white; }
    .myselectable { list-style-type: none; margin: 0; padding: 0; width: 450px; }
    .myselectable li { margin: 3px; padding: 1px; float: left; width: 200px; height: 40px; font-size: 2em; text-align: center; }
    </style>
</head>
<body>

<div class="navbar">
    <div class="navbar-inner">
        <div class="container">
            <ul class="nav">
                <g:render template="/templates/navmenu"></g:render>
            </ul>
        </div>
    </div>
</div>
<div id="body">
<div id="show-plateImport" class="content scaffold-show" role="main">
    <h1><g:message code="spotting.properties"/></h1>
    <div class="message">Hold CTRL to select several extractions for exclusion</div>
    <g:if test="${message}">
        <div class="message" role="status">${message}</div>
    </g:if>
<g:formRemote update="body" name="spottingFlowForm" url="[controller: 'spotting', action:'plateSpotting', params: '']">
    <g:hiddenField name="execution" value="${request.flowExecutionKey}"/>
    <g:hiddenField name="progressId" value="pId${request.flowExecutionKey}"/>
    <g:hiddenField name="_eventId" value="continue"/>
    <div id="accordion" style="margin: 25px; width: 90%;">

<g:set var="counter" value="${1}"/>

<g:each in="${selectedLayouts}" var="layout">
    <h3><a href="#">Exclude extractions in layout ${layouts[layout]}</a></h3>
    <div>
        <ol class="myselectable" id="${counter}ExtractionFilter">

            <g:hiddenField name="layouts" value="${counter}"/>
            <g:each in="${extractionOrder[layout]}" var="extraction">
                <g:set var="extractionId" value="Plate_${counter}|Extraction_${extraction}"/>
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
        $("#${counter}ExtractionFilter").selectable({
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

    <g:set var="counter" value="${counter+1}"/>

</g:each>
</div>

<g:jprogressDialog progressId="pId${request.flowExecutionKey}" message="Virtual spotting of slide layout in progress..." trigger="_eventId_continue"/>
<div class="buttons"><g:submitButton class="save" name="continue" value="Continue"/>
</g:formRemote>

    </div>

</div>
    </div>



<r:script>

            $(function() {
                $("#accordion").accordion({
                    collapsible:true,
                    autoHeight: false
                });

            });
</r:script>

</body>