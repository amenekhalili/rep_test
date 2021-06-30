package ir.fararayaneh.erp.activities.attach_albom;

import androidx.appcompat.app.AppCompatActivity;
import ir.fararayaneh.erp.IBase.common_base.BaseActivity;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.activities.configs.ConfigsPresenter;
import ir.fararayaneh.erp.activities.configs.ConfigsView;

import android.os.Bundle;

public class AttachAlbumActivity extends BaseActivity<AttachAlbumPresenter, AttachAlbumView> {


    @Override
    protected void initView() {
        view = new AttachAlbumView(this);
    }

    @Override
    protected void initPresenter() {
        presenter = new AttachAlbumPresenter(weekView, this, true);
    }
}
