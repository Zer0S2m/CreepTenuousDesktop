package com.zer0s2m.creeptenuous.desktop.core.reactive

/**
 * Node for reactive and lazy loading of objects.
 *
 * Needed to inject data from one handler into multiple [Reactive] or [Lazy] properties.
 * Also needed for object encapsulation.
 */
annotation class Node(

    /**
     * Node type
     */
    val type: NodeType = NodeType.NONE,

    /**
     * The unit to which other nodes will inject to inject data.
     *
     * To embed multiple objects, the nodes must be named the same.
     */
    val unit: String = ""

)
