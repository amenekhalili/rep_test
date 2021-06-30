package ir.fararayaneh.erp.commons;

/**
 * for use in RootModel.class
 * هر بخش که به ارور میخورد باید اررور نامبر مناسب و سینک استیت مناسب را در جیسون پر کرده و برای کلاینت ارسال نماید
 */
public class CommonErrorNumber {

    public static final String NODE_EXECUTION_ERROR ="400"; //ارور در مراحل اجرایی syncState = syncError) node
    public static final String DB_EXECUTION_ERROR ="500";   //ارور در مراحل اجرایی دیتابیس (syncState = syncError)
    public static final String DB_INVALIDATION_ERROR ="404"; //(syncState = syncError)ارور دسترس نبودن دیتابیس
    public static final String DB_CONCURENCY_ERROR ="1000"; //ارور همزمانی دیتابیس    (syncState = accessDenied)

}

