<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <meta name="layout" content="webflow">
    <g:set var="entityName" value="${message(code: 'blockShift.label', default: 'Add blockshift pattern')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>

    <div class="content">

    <h1>Block shift correction: </h1>

    <div style="padding: 20px;">
    <p>Please enter shift correction values for each block.<br/>A block can be shifted horizontally
    (- : left, + : right) or vertically (- : up, + : down)</p><br/>


    <div style="float:right; width: 600px;" id="heatmapArea">
        Loading heatmap preview...
        <img src="<g:resource dir="images" file="spinner.gif"/>"/>
    </div>

    <script type="text/javascript">
        $(document).ready(function(){<g:remoteFunction update="heatmapArea" controller="r" action="plotHeatmap" id="${slideInstance?.id}"/>});
    </script>

    <g:form action="saveBlockShiftPattern">

    <g:set var="blockCount" value="${slideInstance?.layout?.numberOfBlocks?:48}"/>
    <g:set var="blockCountHalf" value="${(int) (blockCount / 2)}"/>
    <g:hiddenField name="id" value="${slideInstance.id}"></g:hiddenField>

    <table style="width:200px;">

        <thead>
            <th>B</th><th>H</th><th>V</th>
            <th>B</th><th>H</th><th>V</th>
        </thead>

        <tbody>

        <g:each in="${1..blockCountHalf}" var="block">
            <tr>
                <td>${block}</td>
                <td><g:select style="width:40px;" name="hshift_${block}" from="${-3..3}" value="${hblockShifts[block-1]?:0}"/></td>
                <td><g:select style="width:40px;" name="vshift_${block}" from="${-3..3}" value="${vblockShifts[block-1]?:0}"/></td>

                <td>${block+blockCountHalf}</td>
                <td><g:select style="width:40px;" name="hshift_${block+blockCountHalf}" from="${-3..3}" value="${hblockShifts[block+blockCountHalf-1]?:0}"/></td>
                <td><g:select style="width:40px;" name="vshift_${block+blockCountHalf}" from="${-3..3}" value="${vblockShifts[block+blockCountHalf-1]?:0}"/></td>
            </tr>
            </g:each>
        </tbody>

    </table>

    <g:submitButton name="Save Block Shifts"/>
</g:form>
</div> </div>
</body>
</html>