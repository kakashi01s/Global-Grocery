package grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.utils

import android.content.Context
import android.graphics.PorterDuff
import android.widget.TextView
import android.widget.Toast
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.R

class Helper {

    fun makeToast(message: String, context: Context) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        val view = toast.view

//Gets the actual oval background of the Toast then sets the colour filter

//Gets the actual oval background of the Toast then sets the colour filter
        view?.background?.setColorFilter(
            context.resources.getColor(R.color.colorPrimary),
            PorterDuff.Mode.SRC_IN
        )

//Gets the TextView from the Toast so it can be editted

//Gets the TextView from the Toast so it can be editted
        var text: TextView? = null
        if (view != null) {
            text = view.findViewById(android.R.id.message)
        }
        text?.setTextColor(context.resources.getColor(R.color.white))
//        val tf1 = Typeface.createFromAsset(context.assets, "montserrat_semibold.ttf")
//        text!!.typeface = tf1

        toast.show()
    }
}