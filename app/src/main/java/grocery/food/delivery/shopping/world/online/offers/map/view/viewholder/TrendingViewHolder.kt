package grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.R
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.base.viewholder.BaseViewHolder
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.listener.home.TrendingItemClickListener

class TrendingViewHolder(itemView: View?) :
    BaseViewHolder<List<String>, TrendingItemClickListener<List<String>>>(itemView) {

    var ivTrendingIcon: ImageView? = null
    var tvTrendingName: TextView? = null
    var tvTrendingDesc: TextView? = null
    var tvExplore: TextView? = null

    init {
        ivTrendingIcon = itemView?.findViewById(R.id.ivTrendingIcon)
        tvTrendingName = itemView?.findViewById(R.id.tvTrendingName)
        tvTrendingDesc = itemView?.findViewById(R.id.tvTrendingDesc)
        tvExplore = itemView?.findViewById(R.id.tvExplore)
    }

    override fun onBind(item: List<String>, listener: TrendingItemClickListener<List<String>>?) {
        Glide.with(ivTrendingIcon!!.context)
            .load(item[4])
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(ivTrendingIcon!!)

        tvTrendingName!!.text = item[1]

        tvTrendingDesc!!.text = item[3]

        tvExplore!!.setOnClickListener {
            listener!!.onTrendingClickListener(item)
        }

    }

}