package com.zer0s2m.creeptenuous.desktop.core.reactive.backend

import com.zer0s2m.creeptenuous.desktop.core.reactive.HANDLER_NAME
import com.zer0s2m.creeptenuous.desktop.core.reactive.NodeType
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveHandlerKtor
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveLazy
import io.ktor.client.statement.*
import io.ktor.util.reflect.*
import kotlin.reflect.KType
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.javaType
import kotlin.reflect.jvm.jvmErasure

/**
 * Type node handler - [NodeType.KTOR]
 */
internal object Ktor {

    /**
     * Set value to reactive properties via nodes
     *
     * @param reactiveLazyObject information about a reactive or lazy object
     * @param handler node handler under type [NodeType.KTOR]
     */
    @OptIn(ExperimentalStdlibApi::class)
    suspend fun load(reactiveLazyObject: Collection<ReactiveLazy>, handler: ReactiveHandlerKtor) {
        if (reactiveLazyObject.isEmpty()) {
            return
        }

        val response: HttpResponse? = handler.handlerKtor()
        if (response != null) {
            reactiveLazyObject.forEach {
                val method = it.handler.declaredMemberFunctions.find { function ->
                    function.name == HANDLER_NAME
                }
                if (method != null) {
                    val returnType: KType = method.returnType

                    it.field.isAccessible = true
                    it.field.set(
                        it.reactiveLazyObject,
                        response.call.bodyNullable(info = TypeInfo(
                            type = returnType.jvmErasure,
                            reifiedType = returnType.javaType,
                            kotlinType = returnType
                        ))
                    )
                    it.isLoad = true
                }
            }
        }
    }

}
