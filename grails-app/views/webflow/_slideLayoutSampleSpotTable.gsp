<g:if test="${sampleProperty != 'sample'}">
	<g:render template="slideLayoutColorLegend"
		model="${[sampleProperty: sampleProperty]}"></g:render>
</g:if>
<g:else>
	<g:render template="slideLayoutSampleLegend" model="${[layoutId: slideLayout.id]}"></g:render>
</g:else>
<g:render template="slideLayoutSpotTooltip" />

<script type="text/javascript">
    $(document).ready(function() {
        $(function() {$("#blockTabs").tabs() });
        registerHandlers("blockTable1");
    });
</script>



<div class="message" id="message" role="status"
	style="padding: 0px; margin: 0px; margin-bottom: 10px;">
	${flash.message?:"Select cells to change the layout"}
</div>

<g:if test="${sampleProperty == 'sample'}">
	<div class="errors">Warning: Colors are not unique for samples!
		Make sure you know what you are doing!</div>
</g:if>

<g:formRemote name="excelPasteBinForm"
	onSuccess="jQuery(function() {jQuery('#blockTabs').tabs() });registerHandlers('blockTable1');"
	update="blockTabs"
	url="${["controller":"slideLayout", action:"parseClipboardData"]}">
	<g:hiddenField name="id" value="${slideLayout.id}" />
	<g:textArea name="excelPasteBin" rows="5" cols="14" />
	<g:hiddenField name="spotProperty" value="${sampleProperty}" />
	<g:submitButton name="parse" />
</g:formRemote>

<g:formRemote
	onSuccess="window.onbeforeunload = null;unsavedChanges=false"
	name="spotPropertiesForm" update="message"
	url="[controller: 'slideLayout', action: 'updateSpotProperty']">
	<div class="buttons" style="margin-top: 5px; margin-bottom: 10px;">
		<input type="submit" value="Save changes" name="layoutUpdateButton" />
		Selection Mode:
		<g:select name="selectionMode"
			from="${['normal', 'whole rows', 'whole columns']}"
			onchange="updateSelectionMode(this.value);" />
	</div>
	<input name="spotProperty" type="hidden" value="${sampleProperty}" />
	<input name="slideLayout" type="hidden" value="${slideLayout.id}" />

	<div id="blockTabs" style="overflow: auto;">
		<g:render template="slideLayoutTableTemplate"
			model="${[blocksPerRow:slideLayout.blocksPerRow,columnsPerBlock:slideLayout.columnsPerBlock,rowsPerBlock:slideLayout.rowsPerBlock,spots:slideLayout.sampleSpots, numberOfBlocks: slideLayout.numberOfBlocks]}"></g:render>
	</div>
</g:formRemote>

<script type="text/javascript">
    var allTDs
    var selColor = "";
    var selName = "";
    var selId = "";
    var buttondown = -1;
    var cellstartr, cellstartc, cellendr, cellendc;
    var tableName
    var unsavedChanges = false;
    var selectionMode = "normal";
    var sampleProperty = "${sampleProperty.toString().capitalize()}";

    $("td").bind("mouseover", function(event) {
        if(true) {
            var spot = $(this);
            var timer = window.setTimeout(function() {
                var id = spot.find("input").attr("name");
                ${remoteFunction(controller: "slideLayout", action: "showSpotTooltip",
            params: "\'id=\' + id", update: "draggableSpotTooltip")}

                $("#draggableSpotTooltip").show();
            }, 800)
            spot.data('timerid', timer);
        }
    }).bind("mouseout", function() {
                if(true) {
                    var timerid = $(this).data('timerid');
                    if(timerid != null)
                    {
                        window.clearTimeout(timerid);
                    }
                    $("#draggableSpotTooltip").hide();
                }
            });
</script>

<r:script>

    function updateSelectionMode(newValue)
    {
        selectionMode = newValue;
    }

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
            alert('Please select a ' + sampleProperty +  ' first!')
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

            /*whole columns selection style */
            if(selectionMode == "whole columns")
            {
                rowstart = 1;
                rowend = ${slideLayout.rowsPerBlock}+1
            }

            /*whole rows selection mode */
            else if(selectionMode == "whole rows"){
                colstart = 1;
                colend = ${slideLayout.columnsPerBlock * (slideLayout.blocksPerRow?:12)}
            }
            //alert("rstart: " + rowstart + "|rend:" + rowend + "|cstart:" + colstart + "|cend:" + colend);

            for (var currRow = parseInt(rowstart); currRow <= parseInt(rowend); currRow++) {
                //alert("current row:" + currRow);
                for (var currCol = parseInt(colstart); currCol <= parseInt(colend); currCol++) {
                    //alert("current column:" + currCol);
                    if(currCol != 0) //protect row names from color changes
                    {
                        var cell = document.getElementById(tableName).rows[currRow].cells[currCol];
                        if(cell.style.backgroundColor != "")
                        {
                             cell.style.backgroundColor = selColor;
                             cell.firstChild.setAttribute("value", selId);
                             $('#message').html("Please save your changes!") ;
                            window.onbeforeunload = unloadPage;
                            unsavedChanges = true
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
