package grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.utils

import android.content.Context
import android.content.Intent
import android.util.Log
import com.onesignal.OSNotificationAction
import com.onesignal.OSNotificationOpenedResult
import com.onesignal.OneSignal
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.MainActivity
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.SplashActivity

class AppNotificationOpenHandler(val context: Context): OneSignal.OSNotificationOpenedHandler {
    override fun notificationOpened(result: OSNotificationOpenedResult?) {
        Log.d(
            "OSNotification",
            "result.notification.toJSONObject(): " + result!!.notification.toJSONObject()
        )

        val data = result!!.notification.additionalData
        if (data != null) {
            val customKey = data.optString("website", null)
            if (customKey != null) {
                Log.d("OneSignalExample", "customkey set with value: $customKey")
                val intent = Intent(
                    context,
                    MainActivity::class.java
                )
                intent.flags =
                    Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_NEW_TASK
                intent.putExtra("route","website")
                intent.putExtra("url",customKey)
                context.startActivity(intent)
            }
            else
            {
                val intent = Intent(
                    context,
                    SplashActivity::class.java
                )
                intent.flags =
                    Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_NEW_TASK
                // intent.putExtra("route","website")
                //intent.putExtra("url",customKey)
                context.startActivity(intent)
            }
        }
        else{
            val intent = Intent(
                context,
                SplashActivity::class.java
            )
            intent.flags =
                Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_NEW_TASK
            // intent.putExtra("route","website")
            //intent.putExtra("url",customKey)
            context.startActivity(intent)

        }

        val actionType = result!!.action.type
        if (actionType == OSNotificationAction.ActionType.ActionTaken) {
            Log.d(
                "OneSignalExample",
                "Button pressed with id: " + result!!.action.actionId
            )
        }


    }
}