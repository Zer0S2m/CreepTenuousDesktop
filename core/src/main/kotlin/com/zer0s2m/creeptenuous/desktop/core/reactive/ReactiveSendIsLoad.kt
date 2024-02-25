package com.zer0s2m.creeptenuous.desktop.core.reactive

import com.zer0s2m.creeptenuous.desktop.core.injection.ReactiveInjection

/**
 * Main annotation to indicate the data that was loaded.
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class ReactiveSendIsLoad(

    /**
     * Should I send information that the data has been downloaded.
     */
    val isSend: Boolean = false,

    /**
     * Injection to introduce information that data has been downloaded.
     */
    val injection: ReactiveInjection = ReactiveInjection()

)
