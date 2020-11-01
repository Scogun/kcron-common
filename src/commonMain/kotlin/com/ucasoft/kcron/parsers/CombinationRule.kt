package com.ucasoft.kcron.parsers

import com.ucasoft.kcron.common.CronGroups

class CombinationRule(val partName:String, val type: CronGroups, val dependencies: List<CombinationRule>? = null)