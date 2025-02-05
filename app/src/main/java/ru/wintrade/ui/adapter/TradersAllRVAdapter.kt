package ru.wintrade.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_traders_all.view.*
import ru.wintrade.R
import ru.wintrade.mvp.presenter.adapter.ITradersAllListPresenter
import ru.wintrade.mvp.view.item.TradersAllItemView
import ru.wintrade.util.loadImage

class TradersAllRVAdapter(val presenter: ITradersAllListPresenter) :
    RecyclerView.Adapter<TradersAllRVAdapter.TradersAllViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TradersAllViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_traders_all, parent, false)
    )

    override fun onBindViewHolder(holder: TradersAllViewHolder, position: Int) {
        holder.pos = position
        presenter.bind(holder)
        initListeners(holder)
        setProfitColor(holder)
    }

    private fun setProfitColor(holder: TradersAllViewHolder) {
        if (holder.itemView.tv_traders_all_item_year_profit.text.substring(0, 1) == "-")
            holder.itemView.tv_traders_all_item_year_profit.setTextColor(Color.RED)
        else
            holder.itemView.tv_traders_all_item_year_profit.setTextColor(Color.GREEN)
    }

    fun initListeners(holder: TradersAllViewHolder) {
        holder.itemView.layout_all_traders_item.setOnClickListener {
            presenter.openTraderStat(holder.adapterPosition)
        }
        holder.itemView.cb_traders_all_item_observe.setOnClickListener {
            if (holder.itemView.cb_traders_all_item_observe.isChecked)
                presenter.observeBtnClicked(holder.adapterPosition, true)
            else
                presenter.observeBtnClicked(holder.adapterPosition, false)
        }
    }

    override fun getItemCount(): Int = presenter.getCount()

    inner class TradersAllViewHolder(view: View) : RecyclerView.ViewHolder(view),
        TradersAllItemView {
        override var pos = -1
        override fun setTraderName(name: String) {
            itemView.tv_traders_all_item_name.text = name
        }

        override fun setTraderProfit(profit: String) {
            itemView.tv_traders_all_item_year_profit.text = profit
        }

        override fun setTraderAvatar(avatar: String) {
            loadImage(avatar, itemView.iv_traders_all_item_ava)
        }

        override fun setTraderObserveBtn(isObserve: Boolean?) {
            isObserve?.let {
                itemView.tv_traders_all_item_subscription.visibility = View.GONE
                itemView.cb_traders_all_item_observe.visibility = View.VISIBLE
                itemView.cb_traders_all_item_observe.isChecked = isObserve
            } ?: setVisibleSubscription()
        }

        private fun setVisibleSubscription() {
            itemView.tv_traders_all_item_subscription.visibility = View.VISIBLE
            itemView.cb_traders_all_item_observe.visibility = View.GONE
        }
    }
}