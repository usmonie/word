package com.usmonie.core.kit.icons.flag

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.usmonie.core.kit.icons.Flag

@Suppress("MagicNumber")
public val Flag.It: ImageVector
    get() {
        if (_it != null) {
            return _it!!
        }
        _it = Builder(
            name = "It", defaultWidth = 128.0.dp, defaultHeight = 128.0.dp,
            viewportWidth =
            512.0f,
            viewportHeight = 512.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = EvenOdd
            ) {
                moveTo(0.0f, 0.0f)
                horizontalLineToRelative(512.0f)
                verticalLineToRelative(512.0f)
                horizontalLineTo(0.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF009246)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = EvenOdd
            ) {
                moveTo(0.0f, 0.0f)
                horizontalLineToRelative(170.7f)
                verticalLineToRelative(512.0f)
                horizontalLineTo(0.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFce2b37)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = EvenOdd
            ) {
                moveTo(341.3f, 0.0f)
                horizontalLineTo(512.0f)
                verticalLineToRelative(512.0f)
                horizontalLineTo(341.3f)
                close()
            }
        }
            .build()
        return _it!!
    }

private var _it: ImageVector? = null
