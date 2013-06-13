package org.nanocan.rppa.webflow

class IdCountingService {
	def idCounter = [:]
	def getId(String objectType){
		if(!idCounter.get(objectType)){
			idCounter.put(objectType,1)
			return 1
		}
		else{
			def i = idCounter.get(objectType)
			i++
			idCounter.remove(objectType)
			idCounter.put(objectType, i)
			return i
		}
	}
}
