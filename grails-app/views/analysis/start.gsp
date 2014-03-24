<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'analysis.label', default: 'Analysis')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>
<a href="#edit-slide" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="navbar">
    <div class="navbar-inner">
        <div class="container">
            <ul class="nav">
                <g:render template="/templates/navmenu"></g:render>
                <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
    </div>
</div>

<div class="body">

    <div style="padding-left:20px;">
        <g:form class="form" name="analysisForm" controller="analysis" action="start">

            <g:select id="project" name="project" optionKey="id" value="${selectedProject}" noSelection="['':'select a project']" from="${projects}" onchange="\$('#analysisForm').submit();"/>

            <g:select id="experiment" optionKey="id" value="${selectedExperiment}" noSelection="['':'select an experiment']" name="experiment" from="${experiments}" onchange="\$('#analysisForm').submit();"/>

            <g:select id="plateLayout" optionKey="id" name="plateLayout" value="${selectedPlateLayout}" noSelection="['':'select a plate layout']" from="${plateLayouts}" onchange="\$('#analysisForm').submit();"/>

            <g:select id="slideLayout" optionKey="id" name="slideLayout" value="${selectedSlideLayout}" noSelection="['':'select a slide layout']" from="${slideLayouts}" onchange="\$('#analysisForm').submit();"/>
        </g:form>
    </div>

    <g:if test="${slideLayoutInstance}">
        <div style="padding: 30px;">
            <g:form name="selectedSlidesAndPlates" action="batchAnalysis">

            <g:each in="${plates}" var="plate">
                <g:hiddenField name="allPlates" value="${plate.id}"/>
            </g:each>

            <g:each in="${slides}" var="slide">
                <g:hiddenField name="allSlides" value="${slide.id}"/>
            </g:each>

            <g:hiddenField name="slideLayout" value="${slideLayoutInstance.id}"/>

            <g:checkBox name="processAll" value="${false}"/> Simply select everything (can cause long processing time)

            <br/><br/>
            <h3>Slides</h3>
            <g:select name="selectedSlides" style="width:100%" optionKey="id" multiple="true" from="${slides}"/>

            <br/><br/>
            <h3>Plates</h3>
            <g:select name="selectedPlates" style="width:100%" optionKey="id" multiple="true" from="${plates}"/>

             <br/><br/>
            <g:submitButton name="Include selected slides and plates in analysis"/>
            </g:form>
        </div>

        <r:script>
            $(document).ready(function() {
                $("#selectedSlides").select2();
                $("#selectedPlates").select2();
            });

        </r:script>

    </g:if>

<g:else>
    <div class="message">You have to select a slide layout to proceed. Various filter options are available.</div>
</g:else>

</div>

<r:script>
    $(document).ready(function() {
                $("#project").select2();
                $("#experiment").select2();
                $("#plateLayout").select2();
                $("#slideLayout").select2();
        }
    );
</r:script>

</body>
</html>