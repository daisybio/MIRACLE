<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
</head>
<body>
<g:if test="${sampleProperty != 'sample'}">
    <g:render template="colorLegend" model="${[sampleProperty: sampleProperty]}"></g:render>
</g:if>
<g:else>
    <g:render template="sampleLegend" model="${[layoutId: slideLayout.id]}"></g:render>
</g:else>

<g:set var="spot" value="${0}"/>
<g:set var="spotList" value="${spots.toList()}"/>

<!-- Figure out how many tabs we need with 12 blocks per tab -->
<g:set var="tabsNeeded" value="${(int) (slideLayout.numberOfBlocks / 12)}"/>
<g:if test="slideLayout.numberOfBlocks % 12 != 0"><g:set var="tabsNeeded" value="${tabsNeeded++}"></g:set></g:if>

<script type="text/javascript">
    $(document).ready(function() {
        $(function() {$("#blockTabs").tabs() });
        registerHandlers("blockTable1");
    });
</script>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="show" id="${slideLayout?.id}">Back to Layout</g:link></li>
    </ul>
</div>

<g:jprogressDialog message="Updating layout information..." progressId="update${slideLayout.id}" trigger="layoutUpdateButton"/>

<h1 style="padding-left:20px;">Modify ${sampleProperty.toString().capitalize()} for layout "${slideLayout}" </h1>

<div class="message" id="message" role="status">${flash.message?:"Select cells to change the layout"}</div>

<g:if test="${sampleProperty == 'sample'}" >
    <div class="errors">Warning: Colors are not unique for samples! Make sure you know what you are doing!</div>
</g:if>

<g:formRemote onSuccess="window.onbeforeunload = null;" name="${sampleProperty}form" update="message" url="[controller: 'slideLayout', action: 'updateSpotProperty']">
    <input name="spotProperty" type="hidden" value="${sampleProperty}"/>
    <input name="slideLayout" type="hidden" value="${slideLayout.id}"/>

<div id = "blockTabs" style="overflow: auto; height:700px;">
 <ul>
 <g:each var="i" in="${1..tabsNeeded}">
  <g:set var="tab" value="${((i-1) * 12)+1}"/>
    <li><a href="#blockTabs-${i}" onclick="registerHandlers('blockTable${i}');">Blocks ${tab}..${tab+11}</a></li>
 </g:each>
 </ul>

 <g:each var="i" in="${1..tabsNeeded}">
    <g:set var="tab" value="${((i-1) * 12)+1}"/>

    <div id="blockTabs-${i}">
    <table id="blockTable${i}" style="border: 1px solid;">
        <thead>
        <tr>
            <th>Block</th>
            <g:each in="${tab..(tab+11)}" var="block">
                <th colspan="${slideLayout.columnsPerBlock}">${block}</th>
            </g:each>
        </tr>

        <tr align="center">
            <th>Column</th>
            <g:each in="${tab..(tab+11)}">
                <g:each in="${1..(slideLayout.columnsPerBlock)}" var="col">
                    <th style="width:25px;">${col}</th>
                </g:each>
            </g:each>
        </tr>
        </thead>

        <tbody>
        <g:each in="${1..(slideLayout.rowsPerBlock)}" var="row">
            <tr id="r${row+1}">
                <td>${row}</td>
                <g:each in="${tab..tab+11}">
                    <g:each in="${1..(slideLayout.columnsPerBlock)}">
                        <td style="border: 1px solid; background-color:${spotList.get(spot)?.properties[sampleProperty]?spotList.get(spot).properties[sampleProperty].color?:'#e0e0e0':''};"><input name="${spotList.get(spot).id}" type="hidden" value=""></td>
                        <g:set var="spot" value="${++spot}"/>
                    </g:each>
                </g:each>
            </tr>
        </g:each>
        </tbody>
    </table>
    </div>
 </g:each>
</div>
<input type="submit" value="Save changes" name="layoutUpdateButton"/>
</g:formRemote>

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