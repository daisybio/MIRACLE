<%@ page import="org.nanocan.rppa.rnai.Sample" %>

<head>
    <r:require module="colorPicker"/>
</head>

<r:script>
                $('#colorPickDiv').ColorPicker({
                    color: '${sampleInstance?.color}',
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


<div class="fieldcontain ${hasErrors(bean: sampleInstance, field: 'name', 'error')} ">
    <label for="name">
        <g:message code="sample.name.label" default="Name"/>

    </label>
    <g:textField name="name" value="${sampleInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: sampleInstance, field: 'type', 'error')} ">
    <label for="type">
        <g:message code="sample.type.label" default="Type"/>

    </label>
    <g:select name="type" from="${sampleInstance.constraints.type.inList}" value="${sampleInstance?.type}"
              valueMessagePrefix="sample.type" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: sampleInstance, field: 'targetGene', 'error')} ">
    <label for="targetGene">
        <g:message code="sample.targetGene.label" default="Target Gene"/>

    </label>
    <g:textField name="targetGene" value="${sampleInstance?.targetGene}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: sampleInstance, field: 'color', 'error')} ">
    <label for="color">
        <g:message code="sample.color.label" default="Color"/>

    </label>
    <div id="colorPickDiv" style="float:right; margin-right:600px; background-color: ${sampleInstance?.color}; border: 1px solid; width:25px; height:25px;">
        <input type="hidden" id="colorInput" name="color" value="${sampleInstance?.color}">
    </div>
</div>

<div class="fieldcontain ${hasErrors(bean: sampleInstance, field: 'identifiers', 'error')} ">
    <label for="identifiers">
        <g:message code="sample.identifiers.label" default="Identifiers"/>

    </label>

    <ul class="one-to-many">
        <g:each in="${sampleInstance?.identifiers ?}" var="i">
            <li><g:link controller="identifier" action="show" id="${i.id}">${i?.encodeAsHTML()}</g:link></li>
        </g:each>
        <li class="add">
            <g:link controller="identifier" action="create"
                    params="['sample.id': sampleInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'identifier.label', default: 'Identifier')])}</g:link>
        </li>
    </ul>

</div>

