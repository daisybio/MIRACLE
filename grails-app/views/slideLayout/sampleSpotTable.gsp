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

<g:if test="${slideLayout.blocksPerRow}">
    <g:set var="blocksPerRow" value="${Math.min(slideLayout.blocksPerRow, slideLayout.numberOfBlocks)}"/>
</g:if>
<g:else>
    <g:set var="blocksPerRow" value="${12}"/>
</g:else>

<!-- Figure out how many tabs we need with n blocks per tab -->
<g:set var="fullTabsNeeded" value="${(int) (slideLayout.numberOfBlocks / blocksPerRow)}"/>
<g:set var="blocksInLastTab" value="${slideLayout.numberOfBlocks % blocksPerRow}" />
<g:if test="${blocksInLastTab != 0}">
    <g:set var="tabsNeeded" value="${++fullTabsNeeded}"></g:set>
</g:if>
<g:else><g:set var="tabsNeeded" value="${fullTabsNeeded}"/></g:else>

<script type="text/javascript">
    $(document).ready(function() {
        $(function() {$("#blockTabs").tabs() });
        registerHandlers("blockTable1");
    });
</script>

<div class="navbar">
    <div class="navbar-inner">
        <div class="container">
            <ul class="nav">
                <g:render template="/templates/navmenu"></g:render>
        <li><g:link class="list" action="show" id="${slideLayout?.id}">Back to Layout</g:link></li>
            </ul>
        </div>
    </div>
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
  <g:set var="tab" value="${((i-1) * blocksPerRow)+1}"/>
    <g:if test="${blocksInLastTab != 0 && i == tabsNeeded}"><g:set var="upperBound" value="${blocksInLastTab}"/></g:if>
    <g:else><g:set var="upperBound" value="${blocksPerRow}"/></g:else>
    <li><a href="#blockTabs-${i}" onclick="registerHandlers('blockTable${i}');">Blocks ${tab}..${tab+upperBound-1}</a></li>
 </g:each>
 </ul>

 <g:each var="i" in="${1..tabsNeeded}">
    <g:set var="tab" value="${((i-1) * blocksPerRow)+1}"/>
    <g:if test="${blocksInLastTab != 0 && i == tabsNeeded}"><g:set var="upperBound" value="${blocksInLastTab}"/></g:if>
    <g:else><g:set var="upperBound" value="${blocksPerRow}"/></g:else>

    <div id="blockTabs-${i}">
    <table id="blockTable${i}" style="border: 1px solid;">
        <thead>
        <tr>
            <th>Block</th>
            <g:each in="${tab..(tab+upperBound-1)}" var="block">
                <th colspan="${slideLayout.columnsPerBlock}">${block}</th>
            </g:each>
        </tr>

        <tr align="center">
            <th>Column</th>
            <g:each in="${tab..(tab+upperBound-1)}">
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
                <g:each in="${tab..(tab+upperBound-1)}" var="block">
                    <g:each in="${1..(slideLayout.columnsPerBlock)}" var="col">
                        <g:if test="${spot < spotList.size() && spotList.get(spot).row == row && spotList.get(spot).col == col && spotList.get(spot).block == block }">
                            <td style="border: 1px solid; background-color:${spotList.get(spot)?.properties[sampleProperty]?spotList.get(spot).properties[sampleProperty].color?:'#e0e0e0':''};"><input name="${spotList.get(spot).id}" type="hidden" value=""></td>
                            <g:set var="spot" value="${++spot}"/>
                        </g:if>
                        <g:else>
                            <td style="border: 1px solid"></td>
                        </g:else>
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
                        if(cell.style.backgroundColor != "")
                        {
                             cell.style.backgroundColor = selColor;
                             cell.firstChild.setAttribute("value", selId);
                             $('#message').html("Please save your changes!") ;
                            window.onbeforeunload = unloadPage;
                        }
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