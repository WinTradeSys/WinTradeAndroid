package ru.wintrade.mvp.presenter

import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import ru.wintrade.mvp.model.resource.ResourceProvider
import ru.wintrade.mvp.presenter.adapter.IOnBoardListPresenter
import ru.wintrade.mvp.view.OnBoardView
import ru.wintrade.mvp.view.item.OnBoardItemView
import ru.wintrade.navigation.Screens
import javax.inject.Inject

class OnBoardPresenter : MvpPresenter<OnBoardView>() {

    @Inject
    lateinit var resourceProvider: ResourceProvider

    @Inject
    lateinit var router: Router

    val listPresenter = OnBoardListPresenter()

    inner class OnBoardListPresenter : IOnBoardListPresenter {
        val images = mutableListOf<Int>()

        override fun getCount() = images.size

        override fun bindView(view: OnBoardItemView) {
            view.setImage(images[view.pos])
        }

        override fun onNextBtnClick(pos: Int) {
            viewState.savePreference()
            router.replaceScreen(Screens.TradersMainScreen())
        }
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()

        val images = resourceProvider.getOnBoardImages()
        listPresenter.images.clear()
        listPresenter.images.addAll(images)
    }
}