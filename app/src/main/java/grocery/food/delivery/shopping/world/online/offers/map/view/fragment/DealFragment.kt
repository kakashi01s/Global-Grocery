package grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.facebook.ads.*
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageClickListener
import com.synnapps.carouselview.ImageListener
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.R
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.utils.Constants
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.MainActivity
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.WebActivity
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.adapter.DealsAdapter
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.adapter.home.AllAppsAdapter
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.adapter.home.TrendingAdapter
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.listener.AllAppsItemClickListener
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.listener.home.TrendingItemClickListener
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.viewmodel.CategoryViewModel
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.viewmodel.DealsViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DealFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DealFragment : Fragment() , AllAppsItemClickListener<List<String>>,
    TrendingItemClickListener<List<String>> {
    // TODO: Rename and change types of parameters
    private var param1: Int? = null
    private var param2: String? = null

    var rvInvest: RecyclerView? = null
    var dealsAdapter: DealsAdapter? = null
    var investDataList: ArrayList<List<String>>? = ArrayList()
    var firebaseAnalytics: FirebaseAnalytics? = null

    var firebaseRemoteConfig: FirebaseRemoteConfig? = null
    var btShowDeals: Button? = null
    private var interstitialFbAd: InterstitialAd? = null
    private var adView: LinearLayout? = null

    private var nativeAdLayout: NativeAdLayout? = null
    private var nativeAdFB1: NativeAd? = null

    var carouselView: CarouselView? = null
    var rvAllApps: RecyclerView? = null
    var allAppsAdapter: AllAppsAdapter? = null
    var dealsViewModel : DealsViewModel? = null

    var rvTrending: RecyclerView? = null
    var trendingAdapter: TrendingAdapter? = null

    var carouselImagesListshop: ArrayList<List<String>>? = ArrayList()
    var bool: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    var webViewDeals: WebView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_invest, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        if(firebaseRemoteConfig == null){
            firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        }
        setRecyclerView()

        dealsViewModel = ViewModelProvider(requireActivity()).get(DealsViewModel::class.java)

        dealsViewModel?.loadData()

        dealsViewModel!!.shopcarouselImagesLiveData.observe(viewLifecycleOwner , Observer {  t ->
            Log.d("food" , "food fragment carousel")
            carouselImagesListshop?.addAll(t.getValues()!!)
            onLoadCarouselImages()
        })

        dealsViewModel!!.shopallAppsLiveData.observe(viewLifecycleOwner , Observer { t ->
            Log.d("food" , "food fragment all apps")
            allAppsAdapter?.setItems(t.getValues())
        })

        dealsViewModel!!.shoptrendingLiveData.observe(viewLifecycleOwner , Observer { t ->
            Log.d("food" , "food fragment trending apps")
            trendingAdapter?.setItems(t.getValues())
        })
        if (firebaseRemoteConfig!!.getBoolean(Constants().SHOW_ADS)) {
            onLoadFBNativeAd1(view, requireContext())
        }


    }
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if(isVisibleToUser){
            if(firebaseRemoteConfig == null){
                firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
            }
            if (firebaseRemoteConfig!!.getBoolean(Constants().SHOW_ADS)) {
                if(nativeAdFB1 == null){
                    onLoadFBNativeAd1(requireView(), requireContext())
                }
            }
        }
    }

    fun initViews(view: View){
        firebaseAnalytics = FirebaseAnalytics.getInstance(requireActivity())
        carouselView = view.findViewById(R.id.cvShop)
        rvAllApps = view.findViewById(R.id.rvAllApps_shop)
        rvTrending = view.findViewById(R.id.rvTrending_shop)
    }

    fun setRecyclerView(){
        allAppsAdapter = AllAppsAdapter(context)
        allAppsAdapter!!.setListener(this)
        rvAllApps.apply {
            rvAllApps?.layoutManager = GridLayoutManager(activity, 4)
            rvAllApps?.adapter = allAppsAdapter
        }

        trendingAdapter = TrendingAdapter(context)
        trendingAdapter!!.setListener(this)
        rvTrending.apply {
            rvTrending?.layoutManager = LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            rvTrending?.adapter = trendingAdapter
        }

    }
    fun onLoadCarouselImages() {
        Log.d("TAG", "onLoadCarouselImages: " + carouselImagesListshop!!.size)

        carouselView!!.setImageListener(imageListener)

        carouselView!!.setImageClickListener(imageClickListener)

        carouselView!!.pageCount = carouselImagesListshop!!.size

    }

    var imageClickListener: ImageClickListener = ImageClickListener { position ->
        val intent: Intent = Intent(activity, WebActivity::class.java)
        intent.putExtra("title", carouselImagesListshop!![position][1])
        intent.putExtra("url", carouselImagesListshop!![position][2])
        intent.putExtra("app_icon", carouselImagesListshop!![position][4])
        intent.putExtra("color", "#666666")

        val bundle = Bundle()
        bundle.putString("title", carouselImagesListshop!![position][1])
        bundle.putString("url", carouselImagesListshop!![position][2])

        (activity as MainActivity?)!!.onUpdateLogEvent(bundle, "carousel_images_visited", true)


        startActivity(intent)
    }


    var imageListener: ImageListener = ImageListener { position, imageView ->
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        Log.d(
            "TAG",
            "onLoadCarouselImages: position " + position + " data " + carouselImagesListshop!![position][3]
        )
        Glide.with(imageView.context)
            .load(carouselImagesListshop!![position][3])
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }






    fun onLoadFBNativeAd1(view: View, context: Context) {
        nativeAdFB1 = NativeAd(context, Constants().getFbNativeHome1())
        val nativeAdListener: NativeAdListener = object : NativeAdListener {
            override fun onError(p0: Ad?, p1: AdError?) {
                Log.d("TAG", "onError: onLoadFBNativeAd1 " + p1!!.errorMessage)
            }

            override fun onAdLoaded(ad: Ad?) {

                // Race condition, load() called again before last ad was displayed
                if (nativeAdFB1 == null || nativeAdFB1 !== ad) {
                    return
                }
                // Inflate Native Ad into Container

                // Add the Ad view into the ad container.
                nativeAdLayout = view.findViewById(R.id.native_ad_container_home_1)
                val inflater = LayoutInflater.from(context)
                // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
                adView =
                    inflater.inflate(
                        R.layout.native_ad_layout,
                        nativeAdLayout,
                        false
                    ) as LinearLayout
                nativeAdLayout!!.addView(adView)

                inflateAd(nativeAdFB1!!, adView!!)

                val adChoicesContainer: LinearLayout = view.findViewById(R.id.ad_choices_container)
                val adOptionsView = AdOptionsView(context, nativeAdFB1, nativeAdLayout)
                adChoicesContainer.removeAllViews()
                adChoicesContainer.addView(adOptionsView, 0)
            }

            override fun onAdClicked(p0: Ad?) {
                Log.d("TAG", "onAdClicked: onLoadFBNativeAd1")
            }

            override fun onLoggingImpression(p0: Ad?) {
                Log.d("TAG", "onLoggingImpression: onLoadFBNativeAd1")
            }

            override fun onMediaDownloaded(p0: Ad?) {
                Log.d("TAG", "onMediaDownloaded: onLoadFBNativeAd1")
            }
        }

        nativeAdFB1!!.loadAd(
            nativeAdFB1!!.buildLoadAdConfig()
                .withAdListener(nativeAdListener)
                .build()
        );
    }

    private fun inflateAd(nativeAd: NativeAd, adView: LinearLayout) {
        nativeAd.unregisterView()

        // Add the AdOptionsView

        // Create native UI using the ad metadata.
        val nativeAdIcon: com.facebook.ads.MediaView = adView.findViewById(R.id.native_ad_icon)
        val nativeAdTitle: TextView = adView.findViewById(R.id.native_ad_title)
        val nativeAdMedia: com.facebook.ads.MediaView = adView.findViewById(R.id.native_ad_media)
        val nativeAdSocialContext: TextView = adView.findViewById(R.id.native_ad_social_context)
        val nativeAdBody: TextView = adView.findViewById(R.id.native_ad_body)
        val sponsoredLabel: TextView = adView.findViewById(R.id.native_ad_sponsored_label)
        val nativeAdCallToAction: Button = adView.findViewById(R.id.native_ad_call_to_action)

        // Set the Text.
        nativeAdTitle.text = nativeAd.advertiserName
        nativeAdBody.text = nativeAd.adBodyText
        nativeAdSocialContext.text = nativeAd.adSocialContext
        nativeAdCallToAction.visibility =
            if (nativeAd.hasCallToAction()) View.VISIBLE else View.INVISIBLE
        nativeAdCallToAction.text = nativeAd.adCallToAction
        sponsoredLabel.text = nativeAd.sponsoredTranslation

        // Create a list of clickable views
        val clickableViews: ArrayList<View> = ArrayList()
        clickableViews.add(nativeAdTitle)
        clickableViews.add(nativeAdCallToAction)

        // Register the Title and CTA button to listen for clicks.
        nativeAd.registerViewForInteraction(
            adView, nativeAdMedia, nativeAdIcon, clickableViews
        )
    }


    override fun onDestroy() {
        dealsViewModel?.reset()
        super.onDestroy()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InvestFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int, param2: String) =
            DealFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun webViewSettings(webView: WebView){
        webView.settings.loadsImagesAutomatically = true
        webView.settings.javaScriptEnabled = true
        webView.settings.allowContentAccess = true
//        webView.evaluateJavascript("\$(function() {\n" +
//                "    var style = document.createElement('style');\n" +
//                "    style.innerHTML = `\n" +
//                "    .ShopNewBtn > img {\n" +
//                "    display: none;\n" +
//                "    }\n" +
//                "    .original_price_p{\n" +
//                "      color:red;\n" +
//                "    }\n" +
//                "    .ShopNewBtn{\n" +
//                "      background-color: red;\n" +
//                "    }\n" +
//                "    .CuponMain{\n" +
//                "      border: 1px solid red;\n" +
//                "    }\n" +
//                "    .button-colors-green{\n" +
//                "      background-color:red;\n" +
//                "      border: 1px solid red;\n" +
//                "    }\n" +
//                "    `;\n" +
//                "    document.head.appendChild(style);\n" +
//                "});",null)
        webView.settings.useWideViewPort = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.domStorageEnabled = true
        webView.clearView()
        webView.isHorizontalScrollBarEnabled = false
        webView.settings.setAppCacheEnabled(true)
        webView.isVerticalScrollBarEnabled = false
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.settings.allowFileAccess = true
        webView.settings.pluginState = WebSettings.PluginState.OFF
        webView.isScrollbarFadingEnabled = false
        webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        webView.settings.defaultZoom = WebSettings.ZoomDensity.FAR
        webView.webViewClient = WebViewClient()
        webView.settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
        webView.setInitialScale(1)

        webView.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                webView.loadUrl("javascript:\$(function() {\n" +
                        "    var style = document.createElement('style');\n" +
                        "    style.innerHTML = `\n" +
                        "    .ShopNewBtn > img {\n" +
                        "    display: none;\n" +
                        "    }\n" +
                        "    .original_price_p{\n" +
                        "      color:#53b9fb;\n" +
                        "    }\n" +
                        "    .ShopNewBtn{\n" +
                        "      background-color: #53b9fb;\n" +
                        "    }\n" +
                        "    .CuponMain{\n" +
                        "      border: 1px solid #53b9fb;\n" +
                        "    }\n" +
                        "    .button-colors-green{\n" +
                        "      background-color:#53b9fb;\n" +
                        "      border: 1px solid #53b9fb;\n" +
                        "    }\n" +
                        "    `;\n" +
                        "    document.head.appendChild(style);\n" +
                        "});")
            }

            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Log.d(TAG, "shouldOverrideUrlLoading: deals "+request.url.toString())
                    val intent = Intent(activity, WebActivity::class.java)
                    intent.putExtra("title", webView.title)
                    intent.putExtra("url", request.url.toString())
                    intent.putExtra("app_icon", "https://firebasestorage.googleapis.com/v0/b/shopping-crate.appspot.com/o/hot-sale.png?alt=media&token=796d1a38-deba-41ec-9516-2fdb9b451b31")
                    intent.putExtra("color", "#666666")


                    val bundle = Bundle()
                    bundle.putString("title", webView.title)
                    bundle.putString("url", request.url.toString())

                    (activity as MainActivity?)!!.onUpdateLogEvent(bundle, "deals_visited", true)


                    startActivity(intent)
                }
                return true
            }
        }
    }

    override fun onAllCardClick(item: List<String>) {
        Log.d("TAG", "onAllCardClick: " + item.get(1))
        val intent: Intent? = Intent(activity, WebActivity::class.java)
        intent?.putExtra("title", item.get(1))
        intent?.putExtra("url", item.get(2))
        intent?.putExtra("app_icon", item.get(3))

        val bundle = Bundle()
        bundle.putString("title", item.get(1))
        bundle.putString("url", item.get(2))

        (activity as MainActivity?)!!.onUpdateLogEvent(bundle, "all_apps_visited", true)


        startActivity(intent)
    }

    override fun onTrendingClickListener(item: List<String>) {
        val intent: Intent? = Intent(activity, WebActivity::class.java)
        intent?.putExtra("title", item.get(1))
        intent?.putExtra("url", item.get(2))
        intent?.putExtra("app_icon", item.get(4))
        intent?.putExtra("color", "#ffffff")


        val bundle = Bundle()
        bundle.putString("title", item.get(1))
        bundle.putString("url", item.get(2))

        (activity as MainActivity?)!!.onUpdateLogEvent(bundle, "trending_visited", true)


        startActivity(intent)
    }


}