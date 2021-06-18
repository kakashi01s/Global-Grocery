package grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.adapter

import android.content.Context
import android.view.ViewGroup
import grocery.food.delivery.shopping.world.online.offers.map.view.listener.category.CountryItemClickListener
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.R
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.base.adapter.GenericRecyclerAdapter
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.viewholder.GlobalViewHolder

class CountryStoresAdapter(context: Context?) : GenericRecyclerAdapter<List<String>, CountryItemClickListener<List<String>>,GlobalViewHolder>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GlobalViewHolder {
        return GlobalViewHolder(inflate(R.layout.card_all_apps_portal_layout,parent))
    }
}