package grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.utils

import android.content.Context
import android.net.ConnectivityManager

class NetworkUtils {
    private fun NetworkUtils() {
        // This class is not publicly instantiable
    }

    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm != null) {
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }
        return false
    }
}