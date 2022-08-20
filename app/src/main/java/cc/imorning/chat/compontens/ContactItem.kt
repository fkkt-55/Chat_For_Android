package cc.imorning.chat.compontens

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cc.imorning.chat.R
import cc.imorning.chat.activity.ChatActivity
import cc.imorning.chat.model.Contact
import cc.imorning.chat.view.ui.ComposeDialogUtils
import cc.imorning.common.constant.Config
import cc.imorning.common.utils.AvatarUtils
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent

private const val TAG = "ContactItem"

@Composable
fun ContactItem(
    contact: Contact
) {
    val context = LocalContext.current
    val jidString = contact.jid
    val nickname = contact.nickName
    var avatarPath = AvatarUtils.instance.getAvatarPath(jidString)
    if (avatarPath == null || !AvatarUtils.instance.hasAvatar(jidString)) {
        avatarPath = AvatarUtils.instance.getOnlineAvatar(jidString)
    }
    TextButton(
        onClick = {
            val chatActivity = Intent(context, ChatActivity::class.java)
            chatActivity.action = Config.Intent.Action.START_CHAT_FROM_APP
            chatActivity.putExtra(Config.Intent.Key.START_CHAT_JID, jidString)
            chatActivity.putExtra(Config.Intent.Key.START_CHAT_TYPE, Config.ChatType.Single)
            context.startActivity(chatActivity)
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        SubcomposeAsyncImage(
            model = avatarPath,
            contentDescription = stringResource(id = R.string.desc_contact_item_avatar),
            modifier = Modifier
                .fillMaxHeight()
                .size(24.dp),
            alignment = Alignment.Center,
        ) {
            when (painter.state) {
                is AsyncImagePainter.State.Loading -> {
                    CircularProgressIndicator()
                }
                is AsyncImagePainter.State.Error -> {
                    Log.w(TAG, "on error for get avatar: $avatarPath")
                    Icon(imageVector = Icons.Filled.Person, contentDescription = null)
                }
                is AsyncImagePainter.State.Empty -> {
                    Icon(imageVector = Icons.Filled.Person, contentDescription = null)
                }
                else -> {
                    SubcomposeAsyncImageContent()
                }
            }
        }
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
        ) {
            Text(
                text = nickname,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1
            )
            Text(
                text = jidString,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Canvas(
                modifier = Modifier
                    .width(12.dp)
                    .height(12.dp)
            ) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                drawCircle(
                    color = Color.Red,
                    center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                    radius = size.minDimension / 4
                )
            }
        }
    }
    Divider(
        modifier = Modifier.padding(horizontal = 0.dp),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
    )
}