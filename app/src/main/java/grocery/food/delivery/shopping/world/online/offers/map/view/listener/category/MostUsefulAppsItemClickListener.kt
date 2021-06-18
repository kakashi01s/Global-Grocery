package grocery.food.delivery.shopping.world.online.offers.map.view.listener.category


import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.base.listener.BaseRecyclerListener

interface MostUsefulAppsItemClickListener <T> : BaseRecyclerListener {
    fun onMostUsefulAppsCardClick(item: T)
}