package grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.listener.home

import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.base.listener.BaseRecyclerListener

interface TrendingItemClickListener <T> : BaseRecyclerListener {
    fun onTrendingClickListener(item: T)
}