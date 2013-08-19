<div id="heatmapPopup" style="width: 100%; height: 100%;">
    Creating heatmap (this usually takes a while)...
    <img src="<g:resource dir="images" file="spinner.gif"/>"/>
</div>

<r:require module="jquery"/>  <r:layoutResources/>

<script type="text/javascript">
    $(document).ready(function(){<g:remoteFunction update="heatmapPopup" controller="r" action="plotHeatmap" id="${slideId}"/>});
</script>