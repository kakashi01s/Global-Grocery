package grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.listener

import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.base.listener.BaseRecyclerListener

interface CategoryStoresItemClickListener<T> : BaseRecyclerListener {
    fun CategoryStoresCardClick(item: T)
}