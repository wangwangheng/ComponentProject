package com.xinye.core.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.xinye.core.R;


/**
 * 只有一个确定按钮并且有Title的Dialog
 *
 * @author wangheng
 */
public class RemindTitleDialog extends AbsDialog implements View.OnClickListener{

    private TextView mTitleView;
    private TextView mMessageView;
    private TextView mConfirmView;
    private String mTitle,mMessage,mConfirm;

    public RemindTitleDialog(Context context) {
        super(context);
        init(context);
    }

    protected RemindTitleDialog(Context context,
                                boolean cancelable,
                                DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_remind_title);

        mTitleView = (TextView) findViewById(R.id.tvDialogTitle);
        mMessageView = (TextView) findViewById(R.id.tvDialogMessage);
        mConfirmView = (TextView) findViewById(R.id.tvDialogLeftButton);

        if(mTitle != null){
            mTitleView.setText(mTitle);
        }
        if(mMessage != null){
            mMessageView.setText(mMessage);
        }
        if(mConfirm != null){
            mConfirmView.setText(mConfirm);
        }
        mConfirmView.setOnClickListener(RemindTitleDialog.this);
    }


    public RemindTitleDialog setTitle(String title){
        mTitle = title;
        if(mTitleView != null){
            mTitleView.setText(mTitle);
        }
        return RemindTitleDialog.this;
    }
    public RemindTitleDialog setMessage(String message){
        mMessage = message;
        if(mMessageView != null){
            mMessageView.setText(mMessage);
        }
        return RemindTitleDialog.this;
    }

    public RemindTitleDialog setConfirmText(String text){
        mConfirm = text;
        if(mConfirmView != null){
            mConfirmView.setText(mConfirm);
        }
        return RemindTitleDialog.this;
    }


    private void init(Context context) {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.tvDialogLeftButton){
            if(mOnButtonClickListener != null){
                mOnButtonClickListener.onButtonClick(RemindTitleDialog.this,v);
            }
            dismiss();
        }
    }

    private OnButtonClickListener mOnButtonClickListener;

    public RemindTitleDialog setOnButtonClickListener(OnButtonClickListener listener){
        mOnButtonClickListener = listener;
        return RemindTitleDialog.this;
    }

    public interface OnButtonClickListener{
        void onButtonClick(RemindTitleDialog dialog, View view);
    }
}
