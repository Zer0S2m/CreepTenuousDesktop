package com.zer0s2m.creeptenuous.desktop.common.dto

import com.zer0s2m.creeptenuous.desktop.common.enums.TypeFileObject
import com.zer0s2m.creeptenuous.desktop.common.enums.TypeRight

/**
 * Data for the granted right to interact with the file object
 */
data class GrantedRight(

    val typeFileObject: TypeFileObject,

    val typeRight: List<TypeRight>,

    val titleFileObject: String

)
