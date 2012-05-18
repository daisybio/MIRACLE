<%@ page import="org.nanocan.rppa.layout.SpotType; org.nanocan.rppa.layout.Inducer; org.nanocan.rppa.layout.Dilution; org.nanocan.rppa.layout.LysisBuffer; org.nanocan.rppa.layout.CellLine" %>
<html>
<head>
    <style>
    #draggableLegend { width:250px; padding: 0.5em; background-color: #ffffff; position: fixed;
        z-index: 10000; opacity: 0.95; top: 135px; left: 190px; height: 200px;
        border-color: #e6e6e6; border-bottom-width: 1px; border-style: solid;}
    </style>
    <r:script>
        $(function(){
            $( "#draggableLegend").draggable();
        });
    </r:script>

    <r:script>
            $(document).ready(function(){

               $('#sampleSelection').autocomplete({
                    source: '<g:createLink controller='sample' action='ajaxSampleFinder'/>',
                    minLength: 0,
                    select: function(event, ui) {
                        document.getElementById('legendSample').innerHTML =
                        "<div id='colorPickDiv' style='margin: 20px; background-color:" + ui.item.colour + "; border: 1px solid; width:25px; height:25px;'/>";
                        selColor = ui.item.colour;
                        selName = ui.item.value;
                        selId = ui.item.id;
                    }
                });
            });
    </r:script>

</head>
<body>

    <div id="draggableLegend">

        Select a sample: <g:textField id="sampleSelection" name="sampleSelection"/>   <br/>

        <div id="legendSample">
              <div class="message">Please select a sample</div>
        </div>  <br/> <br/>

        <input type="button" value="Deselect" onclick="alert('Now just click on a sample to deselect');
        selColor='#ffffff'; selName=''; selId = '';
        document.getElementById('colorPickDiv').setAttribute('style', 'margin: 20px; background-color: #ffffff; border: 1px solid; width:25px; height:25px;');
        "/>
    </div>

</body>
</html>