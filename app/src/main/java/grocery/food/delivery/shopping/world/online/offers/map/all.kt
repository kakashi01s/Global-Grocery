package grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine

import android.app.Application
import android.util.Log
import com.facebook.ads.AdSettings
import com.facebook.ads.AudienceNetworkAds
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.remoteconfig.BuildConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.onesignal.OneSignal
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.data.DataFactory
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.data.DataService
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.utils.Constants
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.utils.ForceUpdateChecker
import io.reactivex.Scheduler
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.utils.AppNotificationOpenHandler
import java.util.*

class all : Application() {
    private var dataService: DataService? = null
    private var scheduler: Scheduler? = null
    private val ONESIGNAL_APP_ID ="df46e0a9-5880-4d88-ade5-f973c52a8a0e"
    companion object{

        var application: all? = null

        fun get():all? {
            return application
        }
    }


    override fun onCreate() {
        super.onCreate()
        application = this

        RxJavaPlugins.setErrorHandler { throwable: Throwable ->
            Log.e(
                "TAG",
                "onCreate: " + throwable.message
            )
        }
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
        OneSignal.setNotificationOpenedHandler(AppNotificationOpenHandler(this))


        AudienceNetworkAds.initialize(this);
        if(BuildConfig.DEBUG){
            AdSettings.setIntegrationErrorMode(AdSettings.IntegrationErrorMode.INTEGRATION_ERROR_CRASH_DEBUG_MODE);
        }

        if (BuildConfig.DEBUG){
            AdSettings.setTestMode(true)
        }else{
            AdSettings.setTestMode(false)
        }
        val firebaseRemoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

        // set in-app defaults

        // set in-app defaults

        // set in-app defaults
        val remoteConfigDefaults = TreeMap<String?, Any?>()
        remoteConfigDefaults[Constants().SHOW_ADS] = false
        remoteConfigDefaults[Constants().DATA_CHANGED_DATE] = "1622868730040"
        remoteConfigDefaults[Constants().DEALS_URL] = "https://inrdeals.com/embed/deals?user=ayu619462617"
        remoteConfigDefaults[Constants().OPEN_BROWSER] = false
        remoteConfigDefaults[ForceUpdateChecker().KEY_UPDATE_REQUIRED] = false
        remoteConfigDefaults[ForceUpdateChecker().KEY_CURRENT_VERSION] = "1.0"
        remoteConfigDefaults[ForceUpdateChecker().KEY_UPDATE_URL] =
            "https://play.google.com/store/apps/details?id="+applicationContext.packageName

        firebaseRemoteConfig.setDefaultsAsync(remoteConfigDefaults)
        firebaseRemoteConfig.fetch(300) // fetch every minutes
            .addOnCompleteListener(OnCompleteListener<Void?> { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "remote config is fetched.$task")
                    firebaseRemoteConfig.fetchAndActivate()
                    Log.d(
                        "TAG",
                        "onComplete: " + firebaseRemoteConfig.getString("banner_facebook_ads")
                    )
                }
            })


    }


    fun getDataService(): DataService? {
        val dataService by lazy {
            DataFactory.create()
        }
        return dataService
    }

    fun subscribeScheduler(): Scheduler? {
        if (scheduler == null) {
            scheduler = Schedulers.io()
        }
        return scheduler
    }


}