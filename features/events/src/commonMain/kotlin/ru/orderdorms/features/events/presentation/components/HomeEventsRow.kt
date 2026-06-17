package ru.orderdorms.features.events.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.orderdorms.features.events.domain.model.Event
import ru.orderdorms.ui.components.Dimensions
import ru.orderdorms.ui.components.EventCard
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip

@Composable
fun HomeEventsRow(
    events: List<Event>,
    onEventClick: (Event) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = Dimensions.regularPadding),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.smallPadding),
        modifier = modifier.padding(vertical = Dimensions.smallPadding)
    ) {
        items(events) { event ->
            EventCard(
                title = event.title,
                subtitle = "${event.date}, ${event.time}",
                location = event.place,
                imageUrl = event.imageUrl,
                modifier = Modifier
                    .width(250.dp)
                    .clip(RoundedCornerShape(Dimensions.largeCornerRadius))
                    .clickable { onEventClick(event) }
            )
        }
    }
}
