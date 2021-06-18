package grocery.food.delivery.shopping.world.online.offers.map.view.viewholder

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import grocery.food.delivery.shopping.world.online.offers.map.view.listener.category.MostUsefulAppsItemClickListener
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.R
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.base.viewholder.BaseViewHolder

class MostUsefulAppsViewHolder(itemView: View) : BaseViewHolder<List<String>, MostUsefulAppsItemClickListener<List<String>>>(itemView) {

        var ivPortalImage: ImageView? = null
        var tvPortalName: TextView? = null
        var cvPortal: CardView? = null

        init {
            ivPortalImage = itemView.findViewById(R.id.ivAllAppsPortal)
            tvPortalName = itemView.findViewById(R.id.tvPortalName)
            cvPortal = itemView.findViewById(R.id.cvAllAppsPortal)
        }


        override fun onBind(item: List<String>, listener: MostUsefulAppsItemClickListener<List<String>>?) {

            Glide.with(ivPortalImage!!.context)
                .load(item.get(3))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivPortalImage!!)

            tvPortalName?.setText(item.get(1))

            cvPortal?.setOnClickListener {
                Log.d("TAG", "onAllCardClick: "+item.get(1))
                listener?.onMostUsefulAppsCardClick(item)
            }

        }
    }
