package com.imam.materialchat.presentation.chatscreen

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.imam.materialchat.state.ChatStateEvent
import com.imam.materialchat.viewmodel.ChatViewModel


class ChatScreen() : Screen {
    @Composable
    override fun Content() {
        ChatScreenContent()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreenContent(viewModel: ChatViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val state = viewModel.state.collectAsState()
    val scrollState = TopAppBarDefaults.pinnedScrollBehavior()
    val lazyState = rememberLazyListState()
    val activity = (LocalContext.current as? Activity)


    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollState.nestedScrollConnection)
            .fillMaxSize(),
        topBar = {
            TopBars(scrollState, clearChat = {
                viewModel.showDialogHistory()
            }, back = {
                activity?.finishAffinity()
            })
        }
    ) { values ->
        Column(
            modifier = Modifier
                .padding(values)
                .fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {

                state.value.list?.let { data ->
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Bottom,
                        state = lazyState
                    ) {
                        items(data) { chat ->
                            ChatList(chat = chat)
                        }
                    }
                }
                if (state.value.list!!.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = viewModel.emojiList, fontSize = 46.sp)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                ) {
                    ChatInput(
                        text = state.value.prompt,
                        onTextChange = { viewModel.onEvent(ChatStateEvent.EnteredPrompt(it)) },
                        sendCLick = {
                            // Update UI state immediately
                            viewModel.onEvent(ChatStateEvent.ButtonClicked)
                        }, progress = state.value.isLoading
                    )
                }
            }

        }

        DeleteDialog(
            isDialog = viewModel.dialogHistory,
            cancel = { viewModel.dismissDialogHistory() },
            delete = {
                viewModel.onEvent(ChatStateEvent.ClearChatClicked)
                viewModel.dismissDialogHistory()
            })


    }
    if (state.value.list!!.isNotEmpty()) {
        LaunchedEffect(scrollState) {
            lazyState.animateScrollToItem(lazyState.layoutInfo.totalItemsCount)
        }
    }
}


@Preview
@Composable
private fun ChatScreenPrev() {
    ChatScreenContent()
}