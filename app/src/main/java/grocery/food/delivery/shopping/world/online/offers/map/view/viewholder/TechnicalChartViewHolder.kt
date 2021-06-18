package grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.listener.category.ChartItemClickListener
import kotlinx.android.synthetic.main.card_all_apps_portal_layout.view.*

class TechnicalChartViewHolder(v: View) : RecyclerView.ViewHolder(v), ChartItemClickListener {
    val ivAllAppsPortal = v.ivAllAppsPortal
    val tvPortalName = v.tvPortalName
    val rlCardAllAppsPortal = v.rlCardAllAppsPortal
    var chartItemClickListener: ChartItemClickListener? = null

    fun setItemClickListener(chartItemClickListener: ChartItemClickListener) {
        this.chartItemClickListener = chartItemClickListener
    }

    override fun onChartClickListener(view: View, position: Int) {
        chartItemClickListener?.onChartClickListener(view,position)
    }

}