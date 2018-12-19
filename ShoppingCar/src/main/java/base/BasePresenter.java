package base;

/**
 * Created by Administrator on 2018/12/18 0018.
 */

public abstract class BasePresenter<V extends IView> {
    protected V view;
    public BasePresenter(V view){
        this.view=view;
        initModel();
    }

    protected abstract void initModel();
    void onDestroy(){
        view=null;
    }
}

