<%@ page import="org.nanocan.rppa.layout.SpotType; org.nanocan.rppa.layout.Inducer; org.nanocan.rppa.layout.Dilution; org.nanocan.rppa.layout.LysisBuffer; org.nanocan.rppa.layout.CellLine" %>
<html>
<head>
    <style>
    #draggableLegend { width:290px; padding: 0.5em; background-color: #ffffff; position: fixed;
        z-index: 10000; opacity: 0.95; top: 135px; left: 10px;
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
                        "<table><tr><td>" + ui.item.value + "</td><td><div id='colorPickDiv' style='" +
                         "background-color:" + ui.item.colour + "; border: 1px solid; width:25px; height:25px;'/></td></tr></table> ";
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

        Select a sample: <g:textField id="sampleSelection" name="sampleSelection"/>

        <div style="float:right;"><input type="button" value="Deselect" onclick="alert('Now just click on a sample to deselect');
        selColor='#ffffff'; selName=''; selId = '';
        document.getElementById('colorPickDiv').setAttribute('style', 'margin: 20px; background-color: #ffffff; border: 1px solid; width:25px; height:25px;');
        "/></div><br/> <br/>

        Selected: <br/>

        <div id="legendSample">
              <div class="message">Please select a sample</div>
        </div>

        <br/>

        Samples in this layout:<br/>(click to select) <br/><br/>
        <g:include action="sampleList" id="${layoutId}"></g:include>
    </div>

</body>
</html>