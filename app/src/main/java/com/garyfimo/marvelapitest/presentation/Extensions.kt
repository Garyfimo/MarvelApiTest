package com.garyfimo.marvelapitest.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.triggerDeeplinkLinkedInGaryfimo() {
    val url = "https://www.linkedin.com/in/garyfimo/"
    val i = Intent(Intent.ACTION_VIEW)
    i.data = Uri.parse(url)
    startActivity(i)
}