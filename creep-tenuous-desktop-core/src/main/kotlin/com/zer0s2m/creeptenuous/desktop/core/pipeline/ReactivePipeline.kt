package com.zer0s2m.creeptenuous.desktop.core.pipeline

import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveHandler
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveLoader
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveMutableList
import kotlin.reflect.KClass

/**
 * Basic annotation for specifying jet piping. Their main launch is
 * [ReactiveLoader] or [ReactiveMutableList].
 * IMPORTANT! Pipelines do not change or supplement data,
 * but serve only to carry out any operations before or after launch.
 *
 * @param T Type of data specified in [ReactiveHandler.handler].
 */
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
