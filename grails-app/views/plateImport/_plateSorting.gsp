<style>
#selectedPlates, #unselectedPlates { list-style-type: none; margin: 0; padding: 0; float: left; margin-right: 10px; background: #fcefa1; padding: 5px; width: 143px;}
#selectedPlates li, #unselectedPlates li { margin: 0 5px 5px 5px; padding: 5px; font-size: 1.2em; width: 120px; }
</style>

<ul id="unselectedPlates" class="connectedSortable">
    <g:each in="${plates}" var="plate">
        <li id="plate_${plate.id}" class="ui-state-default">${plate}</li>
    </g:each>
</ul>

<ul id="selectedPlates" class="connectedSortable">
</ul>

<r:script>
    $(document).ready(function() {
        $( "#unselectedPlates, #selectedPlates" ).sortable({
            connectWith: ".connectedSortable"
        }).disableSelection();
    });
</r:script>