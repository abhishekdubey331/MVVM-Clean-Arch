package com.truecaller.assignment.utils

import android.app.Application
import com.truecaller.assignment.R


interface StringUtils {
    fun noNetworkErrorMessage(): String
    fun somethingWentWrong(): String
}

class StringUtilsImpl(private val appContext: Application) : StringUtils {
    override fun noNetworkErrorMessage() =
        appContext.getString(R.string.message_no_network_connected_str)

    override fun somethingWentWrong() =
        appContext.getString(R.string.message_something_went_wrong_str)
}

