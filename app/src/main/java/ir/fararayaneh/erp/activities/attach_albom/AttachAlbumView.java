package ir.fararayaneh.erp.activities.attach_albom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flipboard.bottomsheet.BottomSheetLayout;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.fararayaneh.erp.IBase.common_base.BaseActivity;
import ir.fararayaneh.erp.IBase.common_base.BaseViewWithRecycle;
import ir.fararayaneh.erp.IBase.common_base.IGoNextPage;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonRequestCodes;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.custom_views.custom_recycle_view.CustomRecycleView;

public class AttachAlbumView extends BaseViewWithRecycle {

    @BindView(R.id.global_toolbar)
    Toolbar global_toolbar;
    @BindView(R.id.img_global_toolbar)
    AppCompatImageView img_global_toolbar;
    @BindView(R.id.txt_global_toolbar)
    AppCompatTextView txt_global_toolbar;
    @BindView(R.id.custom_recycle_attach_album_act)
    CustomRecycleView custom_recycle_attach_album_act;
    @BindView(R.id.conslay_prog_attach_album_act)
    ConstraintLayout conslay_prog_attach_album_act;
    private IAttachAlbumListener iAttachAlbumListener;
    @BindView(R.id.global_bottom_sheet_attach_album)
    BottomSheetLayout global_bottom_sheet_attach_album;

    public void setiAttachAlbumListener(IAttachAlbumListener iAttachAlbumListener) {
        this.iAttachAlbumListener = iAttachAlbumListener;
    }

    public AttachAlbumView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AttachAlbumView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected AttachAlbumView(@NonNull BaseActivity activity) {
        super(activity);
    }

    @Override
    protected void setupRecyclers() {
        //because we no need to scroll listener, we can set grid layout manager
        custom_recycle_attach_album_act.setLayoutManager(new GridLayoutManager(activity, Commons.RECYCLER_SPAN_COUNT, RecyclerView.VERTICAL, false));
        custom_recycle_attach_album_act.setHasFixedSize(true);
    }

    @SafeVarargs
    @Override
    public final void setRecycleAdaptor(RecyclerView.Adapter<RecyclerView.ViewHolder>... recycleAdaptor) {
        custom_recycle_attach_album_act.setAdapter(recycleAdaptor[0]);
    }

    @Override
    public void loadAtEndList(IGoNextPage IGoNextPage) {
        custom_recycle_attach_album_act.setListenEndScroll(() -> {
            //no need to done any work, data were loaded  before here in first time
        });
    }

    @Override
    protected void inflateButterKnife() {
        LayoutInflater.from(activity).inflate(R.layout.activity_attach_album, this, true);
        ButterKnife.bind(this);
        setupToolbar();

    }

    @Override
    protected void setupToolbar() {
        activity.setSupportActionBar(global_toolbar);
        setupTextToolbar(getResources().getString(R.string.attach_albom));
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setupTextToolbar(String txt) {
        txt_global_toolbar.setText(txt);
    }

    @Override
    public void showHideProgress(boolean showHide) {
        conslay_prog_attach_album_act.setVisibility(showHide ? VISIBLE : GONE);
    }

    @Override
    protected void workforOnBackPressed() {
        //
    }

    @Override
    protected void workforOnCreateOptionsMenu(Menu menu) {
        activity.getMenuInflater().inflate(R.menu.attach_album_menu, menu);
    }

    @Override
    protected boolean workforOnOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                clickImageBackToolbar();
                return true;
            case R.id.add_album_attach_menu:
                clickImageAddToolbar();
                return true;
            case R.id.save_album_attach_menu:
                clickImageSaveToolbar();
                return true;
            default:
                return true;
        }
    }




    @Override
    protected void workForPermissionResults(int requestCode, @NonNull int[] grantResults) {

    }

    @Override
    public void workForActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_CANCELED) {
            showMessageNothingsSelected();
        } else {
            switch (requestCode) {
                case CommonRequestCodes.GET_URI_LIST_FROM_ATTACHMENT:
                    sendAttachmentToPresenter(data,true);
                    if(iAttachAlbumListener!=null){
                        iAttachAlbumListener.receivedNewAttachFromAttachProvider();
                    }
                    break;
            }
        }
    }

    @Override
    protected void clearAllViewClickListeners() {

    }

    //-------------------------------------------------------------------------
    private void clickImageBackToolbar() {
        if (iAttachAlbumListener != null) {
            iAttachAlbumListener.clickBackToolbar();
        }
    }

    private void clickImageAddToolbar() {
        if (iAttachAlbumListener != null) {
            iAttachAlbumListener.clickImageAddToolbar();
        }
    }

    private void clickImageSaveToolbar() {
        if (iAttachAlbumListener != null) {
            iAttachAlbumListener.clickImageSaveToolbar();
        }
    }

    void showBottomSheet(boolean camera, boolean voice, boolean file, boolean gallery, boolean location, boolean contact
                         ///,String b5IdRefGUID >>> deprecated
    ) {
        showAttachmentBottomSheet(global_bottom_sheet_attach_album, camera, voice, file, gallery, location, contact
                ///,b5IdRefGUID >>> deprecated
        );
    }


}
