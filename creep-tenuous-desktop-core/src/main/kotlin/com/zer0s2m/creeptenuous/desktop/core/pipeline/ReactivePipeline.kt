package com.zer0s2m.creeptenuous.desktop.core.pipeline

import kotlin.reflect.KClass

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class ReactivePipeline<out T>(

    /**
     * Name of the jet pipeline.
     */
    val title: String,

    /**
     * Jet piping type.
     */
    val type: ReactivePipelineType,

    /**
     * Main pipeline handler.
     */
    val pipeline: KClass<out ReactivePipelineHandler<@UnsafeVariance T>>

)
