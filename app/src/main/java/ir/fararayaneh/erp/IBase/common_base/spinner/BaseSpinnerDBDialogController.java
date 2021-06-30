package ir.fararayaneh.erp.IBase.common_base.spinner;

import android.app.Activity;
import android.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import java.util.Objects;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmModel;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.data.data_providers.FactoryDataProvider;
import ir.fararayaneh.erp.data.data_providers.queries.recycle_list_view_queries.spinner_dialog_quries.SpinnerDialogQueries;
import ir.fararayaneh.erp.utils.SoftKeyHelper;

/**
 * with class BaseSpinnerDialogDBAdapter , show a dialog spinner from realm dataBase
 */

//todo close spinner dialog in onStop or onDestroy

/**
 * on click we send position of filtered list
 */

public abstract class BaseSpinnerDBDialogController {

    private Activity activity;
    protected OrderedRealmCollection[] data;
    private String dTitle,closeTitle = Commons.SPACE;
    private IOnSpinnerItemClick IOnSpinnerItemClick;
    private AlertDialog alertDialog;
    private int style;
    private boolean cancellable=false,showKeyboard=false;
    private Realm realm;
    private SpinnerDialogQueries spinnerDialogQueries;
    private ListView listView;
    private TextView rippleViewClose;
    private AppCompatEditText searchBox;
    private BaseSpinnerDialogDBAdapter baseSpinnerDialogDBAdapter;



    public BaseSpinnerDBDialogController(Activity activity, String dialogTitle) {
        this.activity = activity;
        this.dTitle = dialogTitle;
    }

    public BaseSpinnerDBDialogController(Activity activity, String dialogTitle, String closeTitle) {
        this.activity = activity;
        this.dTitle = dialogTitle;
        this.closeTitle=closeTitle;
    }

    public BaseSpinnerDBDialogController(Activity activity, String dialogTitle, int style) {
        this.activity = activity;
        this.dTitle = dialogTitle;
        this.style = style;

    }

    public BaseSpinnerDBDialogController(Activity activity, String dialogTitle, int style, String closeTitle) {
        this.activity = activity;
        this.dTitle = dialogTitle;
        this.style = style;
        this.closeTitle=closeTitle;
    }

    public void bindOnSpinnerListener(IOnSpinnerItemClick IOnSpinnerItemClick) {
        this.IOnSpinnerItemClick = IOnSpinnerItemClick;
    }

    public void showSpinnerDialog() {
        //--------setupViews dialog---------------------------------------------------
        AlertDialog.Builder adb = new AlertDialog.Builder(activity);
        View v = activity.getLayoutInflater().inflate(R.layout.layout_spinner_dialog, null);
        rippleViewClose = (TextView) v.findViewById(R.id.spinner_close);
        AppCompatTextView title = (AppCompatTextView) v.findViewById(R.id.spinerTitle);
        rippleViewClose.setText(closeTitle);
        title.setText(dTitle);
        listView = (ListView) v.findViewById(R.id.spinner_list_view);
        searchBox = (AppCompatEditText) v.findViewById(R.id.spinner_search_Box);
        if(isShowKeyboard()){
            showKeyboard(searchBox);
        }
        //--------setupAdaptor----------------------------------------------------
        listView.setAdapter(setupCostumeSearchData(Commons.SPACE));
        adb.setView(v);
        alertDialog = adb.create();
        Objects.requireNonNull(alertDialog.getWindow()).getAttributes().windowAnimations = style;//R.style.DialogAnimations_SmileWindow;
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            if(IOnSpinnerItemClick !=null ){
                AppCompatTextView t = view.findViewById(R.id.txt_row_spinner_dialog);
                IOnSpinnerItemClick.onClick(getProperRealmModelRow(0,i),t.getText().toString(),i);//only send first realm model
            }
            closeSpinnerDialog();
        });


        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(editable.toString().equals(Commons.SPACE)){
                        if(listView!=null){
                            listView.setAdapter(setupCostumeSearchData(Commons.SPACE)); }
                } else {
                    listView.setAdapter(setupCostumeSearchData(editable.toString()));
                }
            }
        });

        rippleViewClose.setOnClickListener(v1 -> closeSpinnerDialog());
        //--------setupAdaptor----------------------------------------------------
        alertDialog.setCancelable(isCancellable());
        alertDialog.setCanceledOnTouchOutside(isCancellable());
        alertDialog.show();
    }


    private void hideKeyboard(){
        SoftKeyHelper.hideSoftKeyboard(activity);
    }

    private void showKeyboard(final EditText edtText){
        SoftKeyHelper.showSoftKeyboard(edtText,activity);
    }

    private boolean isCancellable() {
        return cancellable;
    }

    public void setCancellable(boolean cancellable) {
        this.cancellable = cancellable;
    }

    private boolean isShowKeyboard() {
        return showKeyboard;
    }

    public void setShowKeyboard(boolean showKeyboard) {
        this.showKeyboard = showKeyboard;
    }

    /**
     * here, where we remove all listeners
     */
    public void closeSpinnerDialog() {
        hideKeyboard();
        if(realm!=null){
            realm.close();
        }
        if(rippleViewClose!=null){
            rippleViewClose.setOnClickListener(null);
        }
        if(searchBox!=null){
            searchBox.addTextChangedListener(null);
        }
        if(listView!=null){
            listView.setOnItemClickListener(null);
        }
        if(IOnSpinnerItemClick !=null){
            IOnSpinnerItemClick =null;
        }
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    protected Realm getRealmInstance(){
        if(realm==null){
            realm = Realm.getDefaultInstance();
        }
        return realm;
    }

    protected SpinnerDialogQueries getSpinnerDialogQueriesInstance(){
        if(spinnerDialogQueries==null){
            spinnerDialogQueries = (SpinnerDialogQueries) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.SPINNER_DIALOG_QUERIES);
        }
        return spinnerDialogQueries;
    }
    //----------------------------------------------------------------------------------------------

    protected BaseSpinnerDialogDBAdapter getProperAdaptorWithData(){
        if(baseSpinnerDialogDBAdapter!=null){
            baseSpinnerDialogDBAdapter.updateData(data);
            return baseSpinnerDialogDBAdapter;
        } else {
            baseSpinnerDialogDBAdapter = getProperNewAdaptor();
            return baseSpinnerDialogDBAdapter;
        }
    }

    protected abstract BaseSpinnerDialogDBAdapter getProperNewAdaptor();

    protected abstract RealmModel getProperRealmModelRow(int positionOfRealmData,int intPositionOfRow);

    protected abstract BaseSpinnerDialogDBAdapter setupCostumeSearchData(String searchValue);



}
