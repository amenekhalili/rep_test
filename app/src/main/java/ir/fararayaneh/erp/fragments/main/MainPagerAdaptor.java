package ir.fararayaneh.erp.fragments.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * adaptor for fragments that showed in main activity
 */
public class MainPagerAdaptor extends FragmentStatePagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public MainPagerAdaptor(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
        notifyDataSetChanged();
    }

    public void clearAllFragments() {
        mFragmentList.clear();
        mFragmentTitleList.clear();
        notifyDataSetChanged();
    }

    /*public void clearFragment(Fragment fragment) {
        if(fragment!=null){
            if(mFragmentList.contains(fragment)){
                mFragmentTitleList.remove(mFragmentList.lastIndexOf(fragment));
                mFragmentList.remove(fragment);
                notifyDataSetChanged();
            }
        }*/


    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    /**
     * this method was changed because those situations that we need clear the viewPagers
     */
    @Override
    public int getItemPosition(@NonNull Object object) {
        //return super.getItemPosition(object);
        if (mFragmentList.size()!=0) {
            return mFragmentList.size();
        } else {
            return POSITION_NONE;
        }
    }
}
