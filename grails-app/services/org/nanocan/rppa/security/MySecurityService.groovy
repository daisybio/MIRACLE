package org.nanocan.rppa.security

class MySecurityService {

    void updateAdminStatus(Person personInstance, isAdmin, adminRole) {
        def usedToBeAdmin = PersonRole.findByPersonAndRole(personInstance, adminRole) != null

        //case that this used to be an admin and shouldn't be any longer
        if (!isAdmin && usedToBeAdmin) {
            PersonRole.remove personInstance, adminRole, true
        }

        //case that this wasn't an admin before, but should be now
        else if (isAdmin && !usedToBeAdmin) {
            PersonRole.create personInstance, adminRole, true
        }
    }
}
