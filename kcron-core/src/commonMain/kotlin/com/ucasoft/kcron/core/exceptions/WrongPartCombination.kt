package com.ucasoft.kcron.core.exceptions

import com.ucasoft.kcron.core.common.CronPart
import com.ucasoft.kcron.core.common.PartValue
import com.ucasoft.kcron.core.parsers.CombinationRule

class WrongPartCombination(
    private val part: Map.Entry<CronPart, PartValue>,
    private val dependency: CombinationRule,
    secondPart: PartValue
) : Throwable("Wrong part combination: part ${part.key.partName} with ${part.value.type} type requires that part ${dependency.part.partName} has ${dependency.type} type but it was ${secondPart.type}!") {

    val firstPartName : String
        get() = part.key.partName

    val secondPartName : String
        get() = dependency.part.partName
}
