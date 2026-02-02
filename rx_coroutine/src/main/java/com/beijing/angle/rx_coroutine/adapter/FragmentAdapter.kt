package com.beijing.angle.rx_coroutine.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


/**
 *
 *         <com.flyco.tablayout.SlidingTabLayout
 *             android:id="@+id/tab"
 *             tl:tl_textBold="SELECT"
 *             android:layout_width="match_parent"
 *             android:layout_height="@dimen/dp_50"
 *             tl:tl_indicator_width="30dp"
 *             tl:tl_indicator_height="@dimen/dp_7"
 *             tl:tl_tab_padding="20dp"
 *             tl:tl_indicator_bounce_enable="true"
 *             tl:tl_indicator_anim_duration="5000"
 *             tl:tl_tab_space_equal="false"
 *             tl:tl_indicator_style="NORMAL"
 *             tl:tl_indicator_corner_radius="@dimen/dp_10"
 *             tl:tl_textSelectColor="#2E81FF"
 *             tl:tl_indicator_color="#2E81FF"
 *             tl:tl_textUnselectColor="#8CA0CD"
 *             tl:tl_textsize="18dp"
 *
 *             />
 *
 *         <com.beijing.angle.rx_coroutine.widget.NoScrollViewPager
 *             android:id="@+id/mViewPager"
 *             android:layout_width="match_parent"
 *             android:layout_height="match_parent" />
 *
 *
 *            val titles = arrayOf( "推荐")
 *            val fragment = MyFragment.newInstance()
 *
 *            mFragmentList.add(fragment10)
 *
 *           binding.mViewPager.adapter = FragmentAdapter(supportFragmentManager, mFragmentList)
 *           binding.mViewPager.offscreenPageLimit = mFragmentList.size
 *           binding.tab.setViewPager(binding.mViewPager, titles)
 *           binding.tab.onPageSelected(0)
 */



class FragmentAdapter(fm: FragmentManager, var fragments: List<Fragment>) :
    FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }
}