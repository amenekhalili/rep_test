package ir.fararayaneh.erp.activities.update_tables;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.fararayaneh.erp.IBase.common_base.BaseActivity;
import ir.fararayaneh.erp.IBase.common_base.BaseViewWithRecycle;
import ir.fararayaneh.erp.IBase.common_base.IGoNextPage;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.custom_views.custom_recycle_view.CustomRecycleView;
import ir.fararayaneh.erp.utils.show_information_message.ToastMessage;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.IIOSDialogeListener;

public class UpdateTablesView extends BaseViewWithRecycle {

    @BindView(R.id.custom_recycle_update_table)
    CustomRecycleView custom_recycle_update_table;



    public UpdateTablesView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public UpdateTablesView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected UpdateTablesView(@NonNull BaseActivity activity) {
        super(activity);
    }

    @Override
    protected void setupRecyclers() {
        custom_recycle_update_table.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));
        custom_recycle_update_table.setHasFixedSize(true);    }

    @SafeVarargs
    @Override
    public final void setRecycleAdaptor(RecyclerView.Adapter<RecyclerView.ViewHolder>... recycleAdaptor) {
        custom_recycle_update_table.setAdapter(recycleAdaptor[0]);
    }

    @Override
    public void loadAtEndList(IGoNextPage IGoNextPage) {
        custom_recycle_update_table.setListenEndScroll(() -> {
            //no need to done any work, data were loaded  before here in first time
        });
    }

    @Override
    protected void inflateButterKnife() {
        LayoutInflater.from(activity).inflate(R.layout.activity_update_tables, this, true);
        ButterKnife.bind(this);
    }

    @Override
    protected void setupToolbar() {

    }

    @Override
    public void showHideProgress(boolean showHide) {

    }

    @Override
    protected void workforOnBackPressed() {
        finish();
    }

    @Override
    protected void workforOnCreateOptionsMenu(Menu menu) {

    }

    @Override
    protected boolean workforOnOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    protected void workForPermissionResults(int requestCode, @NonNull int[] grantResults) {

    }

    @Override
    public void workForActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void clearAllViewClickListeners() {

    }

    void showCorruptDownloadLayout(IIOSDialogeListener iiosDialogeListener) {
        ToastMessage.showIosDialogTypeConfirmCancle(activity,
                getResources().getString(R.string.caution),
                getResources().getString(R.string.corrupt_download_caution),
                getResources().getString(R.string.ok),
                getResources().getString(R.string.cancel),
                false,
                iiosDialogeListener
                );
    }

}
