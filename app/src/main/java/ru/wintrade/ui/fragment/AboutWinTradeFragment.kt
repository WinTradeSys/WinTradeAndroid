package ru.wintrade.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_blue.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.wintrade.R
import ru.wintrade.mvp.presenter.AboutWinTradePresenter
import ru.wintrade.mvp.view.AboutWinTradeView
import ru.wintrade.ui.App

class AboutWinTradeFragment : MvpAppCompatFragment(), AboutWinTradeView {
    companion object {
        fun newInstance() = AboutWinTradeFragment()
    }

    @InjectPresenter
    lateinit var presenter: AboutWinTradePresenter

    @ProvidePresenter
    fun providePresenter() = AboutWinTradePresenter().apply {
        App.instance.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_about_wt, container, false)

    override fun init() {
        drawerSetUnlockMode()
    }

    private fun drawerSetUnlockMode() {
        requireActivity().drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        requireActivity().toolbar_blue.visibility = View.VISIBLE
    }
}