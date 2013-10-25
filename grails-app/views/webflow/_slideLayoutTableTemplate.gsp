<g:set var="spot" value="${0}"/>
<g:set var="spotList" value="${spots.toList()}"/>

<g:if test="${blocksPerRow}">
    <g:set var="blocksPerRow" value="${Math.min(blocksPerRow, numberOfBlocks)}"/>
</g:if>
<g:else>
    <g:set var="blocksPerRow" value="${12}"/>
</g:else>

<!-- Figure out how many tabs we need with n blocks per tab -->
<g:set var="fullTabsNeeded"
	value="${(int) (numberOfBlocks / blocksPerRow)}" />
<g:set var="blocksInLastTab"
	value="${numberOfBlocks % blocksPerRow}" />
<g:if test="${blocksInLastTab != 0}">
	<g:set var="tabsNeeded" value="${++fullTabsNeeded}"></g:set>
</g:if>
<g:else>
	<g:set var="tabsNeeded" value="${fullTabsNeeded}" />
</g:else>

<!-- Template for rendering the tables in slidelayout -->
<ul>
	<g:each var="i" in="${1..tabsNeeded}">
		<g:set var="tab" value="${((i-1) * blocksPerRow)+1}" />
		<g:if test="${blocksInLastTab != 0 && i == tabsNeeded}">
			<g:set var="upperBound" value="${blocksInLastTab}" />
		</g:if>
		<g:else>
			<g:set var="upperBound" value="${blocksPerRow}" />
		</g:else>
		<li><a href="#blockTabs-${i}"
			onclick="registerHandlers('blockTable${i}');">Blocks ${tab}..${tab+upperBound-1}</a></li>
	</g:each>
</ul>

<g:each var="i" in="${1..tabsNeeded}">
	<g:set var="tab" value="${((i-1) * blocksPerRow)+1}" />
	<g:if test="${blocksInLastTab != 0 && i == tabsNeeded}">
		<g:set var="upperBound" value="${blocksInLastTab}" />
	</g:if>
	<g:else>
		<g:set var="upperBound" value="${blocksPerRow}" />
	</g:else>

	<div id="blockTabs-${i}">
		<table id="blockTable${i}" style="border: 1px solid;">
			<thead>
				<tr>
					<th>Block</th>
					<g:each in="${tab..(tab+upperBound-1)}" var="block">
						<th colspan="${columnsPerBlock}">
							${block}
						</th>
					</g:each>
				</tr>

				<tr align="center">
					<th>Column</th>
					<g:each in="${tab..(tab+upperBound-1)}">
						<g:each in="${1..(columnsPerBlock)}" var="col">
							<th style="width: 25px;">
								${col}
							</th>
						</g:each>
					</g:each>
				</tr>
			</thead>

			<tbody>
				<g:each in="${1..(rowsPerBlock)}" var="row">
					<tr id="r${row+1}">
						<td>
							${row}
						</td>
						<g:each in="${tab..(tab+upperBound-1)}" var="block">
							<g:each in="${1..(columnsPerBlock)}" var="col">
								<g:if
									test="${spot < spotList.size() && spotList.get(spot).row == row && spotList.get(spot).col == col && spotList.get(spot).block == block }">
									<td
										style="border: 1px solid; background-color:${spotList.get(spot)?.properties[sampleProperty]?spotList.get(spot).properties[sampleProperty].color?:'#e0e0e0':'#ffffff'};"><input
										name="${spotList.get(spot).id}" type="hidden" value=""></td>
									<g:set var="spot" value="${++spot}" />
								</g:if>
								<g:else>
									<td style="border: 1px solid"></td>
								</g:else>
							</g:each>
						</g:each>
					</tr>
				</g:each>
			</tbody>
		</table>
	</div>
</g:each>
