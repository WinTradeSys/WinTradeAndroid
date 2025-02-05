package ru.wintrade.ui.resource

import android.content.Context
import ru.wintrade.mvp.model.resource.ResourceProvider

class AndroidResourceProvider(val context: Context): ResourceProvider {
    private val loadingImages = listOf("splash", "splash")
    private val onBoardImages = listOf("on_boarding")

    override fun getLoadingImages() = loadingImages.map{ getImageIdFromDrawable(it) }
    override fun getOnBoardImages() = onBoardImages.map { getImageIdFromDrawable(it) }

    private fun getImageIdFromDrawable(imageName: String): Int {
        return context.resources.getIdentifier(imageName, "drawable", context.packageName)
    }
}