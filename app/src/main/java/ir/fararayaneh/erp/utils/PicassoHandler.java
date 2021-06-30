package ir.fararayaneh.erp.utils;




import androidx.appcompat.widget.AppCompatImageView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.io.File;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.utils.file_helper.FileSizeHelper;
import ir.fararayaneh.erp.utils.file_helper.FileToUriHelper;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

public class PicassoHandler {
    /**
     * this class only shows images , if can show or not, nothing else like download and so on...
     */
    public static void setByPicasso(Picasso picasso,
                                    String filePath,
                                    String imageUrl,
                                    AppCompatImageView imageView, boolean showAlphabetic,
                                    String alphabetic, boolean showErpIcon, boolean showAttachImage
                                    ) {

        if (filePath != null && imageUrl != null) {
            //here we have not file and url
            if (filePath.toLowerCase().equals(Commons.NULL) || filePath.toLowerCase().endsWith(Commons.NULL)) {
                if (showAlphabetic) {
                    picassoForChat(picasso, imageUrl, alphabetic, imageView);
                } else {
                    picasso.load(showErpIcon ? R.drawable.erp_5 : showAttachImage?R.drawable.ic_attach_2:R.drawable.ic_attach_empty)
                            .into(imageView);
                }
            } else {
                    //here we have file, and no need to alphabetic
                    File file = new File(filePath);
                    picasso.load(file)
                            .placeholder(R.drawable.ic_attach_waiting)
                            .into(imageView, new Callback() {
                                @Override
                                public void onSuccess() {
                                    //do nothing
                                }

                                @Override
                                public void onError(Exception e) {
                                    ThrowableLoggerHelper.logMyThrowable("PicassoHandler**setByPicasso**online method error "+e.getMessage() );
                                    picasso.load(imageUrl)
                                            .error(showErpIcon ? R.drawable.erp_5 : R.drawable.ic_attach_2)//if we have file and get error in show it, we show attach instead it
                                            .placeholder(R.drawable.ic_attach_waiting)
                                            .into(imageView);
                                }
                            });

            }
        }

        /*picasso.load(filePath)
                        //.networkPolicy(NetworkPolicy.OFFLINE)
                        .error(R.drawable.ic_attachment)
                        //.fit()
                        .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                //doNothings
                            }
                            @Override
                            public void onError(Exception e) {
                                ThrowableLoggerHelper.logMyThrowable(e.getMessage() + "PicassoHandler**setByPicasso");
                                picasso.load(imageUrl)
                                        .error(R.drawable.ic_attachment)
                                        //.fit()
                                        .into(imageView, new Callback() {
                                            @Override
                                            public void onSuccess() {
                                                //donothings
                                            }

                                            @Override
                                            public void onError(Exception e) {
                                                ThrowableLoggerHelper.logMyThrowable(e.getMessage() + "PicassoHandler**setByPicasso**online method");
                                            }
                                        });
                            }
                        });*/
    }

    private static void picassoForChat(Picasso picasso, String imageUrl, String alphabetic, AppCompatImageView imageView) {

        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .height((int)imageView.getContext().getResources().getDimension(R.dimen.toolbar_height))
                .width((int)imageView.getContext().getResources().getDimension(R.dimen.toolbar_height))
                .endConfig()
                .buildRound(alphabetic, ColorGenerator.DEFAULT.getRandomColor());

        picasso.load(imageUrl)
                .error(drawable)
                .placeholder(drawable)
                .into(imageView);
    }


}


