package grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.adapter.home

import android.content.Context
import android.view.ViewGroup
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.R
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.base.adapter.GenericRecyclerAdapter
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.listener.home.TrendingItemClickListener
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.viewholder.TrendingViewHolder

class TrendingAdapter (context: Context?) :
    GenericRecyclerAdapter<List<String>, TrendingItemClickListener<List<String>>, TrendingViewHolder>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        return TrendingViewHolder(inflate(R.layout.card_trending_layout,parent))
    }
}