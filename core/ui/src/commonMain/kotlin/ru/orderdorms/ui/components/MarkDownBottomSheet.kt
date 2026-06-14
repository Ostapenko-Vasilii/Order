package ru.orderdorms.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mikepenz.markdown.coil3.Coil3ImageTransformerImpl
import com.mikepenz.markdown.m3.Markdown
import com.mikepenz.markdown.m3.markdownColor
import ru.orderdorms.ui.theme.OrderTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarkDownBottomSheet(data : String,onDismiss: () -> Unit, sheetState: SheetState = rememberModalBottomSheetState()) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .width(40.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color.Gray)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.regularPadding)
                .padding(bottom = 32.dp)
        ) {
            Markdown(
                typography = OrderTheme.markdownTypography,
                colors = markdownColor(text = OrderTheme.colors.primaryTextColor),
                content = data,
                imageTransformer = Coil3ImageTransformerImpl,
                modifier = Modifier.fillMaxWidth(),
                components = customMarkdownComponents
            )
        }
    }
}