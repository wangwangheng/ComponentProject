package debug;

import android.os.Bundle;
import com.xinye.architecture.mvp.BaseMVPActivity;
import com.xinye.architecture.mvp.IUI;

public class TestActivity extends BaseMVPActivity<TestPresenter> implements ITestUI {
    @Override
    protected void onCreateExecute(Bundle savedInstanceState) {

    }

    @Override
    protected TestPresenter createPresenter() {
        return new TestPresenter();
    }

    @Override
    protected IUI getUI() {
        return this;
    }
}
