package com.ucasoft.kcron.parsers

class SecondsMinutesParser(partName: String) : TimeParser(partName,"[0-9]|[1-5][0-9]", "[0-9]|[1-5][0-9]|60")