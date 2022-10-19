package com.movies.shared.extensions

import android.view.View

fun View.toggleVisibility(value: Boolean) {
    visibility = if (value) View.VISIBLE else View.GONE
}