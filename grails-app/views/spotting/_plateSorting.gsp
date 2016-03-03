<style>
#wasteBin { list-style-type: none; margin: 0; padding: 0; float: left; margin-right: 10px; background: #4a4a4a; padding: 40px; width: 40px;}
#selectedPlates, #unselectedControlPlates, #unselectedMiraclePlates { list-style-type: none; margin: 0; padding: 0; float: left; margin-right: 10px; background: #fcefa1; padding: 5px; width: 143px;}
#selectedPlates li, #unselectedControlPlates li, #unselectedMiraclePlates li { margin: 0 5px 5px 5px; padding: 5px; font-size: 1.2em; width: 120px; }
</style>

<div style="margin:10px; float:left;">
    <b>Plates selected for spotting</b><br/><br/>
    <div class="stickable">
        <div>
            <ul id="selectedPlates" class="connectedSortable connectedWaste"/>
            Drop here
        </div>  <br/>        <br/>
        <span>
            <ul id="wasteBin" class="connectedWaste"/>
            Trash Bin
        </span>
    </div>
</div>

<div style="margin:10px; float:left;">
    <b>Available plates</b> <br/> <br/>
    <div id="miraclePlatesDiv">
        <g:render template="plateList" model="${[layouts: layouts, prefix: 'layout_miracle', ulid: 'unselectedMiraclePlates']}" />
    </div>
</div>

<div style="margin:10px; float:left;">
    <b>Control plates</b> <br/> <br/>
    <div id="controlPlatesDiv">
        <g:render template="plateList" model="${[layouts: controlPlates, prefix: 'layout_miracle', ulid: 'unselectedControlPlates']}" />
    </div>
</div>

<r:script>
    $(document).ready(function() {
        $( "#selectedPlates").sortable({
            connectWith: ".connectedSortable, .connectedWaste"
        }).disableSelection();
        $( "#wasteBin" ).sortable({
            connectWith: ".connectedWaste",
            update: function(event, ui){
                ui.item.remove();
            }
        }).disableSelection();
    });
</r:script>