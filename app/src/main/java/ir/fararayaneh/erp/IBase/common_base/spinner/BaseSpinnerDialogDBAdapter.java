package ir.fararayaneh.erp.IBase.common_base.spinner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import io.realm.OrderedRealmCollection;
import ir.fararayaneh.erp.IBase.common_base.BaseDBRealmListViewAdaptor;
import ir.fararayaneh.erp.R;

/**
 * with class BaseSpinnerDBDialogController , show a dialog spinner from realm dataBase
 * for make a dialog spinner we need controller and adaptor
 */

//todo remove leak by use butterKnife

public abstract class BaseSpinnerDialogDBAdapter extends BaseDBRealmListViewAdaptor {

    private static class SpinnerViewHolder {
        AppCompatTextView spinnerText;
    }

    public BaseSpinnerDialogDBAdapter(@Nullable OrderedRealmCollection[] data) {
        super(data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SpinnerViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_spinner_diloge_row, parent, false);
            viewHolder = new SpinnerViewHolder();
            viewHolder.spinnerText = convertView.findViewById(R.id.txt_row_spinner_dialog);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SpinnerViewHolder) convertView.getTag();
        }

        if (adapterData[0] != null) {
            showProperDataInSpinnerRows(position,viewHolder.spinnerText);
        }
        return convertView;
    }



    protected abstract void showProperDataInSpinnerRows(int position, AppCompatTextView spinnerRowText);

}
