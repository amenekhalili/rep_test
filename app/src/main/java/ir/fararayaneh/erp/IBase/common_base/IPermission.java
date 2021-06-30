package ir.fararayaneh.erp.IBase.common_base;

import androidx.annotation.NonNull;

public interface IPermission {
    void workForPermissionResults(int requestCode, @NonNull int[] grantResults);
}
