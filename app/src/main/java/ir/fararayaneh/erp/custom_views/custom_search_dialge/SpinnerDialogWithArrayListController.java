package ir.fararayaneh.erp.custom_views.custom_search_dialge;

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
import java.util.ArrayList;
import java.util.Objects;

import ir.fararayaneh.erp.IBase.common_base.spinner.IOnSpinnerItemClick;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.adaptors.spinner_dialog_adaptor.SpinnerDialogWithArrayAdaptor;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.utils.SoftKeyHelper;

//todo remove leak and use butterKnife

/**
 * on click we send position of non filtered list
 */

public class SpinnerDialogWithArrayListController {
    private ArrayList<String> items;
    private ArrayList<String> permanentArrayAdaptorData;//for prevent from change position of origin data
    private Activity context;
    private String dTitle,closeTitle= Commons.SPACE;
    private ir.fararayaneh.erp.IBase.common_base.spinner.IOnSpinnerItemClick IOnSpinnerItemClick;
    private AlertDialog alertDialog;
    private int pos;
    private int style;
    private boolean cancellable=false;
    private boolean showKeyboard=false;
    private boolean useContainsFilter=false;


    public SpinnerDialogWithArrayListController(Activity activity, ArrayList<String> items, String dialogTitle) {
        this.items = items;
        fillPermanentArrayList(items);
        this.context = activity;
        this.dTitle = dialogTitle;
    }

    public SpinnerDialogWithArrayListController(Activity activity, ArrayList<String> items, String dialogTitle, String closeTitle) {
        this.items = items;
        fillPermanentArrayList(items);
        this.context = activity;
        this.dTitle = dialogTitle;
        this.closeTitle=closeTitle;
    }

    public SpinnerDialogWithArrayListController(Activity activity, ArrayList<String> items, String dialogTitle, int style) {
        this.items = items;
        fillPermanentArrayList(items);
        this.context = activity;
        this.dTitle = dialogTitle;
        this.style = style;
    }

    public SpinnerDialogWithArrayListController(Activity activity, ArrayList<String> items, String dialogTitle, int style, String closeTitle) {
        this.items = items;
        fillPermanentArrayList(items);
        this.context = activity;
        this.dTitle = dialogTitle;
        this.style = style;
        this.closeTitle=closeTitle;
    }

    private void fillPermanentArrayList(ArrayList<String> items) {
        permanentArrayAdaptorData = new ArrayList<>();
        permanentArrayAdaptorData.addAll(items);
    }

    public void bindOnSpinerListener(IOnSpinnerItemClick IOnSpinnerItemClick1) {
        this.IOnSpinnerItemClick = IOnSpinnerItemClick1;
    }

    public void showSpinerDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        View v = context.getLayoutInflater().inflate(R.layout.layout_spinner_dialog, null);
        TextView rippleViewClose = v.findViewById(R.id.spinner_close);
        AppCompatTextView title = v.findViewById(R.id.spinerTitle);
        rippleViewClose.setText(closeTitle);
        title.setText(dTitle);
        final ListView listView = v.findViewById(R.id.spinner_list_view);
        final AppCompatEditText searchBox = v.findViewById(R.id.spinner_search_Box);
        if(isShowKeyboard()){
            showKeyboard(searchBox);
        }
       //final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.layout_spinner_diloge_row, items);
        final SpinnerDialogWithArrayAdaptor<String> adapter = new SpinnerDialogWithArrayAdaptor<>(context, R.layout.layout_spinner_diloge_row, permanentArrayAdaptorData);
        listView.setAdapter(adapter);
        adb.setView(v);
        alertDialog = adb.create();
        Objects.requireNonNull(alertDialog.getWindow()).getAttributes().windowAnimations = style;//R.style.DialogAnimations_SmileWindow;

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            AppCompatTextView t = view.findViewById(R.id.txt_row_spinner_dialog);
            for (int j = 0; j < items.size(); j++) {
                if (t.getText().toString().equals(items.get(j))) {
                    pos = j;
                }
            }
            if(IOnSpinnerItemClick!=null){
                IOnSpinnerItemClick.onClick(null,t.getText().toString(), pos);
            }
            closeSpinerDialog();
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
                if(isUseContainsFilter()){
                    adapter.getContainsFilter(Objects.requireNonNull(searchBox.getText()).toString());
                } else {
                    adapter.getFilter().filter(Objects.requireNonNull(searchBox.getText()).toString());
                }
            }
        });

        rippleViewClose.setOnClickListener(v1 -> closeSpinerDialog());
        alertDialog.setCancelable(isCancellable());
        alertDialog.setCanceledOnTouchOutside(isCancellable());
        alertDialog.show();
    }

    private void closeSpinerDialog() {
        hideKeyboard();
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    private void hideKeyboard(){
        SoftKeyHelper.hideSoftKeyboard(context);
    }

    private void showKeyboard(final EditText etText){
        SoftKeyHelper.showSoftKeyboard(etText,context);
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

    private boolean isUseContainsFilter() {
        return useContainsFilter;
    }

    public void setShowKeyboard(boolean showKeyboard) {
        this.showKeyboard = showKeyboard;
    }

    public void setUseContainsFilter(boolean useContainsFilter) {
        this.useContainsFilter = useContainsFilter;
    }

}
