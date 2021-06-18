package grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.listener

import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.base.listener.BaseRecyclerListener

interface AllAppsItemClickListener<T> : BaseRecyclerListener {
    fun onAllCardClick(item: T)
}