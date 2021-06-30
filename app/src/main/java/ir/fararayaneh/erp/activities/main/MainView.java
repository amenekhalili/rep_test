package ir.fararayaneh.erp.activities.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.roughike.bottombar.BottomBarTab;
import java.util.Objects;
import butterknife.BindView;
import butterknife.ButterKnife;
import ir.fararayaneh.erp.IBase.common_base.BaseActivity;
import ir.fararayaneh.erp.IBase.common_base.BaseView;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonClickMainBottomBar;
import ir.fararayaneh.erp.commons.CommonRequestCodes;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.custom_views.CustomCircleImageView;
import ir.fararayaneh.erp.fragments.main.MainFragment;
import ir.fararayaneh.erp.fragments.main.MainPagerAdaptor;
import ir.fararayaneh.erp.utils.GetATTResources;
import ir.fararayaneh.erp.utils.PicassoHandler;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.IIOSDialogeListener;
import ir.fararayaneh.erp.utils.show_information_message.ToastMessage;


public class MainView extends BaseView implements MenuItem.OnMenuItemClickListener {

    private static final int MENU_ITEM_COUNT = 8;//todo add if menu item added
    @BindView(R.id.global_toolbar)
    Toolbar global_toolbar;
    @BindView(R.id.cons_prog_main_act)
    ConstraintLayout cons_prog_main_act;
    @BindView(R.id.conslay_prog_comershial)
    ConstraintLayout conslay_prog_comershial;
    @BindView(R.id.tabs_main_act)
    TabLayout tabs_main_act;
    @BindView(R.id.viewPager_main_act)
    ViewPager viewPager_main_act;
    @BindView(R.id.nav_drawer_main_act)
    NavigationView nav_drawer_main_act;
    @BindView(R.id.drawer_layout_main_act)
    DrawerLayout drawer_layout_main_act;
    @BindView(R.id.global_bottombar)
    com.roughike.bottombar.BottomBar global_bottombar;

    private CustomCircleImageView img_profile_nav_main_act;
    private BottomBarTab tab_home, tab_online, tab_dashBoard, tab_cardable, tab_chat;
    private AppCompatTextView txt_name_nav_main_act,txt_company_nav_main_act;
    private MainPagerAdaptor adapter;
    protected boolean exitFromApp;
    private IBackClick iBackClick;
    private ActionBarDrawerToggle toggle_m_act;
    private IMainClickListener iMainClickListener;

    public void setiMainClickListener(IMainClickListener iMainClickListener) {
        this.iMainClickListener = iMainClickListener;
    }

    public void setiBackClick(IBackClick iBackClick) {
        this.iBackClick = iBackClick;
    }

    public MainView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MainView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    protected MainView(@NonNull BaseActivity activity) {
        super(activity);
    }

    @Override
    protected void inflateButterKnife() {
        LayoutInflater.from(activity).inflate(R.layout.activity_main, this, true);
        ButterKnife.bind(this);
    }

    protected void initMainActivity() {
        setupToolbar();
        initDrawer();
        initClickDrawerMenu();
        setupTabLayout();
        initBottomTabs();
        initDefaultTab();
        initTabSelection();

    }

    @Override
    protected void setupToolbar() {
        activity.setSupportActionBar(global_toolbar);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toggle_m_act = new ActionBarDrawerToggle(activity, drawer_layout_main_act, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer_layout_main_act.addDrawerListener(toggle_m_act);
        toggle_m_act.syncState();

        global_toolbar.setOnClickListener(view ->{

    }) ;

    }

    private void initDrawer() {
        img_profile_nav_main_act = nav_drawer_main_act.getHeaderView(0).findViewById(R.id.img_profile_nav_main_act);
        txt_name_nav_main_act = nav_drawer_main_act.getHeaderView(0).findViewById(R.id.txt_name_nav_main_act);
        txt_company_nav_main_act = nav_drawer_main_act.getHeaderView(0).findViewById(R.id.txt_company_nav_main_act);

    }

    private void initClickDrawerMenu() {
        for (int i = 0; i < MENU_ITEM_COUNT; i++) {
            nav_drawer_main_act.getMenu().getItem(i).setOnMenuItemClickListener(this);
        }
    }

    private void setupTabLayout() {
        tabs_main_act.setupWithViewPager(viewPager_main_act);
        tabs_main_act.setTabTextColors(getResources().getColor(GetATTResources.resId(activity, new int[]{R.attr.colorWhiteAttrEveryWhere})), getResources().getColor(GetATTResources.resId(activity, new int[]{R.attr.colorAccentAttr})));
    }

    private void initBottomTabs() {
        tab_home = global_bottombar.getTabWithId(R.id.tab_home_main_botom_bar);
        tab_dashBoard = global_bottombar.getTabWithId(R.id.tab_dashboard_main_botom_bar);
        tab_cardable = global_bottombar.getTabWithId(R.id.tab_cardabl_main_botom_bar);
        tab_chat = global_bottombar.getTabWithId(R.id.tab_chat_main_botom_bar);
        tab_online = global_bottombar.getTabWithId(R.id.tab_online_main_botom_bar);

        tab_home.setTitle(activity.getString(R.string.home));
        tab_dashBoard.setTitle(activity.getString(R.string.dashboard));
        tab_cardable.setTitle(activity.getString(R.string.cardable));
        tab_chat.setTitle(activity.getString(R.string.chat));
        tab_online.setTitle(activity.getString(R.string.online));

    }

    protected void initDefaultTab() {
        global_bottombar.setDefaultTab(R.id.tab_home_main_botom_bar);
    }

    protected void initTabSelection() {

        global_bottombar.setTabSelectionInterceptor((oldTabId, newTabId) -> {
            switch (newTabId) {
                case R.id.tab_home_main_botom_bar: {
                    //do notings
                    return true;
                }
                case R.id.tab_dashboard_main_botom_bar: {

                    if (iMainClickListener != null) {
                        iMainClickListener.clickBottomBarTab(CommonClickMainBottomBar.DASHBOARD_CLICK);
                    }
                    return true;
                }
                case R.id.tab_cardabl_main_botom_bar: {
                    if (iMainClickListener != null) {
                        iMainClickListener.clickBottomBarTab(CommonClickMainBottomBar.CARTABLE_CLICK);
                    }
                    return true;
                }
               case R.id.tab_chat_main_botom_bar: {
                    if (iMainClickListener != null) {
                        iMainClickListener.clickBottomBarTab(CommonClickMainBottomBar.CHAT_CLICK);
                    }
                    return true;
                }
                case R.id.tab_online_main_botom_bar: {
                    if (iMainClickListener != null) {
                        iMainClickListener.clickBottomBarTab(CommonClickMainBottomBar.ONLINE_CLICK);
                    }
                    return true;
                }
            }
            return false;
        });

        global_bottombar.setOnTabReselectListener(tabId -> {
            //do nothings
        });
    }

    protected void setBadgeNumber(int chatBadgeNumber, int cartableBadgeNumber){
        tab_chat.setBadgeCount(chatBadgeNumber);
        tab_cardable.setBadgeCount(cartableBadgeNumber);
    }

    private void setAdaptor() {
        if (adapter == null) {
            adapter = new MainPagerAdaptor(activity.getSupportFragmentManager());
            viewPager_main_act.setAdapter(adapter);
        }
    }

    private MainFragment getFragment() {
        return new MainFragment();
    }

    private void clearAllFragmentsFromViewPager() {
        if (adapter != null) {
            adapter.clearAllFragments();
        }
    }

    protected void setFakeViewPagerData(Bundle bundle) {
        setAdaptor();
        MainFragment mainFragment = getFragment();
        mainFragment.setArguments(bundle);
        showHideProgress(false);
        adapter.addFragment(mainFragment, getResources().getString(R.string.all));
    }

    public void setCorrectStartViewPagerData(Bundle bundle) {
        setAdaptor();
        MainFragment mainFragment = getFragment();
        mainFragment.setArguments(bundle);
        clearAllFragmentsFromViewPager();
        adapter.addFragment(mainFragment, getResources().getString(R.string.all));
        Log.i("arash", "getdata: end show first page");
    }

    public void setCorrectOtherViewPagerData(Bundle bundle, String tabName) {
        MainFragment fragment = getFragment();
        fragment.setArguments(bundle);
        adapter.addFragment(fragment, tabName);
        Log.i("arash", "getdata: end show other page");
    }

    public void setCorrectOffsetScreen(int pageNumber) {
        // TODO: 1/6/2019 need this method ?
        viewPager_main_act.setOffscreenPageLimit(pageNumber);
        setCurrentPager(0);
    }

    public void setCurrentPager(int pageNumber) {
        viewPager_main_act.setCurrentItem(pageNumber);
    }

    public void setHeaderDrawerData(String name, String profileUrl,String companyName) {
        txt_name_nav_main_act.setText(name);
        txt_company_nav_main_act.setText(companyName);
        PicassoHandler.setByPicasso(picasso, Commons.NULL ,profileUrl, img_profile_nav_main_act, false, Commons.SPACE, true, false);
    }

    /*
    public void clearFragmentFromList(Fragment fragment){
        adapter.clearFragment(fragment);
    }
    */

    @Override
    public void showHideProgress(boolean showHide) {
        cons_prog_main_act.setVisibility(showHide ? VISIBLE : GONE);
    }

    @Override
    protected void workforOnBackPressed() {
        if (exitFromApp) {
            finish();
        } else {
            showExitAppMessage();
            exitFromApp = true;
            if (iBackClick != null) {
                iBackClick.clickOnBackPress();
            }
        }

    }


    @Override
    protected void workforOnCreateOptionsMenu(Menu menu) {
    }


    @Override
    protected boolean workforOnOptionsItemSelected(MenuItem item) {
        return toggle_m_act.onOptionsItemSelected(item);
    }

    @Override
    protected void workForPermissionResults(int requestCode, @NonNull int[] grantResults) {

    }

    @Override
    public void workForActivityResult(int requestCode, int resultCode, Intent data) {
        //todo بقیه ریکوست کدها چک شود
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case CommonRequestCodes.Do_SYNC:
                    reCreate();
                break;
                default:
                    showMessageSaveDataSuccess();
                break;
            }
        }
    }

    @Override
    protected void clearAllViewClickListeners() {
        if(global_bottombar!=null){
            global_bottombar.setTabSelectionInterceptor(null);
            global_bottombar.setOnTabReselectListener(null);
        }

        if (nav_drawer_main_act != null) {
            for (int i = 0; i < MENU_ITEM_COUNT; i++) {
                nav_drawer_main_act.getMenu().getItem(i).setOnMenuItemClickListener(null);
            }
        }
    }

    //------------------------------------------------------------------------>>

    private void showExitAppMessage() {
        showMsg(getResources().getString(R.string.press_again));
    }

    public void showHideCommercial(boolean show) {
        conslay_prog_comershial.setVisibility(show ? VISIBLE : GONE);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        closeDrawer();
        switch (menuItem.getItemId()) {
            case R.id.drawer_main_menu_sync:
                if(iMainClickListener!=null){
                    iMainClickListener.clickSync();
                }
                break;
            case R.id.drawer_main_menu_backup:
                if(iMainClickListener!=null){
                    iMainClickListener.clickBackUp();
                }
                break;
            case R.id.drawer_main_menu_night_mode:
                if(iMainClickListener!=null){
                    iMainClickListener.clickNightMode();
                }
                break;
            case R.id.drawer_main_menu_get_config:
                if(iMainClickListener!=null){
                    iMainClickListener.clickgetConfig();
                }
                break;
            case R.id.drawer_main_menu_logout:
                if(iMainClickListener!=null){
                    iMainClickListener.clickLogOut();
                }
                break;
            case R.id.drawer_main_menu_about:
                if(iMainClickListener!=null){
                    iMainClickListener.clickAbout();
                }
                break;
            case R.id.drawer_main_menu_support:
                if(iMainClickListener!=null){
                    iMainClickListener.clickSupport();
                }
                break;
            case R.id.drawer_main_menu_help:
                if(iMainClickListener!=null){
                    iMainClickListener.clickHelp();
                }
                break;
        }
        return false;
    }

    public void showConfirmLogoutMessage(IIOSDialogeListener iiosDialogeListener) {
        ToastMessage.showIosDialogTypeConfirmCancle(activity,
                getResources().getString(R.string.dear_user),
                getResources().getString(R.string.logout_message),
                getResources().getString(R.string.ok),
                getResources().getString(R.string.cancel),
                false,
                iiosDialogeListener
                );
    }

    public void showConfirmSendLogoutMessage(Context context, IIOSDialogeListener iiosDialogeListener) {
        ToastMessage.showIosDialogType2(context,getResources().getString(R.string.dear_user),getResources().getString(R.string.wait_for_logout),getResources().getString(R.string.ok),true,iiosDialogeListener);
    }

    protected void closeDrawer() {
        drawer_layout_main_act.closeDrawers();
    }


}
