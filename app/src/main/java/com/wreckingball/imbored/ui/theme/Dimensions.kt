package com.wreckingball.imbored.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Dimensions(
    val ChooseActivityMargin: Dp = 16.dp,
    val ChooseActivitySpacer: Dp = 16.dp,
    val ChooseActivitySmallSpacer: Dp = 8.dp,
    val APDropdownWidth: Dp = 174.dp,

    val DisplayActivityMargin: Dp = 16.dp,

    val AttributionTextSize: TextUnit = 8.sp,
    val DisplayActivityTitleSize: TextUnit = 42.sp,
    val DisplayActivityBodySize: TextUnit = 28.sp,
)