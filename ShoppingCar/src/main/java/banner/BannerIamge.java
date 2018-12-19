package banner;
import android.content.Context;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoader;


public class BannerIamge extends ImageLoader {
    public void displayImage(Context context, Object obj, ImageView imageView) {
        com.nostra13.universalimageloader.core.ImageLoader imageLoaderInstance = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
        imageLoaderInstance.displayImage((String) obj, imageView);
    }
}
