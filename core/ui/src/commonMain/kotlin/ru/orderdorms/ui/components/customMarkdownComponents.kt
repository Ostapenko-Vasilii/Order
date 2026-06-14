package ru.orderdorms.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.mikepenz.markdown.compose.LocalImageTransformer
import com.mikepenz.markdown.compose.components.MarkdownComponentModel
import com.mikepenz.markdown.compose.components.MarkdownComponents
import com.mikepenz.markdown.compose.components.markdownComponents

val customMarkdownComponents: MarkdownComponents = markdownComponents(
    image = { model -> MarkdownBannerImage(model) },
    inlineImage = { model -> MarkdownBannerImage(model) },
)

@Composable
private fun MarkdownBannerImage(model: MarkdownComponentModel) {
    val transformer = LocalImageTransformer.current
    val imageData = transformer.transform(model.content) ?: return

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimensions.regularCornerRadius)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = imageData.painter,
            contentDescription = imageData.contentDescription,
            modifier = Modifier.fillMaxWidth(),
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            alpha = imageData.alpha,
            colorFilter = imageData.colorFilter
        )
    }
}