<!doctype HTML>
<meta charset = 'utf-8'>
<html>
<head>
    <r:require module="highchartsHeatmap"/>
    <r:require module="jquery"/>
    <r:layoutResources />
    <!--<link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">-->

</head>
<body>
<div id='dynamicHeatmap'/>
<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>


<r:script>$(document).ready(function() {

    var options = {

        chart: {
            renderTo: 'dynamicHeatmap',
            width: 1200,
            height: 450,
            margin: [90, 10, 30, 50]
        },

        title: {
            text: '${slideInstance.title}'
        },

        subtitle: {
            text: '${slideInstance.barcode}: ${slideInstance.antibody} - PMT ${slideInstance.photoMultiplierTube}'
        },

        legend: {
            align: 'right',
            layout: 'vertical',
            margin: 0,
            verticalAlign: 'top',
            y: 25,
            symbolHeight: 320
        },

        <g:set var="sizeParam" value="${20}"/>
        xAxis:[
            <g:each var="blockRow" in="${(1..blockRows)}">
                <g:each var="blockCol" in="${(1..blocksPerRow)}">
                    {
                        width: 900 / ${blocksPerRow} * 0.98,
                        offset: 0,
                        left: 900 / ${blocksPerRow} * 0.99 * ${(blockCol-1)} + 40,
                        tickInterval: 4,
                        title: null
                    }<g:if test="${(blockCol < blocksPerRow) || (blockRow < blockRows)}">,</g:if>
                </g:each>
            </g:each>
        ],
        yAxis:[
            <g:each var="blockRow" in="${(1..blockRows)}">
                <g:each var="blockCol" in="${(1..blocksPerRow)}">
                    {
                        height: 300 / ${blockRows} * 0.95,
                        offset: 0,
                        top: ${(blockRow-1) * (300 / blockRows)} + 80,
                        reversed: true,
                        tickInterval: 2,
                        gridLineWidth: 0,
                        startOnTick: false,
                        title: null
                    }<g:if test="${blockCol < blocksPerRow || blockRow < blockRows}">,</g:if>
                </g:each>
            </g:each>
        ],
        colorAxis: {
            startOnTick: false,
            endOnTick: false
        },

        tooltip: {
            positioner: function(w, h, p) {
                return {
                    x: p.plotX + this.chart.hoverSeries.xAxis.left + w/12,
                    y: p.plotY
                }
            },
            formatter: function() {
                 return $.ajax({
                    type: "GET",
                    url: "${g.createLink(controller: "spotExport", action:"spotDetailsForHeatmap")}" + "/" + this.point.id,
                    async: false
                 }).responseText;

            }
        },

        series: [
            <g:each var="blockRow" in="${(1..blockRows)}">
                <g:each var="blockCol" in="${(1..blocksPerRow)}">
                    <g:set var="block" value="${(blockRow-1) * blocksPerRow + blockCol}"/>
                  {
                     type: 'heatmap',
                     name: 'Block ${block}',
                     xAxis:  ${block-1},
                     yAxis:  ${block-1},
%{--                     tooltip: {
                        backgroundColor: '#FCFFC5',
                        borderWidth: 3,
                        borderRadius: 1,
                        borderColor: 'black',
                        shadow: true,
                        useHTML: true,
                        formatter: function() {
                             return $.ajax({
                                type: "GET",
                                url: "${g.createLink(controller: "spotExport", action:"spotDetailsForHeatmap")}" + "/" + this.point.id,
                                async: false
                             }).responseText;

                        }
                    },--}%
                     data: []
                 }<g:if test="${blockCol < blocksPerRow || blockRow < blockRows}">,</g:if>
                </g:each>
            </g:each>
        ]
    }
        var dataUrl = "${g.createLink(controller:'spotExport', action: 'exportSpotsForBlocksAsJSON', id:slideId)}";

        $.getJSON(dataUrl, function(data){
                for(var i = 0; i < data.length; i++){
                     options.series[i].data = data[i]
                }
            var chart = new Highcharts.Chart(options);
        });
    });
</r:script>

<r:layoutResources />
</body>
</html>