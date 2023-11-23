package org.ldv.springbootaventure.model.entity

import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded
import java.io.Serializable

@Embeddable
class LigneInventaireId (
    val personnageId : Long,
    val itemId : Long
):Serializable{

}