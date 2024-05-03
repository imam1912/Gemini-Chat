package com.imam.materialchat.presentation.chatscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.twotone.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imam.materialchat.R
import com.imam.materialchat.model.Chat
import dev.jeziellago.compose.markdowntext.MarkdownText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBars(
    scrollBehavior: TopAppBarScrollBehavior,
    clearChat: () -> Unit,
    back: () -> Unit,
) {
    CenterAlignedTopAppBar(title = {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Gemini Chat", fontFamily = FontFamily(Font(R.font.popinr)))

        }
    }, navigationIcon = {
        IconButton(onClick = { back() }) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
        }
    }, actions = {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(end = 10.dp)
        ) {
            IconButton(onClick = {
                clearChat()
            }) {
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Clear Chat")
            }
        }
    }, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ), scrollBehavior = scrollBehavior
    )
}

@Composable
fun ChatList(chat: Chat) {
    Column(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 8.dp)
            .fillMaxWidth()
            .padding(
                end = if (chat.direction) 80.dp else 0.dp,
                start = if (!chat.direction) 80.dp else 0.dp
            ),
        horizontalAlignment = if (chat.direction) Alignment.Start else Alignment.End
    ) {
        Card(
            shape = if (chat.direction)
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 20.dp,
                    bottomStart = 20.dp,
                    bottomEnd = 20.dp
                )
            else
                RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 0.dp,
                    bottomStart = 20.dp,
                    bottomEnd = 20.dp
                ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = Modifier.clickable {
            }
        ) {
            Box(contentAlignment = Alignment.Center) {
                MarkdownText(
                    modifier = Modifier.padding(12.dp),
                    markdown = chat.message,
                    isTextSelectable = true,
                    fontResource = R.font.popinr,
                    style = TextStyle(
                        fontSize = 15.sp,
                        lineHeight = 10.sp,
                        textAlign = TextAlign.Justify,
                    )
                )

            }
        }

    }

}

@Composable
fun ChatInput(
    text: String,
    onTextChange: (String) -> Unit,
    sendCLick: () -> Unit,
    progress: Boolean,
) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = onTextChange,
        shape = MaterialTheme.shapes.extraLarge,
        colors = TextFieldDefaults.colors(
            focusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        label = {
            Text(text = "Message", fontFamily = FontFamily(Font(R.font.popinr)))
        },
        trailingIcon = {
            if (progress) {
                CircularProgressIndicator(
                    modifier = Modifier.size(30.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                IconButton(onClick = {
                    sendCLick()
                }) {
                    Icon(imageVector = Icons.TwoTone.Send, contentDescription = null)
                }
            }

        }
    )
}

@Composable
fun DeleteDialog(isDialog: Boolean, cancel: () -> Unit, delete: () -> Unit) {
    if (isDialog) {
        AlertDialog(onDismissRequest = { }, title = {
            Text(
                text = "Do You Want To Clear The History?",
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.popinr))
            )
        }, confirmButton = {
            TextButton(onClick = { delete() }) {
                Text(text = "Yes", fontFamily = FontFamily(Font(R.font.popinr)))
            }
        }, text = {}, dismissButton = {
            TextButton(onClick = { cancel() }) {
                Text(text = "No", fontFamily = FontFamily(Font(R.font.popinr)))
            }
        })
    }
}





