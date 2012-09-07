<%@ page import="org.nanocan.rppa.rnai.Sample" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'sample.label', default: 'Sample')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-sample" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
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


<div id="show-sample" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list sample">

        <g:if test="${sampleInstance?.name}">
            <li class="fieldcontain">
                <span id="name-label" class="property-label"><g:message code="sample.name.label" default="Name"/></span>

                <span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${sampleInstance}"
                                                                                        field="name"/></span>

            </li>
        </g:if>

        <g:if test="${sampleInstance?.type}">
            <li class="fieldcontain">
                <span id="type-label" class="property-label"><g:message code="sample.type.label" default="Type"/></span>

                <span class="property-value" aria-labelledby="type-label"><g:fieldValue bean="${sampleInstance}"
                                                                                        field="type"/></span>

            </li>
        </g:if>

        <g:if test="${sampleInstance?.targetGene}">
            <li class="fieldcontain">
                <span id="targetGene-label" class="property-label"><g:message code="sample.targetGene.label"
                                                                              default="Target Gene"/></span>

                <span class="property-value" aria-labelledby="targetGene-label"><g:fieldValue bean="${sampleInstance}"
                                                                                              field="targetGene"/></span>

            </li>
        </g:if>

        <g:if test="${sampleInstance?.color}">
            <li class="fieldcontain">
                <span id="color-label" class="property-label"><g:message code="sample.color.label"
                                                                         default="Color"/></span>

                <span style="width: 25px;" class="property-value" aria-labelledby="color-label">
                    <div id="colorPickDiv" style="float:right; background-color: ${sampleInstance?.color}; border: 1px solid; width:25px; height:25px;"/>
                </span>

            </li>
        </g:if>

        <g:if test="${sampleInstance?.identifiers}">
            <li class="fieldcontain">
                <span id="identifiers-label" class="property-label"><g:message code="sample.identifiers.label"
                                                                               default="Identifiers"/></span>

                <g:each in="${sampleInstance.identifiers}" var="i">
                    <span class="property-value" aria-labelledby="identifiers-label"><g:link controller="identifier"
                                                                                             action="show"
                                                                                             id="${i.id}">${i?.encodeAsHTML()}</g:link></span>
                </g:each>

            </li>
        </g:if>

    </ol>
    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="${sampleInstance?.id}"/>
            <g:link class="edit" action="edit" id="${sampleInstance?.id}"><g:message code="default.button.edit.label"
                                                                                     default="Edit"/></g:link>
            <g:actionSubmit class="delete" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
