package ir.fararayaneh.erp.utils.data_handler;



import io.reactivex.disposables.Disposable;
import ir.fararayaneh.erp.commons.CommonsDataProviderFactory;
import ir.fararayaneh.erp.data.data_providers.FactoryDataProvider;
import ir.fararayaneh.erp.data.data_providers.queries.purchasable_goods.PlusTempAmountPurchasableGoods;
import ir.fararayaneh.erp.data.data_providers.queries.salable_goods.PlusTempAmountSalableGoods;
import ir.fararayaneh.erp.data.models.GlobalModel;

/**
 * plus any value to (+ or -) to temp amount
 */
public class RefinePurchasableSalableTempAmountHandler {


    public static Disposable doRefineSalable(GlobalModel modelForRefine, IRefineTempAmountListener iRefineTempAmountListener) {
        PlusTempAmountSalableGoods plusTempAmountSalableGoods = (PlusTempAmountSalableGoods) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.PLUS_TEMP_AMOUNT_SALABLE_GOODS);
        assert plusTempAmountSalableGoods != null;
        return plusTempAmountSalableGoods
                .data(modelForRefine)
                .subscribe(iModels -> {
                    if (iRefineTempAmountListener != null) {
                        iRefineTempAmountListener.done();
                    }
                }, throwable -> {
                    if (iRefineTempAmountListener != null) {
                        iRefineTempAmountListener.error();
                    }
                });
    }


    public static Disposable doRefinePurchasable(GlobalModel modelForRefine, IRefineTempAmountListener iRefineTempAmountListener) {
        PlusTempAmountPurchasableGoods plusTempAmountPurchasableGoods = (PlusTempAmountPurchasableGoods) FactoryDataProvider.getDataProvider(CommonsDataProviderFactory.PLUS_TEMP_AMOUNT_PURCHASABLE_GOODS);
        assert plusTempAmountPurchasableGoods != null;
        return plusTempAmountPurchasableGoods
                .data(modelForRefine)
                .subscribe(iModels -> {
                    if (iRefineTempAmountListener != null) {
                        iRefineTempAmountListener.done();
                    }
                }, throwable -> {
                    if (iRefineTempAmountListener != null) {
                        iRefineTempAmountListener.error();
                    }
                });
    }

}
