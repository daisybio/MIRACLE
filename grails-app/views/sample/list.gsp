<%@ page import="org.nanocan.rppa.rnai.Sample" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'sample.label', default: 'Sample')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<a href="#list-sample" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                             default="Skip to content&hellip;"/></a>

<div class="navbar">
    <div class="navbar-inner">
        <div class="container">
            <ul class="nav">
                <g:render template="/templates/navmenu"></g:render>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
            </ul>
        </div>
    </div>
</div>


<div id="list-sample" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>

            <g:sortableColumn property="name" title="${message(code: 'sample.name.label', default: 'Name')}"/>

            <g:sortableColumn property="type" title="${message(code: 'sample.type.label', default: 'Type')}"/>

            <g:sortableColumn property="targetGene"
                              title="${message(code: 'sample.targetGene.label', default: 'Target Gene')}"/>

            <g:sortableColumn property="color" title="${message(code: 'sample.color.label', default: 'Color')}"/>

        </tr>
        </thead>
        <tbody>
        <g:each in="${sampleInstanceList}" status="i" var="sampleInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                <td><g:link action="show"
                            id="${sampleInstance.id}">${fieldValue(bean: sampleInstance, field: "name")}</g:link></td>

                <td>${fieldValue(bean: sampleInstance, field: "type")}</td>

                <td>${fieldValue(bean: sampleInstance, field: "targetGene")}</td>

                <td><div id="colorPickDiv" style="float:left; background-color: ${sampleInstance?.color}; border: 1px solid; width:25px; height:25px;"/></td>

            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="pagination">
        <g:paginate total="${sampleInstanceTotal}"/>
    </div>
</div>
</body>
</html>
