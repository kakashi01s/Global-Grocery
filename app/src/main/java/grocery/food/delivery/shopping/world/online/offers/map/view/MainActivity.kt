package grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.remoteconfig.BuildConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment
import com.yalantis.contextmenu.lib.MenuObject
import com.yalantis.contextmenu.lib.MenuParams
import github.com.st235.lib_expandablebottombar.ExpandableBottomBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bookmark_layout.*
import kotlinx.android.synthetic.main.dialog_search.*
import me.toptas.fancyshowcase.FancyShowCaseQueue
import me.toptas.fancyshowcase.FancyShowCaseView
import me.toptas.fancyshowcase.FocusShape
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.R
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.base.BaseActivity
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.utils.Constants
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.utils.CustomViewPager
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.utils.ForceUpdateChecker
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.utils.Pref
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.adapter.home.AllAppsAdapter
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.listener.AllAppsItemClickListener
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.viewmodel.CategoryViewModel
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.viewmodel.DealsViewModel
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.viewmodel.HomeViewModel
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.viewpager.AppPagerAdapter
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : BaseActivity(), AllAppsItemClickListener<List<String>>,
    ForceUpdateChecker.OnUpdateNeededListener {

    var viewPager: CustomViewPager? = null
    var viewPagerTab: TabLayout? = null
    var fragmentPagerAdapter: FragmentPagerAdapter? = null
    var homeViewModel: HomeViewModel? = null
    var dealsViewModel: DealsViewModel? = null
    var categoryViewModel: CategoryViewModel? = null
    var firebaseAnalytics: FirebaseAnalytics? = null

    private lateinit var contextMenuDialogFragment: ContextMenuDialogFragment

    private lateinit var bottomNav: ExpandableBottomBar

    var search_rvCountryStores: RecyclerView? = null

    var appsList: ArrayList<List<String>>? = ArrayList()

    val filterList: ArrayList<List<String>> = ArrayList()

    var dialog: Dialog? = null

    var allAppsAdapter: AllAppsAdapter? = null

    lateinit var search: ImageView
    lateinit var llTextContainer: LinearLayout
    lateinit var tvQuery: TextView

    private var searchTxt: String? = null

    var firebaseRemoteConfig: FirebaseRemoteConfig? = null

    var fancyCategories: FancyShowCaseView? = null
    var fancyHome: FancyShowCaseView? = null
    var fancyBookmarks: FancyShowCaseView? = null
    var fancyDeals: FancyShowCaseView? = null
    var fancySearchView: FancyShowCaseView? = null
    var fancyCards : FancyShowCaseView? = null
    var fancyMenu: FancyShowCaseView? = null
    var fancyShowCaseQueue: FancyShowCaseQueue? = null

    override val bindingVariable: Int
        get() = 0
    override val layoutId: Int
        get() = 0
    var bool: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.colorPrimary)
        }



        initViews()
//        initToolbar()
        initMenuFragment()

        onShowIntro()

        Pref.initializeInstance(this)

        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

        Log.d("TAG", "onCreate: currentTime " + System.currentTimeMillis())

        if (!Pref.instance!!.dataChangedDate.equals(firebaseRemoteConfig!!.getString(Constants().DATA_CHANGED_DATE))) {
            Pref.instance!!.dataChanged = true
            Pref.instance!!.dataChangedDate =
                firebaseRemoteConfig!!.getString(Constants().DATA_CHANGED_DATE)
            Log.d(
                "TAG",
                "onCreate: dataChanged " + Pref.instance!!.dataChanged + " dataChangedDate " + Pref.instance!!.dataChangedDate
            )
        } else {
            Pref.instance!!.dataChanged = false
            Log.d(
                "TAG",
                "onCreate: dataChanged " + Pref.instance!!.dataChanged + " dataChangedDate " + Pref.instance!!.dataChangedDate
            )
        }

        dialog = Dialog(this)

        ForceUpdateChecker().with(this)!!.onUpdateNeeded(this).check()

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        dealsViewModel = ViewModelProvider(this).get(DealsViewModel::class.java)
        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)


        homeViewModel?.loadData()

        homeViewModel!!.allAppsLiveData.observe(this, Observer { t ->
            Log.d("TAG", "HomeFragment Live allAppsLiveData$t")
            appsList!!.clear()
            if (t != null) {
                t.getValues()?.let { appsList?.addAll(it) }
            }
        })

        search.setOnClickListener {
            onShowStores(appsList!!, it)
        }
//
        viewPagerTab!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Log.d("TAG", "onTabSelected: " + tab.position)
                val bundleAppUsage = Bundle()
                bundleAppUsage.putString("tab_click", tab.text.toString())
                onUpdateLogEvent(bundleAppUsage, "app_usage", false)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        setupViewPager()

        val menu = bottomNav.menu

        menu.select(R.id.id_home)
//        viewPager!!.currentItem = 0


        bottomNav.onItemSelectedListener = { view, menuItem, bool ->
            when (menuItem.id) {
                R.id.id_home -> {
                    viewPager!!.currentItem = 0
                }
                R.id.id_category -> {
                    viewPager!!.currentItem = 1
                }
                R.id.id_deals -> {
                    viewPager!!.currentItem = 2
                }
                R.id.id_bookmark -> {
                    viewPager!!.currentItem = 3
                }
            }
        }

        ivMenu.setOnClickListener {
            showContextMenuDialogFragment()
        }
    }

    private fun initViews() {
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        bottomNav = findViewById(R.id.expandable_bottom_bar)
        viewPager = findViewById(R.id.vpPager)
        viewPagerTab = findViewById(R.id.view_pager_tab)
        search = findViewById(R.id.search)
    }

    private fun onShowStores(list: ArrayList<List<String>>, view: View) {
        dialog!!.setContentView(R.layout.dialog_search)

        dialog!!.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

        llTextContainer = dialog!!.findViewById(R.id.llTextContainer)
        tvQuery = dialog!!.findViewById(R.id.tvQuery)
        search_rvCountryStores = dialog!!.findViewById(R.id.search_rvCategoryStores)

        setRecyclerView()

        allAppsAdapter!!.setItems(list)

        dialog!!.show()

    }

    private fun setRecyclerView() {
        allAppsAdapter = AllAppsAdapter(applicationContext)
        allAppsAdapter!!.setListener(this)
        search_rvCountryStores.apply {
            search_rvCountryStores?.layoutManager = GridLayoutManager(this@MainActivity, 3)
            search_rvCountryStores?.adapter = allAppsAdapter

            val appSearchView: SearchView = dialog!!.findViewById(R.id.app_search)
            filterList.clear()
            filterList.addAll(appsList!!)

            appSearchView.queryHint = "Search for products here"

            appSearchView.setOnClickListener(View.OnClickListener {
                appSearchView.setIconifiedByDefault(true)
                appSearchView.setIconified(false)
            })

            val searchCloseButtonId: Int = appSearchView.context.resources
                .getIdentifier("android:id/search_close_btn", null, null)
            val closeButton = appSearchView.findViewById<View>(R.id.search_close_btn) as ImageView
// Set on click listener
            // Set on click listener
            closeButton.setOnClickListener { // Manage this event.
                searchTxt = null
                if (llTextContainer.visibility == View.VISIBLE) {
                    llTextContainer.visibility = View.GONE
                }
                appSearchView.setQuery("", false)
                appSearchView.isIconified = false
                appSearchView.clearFocus()
            }

            appSearchView.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchTxt = query
                    Log.d("QuerySearch", searchTxt.toString())
                    if (llTextContainer.visibility == View.GONE) {
                        llTextContainer.visibility = View.VISIBLE
                    }
                    tvQuery.text = "\'" + searchTxt + "\'"
                    appSearchView.setIconified(false)
                    appSearchView.clearFocus()
                    hideKeyboard()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    searchTxt = newText
                    return false
                }
            })
        }
    }

    private fun setupViewPager() {

        fragmentPagerAdapter = AppPagerAdapter(supportFragmentManager)
        viewPager!!.adapter = fragmentPagerAdapter
        val limit =
            if ((fragmentPagerAdapter as AppPagerAdapter).count > 1) (fragmentPagerAdapter as AppPagerAdapter).count - 1 else 1
        viewPager!!.offscreenPageLimit = limit;
        viewPager!!.currentItem = 0

        viewPager!!.setSwipePagingEnabled(false)

        viewPagerTab!!.setupWithViewPager(viewPager)

    }

    override fun onDestroy() {
        homeViewModel?.reset()
        dealsViewModel?.reset()
        categoryViewModel?.reset()
        super.onDestroy()

    }

    override fun onUpdateNeeded(updateUrl: String?) {
        val dialog = AlertDialog.Builder(this)
            .setTitle("New version available")
            .setMessage("Please, update app to new version to continue shopping.")
            .setPositiveButton(
                "Update"
            ) { dialog, which -> redirectStore(updateUrl!!) }.setNegativeButton(
                "No, thanks"
            ) { dialog, which -> finish() }.create()
        dialog.show()

    }

    private fun redirectStore(updateUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    fun onUpdateLogEvent(bundle: Bundle, eventName: String, isUrlVisited: Boolean) {
        Log.d("TAG", "onUpdateLogEvent: ")
        if (BuildConfig.DEBUG) {
            return
        } else {
            if (isUrlVisited) {
                firebaseAnalytics!!.logEvent(eventName, bundle)
                firebaseAnalytics!!.logEvent("url_visited", bundle)
            } else
                firebaseAnalytics!!.logEvent(eventName, bundle)
        }
    }

    override fun onAllCardClick(item: List<String>) {
        Log.d("TAG", "onAllCardClick: " + item[1])

        if (item[1] == "Amazon") {

            if (bool) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item[2]))
                startActivity(browserIntent)
            } else {
                val intent: Intent = Intent(this, WebActivity::class.java)
                intent.putExtra("title", item[1])
                intent.putExtra("url", item[2])
                intent.putExtra("app_icon", item[3])
                intent.putExtra("color", item[4])
                if (searchTxt != null) {
                    intent.putExtra("search_url", item[5] + "$searchTxt")
                }

                startActivity(intent)
            }
        } else {
            val intent: Intent = Intent(this, WebActivity::class.java)
            intent.putExtra("title", item[1])
            intent.putExtra("url", item[2])
            intent.putExtra("app_icon", item[3])
            intent.putExtra("color", item[4])
            if (searchTxt != null) {
                intent.putExtra("search_url", item[5] + "$searchTxt")
            }

            startActivity(intent)
        }

        val bundle = Bundle()
        bundle.putString("title", item[1])
        bundle.putString("url", item[2])

        this.onUpdateLogEvent(bundle, "all_apps_visited", true)
    }

    private fun initMenuFragment() {
        val menuParams = MenuParams(
            actionBarSize = resources.getDimension(R.dimen.menu).toInt(),
            menuObjects = getMenuObjects(),
            isClosableOutside = true
        )

        contextMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams).apply {
            menuItemClickListener = { view, position ->
                when (position) {
                    1 -> {
                        try {
                            startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/dev?id=6823602592155636380")
                                )
                            )
                        } catch (e: ActivityNotFoundException) {
                            startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/details?id=6823602592155636380")
                                )
                            )
                        }
                    }
                    2 -> {
                        try {
                            startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                                )
                            )
                        } catch (e: ActivityNotFoundException) {
                            startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                                )
                            )
                        }
                    }
                    3 -> {
                        val i = Intent(Intent.ACTION_SEND)
                        i.type = "message/rfc822"
                        i.putExtra(Intent.EXTRA_EMAIL, arrayOf("infinitywebapps@gmail.com"))
                        i.putExtra(
                            Intent.EXTRA_SUBJECT,
                            "Feedback " + resources.getString(R.string.app_name)
                        )
                        i.putExtra(Intent.EXTRA_TEXT, "Feedback :: ")
                        try {
                            startActivity(Intent.createChooser(i, "Send feedback..."))
                        } catch (ex: ActivityNotFoundException) {
                            Toast.makeText(
                                this@MainActivity,
                                "There are no email clients installed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    4 -> {
                        val intent = Intent(Intent.ACTION_SEND)
                        /*This will be the actual content you wish you share.*/
                        /*This will be the actual content you wish you share.*/
                        val shareBody =
                            resources.getString(R.string.app_name) + " - " + "Make your shopping experience better!!" + "\n" + "https://play.google.com/store/apps/details?id=$packageName"
                        /*The type of the content is text, obviously.*/
                        /*The type of the content is text, obviously.*/intent.type = "text/plain"
                        /*Applying information Subject and Body.*/
                        /*Applying information Subject and Body.*/
                        intent.putExtra(Intent.EXTRA_TEXT, shareBody)
                        /*Fire!*/
                        /*Fire!*/startActivity(
                            Intent.createChooser(
                                intent,
                                "Share via"
                            )
                        )
                    }


                }
            }
        }
    }


    private fun getMenuObjects() = mutableListOf<MenuObject>().apply {
        val close = MenuObject().apply {
            setResourceValue(R.drawable.ic_baseline_close)
            setBgColorValue(resources.getColor(R.color.colorPrimary))
        }
        val moreApps = MenuObject("More apps").apply {
            setResourceValue(R.drawable.ic_baseline_store)
            setBgColorValue(resources.getColor(R.color.colorPrimary))
        }
        val rateUs = MenuObject("Rate Us").apply {
            setResourceValue(R.drawable.ic_baseline_star_rate)
            setBgColorValue(resources.getColor(R.color.colorPrimary))
        }
        val feedback = MenuObject("Feedback").apply {
            setResourceValue(R.drawable.ic_baseline_feedback)
            setBgColorValue(resources.getColor(R.color.colorPrimary))
        }
        val share = MenuObject("Share").apply {
            setResourceValue(R.drawable.ic_share)
            setBgColorValue(resources.getColor(R.color.colorPrimary))
        }

        add(close)
        add(moreApps)
        add(rateUs)
        add(feedback)
        add(share)
    }

    private fun showContextMenuDialogFragment() {
        Log.d("TAG", "showContextMenuDialogFragment: ")
        if (supportFragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
            contextMenuDialogFragment.show(supportFragmentManager, ContextMenuDialogFragment.TAG)
        }
    }

    fun onShowIntro() {

        val typeface =
            ResourcesCompat.getFont(this, R.font.montserrat_semibold)

        fancyHome = FancyShowCaseView.Builder(this@MainActivity)
            .focusOn(Objects.requireNonNull(bottomNav.getChildAt(0)))
            .focusShape(FocusShape.ROUNDED_RECTANGLE)
            .roundRectRadius(90)
            .titleGravity(Gravity.CENTER)
            .titleSize(28, TypedValue.COMPLEX_UNIT_SP)
            .enableAutoTextPosition()
            .typeface(typeface)
            .showOnce("FANCY_HOME")
            .title("Multiple stores and brands for Grocery Needs")
            .build()
        fancyCategories = FancyShowCaseView.Builder(this@MainActivity)
            .focusOn(Objects.requireNonNull(bottomNav.getChildAt(1)))
            .focusShape(FocusShape.CIRCLE)
            .roundRectRadius(90)
            .titleGravity(Gravity.CENTER)
            .titleSize(28, TypedValue.COMPLEX_UNIT_SP)
            .typeface(typeface)
            .enableAutoTextPosition()
            .showOnce("FANCY_CATEGORIES")
            .title("Your Favourite Food Deliveries at Your Door Step")
            .build()
        fancyDeals = FancyShowCaseView.Builder(this@MainActivity)
            .focusOn(Objects.requireNonNull(bottomNav.getChildAt(2)))
            .focusShape(FocusShape.CIRCLE)
            .roundRectRadius(90)
            .titleGravity(Gravity.CENTER)
            .titleSize(28, TypedValue.COMPLEX_UNIT_SP)
            .typeface(typeface)
            .enableAutoTextPosition()
            .showOnce("FANCY_DEALS")
            .title("Shop Your Heart Out From All Leading Stores")
            .build()
        fancyBookmarks = FancyShowCaseView.Builder(this@MainActivity)
            .focusOn(Objects.requireNonNull(bottomNav.getChildAt(3)))
            .focusShape(FocusShape.CIRCLE)
            .roundRectRadius(90)
            .titleGravity(Gravity.CENTER)
            .titleSize(28, TypedValue.COMPLEX_UNIT_SP)
            .typeface(typeface)
            .enableAutoTextPosition()
            .showOnce("FANCY_BOOKMARKS")
            .title("Create a list of your favourite item(s) to buy later")
            .build()
        fancySearchView = FancyShowCaseView.Builder(this@MainActivity)
            .focusOn(Objects.requireNonNull(search))
            .focusShape(FocusShape.CIRCLE)
            .roundRectRadius(90)
            .titleGravity(Gravity.CENTER)
            .titleSize(28, TypedValue.COMPLEX_UNIT_SP)
            .typeface(typeface)
            .enableAutoTextPosition()
            .showOnce("FANCY_SEARCH")
            .title("Search for your interest across the stores")
            .build()
        fancyMenu = FancyShowCaseView.Builder(this@MainActivity)
            .focusOn(Objects.requireNonNull(ivMenu))
            .focusShape(FocusShape.CIRCLE)
            .roundRectRadius(90)
            .titleGravity(Gravity.CENTER)
            .titleSize(28, TypedValue.COMPLEX_UNIT_SP)
            .typeface(typeface)
            .enableAutoTextPosition()
            .showOnce("FANCY_MENU")
            .title("Checkout more app features here")
            .build()
        fancyShowCaseQueue = FancyShowCaseQueue()
        fancyShowCaseQueue!!.add(fancyHome!!).add(fancyCategories!!)
            .add(fancyDeals!!).add(fancyBookmarks!!).add(fancySearchView!!).add(fancyMenu!!)
        fancyShowCaseQueue!!.show()
    }

}



