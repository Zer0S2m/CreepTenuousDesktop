package screens.user

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dto.GrantedRight
import enums.Screen
import enums.TypeFileObject
import enums.TypeRight
import screens.ProfileUser

/**
 * Rendering part of the user profile screen [Screen.PROFILE_GRANTED_RIGHTS_SCREEN]
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileUser.ProfileGrantedRights.render() {
    val list: MutableList<GrantedRight> = listOf(
        GrantedRight(TypeFileObject.FOLDER, listOf(TypeRight.MOVE), "Folder 1"),
        GrantedRight(TypeFileObject.FILE, listOf(TypeRight.COPY), "File 1"),
        GrantedRight(TypeFileObject.FILE, listOf(TypeRight.SHOW), "File 2"),
        GrantedRight(TypeFileObject.FOLDER, listOf(TypeRight.COPY, TypeRight.SHOW), "Folder 2"),
        GrantedRight(TypeFileObject.FILE, listOf(TypeRight.MOVE, TypeRight.SHOW), "File 3"),
        GrantedRight(TypeFileObject.FOLDER, listOf(TypeRight.MOVE, TypeRight.SHOW), "Folder 3"),
        GrantedRight(TypeFileObject.FOLDER, listOf(TypeRight.MOVE, TypeRight.SHOW, TypeRight.DELETE), "Folder 4"),
        GrantedRight(TypeFileObject.FOLDER, listOf(TypeRight.MOVE, TypeRight.SHOW, TypeRight.DELETE, TypeRight.COPY, TypeRight.UPLOAD), "Folder 5"),
        GrantedRight(TypeFileObject.FOLDER, listOf(TypeRight.MOVE, TypeRight.SHOW, TypeRight.DELETE), "Folder 6"),
        GrantedRight(TypeFileObject.FILE, listOf(TypeRight.MOVE, TypeRight.SHOW, TypeRight.DELETE, TypeRight.DOWNLOAD), "Folder 4"),
    ).toMutableStateList()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {

        }

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(4),
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(list.size) { index ->
                ItemGrantedRight(item = list[index])
            }
        }
    }

}

/**
 * Basic grant card for interacting with file objects
 */
@Composable
internal fun ProfileUser.ProfileGrantedRights.ItemGrantedRight(
    item: GrantedRight
) {
    BaseCardItemGrid(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Column {
            Row {
                Text(
                    if (item.typeFileObject == TypeFileObject.FILE) "File: ${item.titleFileObject}"
                    else "Folder: ${item.titleFileObject}"
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(end = 4.dp)
                ) {
                    Text(text = "Types:")
                }
                Column {
                    item.typeRight.forEach { type ->
                        Text(
                            text = type.title,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        IconButtonDelete(onClick = {})
    }
}
