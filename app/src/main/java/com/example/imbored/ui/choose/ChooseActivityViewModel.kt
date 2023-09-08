package com.example.imbored.ui.choose

import androidx.lifecycle.ViewModel
import com.example.imbored.R

class ChooseActivityViewModel : ViewModel() {
    val tabs = listOf(
        R.string.busywork,
        R.string.charity,
        R.string.cooking,
        R.string.diy,
        R.string.education,
        R.string.music,
        R.string.recreational,
        R.string.relaxation,
        R.string.social,
    )

    fun onTabClick(tabIndex: Int) {

    }
}