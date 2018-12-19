package presenter;

import android.view.View;

import base.BasePresenter;
import base.MainView;
import bean.Goods;

import bean.News;
import model.MainModel;

/**
 * Created by Administrator on 2018/12/18 0018.
 */

public class MainPresenter extends BasePresenter<MainView> {
    private MainModel mainModel;

    public MainPresenter(MainView view) {
        super(view);
    }

    @Override
    protected void initModel() {
        mainModel = new MainModel();
    }

    public void showGoods(int page) {
        mainModel.showGoods(page, new MainModel.MainmodelCallback() {
            @Override
            public void getSuccess(Goods goods) {
                if(view!=null){
                    view.ShowSuccess(goods);
                }
            }

            @Override
            public void getFaid(String error) {
                if(view!=null){
                    view.ShowError(error);
                }
            }
        });

    }




}

