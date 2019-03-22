package com.xinye.core.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.xinye.core.R;


/**
 * 只有一个确定按钮的Dialog
 *
 * @author wangheng
 */
public class RemindDialog extends AbsDialog implements View.OnClickListener{

    private TextView mMessageView;
    private TextView mConfirmView;
    private String mMessage,mConfirm;

    public RemindDialog(Context context) {
        super(context);
        init(context);
    }

    protected RemindDialog(Context context,
                           boolean cancelable,
                           OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_remind);

        mMessageView = (TextView) findViewById(R.id.tvDialogMessage);
        mConfirmView = (TextView) findViewById(R.id.tvDialogLeftButton);

        if(mMessage != null){
            mMessageView.setText(mMessage);
        }
        if(mConfirm != null){
            mConfirmView.setText(mConfirm);
        }
        mConfirmView.setOnClickListener(RemindDialog.this);
    }


    public RemindDialog setMessage(String message){
        mMessage = message;
        if(mMessageView != null){
            mMessageView.setText(mMessage);
        }
        return RemindDialog.this;
    }

    public RemindDialog setConfirmText(String text){
        mConfirm = text;
        if(mConfirmView != null){
            mConfirmView.setText(mConfirm);
        }
        return RemindDialog.this;
    }


    private void init(Context context) {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.tvDialogLeftButton){
            if(mOnButtonClickListener != null){
                mOnButtonClickListener.onButtonClick(RemindDialog.this,v);
            }
            dismiss();
        }
    }

    private OnButtonClickListener mOnButtonClickListener;

    public RemindDialog setOnButtonClickListener(OnButtonClickListener listener){
        mOnButtonClickListener = listener;
        return RemindDialog.this;
    }

    public interface OnButtonClickListener{
        void onButtonClick(RemindDialog dialog, View view);
    }
}
