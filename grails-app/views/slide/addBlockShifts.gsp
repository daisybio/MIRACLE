<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'blockShift.label', default: 'Add blockshift pattern')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>

<g:form action="saveBlockShiftPattern">

    <div class="nav" role="navigation">
        <ul>
            <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
            <li><g:link class="list" action="show" id="${slideInstance.id}">Back to slide (without saving changes)</g:link></li>
        </ul>
    </div>

    <h1>Block shift correction: </h1><hr/><br/>
    <p>Please enter shift correction values for each block (1 to 48).<br/>A block can be shifted horizontally
    (- : left, + : right) or vertically (- : up, + : down)</p><br/>

    <g:hiddenField name="id" value="${slideInstance.id}"></g:hiddenField>

    <table style="width:200px;">

        <thead>
            <th>Block</th><th>Horizontal Shift</th><th>Vertical Shift</th>
            <th>Block</th><th>Horizontal Shift</th><th>Vertical Shift</th>
        </thead>

        <tbody>

        <g:each in="${1..24}" var="block">
            <tr>
                <td>${block}</td>
                <td><g:select name="hshift_${block}" from="${-3..3}" value="${hblockShifts[block-1]?:0}"/></td>
                <td><g:select name="vshift_${block}" from="${-3..3}" value="${vblockShifts[block-1]?:0}"/></td>

                <td>${block+24}</td>
                <td><g:select name="hshift_${block+24}" from="${-3..3}" value="${hblockShifts[block+23]?:0}"/></td>
                <td><g:select name="vshift_${block+24}" from="${-3..3}" value="${vblockShifts[block+23]?:0}"/></td>
            </tr>
            </g:each>
        </tbody>

    </table>

    <g:submitButton name="Update block shifts"/>
</g:form>
</body>
</html>