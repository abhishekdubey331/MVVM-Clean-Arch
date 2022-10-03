package com.sample.template.utils

import android.app.Application
import com.sample.template.R


interface StringUtils {
    fun noNetworkErrorMessage(): String
    fun somethingWentWrong(): String
    fun loading(): String
}

class StringUtilsImpl(private val appContext: Application) : StringUtils {
    override fun noNetworkErrorMessage() =
        appContext.getString(R.string.message_no_network_connected_str)

    override fun somethingWentWrong() =
        appContext.getString(R.string.message_something_went_wrong_str)

    override fun loading() =
        appContext.getString(R.string.loading_str)
}
