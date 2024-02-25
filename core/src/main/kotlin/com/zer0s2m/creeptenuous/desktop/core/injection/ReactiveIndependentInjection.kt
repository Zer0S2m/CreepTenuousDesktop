package com.zer0s2m.creeptenuous.desktop.core.injection

/**
 * Basic annotation for setting up an independent injection using the method.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class ReactiveIndependentInjection
