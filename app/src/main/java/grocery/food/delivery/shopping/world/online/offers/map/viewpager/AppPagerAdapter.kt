package grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.fragment.BookmarkFragment
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.fragment.CategoryFragment
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.fragment.DealFragment
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.fragment.FragmentHome
import grocery.food.delivery.shopping.world.online.offers.map.view.fragment.CardsFragment

class AppPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    val NUM_ITEMS = 4;

    override fun getCount(): Int {
        return NUM_ITEMS
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                FragmentHome.newInstance(position, "Home")
            }
            1 -> {
                CategoryFragment.newInstance(position, "Shop")
            }
            2 -> {
                DealFragment.newInstance(position, "Deals")
            }
            3 -> {
                BookmarkFragment.newInstance(position, "Bookmark")
            }

            else -> FragmentHome.newInstance(position, "Home")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var title: String? = null;
        if (position == 0) {
            title = "Home"
        } else if (position == 1) {
            title = "Category"
        } else if (position == 2) {
            title = "Deals"
        }
        else if (position == 3) {
            title = "Card"
        }
        return title
    }
}