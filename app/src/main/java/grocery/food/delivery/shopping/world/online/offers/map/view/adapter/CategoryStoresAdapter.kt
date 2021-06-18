package grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.adapter

import android.content.Context
import android.view.ViewGroup
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.R
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.base.adapter.GenericRecyclerAdapter
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.listener.CategoryStoresItemClickListener
import grocery.food.delivery.shopping.world.online.online.guide.map.map.view.viewholder.CategoryStoresViewHolder

class CategoryStoresAdapter(context: Context?) :
    GenericRecyclerAdapter<List<String>, CategoryStoresItemClickListener<List<String>>, CategoryStoresViewHolder>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryStoresViewHolder {
        return CategoryStoresViewHolder(inflate(R.layout.card_all_apps_portal_layout,parent))
    }
}