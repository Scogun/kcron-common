package com.ucasoft.kcron.core.exceptions

class WrongPartCombinations(combinationExceptions: List<WrongPartCombination>) :
    Throwable("Multiple combination rules have been violated: ${combinationExceptions.joinToString { ce -> "${ce.firstPartName} -> ${ce.secondPartName}" }}!")
