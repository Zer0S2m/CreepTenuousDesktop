package com.zer0s2m.creeptenuous.desktop.reactive.models

import com.zer0s2m.creeptenuous.desktop.common.dto.ConfigState
import com.zer0s2m.creeptenuous.desktop.common.dto.GrantedRight
import com.zer0s2m.creeptenuous.desktop.common.dto.IssuedRights
import com.zer0s2m.creeptenuous.desktop.common.dto.UserCategory
import com.zer0s2m.creeptenuous.desktop.common.dto.UserColor
import com.zer0s2m.creeptenuous.desktop.common.dto.UserProfileSettings
import com.zer0s2m.creeptenuous.desktop.common.dto.UserSettingsFileObjectDistribution
import com.zer0s2m.creeptenuous.desktop.core.injection.ReactiveInjection
import com.zer0s2m.creeptenuous.desktop.core.pipeline.ReactivePipeline
import com.zer0s2m.creeptenuous.desktop.core.pipeline.ReactivePipelineType
import com.zer0s2m.creeptenuous.desktop.core.reactive.Lazy
import com.zer0s2m.creeptenuous.desktop.core.reactive.Node
import com.zer0s2m.creeptenuous.desktop.core.reactive.NodeType
import com.zer0s2m.creeptenuous.desktop.core.reactive.Reactive
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveIndependentTrigger
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveLazyObject
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveMutableList
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveTrigger
import com.zer0s2m.creeptenuous.desktop.core.reactive.mutableReactiveListOf
import com.zer0s2m.creeptenuous.desktop.reactive.handlers.HandlerReactiveUserAssignedRights
import com.zer0s2m.creeptenuous.desktop.reactive.handlers.HandlerReactiveUserColor
import com.zer0s2m.creeptenuous.desktop.reactive.handlers.HandlerReactiveUserCustomCategories
import com.zer0s2m.creeptenuous.desktop.reactive.handlers.HandlerReactiveUserGrantedRights
import com.zer0s2m.creeptenuous.desktop.reactive.handlers.HandlerReactiveUserProfileSettings
import com.zer0s2m.creeptenuous.desktop.reactive.handlers.HandlerUserSettingsFileObjectDistribution
import com.zer0s2m.creeptenuous.desktop.reactive.pipelines.ReactivePipelineHandlerDeleteUserCategoryCleanInFileObject
import com.zer0s2m.creeptenuous.desktop.reactive.pipelines.ReactivePipelineHandlerDeleteUserColorCleanInFileObject
import com.zer0s2m.creeptenuous.desktop.reactive.pipelines.ReactivePipelineHandlerDeleteUserColorCleanInUserCategory
import com.zer0s2m.creeptenuous.desktop.reactive.pipelines.ReactivePipelineHandlerUpdateUserColorSetNewColorInFileObject
import com.zer0s2m.creeptenuous.desktop.reactive.pipelines.ReactivePipelineHandlerUpdateUserColorSetNewColorInUserCategory
import com.zer0s2m.creeptenuous.desktop.reactive.triggers.ReactiveTriggerUserCategoryAdd
import com.zer0s2m.creeptenuous.desktop.reactive.triggers.ReactiveTriggerUserCategoryRemove
import com.zer0s2m.creeptenuous.desktop.reactive.triggers.ReactiveTriggerUserCategorySet
import com.zer0s2m.creeptenuous.desktop.reactive.triggers.ReactiveTriggerUserColorAdd
import com.zer0s2m.creeptenuous.desktop.reactive.triggers.ReactiveTriggerUserColorRemove
import com.zer0s2m.creeptenuous.desktop.reactive.triggers.ReactiveTriggerUserColorSet
import com.zer0s2m.creeptenuous.desktop.reactive.triggers.ReactiveTriggerUserGrantedRightDelete
import com.zer0s2m.creeptenuous.desktop.reactive.triggers.ReactiveTriggerUserSettingsSetIsDeleteFileObject
import com.zer0s2m.creeptenuous.desktop.reactive.triggers.ReactiveTriggerUserSettingsSetTransferUser

/**
 * `Reactive` behavior user model
 */
object ReactiveUser : ReactiveLazyObject {

    /**
     * Custom categories for the user
     */
    @Reactive<ReactiveMutableList<UserCategory>>(
        handler = HandlerReactiveUserCustomCategories::class,
        priority = 15,
        pipelines = [
            ReactivePipeline(
                title = "deleteUserCategoryAndCleanFileObject",
                type = ReactivePipelineType.AFTER,
                pipeline = ReactivePipelineHandlerDeleteUserCategoryCleanInFileObject::class
            )
        ]
    )
    var customCategories: ReactiveMutableList<UserCategory> = mutableReactiveListOf(
        triggerAdd = ReactiveTriggerUserCategoryAdd(),
        triggerRemove = ReactiveTriggerUserCategoryRemove(),
        triggerSet = ReactiveTriggerUserCategorySet(),
        pipelinesRemove = listOf(
            "deleteUserCategoryAndCleanFileObject"
        )
    )

    /**
     * Basic parameters for the user
     */
    @Reactive<UserProfileSettings>(
        handler = HandlerReactiveUserProfileSettings::class,
        node = Node(
            type = NodeType.KTOR,
            unit = "userProfile"
        ),
        injection = ReactiveInjection(
            method = "setUserProfile"
        )
    )
    var profileSettings: UserProfileSettings? = null

    /**
     * Custom colors
     */
    @Reactive<ReactiveMutableList<UserColor>>(
        handler = HandlerReactiveUserColor::class,
        priority = 20,
        pipelines = [
            ReactivePipeline(
                title = "deleteUserColorAndCleanFileObject",
                type = ReactivePipelineType.AFTER,
                pipeline = ReactivePipelineHandlerDeleteUserColorCleanInFileObject::class
            ),
            ReactivePipeline(
                title = "updateUserColorAndSetNewColorFileObject",
                type = ReactivePipelineType.AFTER,
                pipeline = ReactivePipelineHandlerUpdateUserColorSetNewColorInFileObject::class
            ),
            ReactivePipeline(
                title = "deleteUserColorAndCleanUserCategory",
                type = ReactivePipelineType.AFTER,
                pipeline = ReactivePipelineHandlerDeleteUserColorCleanInUserCategory::class
            ),
            ReactivePipeline(
                title = "updateUserColorAndSetNewColorUserCategory",
                type = ReactivePipelineType.AFTER,
                pipeline = ReactivePipelineHandlerUpdateUserColorSetNewColorInUserCategory::class
            )
        ]
    )
    var userColors: ReactiveMutableList<UserColor> = mutableReactiveListOf(
        triggerAdd = ReactiveTriggerUserColorAdd(),
        triggerRemove = ReactiveTriggerUserColorRemove(),
        triggerSet = ReactiveTriggerUserColorSet(),
        pipelinesRemove = listOf(
            "deleteUserColorAndCleanFileObject",
            "deleteUserColorAndCleanUserCategory"
        ),
        pipelinesSet = listOf(
            "updateUserColorAndSetNewColorUserCategory",
            "updateUserColorAndSetNewColorFileObject"
        )
    )

    /**
     * Storage of user settings
     */
    object UserSettings : ReactiveLazyObject {

        /**
         * Basic settings for the distribution of file objects
         */
        @Lazy<UserSettingsFileObjectDistribution>(
            event = "Go to the user profile in the section - File object distribution settings",
            handler = HandlerUserSettingsFileObjectDistribution::class,
            node = Node(
                type = NodeType.KTOR,
                unit = "userProfile"
            ),
            triggers = [
                ReactiveTrigger(
                    event = "setTransferUserFileObjects",
                    trigger = ReactiveTriggerUserSettingsSetTransferUser::class
                ),
                ReactiveTrigger(
                    event = "setIsDeleteFileObjects",
                    trigger = ReactiveTriggerUserSettingsSetIsDeleteFileObject::class
                ),
            ]
        )
        var userSettingsFileObjectDistribution: UserSettingsFileObjectDistribution =
            UserSettingsFileObjectDistribution(null, null)

    }

    /**
     * Storage of granted rights to file objects
     */
    object GrantedRights : ReactiveLazyObject {

        /**
         * Permissions granted to other users on file objects
         */
        @Lazy<GrantedRight>(
            event = "Go to the user profile in the section - Viewing granted rights",
            handler = HandlerReactiveUserGrantedRights::class,
            independentTriggers = [
                ReactiveIndependentTrigger(
                    event = "deleteUserGrantedRight",
                    trigger = ReactiveTriggerUserGrantedRightDelete::class
                )
            ],
            injection = ReactiveInjection(
                method = "setUserProfileGrantedRightsFileObjects"
            ),
        )
        var grantedRightsFileObjects: GrantedRight = GrantedRight()

    }

    /**
     * Storage of issued rights to file objects.
     */
    object AssignedRights : ReactiveLazyObject {

        /**
         * Issued rights to interact with a file object.
         */
        @Lazy<IssuedRights>(
            event = "Fires when a request for information occurs",
            handler = HandlerReactiveUserAssignedRights::class
        )
        var assignedRightsFileObjects: IssuedRights? = null

    }

    /**
     * Storing authorization information.
     */
    object Authorization : ReactiveLazyObject {

        var authorizationInfo: ConfigState? = null

    }

    fun findCustomCategory(id: Int): UserCategory? {
        return customCategories.find { it.id == id }
    }

    fun findUserColor(id: Int): UserColor? {
        return userColors.find { it.id == id }
    }

    fun findUserColor(color: String?): UserColor? {
        return userColors.find { it.color == color }
    }

}
