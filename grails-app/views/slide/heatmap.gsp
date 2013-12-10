<!doctype HTML>
<meta charset = 'utf-8'>
<html>
<head>
    <r:require module="polychart2"/>
    <r:require module="jquery"/>
    <r:layoutResources />
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">

    <style>
    .rChart {
        display: block;
        margin-left: auto;
        margin-right: auto;
        width: 1200px;
        height: 800px;
    }
    </style>

</head>
<body>
<div id='dynamicHeatmap' class='rChart polycharts'></div>
<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>


<r:script>
    var dataUrl = "${g.createLink(controller:"spotExport", action: "exportSpotsForHeatmapAsJSON", id:slideId)}";
    var polyjsData = polyjs.data.url(dataUrl);

    var chartParams = {
        "dom": "dynamicHeatmap",
        "width": "1024",
        "height": "800",
        "layers": [
            {
                "x": {"var":"bin(Column,1)"},
                "y": {"var":"bin(Row,1)", sort:'Column', asc: false},
                "data": polyjsData,
                "facet": null,
                "color": "Signal",
                "type": "tile",
                "height":    800,
                "tooltip": function(item){
                             return $.ajax({
                                type: "GET",
                                url: "${g.createLink(controller: "spotExport", action:"spotDetailsForHeatmap")}" + "/" + item.id,
                                async: false
                             }).responseText;
                            }
                    }
                 ],
                "facet": {
                    "var": "Block",
                    "type": "wrap",
                    "rows":      4
                },
                "guides": {
                    "x": {
                        "title": "Column"
                    },
                    "y": {
                        "title": "Row"
                    },
                    "color": {
                        "scale": {
                            "type": "gradient2",
                            "lower": "blue",
                            "middle": "yellow",
                            "upper": "red"
                        }
                    }
                },
                "coord": [],
                "id": "dynamicHeatmap"
            }

    polyjs.chart(chartParams);
</r:script>

<r:layoutResources />
</body>
</html>