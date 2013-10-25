<g:if test="${prefix.toString().startsWith('miracle')}">
    <g:set var="attributeController" value="plateLayout"/>
</g:if>
<g:else>
    <g:set var="attributeController" value="plateImport"/>
</g:else>

<ul id="${ulid}">
    <g:each in="${layouts}" var="layout">
        <li id="${prefix}${layout.id}" class="ui-state-default">${layout}</li>
            <script type="text/javascript">
                $('#${prefix}${layout.id}').dblclick(function(){
                    window.open('${g.createLink(controller: attributeController, action:"showAttributes", params: [sampleProperty: 'cellLine', nobanner: true], id: layout.id)}', '_blank', 'height=800,width=1024,toolbar=0,location=0,menubar=0');
                });
            </script>
    </g:each>
</ul>

<script type="text/javascript">
    $( "#${ulid}").sortable({
        connectWith: ".connectedSortable",
        remove: function(event, ui){
            ui.item.clone().appendTo("#selectedPlates");
            $(this).sortable('cancel');
        }
    }).disableSelection();
</script>