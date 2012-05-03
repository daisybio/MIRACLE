<%@ page import="rppa.org.nanocan.rppa.layout.SlideLayout" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'slideLayout.label', default: 'SlideLayout')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
    <g:javascript library="jquery" />
</head>

<body>
<a href="#show-slideLayout" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                  default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="show" id="${slideLayout?.id}">Back to Slide</g:link></li>
    </ul>
</div>

<h1>Edit Spot Layout</h1><hr><br/>

    Property:
    <g:link action="sampleSpotTable" id="${slideLayout?.id}" params="${[sampleProperty: 'cellLine']}"> Cellline </g:link>
    <g:link action="sampleSpotTable" id="${slideLayout?.id}" params="${[sampleProperty: 'lysisBuffer']}"> Lysis Buffer </g:link>

</body>
</html>