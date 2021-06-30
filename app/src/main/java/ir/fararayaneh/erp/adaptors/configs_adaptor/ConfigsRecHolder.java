package ir.fararayaneh.erp.adaptors.configs_adaptor;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.fararayaneh.erp.IBase.Ibase_recycle_view_holder.BaseRecHolder;
import ir.fararayaneh.erp.R;

public class ConfigsRecHolder extends BaseRecHolder {

    private IClickRowConfigsRecHolderListener holderListener;
    @BindView(R.id.img_row_configs_list)
    AppCompatImageView img_row_configs_list;
    @BindView(R.id.txt_title_row_configs_list)
    AppCompatTextView txt_title_row_configs_list;
    @BindView(R.id.txt_description_row_configs_list)
    AppCompatTextView txt_description_row_configs_list;

    @OnClick({R.id.img_row_configs_list, R.id.txt_title_row_configs_list,R.id.txt_description_row_configs_list})
    public void clickConfigsRecHolder(View view) {
        if(holderListener!=null){
            holderListener.clickRow(getAdapterPosition());
        }
    }

    ConfigsRecHolder(View itemView,IClickRowConfigsRecHolderListener holderListener) {
        super(itemView);
        this.holderListener=holderListener;
    }

    @Override
    protected void inflateButterKnife(View itemView) {
        ButterKnife.bind(this, itemView);
    }

    void setRowData(int title, int description, int imageSrc) {
        txt_title_row_configs_list.setText(itemView.getResources().getString(title));
        txt_description_row_configs_list.setText(itemView.getResources().getString(description));
        img_row_configs_list.setImageResource(imageSrc);
    }
}
