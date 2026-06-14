package ru.orderdorms.features.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.orderdorms.core.domain.model.Service
import ru.orderdorms.ui.components.Dimensions

@Composable
fun QuickActionsRow(
    services: List<Service>,
    onItemClick: (Service) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensions.regularPadding, vertical = Dimensions.smallPadding)
    ) {
        for (service in services) {
            QuickActionItem(service = service, onClick = { onItemClick(service) })
        }
    }
}
