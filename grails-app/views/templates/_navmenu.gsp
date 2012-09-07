<%@ page import="org.nanocan.rppa.project.Project" %>
<li class="dropdown" id="main.menu">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#main.menu">
        <g:message code="default.menu.label" default="Home"/>
        <b class="caret"></b>
    </a>
    <ul class="dropdown-menu">
        <li>
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
        </li>
        <li>
            <g:link class="list" controller="slideLayout" action="list">List Layouts</g:link>
        </li>
        <li>
            <g:link class="list" controller="slide" action="list">List Slide Results</g:link>
        </li>
    </ul>
</li>
<li class="dropdown" id="browse.menu">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#browse.menu">
        <g:message code="default.slideLayout.menu.label" args="['...']" default="Slide Layout"/>
        <b class="caret"></b>
    </a>
    <ul class="dropdown-menu">
        <li>
            <g:link class="list" controller="slideLayout" action="list">List Layouts</g:link>
        </li>
        <li>
            <g:link class="create" controller="slideLayout" action="create">Create New Layout</g:link>
        </li>
        <li class="divider"></li>
        <li>
            <g:link class="slide_layout" controller="cellLine" action="list">CellLines</g:link>
        </li>
        <li>
            <g:link class="slide_layout" controller="dilution" action="list">Dilution Factors</g:link>
        </li>
        <li>
            <g:link class="slide_layout" controller="inducer" action="list">Inducers</g:link>
        </li>
        <li>
            <g:link class="slide_layout" controller="lysisBuffer" action="list">Lysis Buffers</g:link>
        </li>
        <li>
            <g:link class="slide_layout" controller="spotType" action="list">Spot Types</g:link>
        </li>
        <li>
            <g:link class="slide_layout" controller="treatment" action="list">Treatments</g:link>
        </li>
        <li>
            <g:link class="slide_layout" controller="sample" action="list">Samples</g:link>
        </li>
    </ul>
</li>
<li class="dropdown" id="organize.menu">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#organize.menu">
        <g:message code="default.slide.menu.label" default="Imported Results"/>
        <b class="caret"></b>
    </a>
    <ul class="dropdown-menu">
        <li>
            <g:link class="list" controller="slide" action="list">List Slide Results</g:link>
        </li>
        <li class="divider"></li>
        <li>
            <g:link class="slide" controller="antibody" action="list">Antibodies</g:link>
        </li>
        <li>
            <g:link class="slide" controller="resultFileConfig" action="list">Result File Config</g:link>
        </li>
    </ul>
</li>
<li class="divider-vertical"></li>
<li><form class="navbar-search">
    <input type="text" class="search-query" placeholder="Search">
</form>
</li>
<li class="divider-vertical"></li>
<li><g:form class="navbar-form" name="projectForm" controller="project" action="updateSelectedProject">
    <g:hiddenField name="returnPage" value="${createLink(action:actionName, params:params, absolute: true)}"/>
    <g:select from="${Project.list()}" value="${session.projectSelected?:""}" optionKey="id" noSelection="['':'All projects']" name="projectSelect" onchange="\$('#projectForm').submit();"/>
</g:form></li>
<li class="divider-horizontal"></li>
</ul>
<ul class="nav nav-pills">
