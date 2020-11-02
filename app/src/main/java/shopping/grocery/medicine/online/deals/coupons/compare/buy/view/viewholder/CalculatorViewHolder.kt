package shopping.grocery.medicine.online.deals.coupons.compare.buy.view.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import shopping.grocery.medicine.online.deals.coupons.compare.buy.view.listener.category.CalculatorItemClickListener
import kotlinx.android.synthetic.main.card_calculator_layout.view.*

class CalculatorViewHolder(v: View) : RecyclerView.ViewHolder(v), CalculatorItemClickListener {
    val tvCalName = v.tvCalName
    val cvCalPortal = v.cvCalPortal
    var calculatorItemClickListener: CalculatorItemClickListener? = null

    fun setItemClickListener(calculatorItemClickListener: CalculatorItemClickListener) {
        this.calculatorItemClickListener = calculatorItemClickListener
    }

    override fun onCalculatorCardClick(view: View, position: Int) {
        calculatorItemClickListener?.onCalculatorCardClick(view,position)
    }


}