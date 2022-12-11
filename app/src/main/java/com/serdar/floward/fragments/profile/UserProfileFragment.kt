package com.serdar.floward.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.serdar.floward.R
import com.serdar.floward.fragments.list.UserListViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class UserProfileFragment: Fragment() {

    private val viewModel: UserListViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return context?.let {
            val view = ComposeView(it)
            view.apply {
                setContent {
                    Column {
                        imageCard()
                        postList()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    fun imageCard() {
        Card(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
                .height(200.dp)
                .wrapContentSize(align = Alignment.Center),
            shape = RoundedCornerShape(15.dp)
        ) {
            GlideImage(
                model = viewModel.selectedUser?.url,
                contentDescription = getString(R.string.profile_image),
                contentScale = ContentScale.FillWidth
            )
        }
    }

    @Composable
    fun postList() {
        LazyColumn {
            viewModel.selectedUser?.posts?.let {
                itemsIndexed(it) { index, item ->
                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.background(
                                Color.LightGray,
                                shape = RoundedCornerShape(15.dp)
                            )
                        ) {
                            Text(
                                text = item.title.orEmpty(),
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth()
                            )
                            Text(
                                text = item.body.orEmpty(),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}