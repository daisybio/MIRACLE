<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'create.url.for.R.title', default: 'Create URL for R')}" />
    <r:require module="syntaxhighlighter"/>
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" controller="slide" action="show" id="${slideInstanceId}">Back to slide</g:link></li>
        <li><g:link class="list" controller="spotExport" action="exportAsCSV" id="${slideInstanceId}">Back to export page</g:link></li>
    </ul>
</div>

<div class="content">
    <h1>Export spots to R</h1>

    <div style="margin: 20px;">In order to import this slide into R you will need to use the read function of R in combination with a
        <a href="${exportLink}">URL</a>.</div>

    <div style="margin: 20px;">The full command for the direct import is then:</div>

    <pre class="brush: r">${importCommand}</pre>

    <div style="margin: 20px;">Double click on the command to select it, then use CTRL+C to copy to the clipboard and
    paste in the R console. <br/>You can assign your data to a variable like this:
    </div>

    <pre class="brush: r">myDataName <- read.table(...) //above command</pre>

    <script type="text/javascript">
        $(document).ready(function() { SyntaxHighlighter.all() });
    </script>

</div>
</body>
</html>