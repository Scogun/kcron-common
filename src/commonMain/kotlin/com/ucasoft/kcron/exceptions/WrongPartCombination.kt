package com.ucasoft.kcron.exceptions

import com.ucasoft.kcron.parsers.CombinationRule
import com.ucasoft.kcron.parsers.PartResult

class WrongPartCombination(
    private val part: MutableMap.MutableEntry<String, PartResult>,
    private val dependency: CombinationRule,
    secondPart: PartResult
) : Throwable("Wrong part combination: part ${part.key} with ${part.value.type} type requires that part ${dependency.partName} has ${dependency.type} type but it was ${secondPart.type}!") {

    val firstPartName : String
        get() = part.key

    val secondPartName : String
        get() = dependency.partName
}
