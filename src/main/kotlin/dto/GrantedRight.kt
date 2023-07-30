package dto

import enums.TypeFileObject
import enums.TypeRight

/**
 * Data for the granted right to interact with the file object
 */
data class GrantedRight(

    val typeFileObject: TypeFileObject,

    val typeRight: List<TypeRight>,

    val titleFileObject: String

)
