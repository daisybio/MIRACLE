<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'blockShift.label', default: 'Add blockshift pattern')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>

<g:form action="saveBlockShiftPattern">

    <div class="navbar">
<div class="navbar-inner">
<div class="container">
   <ul class="nav">
    <g:render template="/templates/navmenu"></g:render>
            <li><g:link class="list" action="show" id="${slideInstance.id}">Back to slide (without saving changes)</g:link></li>
   </ul>
</div>
</div>
    </div>


    <div class="content">

    <h1>Block shift correction: </h1>

    <div style="padding: 20px;">
    <p>Please enter shift correction values for each block.<br/>A block can be shifted horizontally
    (- : left, + : right) or vertically (- : up, + : down)</p><br/>

    <g:set var="blockCount" value="${slideInstance?.layout?.numberOfBlocks?:48}"/>
    <g:set var="blockCountHalf" value="${(int) (blockCount / 2)}"/>
    <g:hiddenField name="id" value="${slideInstance.id}"></g:hiddenField>

    <table style="width:200px;">

        <thead>
            <th>Block</th><th>Horizontal Shift</th><th>Vertical Shift</th>
            <th>Block</th><th>Horizontal Shift</th><th>Vertical Shift</th>
        </thead>

        <tbody>

        <g:each in="${1..blockCountHalf}" var="block">
            <tr>
                <td>${block}</td>
                <td><g:select name="hshift_${block}" from="${-3..3}" value="${hblockShifts[block-1]?:0}"/></td>
                <td><g:select name="vshift_${block}" from="${-3..3}" value="${vblockShifts[block-1]?:0}"/></td>

                <td>${block+blockCountHalf}</td>
                <td><g:select name="hshift_${block+blockCountHalf}" from="${-3..3}" value="${hblockShifts[block+blockCountHalf-1]?:0}"/></td>
                <td><g:select name="vshift_${block+blockCountHalf}" from="${-3..3}" value="${vblockShifts[block+blockCountHalf-1]?:0}"/></td>
            </tr>
            </g:each>
        </tbody>

    </table>

    <g:submitButton name="Update block shifts"/>
</g:form>
</div> </div>
</body>
</html>