package com.usmonie.core.kit.icons.service

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.usmonie.core.kit.icons.Service

@Suppress("MagicNumber")
public val Service.IcRocketLaunchOutline: ImageVector
    get() {
        if (_icRocketLaunchOutline != null) {
            return _icRocketLaunchOutline!!
        }
        _icRocketLaunchOutline = Builder(
            name = "IcRocketLaunchOutline", defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(13.13f, 22.19f)
                lineTo(11.5f, 18.36f)
                curveTo(13.07f, 17.78f, 14.54f, 17.0f, 15.9f, 16.09f)
                lineTo(13.13f, 22.19f)
                moveTo(5.64f, 12.5f)
                lineTo(1.81f, 10.87f)
                lineTo(7.91f, 8.1f)
                curveTo(7.0f, 9.46f, 6.22f, 10.93f, 5.64f, 12.5f)
                moveTo(19.22f, 4.0f)
                curveTo(19.5f, 4.0f, 19.75f, 4.0f, 19.96f, 4.05f)
                curveTo(20.13f, 5.44f, 19.94f, 8.3f, 16.66f, 11.58f)
                curveTo(14.96f, 13.29f, 12.93f, 14.6f, 10.65f, 15.47f)
                lineTo(8.5f, 13.37f)
                curveTo(9.42f, 11.06f, 10.73f, 9.03f, 12.42f, 7.34f)
                curveTo(15.18f, 4.58f, 17.64f, 4.0f, 19.22f, 4.0f)
                moveTo(19.22f, 2.0f)
                curveTo(17.24f, 2.0f, 14.24f, 2.69f, 11.0f, 5.93f)
                curveTo(8.81f, 8.12f, 7.5f, 10.53f, 6.65f, 12.64f)
                curveTo(6.37f, 13.39f, 6.56f, 14.21f, 7.11f, 14.77f)
                lineTo(9.24f, 16.89f)
                curveTo(9.62f, 17.27f, 10.13f, 17.5f, 10.66f, 17.5f)
                curveTo(10.89f, 17.5f, 11.13f, 17.44f, 11.36f, 17.35f)
                curveTo(13.5f, 16.53f, 15.88f, 15.19f, 18.07f, 13.0f)
                curveTo(23.73f, 7.34f, 21.61f, 2.39f, 21.61f, 2.39f)
                reflectiveCurveTo(20.7f, 2.0f, 19.22f, 2.0f)
                moveTo(14.54f, 9.46f)
                curveTo(13.76f, 8.68f, 13.76f, 7.41f, 14.54f, 6.63f)
                reflectiveCurveTo(16.59f, 5.85f, 17.37f, 6.63f)
                curveTo(18.14f, 7.41f, 18.15f, 8.68f, 17.37f, 9.46f)
                curveTo(16.59f, 10.24f, 15.32f, 10.24f, 14.54f, 9.46f)
                moveTo(8.88f, 16.53f)
                lineTo(7.47f, 15.12f)
                lineTo(8.88f, 16.53f)
                moveTo(6.24f, 22.0f)
                lineTo(9.88f, 18.36f)
                curveTo(9.54f, 18.27f, 9.21f, 18.12f, 8.91f, 17.91f)
                lineTo(4.83f, 22.0f)
                horizontalLineTo(6.24f)
                moveTo(2.0f, 22.0f)
                horizontalLineTo(3.41f)
                lineTo(8.18f, 17.24f)
                lineTo(6.76f, 15.83f)
                lineTo(2.0f, 20.59f)
                verticalLineTo(22.0f)
                moveTo(2.0f, 19.17f)
                lineTo(6.09f, 15.09f)
                curveTo(5.88f, 14.79f, 5.73f, 14.47f, 5.64f, 14.12f)
                lineTo(2.0f, 17.76f)
                verticalLineTo(19.17f)
                close()
            }
        }
            .build()
        return _icRocketLaunchOutline!!
    }

private var _icRocketLaunchOutline: ImageVector? = null
