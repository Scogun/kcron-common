package com.ucasoft.kcron.exceptions

class WrongPartCombinations(val combinationExceptions: List<WrongPartCombination>) :
    Throwable("Multiple combination rules have been violated: ${combinationExceptions.joinToString { ce -> "${ce.firstPartName} -> ${ce.secondPartName}" }}!")
