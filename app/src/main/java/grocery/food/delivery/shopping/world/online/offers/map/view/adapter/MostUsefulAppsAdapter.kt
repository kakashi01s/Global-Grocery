package grocery.food.delivery.shopping.world.online.offers.map.view.adapter

import android.content.Context
import android.view.ViewGroup
import grocery.food.delivery.shopping.world.online.offers.map.view.listener.category.MostUsefulAppsItemClickListener
import grocery.food.delivery.shopping.world.online.offers.map.view.viewholder.MostUsefulAppsViewHolder
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.R
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.base.adapter.GenericRecyclerAdapter

class MostUsefulAppsAdapter(context: Context?) :
    GenericRecyclerAdapter<List<String>, MostUsefulAppsItemClickListener<List<String>>, MostUsefulAppsViewHolder>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MostUsefulAppsViewHolder{
        return MostUsefulAppsViewHolder(inflate(R.layout.card_all_apps_portal_layout,parent))
    }
}