package ir.fararayaneh.erp.activities.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.SearchMemoryHandler;

import java.util.LinkedList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.utils.ContextHelper;
import ir.fararayaneh.erp.utils.SoftKeyHelper;
import ir.fararayaneh.erp.utils.intent_handler.ActivityIntents;

import static androidx.appcompat.app.AppCompatDelegate.setCompatVectorFromResourcesEnabled;


public class SearchActivity extends AppCompatActivity {

    static {
        setCompatVectorFromResourcesEnabled(true);
    }

    @BindView(R.id.global_search_bar_3)
    MaterialSearchBar global_search_bar;
    @BindView(R.id.global_toolbar_3)
    Toolbar global_toolbar_3;
    @BindView(R.id.conslay_prog_search_act)
    ConstraintLayout conslay_prog_search_act;

    LinkedList<String> tempSearchMemory = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUpTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setupSearch();
        showHideProgress(false);
    }

    private void setUpTheme() {
        setTheme(SharedPreferenceProvider.getAppTheme(this));
        AppCompatDelegate.setDefaultNightMode(SharedPreferenceProvider.getNightMode(this));
    }

    private void setupSearch() {

        setSupportActionBar(global_toolbar_3);
        global_search_bar.setPlaceHolder(getString(R.string.write_some_thing));
        global_search_bar.setLastSuggestions(SearchMemoryHandler.getSearchList());
        global_search_bar.enableSearch();
        global_search_bar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if(!enabled){
                    showHideProgress(true);
                    cancelResultIntent();
                }
            }
            @Override
            public void onSearchConfirmed(CharSequence text) {
                showHideProgress(true);
                SearchMemoryHandler.addSearchValue(text.toString());
                sendBackResult(text.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        global_search_bar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                getContainsFilter(editable.toString());
            }
        });

    }

    public void getContainsFilter(String charText) {


        charText = charText.toLowerCase(Locale.getDefault());
        tempSearchMemory.clear();
        if (charText.length() == 0) {
            tempSearchMemory.addAll(SearchMemoryHandler.getSearchList());
        }
        else {
            for (String item : SearchMemoryHandler.getSearchList())
            {
                if (item.toLowerCase(Locale.getDefault()).contains(charText)) {
                    tempSearchMemory.add(item);
                }
            }
        }

        global_search_bar.updateLastSuggestions(tempSearchMemory);
    }

    @Override
    protected void onStart() {
        super.onStart();
        App.addToAppPowerOnList();
    }

    @Override
    protected void onStop() {
        App.removeFromAppPowerOnList();
        super.onStop();
    }

    protected void cancelResultIntent() {
        ActivityIntents.cancelResultIntent(this);
        finish();
    }

    @Override
    public void onBackPressed() {
        cancelResultIntent();
    }

    private void sendBackResult(String result) {
        ActivityIntents.resultOkFromSearchAct(this, result);
    }

    private void showHideProgress(boolean showHide) {
        conslay_prog_search_act.setVisibility(showHide? View.VISIBLE:View.GONE);
        SoftKeyHelper.hideSoftKeyboard(this);
    }

    @Override
    protected void onDestroy() {
        if(global_search_bar!=null){
            global_search_bar.setSuggestionsClickListener(null);
            global_search_bar.addTextChangeListener(null);
        }
        super.onDestroy();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ContextHelper.customContext(newBase));
    }
}
