package com.ucasoft.kcron.parsers

import com.ucasoft.kcron.common.CronGroups
import com.ucasoft.kcron.common.CronPart

class CombinationRule(val part: CronPart, val type: CronGroups, val dependencies: List<CombinationRule>? = null)