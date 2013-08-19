<%@ page import="org.nanocan.rppa.layout.NumberOfCellsSeeded" %>

<head>
    <r:require module="colorPicker"/>
</head>

<r:script>
                $('#colorPickDiv').ColorPicker({
                    color: '${numberOfCellsSeededInstance?.color}',
                    onShow: function (colpkr) {
                        $(colpkr).fadeIn(500);
                        return false;
                    },
                    onHide: function (colpkr) {
                        $(colpkr).fadeOut(500);
                        return false;
                    },
                    onChange: function (hsb, hex, rgb) {
                        $('#colorPickDiv').css('backgroundColor', '#' + hex);
                        $('#colorInput').attr("value", "#" + hex);
                    }
                });
</r:script>
<div class="fieldcontain ${hasErrors(bean: numberOfCellsSeededInstance, field: 'name', 'error')} ">
    <label for="name">
        <g:message code="numberOfCellsSeeded.name.label" default="Name" />

    </label>
    <g:textField name="name" value="${numberOfCellsSeededInstance?.name}"/>
</div>


<div class="fieldcontain ${hasErrors(bean: numberOfCellsSeededInstance, field: 'color', 'error')} required">
	<label for="color">
		<g:message code="numberOfCellsSeeded.color.label" default="Color" />
		<span class="required-indicator">*</span>
	</label>
    <div id="colorPickDiv" style="float:right; margin-right:600px; background-color: ${numberOfCellsSeededInstance?.color}; border: 1px solid; width:25px; height:25px;">
        <input type="hidden" id="colorInput" name="color" value="${numberOfCellsSeededInstance?.color}">
    </div>
</div>



