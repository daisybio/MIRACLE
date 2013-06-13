<div id="plateLayout" style="overflow: auto; padding: 20px;">
	<table id="plateLayoutTable" style="border: 1px solid;">
		<thead>
			<tr align="center">
				<th style="width: 25px;" />
				<g:each in="${1..(plateLayout.cols)}" var="col">
					<th style="width: 25px;">
						${col}
					</th>
				</g:each>

			</tr>

		</thead>
		<tbody>
			<g:each in="${1..(plateLayout.rows)}" var="row">
				<tr id="r${row}">
					<td>
						${row}
					</td>
					<g:each in="${1..(plateLayout.cols)}">
						<td
							style="border: 1px solid; background-color:${wellList.get(well)?.properties[sampleProperty]?wellList.get(well).properties[sampleProperty].color?:'#e0e0e0':''};">
							<input name="${wellList.get(well).id}" type="hidden" value="">
						</td>
						<g:set var="well" value="${++well}" />
					</g:each>

				</tr>
			</g:each>

		</tbody>

	</table>
</div>
<div style="padding-left: 20px; padding-bottom: 20px;">
	<input type="submit" value="Save changes" name="layoutUpdateButton" />
</div>