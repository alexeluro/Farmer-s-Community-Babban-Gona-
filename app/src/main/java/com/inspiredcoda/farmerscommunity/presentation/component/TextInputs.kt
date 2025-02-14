package com.inspiredcoda.farmerscommunity.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.inspiredcoda.farmerscommunity.presentation.ui.theme.FarmersCommunityTheme

@Composable
fun TextInputWithLabel(
    title: String,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    value: State<String>,
    onValueChange: (String) -> Unit,
    error: String?
) {
    Column(
        modifier = Modifier
            .padding(
                top = 16.dp
            )
            .fillMaxWidth()
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .clip(RoundedCornerShape(5.dp))
            ,
            value = value.value,
            onValueChange = onValueChange,
            shape = RoundedCornerShape(7.dp),
            colors = TextFieldDefaults.colors().copy(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            textStyle = MaterialTheme.typography.bodyMedium,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            supportingText = {
                error?.let { errorMessage ->
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            },

            )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInputWithLabel(
    title: String,
    placeholder: String,
    options: List<String>,
    value: State<String>,
    onOptionClicked: (String) -> Unit,
    error: String?
) {

    var isExpanded by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .padding(
                top = 16.dp
            )
            .fillMaxWidth()
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall
        )

        Box(
            modifier = Modifier
                .padding(top = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            ExposedDropdownMenuBox(
                expanded = isExpanded,
                onExpandedChange = { isExpanded = it }
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(5.dp))
                        .menuAnchor(),
                    value = value.value,
                    onValueChange = {  },
                    shape = RoundedCornerShape(7.dp),
                    readOnly = true,
                    colors = ExposedDropdownMenuDefaults.textFieldColors().copy(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                    },
                    textStyle = MaterialTheme.typography.bodyMedium,
                    placeholder = {
                        Text(
                            text = placeholder,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    supportingText = {
                        error?.let { errorMessage ->
                            Text(
                                text = errorMessage,
                                color = Color.Red,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                )

                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ){
                        options.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = item)
                                },
                                onClick = {
                                    onOptionClicked(item)
                                    isExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun TextInputs_Preview() {
    FarmersCommunityTheme {
        TextInputWithLabel(
            title = "First name",
            placeholder = "e.g Hillsplay",
            value = remember { mutableStateOf("") },
            onOptionClicked = {},
            error = null,
            options = listOf("Rice", "Beans", "Wheat")
        )
    }
}