package com.zer0s2m.creeptenuous.desktop.ui.screens

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.v2.ScrollbarAdapter
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zer0s2m.creeptenuous.desktop.common.dto.CommentFileObject
import com.zer0s2m.creeptenuous.desktop.common.dto.FileObject
import com.zer0s2m.creeptenuous.desktop.common.dto.ManagerFileObject
import com.zer0s2m.creeptenuous.desktop.common.dto.UserProfileSettings
import com.zer0s2m.creeptenuous.desktop.common.enums.Resources
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.common.enums.Sections
import com.zer0s2m.creeptenuous.desktop.common.enums.SizeComponents
import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreen
import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreenPage
import com.zer0s2m.creeptenuous.desktop.core.injection.ReactiveInjectionClass
import com.zer0s2m.creeptenuous.desktop.core.navigation.actions.reactiveNavigationScreen
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveMutableList
import com.zer0s2m.creeptenuous.desktop.navigation.NavigationController
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveFileObject
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveUser
import com.zer0s2m.creeptenuous.desktop.ui.components.CardModalSheet
import com.zer0s2m.creeptenuous.desktop.ui.components.CartCommentForFileObject
import com.zer0s2m.creeptenuous.desktop.ui.components.ModalRightSheetLayout
import com.zer0s2m.creeptenuous.desktop.ui.components.base.BaseDashboard
import com.zer0s2m.creeptenuous.desktop.ui.components.misc.BreadCrumbs
import com.zer0s2m.creeptenuous.desktop.ui.components.misc.BreadCrumbsItem
import com.zer0s2m.creeptenuous.desktop.ui.misc.Colors
import com.zer0s2m.creeptenuous.desktop.ui.misc.float
import com.zer0s2m.creeptenuous.desktop.ui.screens.dashboard.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * The main dashboard for interacting with system file objects
 *
 * @param navigation Handler for the navigation host for changing the current screen state
 */
class Dashboard(override var navigation: NavigationController) : BaseDashboard, ReactiveInjectionClass {

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
         * Set information about file objects by nesting level.
         *
         * @param managerFileObject information about file objects by nesting level.
         */
        internal fun setManagerFileObject(managerFileObject: ManagerFileObject) {
            this.managerFileObject.value = managerFileObject

            val folders: MutableList<FileObject> = mutableListOf()
            val files: MutableList<FileObject> = mutableListOf()

            this.managerFileObject.value.objects.forEach {
                if (it.isDirectory) folders.add(it)
                else if (it.isFile) files.add(it)
            }

            managerFileObject_Directories.value = folders
            managerFileObject_Files.value = files
        }

        /**
         * User profile information.
         */
        private val userProfile: MutableState<UserProfileSettings?> =
            mutableStateOf(ReactiveUser.profileSettings)

        /**
         * Setting user profile information.
         *
         * @param userProfile User profile information.
         */
        internal fun setUserProfile(userProfile: UserProfileSettings) {
            this.userProfile.value = userProfile
        }

        /**
         * Comments for a file object.
         */
        private val commentsInFileObject: SnapshotStateList<CommentFileObject> =
            ReactiveFileObject.commentsFileSystemObject.toMutableStateList()

        /**
         * Set comments for a file object.
         *
         * @param comments Comments for a file object.
         */
        internal fun setCommentsInFileObject(comments: ReactiveMutableList<CommentFileObject>) {
            commentsInFileObject.clear()
            commentsInFileObject.addAll(comments)
        }

    }

    /**
     * Rendering content on the left side of the dashboard
     */
    @Composable
    override fun renderLeftContent() {
        RenderLeftContentDashboard(
            systemBaseFolderForUser = baseFolderForUser
        )
    }

    /**
     * Rendering content on the right side of the dashboard
     */
    @Composable
    override fun renderRightContent() {
        val directories: MutableState<MutableList<FileObject>> = remember {
            managerFileObject_Directories
        }
        val files: MutableState<MutableList<FileObject>> = remember {
            managerFileObject_Files
        }

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
                val indexComment: Int = ContextScreen.get(
                    Screen.DASHBOARD_SCREEN,
                    "currentIndexFileObjectForInteractive"
                )
                ReactiveFileObject.commentsFileSystemObject.setReactive(indexComment, comment)
                setCommentsInFileObject(comments = ReactiveFileObject.commentsFileSystemObject)
            }
        )

        val scaffoldStateProfileUser = rememberScaffoldState()
        val scaffoldStateCommentFileObject = rememberScaffoldState()
        val scope = rememberCoroutineScope()

        val modalProfileUser = ModalRightSheetLayout(
            state = scaffoldStateProfileUser,
            modifier = Modifier
                .fillMaxSize(),
            modifierDrawerInternal = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .background(Color.White)
        )
        val modalCommentsFileObject = ModalRightSheetLayout(
            state = scaffoldStateCommentFileObject,
            modifier = Modifier
                .fillMaxSize(),
            modifierDrawerInternal = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .background(Color.White)
        )

        modalCommentsFileObject.render(
            drawerContent = {
                ContentCommentsInFileObjectModal(
                    comments = commentsInFileObject,
                    expandedStateModelInteractiveComment = expandedStateModalInteractionCommentFileObject
                )
            }
        ) {
            modalProfileUser.render(
                drawerContent = {
                    ContentProfileUserModal(
                        navigationState = navigationState
                    )
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Column (
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
                        RenderLayoutFilesObject(
                            directories = directories,
                            files = files,
                            expandedStateSetCategoryPopup = expandedStateModalSetCategoryPopup,
                            expandedStateSetColorPopup = expandedStateModalSetColorPopup,
                            expandedStateCreateFileObjectTypeDirectory = expandedStateModalCreateDirectory,
                            expandedStateModalRenameFileObject = expandedStateModalRenameFileObject,
                            scaffoldStateCommentFileObject = scaffoldStateCommentFileObject
                        )

                        BreadCrumbs(
                            items = listOf(
                                BreadCrumbsItem(
                                    text = "Folder 1",
                                    action = {
                                        println(true)
                                    }
                                ),
                                BreadCrumbsItem(
                                    text = "Folder 2",
                                    action = {
                                        println(true)
                                    }
                                ),
                                BreadCrumbsItem(
                                    text = "Folder 3",
                                    action = {
                                        println(true)
                                    }
                                )
                            ),
                            modifier = Modifier
                                .height(40.dp)
                                .background(Colors.BREAD_CRUMBS_BASE.color)
                                .fillMaxWidth()
                                .padding(4.dp, 8.dp)
                        ).render()
                    }
                }
            }
        }
    }

}

/**
 * Text for user profile navigation element
 *
 * @param text The text to be displayed.
 */
@Composable
private fun TextInCardModalSheet(text: String = "") = Text(
    text = text,
    textAlign = TextAlign.Center,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp
)

/**
 * Header for profile settings section
 *
 * @param text The text to be displayed.
 */
@Composable
private fun TitleInSectionForCardsModalSheet(text: String = ""): Unit = Text(
    text = text,
    modifier = Modifier
        .padding(4.dp, 0.dp),
    color = Colors.TEXT.color,
    fontWeight = FontWeight.Bold
)

/**
 * Renders a popup modal window to navigate to the screen state - user settings [Screen.PROFILE_SCREEN].
 *
 * @param navigationState Navigation controller for handling screen changes.
 */
@Composable
private fun ContentProfileUserModal(
    navigationState: State<NavigationController>
) {
    val baseModifierCard: Modifier = Modifier
        .height(60.dp)
        .fillMaxWidth()
        .padding(4.dp)
        .pointerHoverIcon(icon = PointerIcon.Hand)

    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    Column {
        TitleInSectionForCardsModalSheet(Sections.MAIN_PROFILE.title)
        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            content = {
                items(Sections.MAIN_PROFILE.sections.size) { index ->
                    CardModalSheet(
                        modifier = baseModifierCard
                    ) {
                        onClickCardSheet(
                            screen = Sections.MAIN_PROFILE.routes[index],
                            scope = coroutineScope,
                            sectionProfile = Sections.MAIN_PROFILE,
                            navigationState = navigationState
                        )
                    }.render {
                        TextInCardModalSheet(Sections.MAIN_PROFILE.sections[index])
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
        TitleInSectionForCardsModalSheet(Sections.USER_CONTROL.title)
        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            content = {
                items(Sections.USER_CONTROL.sections.size) { index ->
                    CardModalSheet(
                        modifier = baseModifierCard
                    ) {
                        onClickCardSheet(
                            screen = Sections.USER_CONTROL.routes[index],
                            scope = coroutineScope,
                            sectionProfile = Sections.USER_CONTROL,
                            navigationState = navigationState
                        )
                    }.render {
                        TextInCardModalSheet(Sections.USER_CONTROL.sections[index])
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
        TitleInSectionForCardsModalSheet(Sections.USER_CUSTOMIZATION.title)
        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            content = {
                items(Sections.USER_CUSTOMIZATION.sections.size) { index ->
                    CardModalSheet(
                        modifier = baseModifierCard
                    ) {
                        onClickCardSheet(
                            screen = Sections.USER_CUSTOMIZATION.routes[index],
                            scope = coroutineScope,
                            sectionProfile = Sections.USER_CUSTOMIZATION,
                            navigationState = navigationState
                        )
                    }.render {
                        TextInCardModalSheet(Sections.USER_CUSTOMIZATION.sections[index])
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
    sectionProfile: Sections,
    navigationState: State<NavigationController>
) {
    scope.launch {
        ProfileUser.setAppliedScreenFromTransitionFromPast(context = ContextScreenPage(screen))
        ProfileUser.setAppliedSectionFromTransitionFromPast(section = sectionProfile)

        reactiveNavigationScreen.action(
            state = navigationState,
            route = Screen.PROFILE_SCREEN,
            objects = listOf(),
            scope = scope
        )
    }
}

/**
 * Render the contents of a modal window to show the comments of a file object.
 *
 * @param comments Comments for file objects.
 * @param expandedStateModelInteractiveComment Current state of the modal
 * [PopupInteractionCommentFileObject] rename file object.
 */
@Composable
@Suppress("SameParameterValue")
private fun ContentCommentsInFileObjectModal(
    comments: SnapshotStateList<CommentFileObject>,
    expandedStateModelInteractiveComment: MutableState<Boolean>
) {
    Text(
        text = "File object comments",
        color = Colors.TEXT.color,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
    Spacer(
        modifier = Modifier
            .height(20.dp)
    )
    LayoutCommentsInFileObject(
        comments = comments,
        expandedStateModelInteractiveComment = expandedStateModelInteractiveComment
    )
}

/**
 * Render the contents of file object comments.
 *
 * @param comments Comments for file objects.
 * @param expandedStateModelInteractiveComment Current state of the modal
 * [PopupInteractionCommentFileObject] rename file object.
 */
@Composable
private fun LayoutCommentsInFileObject(
    comments: SnapshotStateList<CommentFileObject>,
    expandedStateModelInteractiveComment: MutableState<Boolean>
) {
    Box {
        val stateScroll: LazyListState = rememberLazyListState()
        val adapterScroll: ScrollbarAdapter = rememberScrollbarAdapter(scrollState = stateScroll)

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 12.dp),
            state = stateScroll
        ) {
            items(comments.size) { index ->
                val comment: CommentFileObject = comments[index]

                CartCommentForFileObject(
                    text = comment.comment,
                    createdAt = comment.createdAt,
                    actionEdit = {
                        ContextScreen.set(
                            Screen.DASHBOARD_SCREEN,
                            "currentFileObjectForInteractive",
                            comment
                        )
                        ContextScreen.set(
                            Screen.DASHBOARD_SCREEN,
                            "currentIndexFileObjectForInteractive",
                            index
                        )
                        expandedStateModelInteractiveComment.value = true
                    },
                    actionDelete = {
                        ReactiveFileObject.commentsFileSystemObject.removeAtReactive(index)
                        comments.removeAt(index)
                    }
                )

                if (comments.size - 1 != index) {
                    Spacer(
                        modifier = Modifier
                            .height(16.dp)
                    )
                }
            }
        }
        VerticalScrollbar(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .background(Color.Gray.copy(0.8f), RoundedCornerShape(4.dp))
                .fillMaxHeight(),
            adapter = adapterScroll
        )
    }
}
