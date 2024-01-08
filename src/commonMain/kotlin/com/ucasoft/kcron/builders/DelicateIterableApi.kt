package com.ucasoft.kcron.builders

@RequiresOptIn(
    message = "This method returns Iterable that possibly infinite. Be sure to limit elements count when collecting result.",
)
@Target(AnnotationTarget.FUNCTION)
annotation class DelicateIterableApi
