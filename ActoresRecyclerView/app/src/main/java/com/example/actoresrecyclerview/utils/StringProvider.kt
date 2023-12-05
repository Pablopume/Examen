package com.example.actoresrecyclerview.utils

import android.content.Context

class StringProvider(private val context: Context) {
    fun getString(resourceId: Int): String {
        return context.getString(resourceId)
    }
}