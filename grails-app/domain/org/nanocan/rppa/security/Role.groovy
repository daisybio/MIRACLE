package org.nanocan.rppa.security

import java.io.Serializable;

class Role implements Serializable{

	String authority

	static mapping = {
		cache true
	}

	static constraints = {
		authority blank: false, unique: true
	}
}
