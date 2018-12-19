package base;

import bean.Goods;
import bean.News;


/**
 * Created by Administrator on 2018/12/18 0018.
 */

public interface MainView extends IView {
    void ShowSuccess(Goods goods);
    void ShowError(String error);


}
