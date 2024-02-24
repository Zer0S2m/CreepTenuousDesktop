package com.zer0s2m.creeptenuous.desktop.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import com.zer0s2m.creeptenuous.desktop.common.dto.BreadCrumbFileObject
import com.zer0s2m.creeptenuous.desktop.common.dto.CommentFileObject
import com.zer0s2m.creeptenuous.desktop.common.dto.FileObject
import com.zer0s2m.creeptenuous.desktop.common.dto.ManagerFileObject
import com.zer0s2m.creeptenuous.desktop.common.dto.UserProfileSettings
import com.zer0s2m.creeptenuous.desktop.common.enums.Resources
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.common.enums.SectionsProfileUser
import com.zer0s2m.creeptenuous.desktop.common.enums.SizeComponents
import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreen
import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreenPage
import com.zer0s2m.creeptenuous.desktop.core.injection.ReactiveIndependentInjection
import com.zer0s2m.creeptenuous.desktop.core.injection.ReactiveInjection
import com.zer0s2m.creeptenuous.desktop.core.injection.ReactiveInjectionClass
import com.zer0s2m.creeptenuous.desktop.core.navigation.actions.reactiveNavigationScreen
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveLoader
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveMutableList
import com.zer0s2m.creeptenuous.desktop.navigation.NavigationController
import com.zer0s2m.creeptenuous.desktop.reactive.actions.ActionSwitchBreadCrumbsThroughDirectories
import com.zer0s2m.creeptenuous.desktop.reactive.actions.ActionSwitchMainThroughDirectories
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveFileObject
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveUser
import com.zer0s2m.creeptenuous.desktop.ui.components.BaseBreadCrumbsItem
import com.zer0s2m.creeptenuous.desktop.ui.components.BreadCrumbs
import com.zer0s2m.creeptenuous.desktop.ui.components.BreadCrumbsItem
import com.zer0s2m.creeptenuous.desktop.ui.components.CardModalSheetSectionProfileUser
import com.zer0s2m.creeptenuous.desktop.ui.components.ModalRightSheetLayout
import com.zer0s2m.creeptenuous.desktop.ui.components.TextInCardModalSheet
import com.zer0s2m.creeptenuous.desktop.ui.components.TitleInSectionForCardsModalSheet
import com.zer0s2m.creeptenuous.desktop.ui.components.TopPanelDashboard
import com.zer0s2m.creeptenuous.desktop.ui.components.misc.Colors
import com.zer0s2m.creeptenuous.desktop.ui.components.misc.float
import com.zer0s2m.creeptenuous.desktop.ui.screens.dashboard.PopupContentCommentsInFileObjectModal
import com.zer0s2m.creeptenuous.desktop.ui.screens.dashboard.PopupContentInfoFileObjectModal
import com.zer0s2m.creeptenuous.desktop.ui.screens.dashboard.PopupCreateFileObjectTypeDirectory
import com.zer0s2m.creeptenuous.desktop.ui.screens.dashboard.PopupInteractionCommentFileObject
import com.zer0s2m.creeptenuous.desktop.ui.screens.dashboard.PopupRenameFileObject
import com.zer0s2m.creeptenuous.desktop.ui.screens.dashboard.PopupSetUserCategoryInFileObject
import com.zer0s2m.creeptenuous.desktop.ui.screens.dashboard.PopupSetUserColorInFileObject
import com.zer0s2m.creeptenuous.desktop.ui.screens.dashboard.RenderLayoutFilesObject
import com.zer0s2m.creeptenuous.desktop.ui.screens.dashboard.RenderLeftContentDashboard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * The main dashboard for interacting with system file objects
 *
 * @param navigation Handler for the navigation host for changing the current screen state
 */
class Dashboard(var navigation: NavigationController) : ReactiveInjectionClass {

    private val navigationState: State<NavigationController> = mutableStateOf(navigation)

    /**
     * Base directories for system user
     */
    private val baseFolderForUser: Map<String, String> = mapOf(
        "Videos" to Resources.ICON_VIDEO.path,
        "Documents" to Resources.ICON_DOCUMENT.path,
        "Images" to Resources.ICON_IMAGE.path,
        "Musics" to Resources.ICON_MUSIC.path
    )

    /**
     * Current state of the modal [PopupSetUserCategoryInFileObject] when setting a custom category
     */
    private val expandedStateModalSetCategoryPopup: MutableState<Boolean> = mutableStateOf(false)

    /**
     * Current state of the modal [PopupSetUserColorInFileObject] when setting a custom color
     */
    private val expandedStateModalSetColorPopup: MutableState<Boolean> = mutableStateOf(false)

    /**
     *  Current state of the modal [PopupCreateFileObjectTypeDirectory] when create directory
     */
    private val expandedStateModalCreateDirectory: MutableState<Boolean> = mutableStateOf(false)

    /**
     * Current state of the modal [PopupRenameFileObject] rename file object.
     */
    private val expandedStateModalRenameFileObject: MutableState<Boolean> = mutableStateOf(false)

    /**
     * Current state of the modal [PopupInteractionCommentFileObject] rename file object.
     */
    private val expandedStateModalInteractionCommentFileObject: MutableState<Boolean> =
        mutableStateOf(false)

    internal companion object {

        /**
         * Information about the directory at a certain segment of the nesting level
         */
        private val managerFileObject: MutableState<ManagerFileObject> =
            mutableStateOf(ReactiveFileObject.managerFileSystemObjects)

        private val managerFileObject_Directories: MutableState<MutableList<FileObject>> =
            mutableStateOf(mutableListOf())

        private val managerFileObject_Files: MutableState<MutableList<FileObject>> =
            mutableStateOf(mutableListOf())

        /**
         * User profile information.
         */
        private val userProfile: MutableState<UserProfileSettings?> =
            mutableStateOf(ReactiveUser.profileSettings)

        /**
         * Comments for a file object.
         */
        private val commentsInFileObject: SnapshotStateList<CommentFileObject> =
            ReactiveFileObject.commentsFileSystemObject.toMutableStateList()

        /**
         * Information about whether data has been downloaded.
         */
        private val managerFileObjectIsLoad: MutableState<Boolean> = mutableStateOf(false)

        /**
         * Breadcrumbs (navigation).
         */
        private val itemsBreadCrumbs: MutableState<Collection<BreadCrumbFileObject>> = mutableStateOf(mutableListOf())

        /**
         * The name of the current directory on the top panel.
         */
        private val titleSwitchPanelDashboard: MutableState<String> = mutableStateOf("Main")

        /**
         * Whether system names have been set for the main folders for each user.
         */
        private val isSetBaseFolderUser: MutableState<Boolean> = mutableStateOf(false)

        /**
         * Set information about file objects by nesting level.
         *
         * @param managerFileObject information about file objects by nesting level.
         */
        @ReactiveInjection
        internal fun setManagerFileObject(managerFileObject: ManagerFileObject) {
            this.managerFileObject.value = managerFileObject

            val folders: MutableList<FileObject> = mutableListOf()
            val files: MutableList<FileObject> = mutableListOf()

            this.managerFileObject.value.objects.forEach {
                if (it.isDirectory) folders.add(it)
                else if (it.isFile) files.add(it)
            }

            managerFileObject_Directories.value = folders.sortedBy { it.realName }.toMutableList()
            managerFileObject_Files.value = files.sortedBy { it.realName }.toMutableList()

            if (!isSetBaseFolderUser.value) {
                val systemNameFolderVideos = managerFileObject_Directories.value.find {
                    it.realName == "Videos"
                }?.systemName ?: ""
                val systemNameFolderMusics = managerFileObject_Directories.value.find {
                    it.realName == "Musics"
                }?.systemName ?: ""
                val systemNameFolderDocuments = managerFileObject_Directories.value.find {
                    it.realName == "Documents"
                }?.systemName ?: ""
                val systemNameFolderImages = managerFileObject_Directories.value.find {
                    it.realName == "Images"
                }?.systemName ?: ""

                isSetBaseFolderUser.value = true

                ContextScreen.set(
                    Screen.DASHBOARD_SCREEN,
                    mapOf(
                        "systemNameFolderUser_Videos" to systemNameFolderVideos,
                        "systemNameFolderUser_Musics" to systemNameFolderMusics,
                        "systemNameFolderUser_Documents" to systemNameFolderDocuments,
                        "systemNameFolderUser_Images" to systemNameFolderImages
                    )
                )
            }
        }

        /**
         * Setting user profile information.
         *
         * @param userProfile User profile information.
         */
        @ReactiveInjection
        internal fun setUserProfile(userProfile: UserProfileSettings) {
            this.userProfile.value = userProfile
        }

        /**
         * Set comments for a file object.
         *
         * @param comments Comments for a file object.
         */
        @ReactiveInjection
        internal fun setCommentsInFileObject(comments: ReactiveMutableList<CommentFileObject>) {
            commentsInFileObject.clear()
            commentsInFileObject.addAll(comments)
        }

        /**
         * Set information about whether data has been downloaded.
         *
         * @param isLoad Information about whether data has been downloaded.
         */
        @ReactiveInjection
        internal fun setManagerFileObjectIsLoad(isLoad: Boolean) {
            managerFileObjectIsLoad.value = isLoad
        }

        /**
         * Install breadcrumbs (navigation).
         *
         * @param itemsBreadCrumbs Breadcrumbs (navigation).
         */
        @ReactiveIndependentInjection
        internal fun setItemsBreadCrumbs(itemsBreadCrumbs: Collection<BreadCrumbFileObject>) {
            this.itemsBreadCrumbs.value = itemsBreadCrumbs
        }

        /**
         * Set the name of the current directory on the top panel.
         *
         * @param title Name of the current directory.
         */
        @ReactiveIndependentInjection
        internal fun setTitleSwitchPanelDashboard(title: String) {
            titleSwitchPanelDashboard.value = title
        }

    }

    init {
        ContextScreen.set(
            Screen.DASHBOARD_SCREEN,
            mapOf(
                "currentLevelManagerDirectory" to 0,
                "currentParentsManagerDirectory" to listOf<String>().toMutableList(),
                "currentSystemParentsManagerDirectory" to listOf<String>().toMutableList()
            )
        )
    }

    /**
     * Splitter render for splitting the screen into two parts
     *
     * @param scaffoldState State of this scaffold widget.
     */
    @Composable
    fun render(
        scaffoldState: ScaffoldState
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            scaffoldState = scaffoldState
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    renderLeftContent()
                    renderRightContent()
                }
            }
        }
    }

    /**
     * Rendering content on the left side of the dashboard
     */
    @Composable
    fun renderLeftContent() {
        val scope: CoroutineScope = rememberCoroutineScope()

        RenderLeftContentDashboard(
            scope = scope,
            titleSwitchPanelDashboard = titleSwitchPanelDashboard,
            systemBaseFolderForUser = baseFolderForUser
        )
    }

    /**
     * Rendering content on the right side of the dashboard
     */
    @Composable
    fun renderRightContent() {
        val directories: MutableState<MutableList<FileObject>> = remember {
            managerFileObject_Directories
        }
        val files: MutableState<MutableList<FileObject>> = remember {
            managerFileObject_Files
        }

        val scope = rememberCoroutineScope()
        val scaffoldStateProfileUser = rememberScaffoldState()
        val scaffoldStateCommentFileObject = rememberScaffoldState()
        val scaffoldStateInfoFileObject = rememberScaffoldState()

        PopupSetUserCategoryInFileObject(
            expandedState = expandedStateModalSetCategoryPopup,
            actionSetCategory = {
                // TODO: A crutch for forcing layout reflow
                directories.value = mutableListOf()
                files.value = mutableListOf()
            }
        )
        PopupSetUserColorInFileObject(
            expandedState = expandedStateModalSetColorPopup,
            actionSetColor = {
                // TODO: A crutch for forcing layout reflow
                directories.value = mutableListOf()
                files.value = mutableListOf()
            }
        )
        PopupCreateFileObjectTypeDirectory(
            expandedState = expandedStateModalCreateDirectory
        )
        PopupRenameFileObject(
            expandedState = expandedStateModalRenameFileObject,
            actionRename = {
                // TODO: A crutch for forcing layout reflow
                directories.value = mutableListOf()
                files.value = mutableListOf()
            }
        )
        PopupInteractionCommentFileObject(
            expandedState = expandedStateModalInteractionCommentFileObject,
            actionSave = { comment: CommentFileObject ->
                val isEdit: Boolean = ContextScreen.get(
                    Screen.DASHBOARD_SCREEN,
                    "isEditFileObjectForInteractive"
                )
                val isCreate: Boolean = ContextScreen.get(
                    Screen.DASHBOARD_SCREEN,
                    "isCreateFileObjectForInteractive"
                )

                if (isEdit && !isCreate) {
                    val indexComment: Int = ContextScreen.get(
                        Screen.DASHBOARD_SCREEN,
                        "currentIndexFileObjectForInteractive"
                    )

                    scope.launch {
                        ReactiveFileObject.commentsFileSystemObject.setReactive(indexComment, comment)
                        setCommentsInFileObject(comments = ReactiveFileObject.commentsFileSystemObject)
                    }
                } else if (!isEdit && isCreate) {
                    scope.launch {
                        ReactiveFileObject.commentsFileSystemObject.addReactive(comment)
                        setCommentsInFileObject(comments = ReactiveFileObject.commentsFileSystemObject)
                    }
                }

                ContextScreen.clearValueByKey(
                    Screen.DASHBOARD_SCREEN, listOf(
                        "isEditFileObjectForInteractive",
                        "isCreateFileObjectForInteractive",
                        "currentCommentFileObjectForInteractive",
                        "currentIndexFileObjectForInteractive"
                    )
                )
            },
            onDismissRequest = { ContextScreen.clearScreen(Screen.DASHBOARD_SCREEN) }
        )

        val modifierDrawerInternal: Modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .background(Color.White)

        ModalRightSheetLayout(
            state = scaffoldStateInfoFileObject,
            modifier = Modifier.fillMaxSize(),
            modifierDrawerInternal = modifierDrawerInternal,
            drawerContent = {
                PopupContentInfoFileObjectModal(
                    scaffoldState = scaffoldStateInfoFileObject
                )
            }
        ) {
            ModalRightSheetLayout(
                state = scaffoldStateCommentFileObject,
                modifier = Modifier.fillMaxSize(),
                modifierDrawerInternal = modifierDrawerInternal,
                drawerContent = {
                    PopupContentCommentsInFileObjectModal(
                        comments = commentsInFileObject,
                        expandedStateModelInteractiveComment = expandedStateModalInteractionCommentFileObject
                    )
                }
            ) {
                ModalRightSheetLayout(
                    state = scaffoldStateProfileUser,
                    modifier = Modifier.fillMaxSize(),
                    modifierDrawerInternal = modifierDrawerInternal,
                    drawerContent = {
                        ContentProfileUserModal(
                            navigationState = navigationState,
                            userProfile = userProfile
                        )
                    }
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight(SizeComponents.UPPER_BLOCK_LEFT_PANEL.float)
                        ) {
                            TopPanelDashboard(
                                scaffoldState = scaffoldStateProfileUser,
                                scope = scope,
                                avatar = if (userProfile.value != null)
                                    mutableStateOf(userProfile.value!!.avatar)
                                else mutableStateOf(null)
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.94f)
                            ) {
                                LayoutFileObjects(
                                    scope = scope,
                                    directories = directories,
                                    files = files,
                                    scaffoldStateCommentFileObject = scaffoldStateCommentFileObject,
                                    scaffoldStateInfoFileObject = scaffoldStateInfoFileObject
                                )
                            }
                            Column(modifier = Modifier.fillMaxSize()) {
                                LayoutBreadCrumbs(scope = scope)
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun LayoutFileObjects(
        scope: CoroutineScope,
        directories: MutableState<MutableList<FileObject>>,
        files: MutableState<MutableList<FileObject>>,
        scaffoldStateCommentFileObject: ScaffoldState,
        scaffoldStateInfoFileObject: ScaffoldState
    ) {
        when {
            !managerFileObjectIsLoad.value -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.secondaryVariant,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            else -> {
                RenderLayoutFilesObject(
                    scope = scope,
                    directories = directories,
                    files = files,
                    expandedStateSetCategoryPopup = expandedStateModalSetCategoryPopup,
                    expandedStateSetColorPopup = expandedStateModalSetColorPopup,
                    expandedStateCreateFileObjectTypeDirectory = expandedStateModalCreateDirectory,
                    expandedStateModalRenameFileObject = expandedStateModalRenameFileObject,
                    scaffoldStateCommentFileObject = scaffoldStateCommentFileObject,
                    scaffoldStateInfoFileObject = scaffoldStateInfoFileObject
                )
            }
        }
    }

    private fun getMainBreadCrumb(scope: CoroutineScope): BaseBreadCrumbsItem {
        return BreadCrumbsItem(
            text = "Main",
            onClick = { ActionSwitchMainThroughDirectories.call(scope = scope) }
        )
    }

    @Composable
    private fun LayoutBreadCrumbs(scope: CoroutineScope) {
        val itemsBreadCrumbsLocal: MutableList<BaseBreadCrumbsItem> = mutableListOf()
        itemsBreadCrumbsLocal.add(getMainBreadCrumb(scope))
        itemsBreadCrumbs.value.forEachIndexed { index: Int, breadCrumbFileObject: BreadCrumbFileObject ->
            itemsBreadCrumbsLocal.add(BreadCrumbsItem(
                text = breadCrumbFileObject.realName,
                onClick = { ActionSwitchBreadCrumbsThroughDirectories.call(scope = scope, index) }
            ))
        }

        BreadCrumbs(
            items = itemsBreadCrumbsLocal,
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.BREAD_CRUMBS_BASE.color)
                .padding(4.dp, 8.dp)
        )
    }

}

/**
 * Renders a popup modal window to navigate to the screen state - user settings [Screen.PROFILE_SCREEN].
 *
 * @param navigationState Navigation controller for handling screen changes.
 */
@Composable
private fun ContentProfileUserModal(
    navigationState: State<NavigationController>,
    userProfile: MutableState<UserProfileSettings?>
) {
    val baseModifierCard: Modifier = Modifier
        .height(60.dp)
        .fillMaxWidth()
        .padding(4.dp)
        .pointerHoverIcon(icon = PointerIcon.Hand)

    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    Column {
        TitleInSectionForCardsModalSheet(SectionsProfileUser.MAIN_PROFILE.title)
        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            content = {
                items(SectionsProfileUser.MAIN_PROFILE.sections.entries.size) { index: Int ->
                    val sectionInfo: Map.Entry<String, Boolean> =
                        SectionsProfileUser.MAIN_PROFILE.sections.entries.toList()[index]

                    if (!sectionInfo.value || (sectionInfo.value && userProfile.value?.role?.contains("ROLE_ADMIN") != false)) {
                        CardModalSheetSectionProfileUser(
                            onClick = {
                                onClickCardSheet(
                                    screen = SectionsProfileUser.MAIN_PROFILE.routes[index],
                                    scope = coroutineScope,
                                    sectionProfile = SectionsProfileUser.MAIN_PROFILE,
                                    navigationState = navigationState
                                )
                            },
                            modifier = baseModifierCard
                        ) {
                            TextInCardModalSheet(sectionInfo.key)
                        }
                    }
                }
            }
        )
    }

    Divider(
        modifier = Modifier.padding(bottom = 12.dp),
        thickness = 0.dp
    )

    Column {
        TitleInSectionForCardsModalSheet(SectionsProfileUser.USER_CONTROL.title)
        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            content = {
                items(SectionsProfileUser.USER_CONTROL.sections.entries.size) { index ->
                    val sectionInfo: Map.Entry<String, Boolean> =
                        SectionsProfileUser.USER_CONTROL.sections.entries.toList()[index]

                    if (!sectionInfo.value || (sectionInfo.value && userProfile.value?.role?.contains("ROLE_ADMIN") != false)) {
                        CardModalSheetSectionProfileUser(
                            onClick = {
                                onClickCardSheet(
                                    screen = SectionsProfileUser.USER_CONTROL.routes[index],
                                    scope = coroutineScope,
                                    sectionProfile = SectionsProfileUser.USER_CONTROL,
                                    navigationState = navigationState
                                )
                            },
                            modifier = baseModifierCard
                        ) {
                            TextInCardModalSheet(SectionsProfileUser.USER_CONTROL.sections.entries.toList()[index].key)
                        }
                    }
                }
            }
        )
    }

    Divider(
        modifier = Modifier.padding(bottom = 12.dp),
        thickness = 0.dp
    )

    Column {
        TitleInSectionForCardsModalSheet(SectionsProfileUser.USER_CUSTOMIZATION.title)
        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            content = {
                items(SectionsProfileUser.USER_CUSTOMIZATION.sections.entries.size) { index ->
                    val sectionInfo: Map.Entry<String, Boolean> =
                        SectionsProfileUser.USER_CUSTOMIZATION.sections.entries.toList()[index]

                    if (!sectionInfo.value || (sectionInfo.value && userProfile.value?.role?.contains("ROLE_ADMIN") != false)) {
                        CardModalSheetSectionProfileUser(
                            onClick = {
                                onClickCardSheet(
                                    screen = SectionsProfileUser.USER_CUSTOMIZATION.routes[index],
                                    scope = coroutineScope,
                                    sectionProfile = SectionsProfileUser.USER_CUSTOMIZATION,
                                    navigationState = navigationState
                                )
                            },
                            modifier = baseModifierCard
                        ) {
                            TextInCardModalSheet(SectionsProfileUser.USER_CUSTOMIZATION.sections.entries.toList()[index].key)
                        }
                    }
                }
            }
        )
    }
}

/**
 * Event when clicking on the button to go to the section of an individual user profile
 *
 * @param screen Internal profile screen to go to
 * @param scope Defines a scope for new coroutines
 * @param sectionProfile New section in profile
 * @param navigationState Navigation controller for handling screen changes
 */
private fun onClickCardSheet(
    screen: Screen,
    scope: CoroutineScope,
    sectionProfile: SectionsProfileUser,
    navigationState: State<NavigationController>
) {
    ReactiveLoader.runReactiveIndependentInjection(
        method = "setAppliedScreenFromTransitionFromPast",
        value = ContextScreenPage(screen)
    )
    ReactiveLoader.runReactiveIndependentInjection(
        method = "setAppliedSectionFromTransitionFromPast",
        value = sectionProfile
    )

    scope.launch {
        reactiveNavigationScreen.action(
            state = navigationState,
            route = Screen.PROFILE_SCREEN,
            objects = listOf(),
            scope = scope
        )
    }
}
