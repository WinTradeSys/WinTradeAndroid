package ru.wintrade.mvp.presenter

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import ru.wintrade.mvp.model.entity.Profile
import ru.wintrade.mvp.model.repo.ApiRepo
import ru.wintrade.mvp.model.repo.ProfileRepo
import ru.wintrade.mvp.model.repo.RoomRepo
import ru.wintrade.mvp.view.MainView
import ru.wintrade.navigation.Screens
import javax.inject.Inject

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {
    @Inject
    lateinit var profile: Profile

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var profileRepo: ProfileRepo

    @Inject
    lateinit var roomRepo: RoomRepo

    @Inject
    lateinit var apiRepo: ApiRepo

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()

        if (profile.hasVisitedTutorial) {
            if (profile.user != null) {
                if (profile.user!!.isTrader)
                    router.newRootScreen(Screens.TraderMeMainScreen())
                else
                    router.newRootScreen(Screens.SubscriberMainScreen())
            } else
                router.newRootScreen(Screens.TradersMainScreen())
        } else
            router.newRootScreen(Screens.OnBoardScreen())
    }


    fun tradersMenuClicked() {
        router.replaceScreen(Screens.TradersMainScreen())
    }

    fun observationMenuClicked() {
        if (profile.user != null) {
            if (profile.user!!.isTrader)
                router.replaceScreen(Screens.TraderMeMainScreen())
            else
                router.replaceScreen(Screens.SubscriberMainScreen())

        } else
            router.navigateTo(Screens.SignInScreen())
    }

    fun aboutWTMenuClicked() {
        if (profile.user != null)
            router.replaceScreen(Screens.AboutWinTradeScreen())
        else
            router.navigateTo(Screens.SignInScreen())
    }

    fun questionMenuClicked() {
        if (profile.user != null)
            router.replaceScreen(Screens.QuestionScreen())
        else
            router.navigateTo(Screens.SignInScreen())
    }

    fun settingsMenuClicked() {
        router.navigateTo(Screens.SettingsScreen())
    }

    fun friendInviteMenuClicked() {
        if (profile.user != null)
            router.replaceScreen(Screens.FriendInviteScreen())
        else
            router.navigateTo(Screens.SignInScreen())
    }

    fun exitClicked() {
        profile.deviceToken = null
        profile.token = null
        profile.user = null
        profileRepo.save(profile).observeOn(AndroidSchedulers.mainThread()).subscribe(
            {
                router.newRootScreen(Screens.SignInScreen())
            }, {}
        )
    }



    fun onDrawerOpened() {
        viewState.setupHeader(profile.user?.avatar, profile.user?.username)
    }

    fun backClicked() {
        router.exit()
    }
}