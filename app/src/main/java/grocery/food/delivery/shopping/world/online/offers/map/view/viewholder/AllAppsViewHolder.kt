package grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.viewholder

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.R
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.base.viewholder.BaseViewHolder
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.listener.AllAppsItemClickListener

class AllAppsViewHolder(itemView: View?) :
    BaseViewHolder<List<String>, AllAppsItemClickListener<List<String>>>(itemView) {

    var ivAllAppsPortal: ImageView? = null
    var tvPortalName: TextView? = null
    var cvPortal: CardView? = null

    init {
        ivAllAppsPortal = itemView?.findViewById(R.id.ivAllAppsPortal)
        tvPortalName = itemView?.findViewById(R.id.tvPortalName)
        cvPortal = itemView?.findViewById(R.id.cvAllAppsPortal)
    }

    override fun onBind(item: List<String>, listener: AllAppsItemClickListener<List<String>>?) {

        Glide.with(ivAllAppsPortal!!.context)
            .load(item[3])
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(ivAllAppsPortal!!)

        tvPortalName?.setText(item.get(1))

        cvPortal?.setOnClickListener {
            Log.d("TAG", "onAllCardClick: "+item.get(1))
            listener?.onAllCardClick(item)
        }
    }

//    fun showcase(acc: Activity, it : View, title : String)
//    {
//        FancyShowCaseView.Builder(acc).focusOn(it).title(title).delay(50).showOnce(title)
//            .build()
//            .show()
//    }
}