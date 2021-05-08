package ru.wintrade.ui.fragment

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.layout_title.*
import kotlinx.android.synthetic.main.toolbar_blue.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.wintrade.R
import ru.wintrade.mvp.presenter.SignInPresenter
import ru.wintrade.mvp.view.SignInView
import ru.wintrade.ui.App
import ru.wintrade.util.PREFERENCE_NAME

class SignInFragment : MvpAppCompatFragment(), SignInView {
    companion object {
        fun newInstance() = SignInFragment()
    }

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    @InjectPresenter
    lateinit var presenter: SignInPresenter

    @ProvidePresenter
    fun providePresenter() = SignInPresenter().apply {
        App.instance.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_sign_in, container, false)

    override fun init() {
        setDrawerLockMode()
        iv_close.setOnClickListener { requireActivity().finish() }
        entrance_registration_button.setOnClickListener { presenter.openRegistrationScreen() }
        entrance_enter_button.setOnClickListener { enterBtnClicked() }
    }

    private fun enterBtnClicked() {
        presenter.loginBtnClicked(
            et_sign_in_nickname.text.toString(),
            et_sign_in_password.text.toString()
        )
        (requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
            requireActivity().currentFocus?.windowToken,
            0
        )
    }

    private fun setDrawerLockMode() {
        requireActivity().drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        requireActivity().toolbar_blue.visibility = View.GONE
    }

    override fun setAccess(isAuthorized: Boolean) {
        val pref = requireActivity().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        with(pref.edit()) {
            putBoolean(ru.wintrade.util.IS_AUTHORIZED, isAuthorized)
            apply()
        }
    }

    override fun showToast(toast: String) {
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
    }
}