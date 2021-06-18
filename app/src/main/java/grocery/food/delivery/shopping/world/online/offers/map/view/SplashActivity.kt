package grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.R
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.base.BaseActivity

class SplashActivity : BaseActivity() {
    override val bindingVariable: Int
        get() = 0
    override val layoutId: Int
        get() = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.white)
        }


        Handler().postDelayed({
            openActivity(null, MainActivity::class.java)
            finish()
        }, 2000)


    }
}