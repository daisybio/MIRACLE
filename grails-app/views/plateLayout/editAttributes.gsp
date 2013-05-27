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


<a href="#show-plateLayout" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                  default="Skip to content&hellip;"/></a>

<div class="navbar">
    <div class="navbar-inner">
        <div class="container">
            <ul class="nav">
                <g:render template="/templates/navmenu"></g:render>
                <li><g:link class="list" action="list"><g:message code="default.list.label"
                                                                  args="[entityName]"/></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                                      args="[entityName]"/></g:link></li>
            </ul>
        </div>
    </div>
</div>

<g:jprogressDialog message="Updating layout information..." progressId="update${plateLayout.id}" trigger="layoutUpdateButton"/>

<h1 style="padding-left:20px;">Modify ${sampleProperty.toString().capitalize()} for layout ${plateLayout}</h1>


<g:message code="slideLayout.experiments.label" default="Experiments" />
    <ul class="property-list">
        <g:each in="${selectedExperiments}">
            <li>
                <g:link controller="experiment" action="show" id="${it.id}">${it.title}</g:link>
            </li>
        </g:each>
    </ul>
    <g:select name="addToExperiment" from="${experiments.removeAll(selectedExperiments)}"/>

<div class="message" id="message" role="status">${flash.message?:"Select cells to change the layout"}</div>

<g:if test="${sampleProperty == 'sample'}" >
    <div class="errors">Warning: Colors are not unique for samples! Make sure you know what you are doing!</div>
</g:if>

<div style="float:left; padding-left:20px;">
    <g:form name="changeAttribute" action="editAttributes">
        <g:hiddenField name="id" value="${plateLayout?.id}"/>
        Select a property: <g:select name="sampleProperty" optionKey="key" optionValue="value" value="${sampleProperty}"
                  from="${["cellLine":"CellLine", "inducer":"Inducer", "spotType": "Spot Type", "treatment":"Treatment", "numberOfCellsSeeded":"Number of cells seeded", "sample":"Sample"]}"
                  onchange="document.forms['changeAttribute'].submit();"
        />
    </g:form> <br/>
    <b>Copy this layout:</b> <br/> <br/>
    <g:form name="createCopy" action="createLayoutCopy">
        <g:hiddenField name="id" value="${plateLayout?.id}"/>
        <g:hiddenField name="sampleProperty" value="${sampleProperty}"/>
        New title: <g:textField name="name" value="${plateLayout.name} (copy)"/><br/>
        <span><g:submitButton name="submit" value="Create copy of this layout"/> </span>
    </g:form>
    <br/><br/>
    <b>Clear this sheet and save</b><br/> <br/>
    <g:form name="clearSheet" action="clearProperty">
    <g:hiddenField name="id" value="${plateLayout?.id}"/>
    <g:hiddenField name="sampleProperty" value="${sampleProperty}"/>
    <g:submitButton onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" name="Clear"/>
    </g:form>
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
    <div style="padding-left:20px;padding-bottom: 20px;"><input type="submit" value="Save changes" name="layoutUpdateButton"/></div>
</g:formRemote>

<g:form>
    <fieldset class="buttons">
        <g:hiddenField name="id" value="${plateLayout?.id}"/>
        <g:link class="edit" action="edit" id="${plateLayout?.id}"><g:message
                code="default.button.edit.label" default="Edit"/></g:link>
        <g:actionSubmit class="delete" action="delete"
                        value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                        onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
    </fieldset>
</g:form>

<r:script>

    var allTDs
    var selColor
    var selName
    var selId
    var buttondown = -1
    var cellstartr, cellstartc, cellendr, cellendc
    var tableName

    function registerHandlers(tN) {
        tableName = tN
        allTDs = document.getElementById(tableName).getElementsByTagName("td")
        document.getElementById(tableName).onmousedown = mouseDownHandler
        document.getElementById(tableName).onmouseup = mouseUpHandler
        document.getElementById(tableName).onmouseover = mouseOverHandler
    }

    function mouseOverHandler(e) {

        if (buttondown != -1) {
            if (window.getSelection) window.getSelection().removeAllRanges()
            if (document.selection) document.selection.empty()
        }
    }

    function mouseDownHandler(e) {
        if (!e) e = window.event
        var daTarget
        if (document.all)
            daTarget = e.srcElement
        else
            daTarget = e.target
        cellstartr = daTarget.parentNode.id.substring(1)
        cellstartc = daTarget.cellIndex
        buttondown = e.button
    }
    function mouseUpHandler(e) {

        if (!selColor) {
            alert('Please select a ${sampleProperty.toString().capitalize()} first!')
        }

        else {
            if (!e) e = window.event
            var daTarget
            if (document.all)
                daTarget = e.srcElement
            else
                daTarget = e.target
            cellendr = daTarget.parentNode.id.substring(1)
            cellendc = daTarget.cellIndex
            var rowstart = cellstartr
            var rowend = cellendr
            if (parseInt(cellendr) < parseInt(cellstartr)) {
                rowstart = cellendr
                rowend = cellstartr
            }
            var colstart = cellstartc
            var colend = cellendc
            if (parseInt(cellendc) < parseInt(cellstartc)) {
                colstart = cellendc
                colend = cellstartc
            }
            for (var i = rowstart; i <= rowend; i++) {
                for (var j = colstart; j <= colend; j++) {
                    if(j != 0) //protect row names from color changes
                    {
                        var cell = document.getElementById(tableName).rows[i].cells[j];
                        cell.style.backgroundColor = selColor;
                        cell.firstChild.setAttribute("value", selId);
                        $('#message').html("Please save your changes!") ;

                        window.onbeforeunload = unloadPage;
                    }
                }
            }
            buttondown = -1
        }
    }

    function unloadPage(){
        return "If you leave without saving, your changes will be discarded! Are you sure?";
    }

</r:script>

</body>
</html>