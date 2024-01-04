package com.ucasoft.kcron.builders

@RequiresOptIn(
    message = "This method returns and Iterable that possibly infinite. Be sure to limit total size when collecting result.",
)
@Target(AnnotationTarget.FUNCTION)
annotation class DelicateIterableApi
