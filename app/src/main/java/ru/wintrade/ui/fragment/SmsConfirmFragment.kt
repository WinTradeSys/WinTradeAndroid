package ru.wintrade.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding4.widget.textChanges
import kotlinx.android.synthetic.main.fragment_sms_confirm.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.wintrade.R
import ru.wintrade.mvp.presenter.SmsConfirmPresenter
import ru.wintrade.mvp.view.SmsConfirmView
import ru.wintrade.ui.App
import ru.wintrade.util.showLongToast
import java.util.concurrent.TimeUnit

class SmsConfirmFragment : MvpAppCompatFragment(), SmsConfirmView {

    companion object {
        const val PHONE_KEY = "phone"
        fun newInstance(phone: String) = SmsConfirmFragment().apply {
            arguments = Bundle().apply {
                putString(PHONE_KEY, phone)
            }
        }
    }

    @InjectPresenter
    lateinit var presenter: SmsConfirmPresenter

    @ProvidePresenter
    fun providePresenter() = SmsConfirmPresenter(requireArguments()[PHONE_KEY] as String).apply {
        App.instance.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_sms_confirm, container, false)

    override fun init() {
        btn_sms_confirm_confirm.setOnClickListener {
            presenter.confirmClicked()
        }

        btn_sms_confirm_resend.setOnClickListener {
            presenter.resendClicked()
        }

        et_sms_confirm_sms.textChanges()
            .debounce(1000, TimeUnit.MILLISECONDS)
            .subscribe(
                {
                    presenter.codeChanged(it.toString())
                },
                {}
            )
    }

    override fun showToast(msg: String) {
        context?.showLongToast(msg)
    }
}