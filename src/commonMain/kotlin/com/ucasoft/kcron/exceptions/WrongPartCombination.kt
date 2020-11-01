package com.ucasoft.kcron.exceptions

import com.ucasoft.kcron.common.CronPart
import com.ucasoft.kcron.parsers.CombinationRule
import com.ucasoft.kcron.common.PartValue

class WrongPartCombination(
    private val part: MutableMap.MutableEntry<CronPart, PartValue>,
    private val dependency: CombinationRule,
    secondPart: PartValue
) : Throwable("Wrong part combination: part ${part.key.partName} with ${part.value.type} type requires that part ${dependency.part.partName} has ${dependency.type} type but it was ${secondPart.type}!") {

    val firstPartName : String
        get() = part.key.partName

    val secondPartName : String
        get() = dependency.part.partName
}
