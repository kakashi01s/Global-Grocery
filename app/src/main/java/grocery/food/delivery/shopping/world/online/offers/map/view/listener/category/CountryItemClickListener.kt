package grocery.food.delivery.shopping.world.online.offers.map.view.listener.category

import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.base.listener.BaseRecyclerListener

interface CountryItemClickListener<T> : BaseRecyclerListener {
    fun CategoryStoresCardClick(item: T)
}