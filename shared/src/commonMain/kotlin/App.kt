import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.recipesList
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MaterialTheme {
        AnimatedListView()
    }
}

expect fun getPlatformName(): String


@Composable
fun AnimatedListView() {
    val items by remember { mutableStateOf(recipesList) }

    val listState = rememberLazyListState()

    LazyColumn(modifier = Modifier.fillMaxSize(), state = listState) {
        items(items) { item ->
            RecipeListItemWrapper(
                child = {
                    RecipeListItem(item)
                },
                scrollDirection = if (listState.isScrollingDown()) ScrollDirection.Backward else ScrollDirection.Forward
            )
        }
    }
}

@Composable
fun LazyListState.isScrollingDown(): Boolean {
    val offset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    return remember(this) { derivedStateOf { (firstVisibleItemScrollOffset - offset) > 0 } }.value
}

@Composable
fun LazyListState.isScrollingUp(): Boolean {
    val offset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    return remember(this) { derivedStateOf { (firstVisibleItemScrollOffset - offset) < 0 } }.value
}

@Composable
fun AnimatedScrollViewItem(item: String) {
    Surface(
        color = Color.Blue.copy(alpha = 0.2f),
        modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .background(color = Color.Blue.copy(alpha = 0.2f), shape = RoundedCornerShape(8.dp))
    ) {
        Text(
            text = item,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(20.dp).fillMaxWidth().height(200.dp),
        )
    }
}

private fun generateItems(): List<String> {
    return (1..100).map { "Item $it" }
}