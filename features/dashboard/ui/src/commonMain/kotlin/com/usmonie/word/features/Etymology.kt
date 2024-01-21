package com.usmonie.word.features

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.ui.BaseCard

@Composable
fun EtymologyCard(etymology: String, modifier: Modifier = Modifier) {
    BaseCard(elevation = 2.dp, modifier = modifier) {
        Spacer(Modifier.height(20.dp))
        EtymologyTitle()
        Spacer(Modifier.height(4.dp))
        Text(
            etymology,
            textAlign = TextAlign.Justify,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 24.dp),
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
fun EtymologyTitle() {
    TitleUiComponent(
        "Etymology",
        Modifier.padding(horizontal = 24.dp),
        color = MaterialTheme.colorScheme.onSurface,
    )
}
