package ir.fararayaneh.erp.data.data_providers.offline;

import ir.fararayaneh.erp.data.models.middle.IntentModel;

/**
 * guidForShowAsPrimitiveData : agar ma be form miravim va mikhahim data avalie dar an bebinim,
 * bayad guid an satr ra be activity befrestm
 * dar gheyr in sorat guid=Commons.NULL ast
 */
public class SetFormCodeToIntentModel {

    public static IntentModel setFormCode(String formCode,String guidForShowAsPrimitiveData){
        IntentModel intentModel=new IntentModel();
        intentModel.setSomeString2(formCode);
        intentModel.setSomeString(guidForShowAsPrimitiveData);
        return intentModel;
    }
}
