<g:select from="${experiments}" name="experiment" value="${experiment}"
          noSelection="['':'']"
          onchange="${g.remoteFunction(action: filterAction, params: '\'&experiment=\'+this.value', update: updateDiv?:'plateSelect')}"/>
