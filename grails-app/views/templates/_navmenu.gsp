<%@ page import="org.nanocan.project.Experiment; org.nanocan.project.Project" %>
<li class="dropdown" id="main.menu">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#main.menu">
        <g:message code="default.menu.label" default="Organize"/>
        <b class="caret"></b>
    </a>
    <ul class="dropdown-menu">
        <li>
            <g:link class="list" controller="project" action="list">List Projects</g:link>
        </li>
        <li>
            <g:link class="create" controller="project" action="create">Create New Project</g:link>
        </li>
        <li>
            <g:link class="list" controller="experiment" action="list">List Experiments</g:link>
        </li>
        <li>
            <g:link class="create" controller="experiment" action="create">Create New Experiment</g:link>
        </li>

    </ul>
</li>
<li class="dropdown" id="plate.menu">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#plate.menu">
        <g:message code="default.plateLayout.menu.label" args="['...']" default="Plate Layout"/>
        <b class="caret"></b>
    </a>
    <ul class="dropdown-menu">
        <li>
            <g:link class="list" controller="plateLayout" action="list">List Layouts</g:link>
        </li>
        <li>
            <g:link class="create" controller="plateLayout" action="create">Create New Layout</g:link>
        </li>
    </ul>
</li>
<li class="dropdown" id="plate.menu">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#plate.menu">
        <g:message code="default.plate.label" args="['...']" default="Plate"/>
        <b class="caret"></b>
    </a>
    <ul class="dropdown-menu">
        <li>
            <g:link class="list" controller="plate" action="list">List Plates</g:link>
        </li>
        <li>
            <g:link class="create" controller="plate" action="create">Create New Plate</g:link>
        </li>
        <li class="divider"></li>
        <li>
            <g:link class="create" controller="plateType" action="list">Plate Types</g:link>
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
        <li>
            <g:link class="create" controller="spotting" action="index"
                    params="${[projectSelected: session.projectSelected,
                               experimentSelected: session.experimentSelected]}">Create Slide Layout from Plates</g:link>
        </li>
        <li>
            <g:link class="create" controller="slideLayout" action="createFromFile">Create Slide Layout from File</g:link>
        </li>
        <li>
            <g:link class="list" controller="extractionHead" action="list">Extraction Heads</g:link>
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
            <g:link class="slide_layout" controller="numberOfCellsSeeded" action="list">Number of Cells Seeded</g:link>
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
        <g:message code="default.slide.menu.label" default="Slide"/>
        <b class="caret"></b>
    </a>
    <ul class="dropdown-menu">
        <li>
            <g:link class="list" controller="slide" action="list">List Slide Results</g:link>
        </li>
        <li>
            <g:link class="create" controller="slide" action="create">Create Slide Result</g:link>
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
<li>
    <g:form class="navbar-search" url='[controller: "searchable", action: "index"]' id="searchableForm" name="searchableForm" method="get">
        <g:textField class="search-query" placeholder="Search" name="q" value="${params.q}"/>
    </g:form>

</li>
<li class="divider-vertical"></li>
<li class="dropdown" id="filter.menu">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#filter.menu">
        <g:message code="default.slide.menu.label" default="Filter"/>
        <b class="caret"></b>
    </a>
    <ul class="dropdown-menu">
        <li style="padding:20px;">Filter project: <g:form class="navbar-form" name="projectForm" controller="project" action="updateSelectedProject">
            <g:hiddenField name="returnPage" value="${createLink(action:actionName, params:params, absolute: true)}"/>
            <g:select style="width:350px;" from="${Project.list()}" value="${session.projectSelected?:""}" optionKey="id" noSelection="['':'All projects']" name="projectSelect" onchange="\$('#projectForm').submit();"/>
        </g:form></li>
        <li style="padding:20px;">Filter experiment: <g:form class="navbar-form" name="experimentForm" controller="experiment" action="updateSelectedExperiment">
            <g:hiddenField name="returnPage" value="${createLink(action:actionName, params:params, absolute: true)}"/>
            <g:select style="width:350px;" from="${session.projectSelected?Experiment.findAllByProject(Project.get(session.projectSelected)):Experiment.list()}" value="${session.experimentSelected?:""}" optionKey="id" noSelection="['':'All experiments']" name="experimentSelect" onchange="\$('#experimentForm').submit();"/>
        </g:form></li>
    </ul>
</li>
<li class="divider-vertical"></li>
<li>
    <g:if test="${controllerName == 'slideLayout' && actionName == 'show'}">
        <g:link class="plot" controller="analysis" action="start" params="${['slideLayout': slideLayoutInstance.id]}">Data Analysis</g:link>
    </g:if>

    <g:else>
        <g:if test="${controllerName == 'slide' && actionName == 'show'}">
            <g:link class="plot" controller="analysis" action="start" params="${['slideLayout': slideInstance.layout.id]}">Data Analysis</g:link>
        </g:if>
        <g:else>
            <g:link class="plot" controller="analysis" action="start">Data Analysis</g:link>
        </g:else>
    </g:else>
</li>
</ul>
<ul class="nav nav-pills">
