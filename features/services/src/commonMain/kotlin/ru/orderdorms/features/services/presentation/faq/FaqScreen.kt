package ru.orderdorms.features.services.presentation.faq

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.orderdorms.features.services.domain.model.faq.FaqCategory
import ru.orderdorms.features.services.domain.model.faq.FaqQuestion
import ru.orderdorms.ui.components.Dimensions
import ru.orderdorms.ui.components.MarkDownBottomSheet
import ru.orderdorms.ui.icons.ArrowBack
import ru.orderdorms.ui.icons.ArrowRightIco
import ru.orderdorms.ui.icons.SeaechIco
import ru.orderdorms.ui.theme.OrderTheme

@Composable
fun FaqFlow(onBack: () -> Unit) {
    val viewModel = rememberFaqViewModel()
    val state = viewModel.state

    FaqScreen(
        state = state,
        onBack = onBack,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onQuestionClick = viewModel::onQuestionClick,
        onDismissBottomSheet = { viewModel.onQuestionClick(null) },
        onRefresh = viewModel::loadCategories,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FaqScreen(
    state: FaqState,
    onBack: () -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onQuestionClick: (FaqQuestion) -> Unit,
    onDismissBottomSheet: () -> Unit,
    onRefresh: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2)),
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .systemBarsPadding()
                .padding(Dimensions.smallPadding),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable { onBack() },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = ArrowBack,
                    contentDescription = "Назад",
                    modifier = Modifier.size(16.dp),
                    tint = Color.Black,
                )
            }

            Text(
                text = "База Знаний",
                style = OrderTheme.typography.displayMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier.padding(start = Dimensions.regularPadding),
            )
        }

        // Search Bar
        Row(
            modifier = Modifier
                .padding(horizontal = Dimensions.regularPadding)
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(Dimensions.smallCornerRadius))
                .background(Color(0xFFE0E0E0))
                .padding(horizontal = Dimensions.smallPadding),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = SeaechIco,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color.Gray,
            )
            Spacer(modifier = Modifier.width(Dimensions.smallPadding))
            BasicTextField(
                value = state.searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier.fillMaxWidth(),
                textStyle = OrderTheme.typography.bodyLarge.copy(color = Color.Black),
                decorationBox = { innerTextField ->
                    if (state.searchQuery.isEmpty()) {
                        Text(
                            text = "Поиск по вопросам",
                            color = Color.Gray,
                            style = OrderTheme.typography.bodyLarge,
                        )
                    }
                    innerTextField()
                },
            )
        }

        Spacer(modifier = Modifier.height(Dimensions.regularPadding))

        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = OrderTheme.colors.activeColor,
                )
            } else if (state.error != null) {
                Text(
                    text = state.error.message,
                    color = Color.Red,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clickable { onRefresh() }
                        .padding(Dimensions.regularPadding),
                )
            } else {
                CategoryList(
                    categories = state.filteredCategories,
                    onQuestionClick = onQuestionClick,
                )
            }
        }

        if (state.selectedQuestion != null) {
            MarkDownBottomSheet(
                data = state.selectedQuestion.answerMarkdown,
                onDismiss = onDismissBottomSheet,
            )
        }
    }
}

@Composable
private fun CategoryList(
    categories: List<FaqCategory>,
    onQuestionClick: (FaqQuestion) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = Dimensions.regularPadding,
            end = Dimensions.regularPadding,
            bottom = Dimensions.largePadding,
        ),
        verticalArrangement = Arrangement.spacedBy(Dimensions.regularPadding),
    ) {
        items(categories) { category ->
            FaqCategoryItem(category = category, onQuestionClick = onQuestionClick)
        }
    }
}

@Composable
private fun FaqCategoryItem(
    category: FaqCategory,
    onQuestionClick: (FaqQuestion) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = category.title,
            style = OrderTheme.typography.displayMedium.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            ),
            modifier = Modifier.padding(bottom = Dimensions.smallPadding),
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(Dimensions.regularCornerRadius))
                .background(Color(0xFFE0E0E0)),
        ) {
            category.questions.forEachIndexed { index, question ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onQuestionClick(question) }
                        .padding(Dimensions.regularPadding),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = category.icon,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = Color(0xFF666666),
                    )
                    Spacer(modifier = Modifier.width(Dimensions.regularPadding))
                    Text(
                        text = question.title,
                        style = OrderTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                        modifier = Modifier.weight(1f),
                    )
                    Icon(
                        ArrowRightIco,
                        contentDescription = null,
                        tint = Color(0xFF666666),
                    )
                }
                if (index < (category.questions.size - 1)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.LightGray.copy(alpha = 0.5f)),
                    )
                }
            }
        }
    }
}
