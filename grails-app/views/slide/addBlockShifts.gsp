<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'blockShift.label', default: 'Add blockshift pattern')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>

<div class="navbar">
    <div class="navbar-inner">
        <div class="container">
            <ul class="nav">
                <g:render template="/templates/navmenu"></g:render>
                <li><g:link class="list" action="show" id="${slideInstance.id}">Back to slide (without saving changes)</g:link></li>
                <g:if test="${slideInstance.blockShifts.size() != 0}"><li><g:link controller="slide" class="delete" action="deleteBlockShifts" id="${slideInstance.id}">Delete all block shifts</g:link></li></g:if>
            </ul>
        </div>
    </div>
</div>


    <div class="content">

    <h1>Block shift correction: </h1>

    <div style="float:left; width:200px; padding: 20px;">
    <p>Please enter shift correction values for each block.<br/>A block can be shifted horizontally
    (- : left, + : right) or vertically (- : up, + : down)</p><br/>

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

    </div>
    <div style="float:right; height:700px;" id="heatmapArea">
        <g:include controller='slide' action='heatmapInIFrame' id="${slideInstance?.id}"/>
    </div>
  </div>

</body>
</html>