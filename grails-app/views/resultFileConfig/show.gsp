<%@ page import="org.nanocan.rppa.scanner.ResultFileConfig" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'resultFileConfig.label', default: 'ResultFileConfig')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-resultFileConfig" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                       default="Skip to content&hellip;"/></a>

<div class="navbar">
    <div class="navbar-inner">
        <div class="container">
            <ul class="nav">
                <g:render template="/templates/navmenu"></g:render>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
            </ul>
        </div>
    </div>
</div>

<div id="show-resultFileConfig" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list resultFileConfig">

        <g:if test="${resultFileConfigInstance?.name}">
            <li class="fieldcontain">
                <span id="name-label" class="property-label"><g:message code="resultFileConfig.name.label"
                                                                        default="Name"/></span>

                <span class="property-value" aria-labelledby="name-label"><g:fieldValue
                        bean="${resultFileConfigInstance}" field="name"/></span>

            </li>
        </g:if>

        <g:if test="${resultFileConfigInstance?.blockCol}">
            <li class="fieldcontain">
                <span id="blockCol-label" class="property-label"><g:message code="resultFileConfig.blockCol.label"
                                                                            default="Block Col"/></span>

                <span class="property-value" aria-labelledby="blockCol-label"><g:fieldValue
                        bean="${resultFileConfigInstance}" field="blockCol"/></span>

            </li>
        </g:if>

        <g:if test="${resultFileConfigInstance?.columnCol}">
            <li class="fieldcontain">
                <span id="columnCol-label" class="property-label"><g:message code="resultFileConfig.columnCol.label"
                                                                             default="Column Col"/></span>

                <span class="property-value" aria-labelledby="columnCol-label"><g:fieldValue
                        bean="${resultFileConfigInstance}" field="columnCol"/></span>

            </li>
        </g:if>

        <g:if test="${resultFileConfigInstance?.rowCol}">
            <li class="fieldcontain">
                <span id="rowCol-label" class="property-label"><g:message code="resultFileConfig.rowCol.label"
                                                                          default="Row Col"/></span>

                <span class="property-value" aria-labelledby="rowCol-label"><g:fieldValue
                        bean="${resultFileConfigInstance}" field="rowCol"/></span>

            </li>
        </g:if>

        <g:if test="${resultFileConfigInstance?.fgCol}">
            <li class="fieldcontain">
                <span id="fgCol-label" class="property-label"><g:message code="resultFileConfig.fgCol.label"
                                                                         default="Fg Col"/></span>

                <span class="property-value" aria-labelledby="fgCol-label"><g:fieldValue
                        bean="${resultFileConfigInstance}" field="fgCol"/></span>

            </li>
        </g:if>

        <g:if test="${resultFileConfigInstance?.bgCol}">
            <li class="fieldcontain">
                <span id="bgCol-label" class="property-label"><g:message code="resultFileConfig.bgCol.label"
                                                                         default="Bg Col"/></span>

                <span class="property-value" aria-labelledby="bgCol-label"><g:fieldValue
                        bean="${resultFileConfigInstance}" field="bgCol"/></span>

            </li>
        </g:if>

        <g:if test="${resultFileConfigInstance?.flagCol}">
            <li class="fieldcontain">
                <span id="flagCol-label" class="property-label"><g:message code="resultFileConfig.flagCol.label"
                                                                           default="Flag Col"/></span>

                <span class="property-value" aria-labelledby="flagCol-label"><g:fieldValue
                        bean="${resultFileConfigInstance}" field="flagCol"/></span>

            </li>
        </g:if>

        <g:if test="${resultFileConfigInstance?.diameterCol}">
            <li class="fieldcontain">
                <span id="diameterCol-label" class="property-label"><g:message code="resultFileConfig.diameterCol.label"
                                                                               default="Diameter Col"/></span>

                <span class="property-value" aria-labelledby="diameterCol-label"><g:fieldValue
                        bean="${resultFileConfigInstance}" field="diameterCol"/></span>

            </li>
        </g:if>


        <g:if test="${resultFileConfigInstance?.skipLines}">
            <li class="fieldcontain">
                <span id="skipLines-label" class="property-label"><g:message code="resultFileConfig.skipLines.label"
                                                                             default="Skip Lines"/></span>

                <span class="property-value" aria-labelledby="skipLines-label"><g:fieldValue
                        bean="${resultFileConfigInstance}" field="skipLines"/></span>

            </li>
        </g:if>

        <g:if test="${resultFileConfigInstance?.xCol}">
            <li class="fieldcontain">
                <span id="xCol-label" class="property-label"><g:message code="resultFileConfig.xCol.label"
                                                                        default="X Col"/></span>

                <span class="property-value" aria-labelledby="xCol-label"><g:fieldValue
                        bean="${resultFileConfigInstance}" field="xCol"/></span>

            </li>
        </g:if>

        <g:if test="${resultFileConfigInstance?.yCol}">
            <li class="fieldcontain">
                <span id="yCol-label" class="property-label"><g:message code="resultFileConfig.yCol.label"
                                                                        default="Y Col"/></span>

                <span class="property-value" aria-labelledby="yCol-label"><g:fieldValue
                        bean="${resultFileConfigInstance}" field="yCol"/></span>

            </li>
        </g:if>

    </ol>
    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="${resultFileConfigInstance?.id}"/>
            <g:link class="edit" action="edit" id="${resultFileConfigInstance?.id}"><g:message
                    code="default.button.edit.label" default="Edit"/></g:link>
            <g:actionSubmit class="delete" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
