package com.ucasoft.kcron.core.parsers

import com.ucasoft.kcron.core.common.CronGroups
import com.ucasoft.kcron.core.common.CronPart

class CombinationRule(val part: CronPart, val type: CronGroups, val dependencies: List<CombinationRule>? = null)