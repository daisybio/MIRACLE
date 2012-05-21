<%@ page import="org.nanocan.rppa.layout.LysisBuffer" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'lysisBuffer.label', default: 'LysisBuffer')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<a href="#list-lysisBuffer" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                  default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="list-lysisBuffer" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>

            <g:sortableColumn property="concentrationUnit"
                              title="${message(code: 'lysisBuffer.concentrationUnit.label', default: 'Concentration Unit')}"/>

            <g:sortableColumn property="color" title="${message(code: 'lysisBuffer.color.label', default: 'Color')}"/>

            <g:sortableColumn property="name" title="${message(code: 'lysisBuffer.name.label', default: 'Name')}"/>

            <g:sortableColumn property="concentration"
                              title="${message(code: 'lysisBuffer.concentration.label', default: 'Concentration')}"/>

        </tr>
        </thead>
        <tbody>
        <g:each in="${lysisBufferInstanceList}" status="i" var="lysisBufferInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                <td><g:link action="show"
                            id="${lysisBufferInstance.id}">${fieldValue(bean: lysisBufferInstance, field: "name")}</g:link></td>

                <td><div id="colorPickDiv" style="float:left; background-color: ${lysisBufferInstance?.color}; border: 1px solid; width:25px; height:25px;"/></td>

                <td>${fieldValue(bean: lysisBufferInstance, field: "concentration")}</td>

                <td>${fieldValue(bean: lysisBufferInstance, field: "concentrationUnit")}</td>

            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="pagination">
        <g:paginate total="${lysisBufferInstanceTotal}"/>
    </div>
</div>
</body>
</html>
