package ru.orderdorms.features.events.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikepenz.markdown.m3.Markdown
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import com.mikepenz.markdown.coil3.Coil3ImageTransformerImpl
import com.mikepenz.markdown.m3.markdownColor
import ru.orderdorms.ui.icons.SeaechIco
import ru.orderdorms.features.events.domain.model.Event
import ru.orderdorms.ui.components.Dimensions
import ru.orderdorms.ui.components.EventCard
import ru.orderdorms.ui.components.MarkDownBottomSheet
import ru.orderdorms.ui.theme.OrderTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsScreen(
    viewModel: EventsViewModel = rememberEventsViewModel()
) {
    val state = viewModel.state

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFF2F2F2))) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Все События",
                style = OrderTheme.typography.displayMedium.copy(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(Dimensions.regularPadding)
            )

            SearchBar(
                query = state.searchQuery,
                onQueryChange = viewModel::onSearchQueryChanged,
                modifier = Modifier.padding(horizontal = Dimensions.regularPadding)
            )

            Spacer(modifier = Modifier.height(Dimensions.smallPadding))

            LazyColumn(
                modifier = Modifier.weight(1f).fillMaxWidth(0.85f),
                contentPadding = PaddingValues(
                    start = Dimensions.regularPadding,
                    end = Dimensions.regularPadding,
                    bottom = Dimensions.largePadding
                )
            ) {
                items(state.filteredEvents) { event ->
                    EventCard(
                        title = event.title,
                        subtitle = "${event.date}, ${event.time}",
                        location = event.place,
                        imageUrl = event.imageUrl,
                        modifier = Modifier
                            .padding(vertical = Dimensions.smallPadding)
                            .fillMaxWidth()
                            .clickable { viewModel.onEventSelected(event) }
                    )
                }
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        state.errorMessage?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center).clickable { viewModel.loadEvents() }
            )
        }

        state.selectedEvent?.let { event ->
            MarkDownBottomSheet(
                data = event.descriptionMarkdown,
                onDismiss = { viewModel.onEventSelected(null) }
            )
        }
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(Dimensions.smallCornerRadius))
            .background(Color(0xFFE0E0E0))
            .padding(horizontal = Dimensions.smallPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = SeaechIco,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.width(Dimensions.tinyPadding))
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = OrderTheme.typography.bodyLarge.copy(color = Color.Black),
            decorationBox = { innerTextField ->
                if (query.isEmpty()) {
                    Text(
                        text = "Поиск событий",
                        color = Color.Gray,
                        style = OrderTheme.typography.bodyLarge
                    )
                }
                innerTextField()
            }
        )
    }
}


