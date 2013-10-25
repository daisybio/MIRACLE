<style>
#wasteBin { list-style-type: none; margin: 0; padding: 0; float: left; margin-right: 10px; background: #4a4a4a; padding: 40px; width: 40px;}
#selectedPlates, #unselectedSavanahPlateLayouts, #unselectedMiraclePlateLayouts { list-style-type: none; margin: 0; padding: 0; float: left; margin-right: 10px; background: #fcefa1; padding: 5px; width: 143px;}
#selectedPlates li, #unselectedSavanahPlateLayouts li, #unselectedMiraclePlateLayouts li { margin: 0 5px 5px 5px; padding: 5px; font-size: 1.2em; width: 120px; }
</style>

<div style="margin:10px; float:left;">
    <b>SAVANAH Plate Layouts</b> <br/> <br/>
    Select a project:
    <div>
        <g:select from="${savanahProjects}" name="project" value="${project}"
                  noSelection="['':'']"
                  onchange="${g.remoteFunction(controller: 'plateImport', action: 'filterSavanahExperimentsByProject', params: '\'&project=\'+this.value', update: 'experimentSelect')}"/>
    </div>  <br/>
    Select an experiment:
    <div id="experimentSelect">
        <g:select from="${experiments}" name="experiment" noSelection="['':'']" value="${experiment}"
                  onchange="${g.remoteFunction(controller: 'plateImport', action: 'filterSavanahPlateLayoutsByExperiment', params: '\'&experiment=\'+this.value', update: 'savanahPlateLayoutsDiv')}"/>
    </div>
    <br/><br/>
    <div id="savanahPlateLayoutsDiv">
        <g:render template="plateLayoutList" model="${[layouts: savanahLayouts, prefix: 'layout_savanah', ulid: 'unselectedSavanahPlateLayouts']}"/>
    </div>
</div>

<div style="margin:10px; float:left;">
    <b>MIRACLE Plate Layouts</b> <br/> <br/>
    Select a project:
    <div>
        <g:select from="${miracleProjects}" name="project" value="${session.projectSelected?miracleProjects.find{it.id == session.projectSelected as Long}:null}"
                  noSelection="['':'']"
                  onchange="${g.remoteFunction(controller: 'plateImport', action: 'filterMiraclePlateLayoutsByProject', params: '\'&project=\'+this.value', update: 'miraclePlateLayoutsDiv')}"/>
    </div> <br/><br/>
    <div id="miraclePlateLayoutsDiv">
        <g:render template="plateLayoutList" model="${[layouts: session.projectSelected?miracleProjects.find{it.id == session.projectSelected as Long}.plateLayouts:miracleLayouts, prefix: 'layout_miracle', ulid: 'unselectedMiraclePlateLayouts']}" />
    </div>
</div>

<div style="margin:10px; float:left;">
    <b>Plate Layouts Selected For Spotting</b><br/><br/>
    <ul id="selectedPlates" class="connectedSortable connectedWaste"/>
</div>

<div style="margin:10px; float:right;">
    Trash Bin <br/>
    <ul id="wasteBin" class="connectedWaste"/>
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