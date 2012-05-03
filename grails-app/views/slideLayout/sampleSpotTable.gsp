<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
</head>
<body>
<g:render template="colorLegend" model="${[sampleProperty: sampleProperty]}"></g:render>

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

<div id = "blockTabs">
 <ul>
 <g:each var="i" in="${1..tabsNeeded}">
  <g:set var="tab" value="${((i-1) * 12)+1}"/>
    <li><a href="#blockTabs-${i}" onclick="registerHandlers('blockTable${i}');">Blocks ${tab}..${tab+11}</a></li>
 </g:each>
 </ul>

 <g:each var="i" in="${1..tabsNeeded}">
    <g:set var="tab" value="${((i-1) * 12)+1}"/>

    <div id="blockTabs-${i}">
    <table id="blockTable${i}">
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
                    <th>${col}</th>
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
                        <td><input name="${spotList.get(spot).id}" type="hidden" value="${spotList.get(spot).properties[sampleProperty]?:""}"></td>
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

<r:script>

    var allTDs
    var selColor
    var selName
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
            alert('Please select a sample type first!')
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
                    var cell = document.getElementById(tableName).rows[i].cells[j];
                    //cell.innerHTML = "<input type='hidden' name='"+ i + j + "' value='"+ selName +"'/>"
                    cell.style.backgroundColor = selColor;
                    //cell.child.attribute("value") = selName;
                }
            }
            buttondown = -1
        }
    }

</r:script>

</body>
</html>