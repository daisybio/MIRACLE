<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'protein.concentration', default: 'Protein Concentration Estimation')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>

    <r:script>$(function() {
        $("#accordion").accordion({
            collapsible:true,
            autoHeight: false
        });

    });
    </r:script>

</head>
<body>

<div class="navbar">
    <div class="navbar-inner">
        <div class="container">
            <ul class="nav">
                <g:render template="/templates/navmenu"></g:render>
                <li><g:link class="list" controller="slide" action="show" id="${id}">Back to slide</g:link></li>
            </ul>
        </div>
    </div>
</div>


<div class="content">
    <h1>Protein Concentration Estimation</h1>

    <div class="message" id="messageBox">Please select the experimental categories you would like to see separated in your analysis (grouping factors):</div>

    <div style="margin:30px;">

    <p>Here you can estimate the protein concentration of the samples in your slide using one of two algorithms (serial dilution curve or SuperCurve).
    The estimates are dimensionless and thus only allow relative comparison among samples of the same slide unless normalized in a suitable way.</p><br/>


    <g:formRemote name="plotProtConc" url="[controller:'R', id: id, action: 'plotProteinConcentration']" update="[success:'plotArea', failure:'messageBox']">

        <g:select from="${['SerialDilutionCurve', 'SuperCurve']}" name="method" value="SerialDilutionCurve"/>
        <div id="accordion" style="margin: 25px; width: 90%;">

        <h3><a href="#">Categories</a></h3>
            <div>
                <table>
                    <tr>
                        <td>Sample (x-axis)</td>
                        <td><g:select multiple="${true}" from="${layoutProperties}" value="${[:]}" name="sample" size="${layoutProperties.size()}"/></td>
                                <td>Fill (Color Separation)</td>
                                <td><g:select multiple="${true}" from="${layoutProperties}" value="${[:]}" name="fill" size="${layoutProperties.size()}"/></td>
                    </tr>
                    <tr>
                            <td>Category A</td>
                            <td><g:select multiple="${true}" from="${layoutProperties}" value="${[:]}" name="A" size="${layoutProperties.size()}"/></td>
                            <td>Category B</td>
                            <td><g:select multiple="${true}" from="${layoutProperties}" value="${[:]}" name="B" size="${layoutProperties.size()}"/></td>
                    </tr>
                    <tr>


                    </tr>
                </table>
            </div>
        </div>
        <g:submitButton name="Plot"/>
    </g:formRemote>

    <div id="plotArea"/>
    </div>

</div>
</body>
</html>