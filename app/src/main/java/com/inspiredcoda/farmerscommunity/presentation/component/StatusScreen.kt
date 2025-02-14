package com.inspiredcoda.farmerscommunity.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.inspiredcoda.farmerscommunity.R
import com.inspiredcoda.farmerscommunity.presentation.ui.theme.FarmersCommunityTheme

@Composable
fun StatusScreen(
    onClick: () -> Unit
) {
    StatusScreenContainer(
        onClick = onClick
    )
}

@Composable
fun StatusScreenContainer(
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background
            ),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .padding(horizontal = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.farmer_added_successfully),
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(R.string.you_have_successfully_added_a_farmer_see_farmer_list),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = onClick
            ) {
                Row(
                    modifier = Modifier.padding(
                        vertical = 5.dp
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Proceed to farmer list"
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                }
            }

        }

    }

}

@Preview
@Composable
private fun StatusScreen_Preview() {
    FarmersCommunityTheme {
        StatusScreenContainer(
            onClick = {  }
        )
    }
}