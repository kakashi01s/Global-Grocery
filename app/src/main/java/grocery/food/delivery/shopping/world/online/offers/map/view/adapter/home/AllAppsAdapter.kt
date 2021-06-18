package grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.adapter.home

import android.content.Context
import android.view.ViewGroup
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.R
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.base.adapter.GenericRecyclerAdapter
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.listener.AllAppsItemClickListener
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.viewholder.AllAppsViewHolder

class AllAppsAdapter(context: Context?) :
GenericRecyclerAdapter<List<String>, AllAppsItemClickListener<List<String>>, AllAppsViewHolder>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllAppsViewHolder {
        return AllAppsViewHolder(inflate(R.layout.card_all_apps_portal_layout,parent))
    }
}