package com.kashif.yelpfinder.presenter.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.kashif.yelpfinder.R
import com.kashif.yelpfinder.ui.theme.Black
import com.kashif.yelpfinder.ui.theme.Body
import com.kashif.yelpfinder.ui.theme.Placeholder
import com.kashif.yelpfinder.ui.theme.TextMediumGray
import com.kashif.yelpfinder.ui.theme.White
import com.kashif.yelpfinder.ui.theme.WhiteGray
import com.kashif.yelpfinder.ui.theme.YelpFinderTheme
import com.kashif.yelpfinder.util.Dimens

/**
 * Created by Mohammad Kashif Ansari on 30,October,2024
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(
    modifier: Modifier = Modifier,
    text: String,
    readOnly: Boolean,
    onClick: (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    // Create an interaction source to detect click events
    val interactionSource = remember { MutableInteractionSource() }
    // Collect the pressed state of the interaction source
    val isClicked = interactionSource.collectIsPressedAsState().value

    // Trigger the onClick callback when the search bar is pressed
    LaunchedEffect(key1 = isClicked) {
        if (isClicked) {
            onClick?.invoke()
        }
    }

    // Get the keyboard controller to manage the software keyboard
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(modifier = modifier) {
        // TextField for the search input
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .searchBar(), // Apply the custom search bar modifier
            value = text,
            onValueChange = onValueChange,
            readOnly = readOnly,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    modifier = Modifier.size(Dimens.IconSize),
                    tint = Body
                )
            },
            placeholder = {
                Text(
                    text = "Search pizza/juices...",
                    style = MaterialTheme.typography.bodySmall,
                    color = Placeholder
                )
            },
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = White,
                focusedTextColor = if (isSystemInDarkTheme()) White else Black,
                cursorColor = if (isSystemInDarkTheme()) White else Black,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch() // Trigger the search action
                    keyboardController?.hide() // Close the keyboard
                }
            ),
            textStyle = MaterialTheme.typography.bodySmall,
            interactionSource = interactionSource // Set the interaction source
        )
    }
}

// Modifier function to apply a search bar style
fun Modifier.searchBar(): Modifier = composed {
    if (!isSystemInDarkTheme()) {
        this.then(
            border(
                width = Dimens.Border,
                color = Color.Blue,
                shape = MaterialTheme.shapes.medium
            )
        )
    } else {
        this // Return original modifier if in dark theme
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SearchBarPreview() {
    YelpFinderTheme {
        CustomSearchBar(text = "", onValueChange = {}, readOnly = false) {

        }
    }
}