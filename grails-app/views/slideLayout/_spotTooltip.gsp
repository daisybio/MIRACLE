<head>
    <style>
    #draggableSpotTooltip { width:250px; padding: 0.5em; background-color: #ffffff; position: fixed;
        z-index: 10000; opacity: 0.95; top: 135px; right: 190px;
        border-color: #e6e6e6; border-bottom-width: 1px; border-style: solid; display:none;}
    </style>
    <r:script>
        $(function(){
            $( "#draggableSpotTooltip").draggable();
        });
    </r:script>
</head>
<body>
    <div id="draggableSpotTooltip">Spot</div>
</body>