<!doctype HTML>
<meta charset = 'utf-8'>
<html>
<head>
    <style>
    .rChart {
        display: block;
        margin-left: auto;
        margin-right: auto;
        width: 1024px;
        height: 800px;
    }
    </style>

</head>
<body>
<div id='dynamicHeatmap' class='rChart polycharts'></div>

<script type="text/javascript">
    var dataUrl = "${g.createLink(controller:"spotExport", action: "exportSpotsAsJSON", id:slideId)};"
    var polyjsData = polyjs.data.url(dataUrl);

    var chartParams = {
        "dom": "dynamicHeatmap",
        "width": "1024",
        "height": "800",
        "layers": [
            {
                "x": "bin(Column,1)",
                "y": "bin(Row,1)",
                "data": polyjsData,
                "facet": null,
                "color": "Signal",
                "type": "tile",
                "height":    800//,
                //"tooltip": function(item){return 'Sample: ' + item.SampleName + '\n' + 'Block: ' + item.Block + '\n' + 'Row: ' + item.Row + '\n' + 'Column: ' + item.Column + '\n' + 'Deposition: ' + item.Deposition + '\n' + 'CellLine: ' + item.CellLine + '\n' + 'NumberOfCellsSeeded: ' + item.NumberOfCellsSeeded + '\n' + 'Treatment: ' + item.Treatment + '\n' + 'LysisBuffer: ' + item.LysisBuffer + '\n' + 'Signal: ' + item.Signal + '\n' + 'FG: ' + item.FG + '\n' + 'BG: ' + item.BG}
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
                            "type": "gradient",
                            "lower": "blue",
                            "upper": "red"
                        }
                    }
                },
                "coord": [],
                "id": "dynamicHeatmap"
            }
            /*_.each(chartParams.layers, function(el){
                el.data = polyjs.data(el.data)
            })*/
        var graph_dynamicHeatmap = polyjs.chart(chartParams);
</script>

</body>
</html>