<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'plateLayout.label', default: 'PlateLayout')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>
<body>
<g:if test="${sampleProperty != 'sample'}">
    <g:render template="colorLegend" model="${[sampleProperty: sampleProperty]}"></g:render>
</g:if>
<g:else>
    <g:render template="sampleLegend" model="${[layoutId: plateLayout.id]}"></g:render>
</g:else>

<g:set var="well" value="${0}"/>
<g:set var="wellList" value="${wells.toList()}"/>


<script type="text/javascript">
    $(document).ready(function() {
        registerHandlers("plateLayoutTable");
    });
</script>

<h1 style="padding:20px;"><b>Showing ${sampleProperty.toString().capitalize()} for layout ${plateLayout}</b></h1>

<div style="float:left; padding-left:20px;">
    <g:form name="changeAttribute" action="showAttributes">
        <g:hiddenField name="id" value="${plateLayout?.id}"/>
        <g:hiddenField name="nobanner" value="${true}"/>
        Select a property: <g:select name="sampleProperty" optionKey="key" optionValue="value" value="${sampleProperty}"
                  from="${["cellLine":"CellLine", "inducer":"Inducer", "spotType": "Spot Type", "treatment":"Treatment", "numberOfCellsSeeded":"Number of cells seeded", "sample":"Sample"]}"
                  onchange="document.forms['changeAttribute'].submit();"
        />
    </g:form> <br/>
</div>

<g:formRemote onSuccess="window.onbeforeunload = null;" name="${sampleProperty}form" update="message" url="[controller: 'plateLayout', action: 'updateWellProperty']">
    <input name="wellProperty" type="hidden" value="${sampleProperty}"/>
    <input name="plateLayout" type="hidden" value="${plateLayout.id}"/>

    <div id = "plateLayout" style="overflow: auto; padding: 20px;">
        <table id="plateLayoutTable" style="border: 1px solid;">
            <thead>
            <tr align="center">
                <th style="width:25px;"/>
                <g:each in="${1..(plateLayout.cols)}" var="col">
                    <th style="width:25px;">${col}</th>
                </g:each>
            </tr>
            </thead>

            <tbody>
            <g:each in="${1..(plateLayout.rows)}" var="row">
                <tr id="r${row}">
                    <td>${row}</td>
                    <g:each in="${1..(plateLayout.cols)}">
                            <td style="border: 1px solid; background-color:${wellList.get(well)?.properties[sampleProperty]?wellList.get(well).properties[sampleProperty].color?:'#e0e0e0':''};"><input name="${wellList.get(well).id}" type="hidden" value=""></td>
                            <g:set var="well" value="${++well}"/>
                    </g:each>
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
</g:formRemote>

</body>
</html>