package com.xinye.core.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.xinye.core.R;


/**
 * 具有确定、取消按钮的Dialog
 *
 * @author wangheng
 */
public class ConfirmDialog extends AbsDialog implements View.OnClickListener{

    private TextView mMessageView;
    private TextView mLeftButton, mRightButton;
    private String mMessage, mLeftButtonText, mRightButtonText;

    public ConfirmDialog(Context context) {
        super(context);
        init(context);
    }

    protected ConfirmDialog(Context context,
                            boolean cancelable,
                            OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirm);

        mMessageView = (TextView) findViewById(R.id.tvDialogMessage);
        mLeftButton = (TextView) findViewById(R.id.tvDialogLeftButton);
        mRightButton = (TextView) findViewById(R.id.tvDialogRightButton);

        if(mMessage != null){
            mMessageView.setText(mMessage);
        }
        if(mLeftButtonText != null){
            mLeftButton.setText(mLeftButtonText);
        }
        if(mRightButtonText != null){
            mRightButton.setText(mRightButtonText);
        }
        mLeftButton.setOnClickListener(ConfirmDialog.this);
        mRightButton.setOnClickListener(ConfirmDialog.this);
    }


    public ConfirmDialog setMessage(String message){
        mMessage = message;
        if(mMessageView != null){
            mMessageView.setText(mMessage);
        }
        return ConfirmDialog.this;
    }

    public ConfirmDialog setLeftButtonText(String text){
        mLeftButtonText = text;
        if(mLeftButton != null){
            mLeftButton.setText(mLeftButtonText);
        }
        return ConfirmDialog.this;
    }

    public ConfirmDialog setRightButtonText(String text){
        mRightButtonText = text;
        if(mRightButton != null){
            mRightButton.setText(mRightButtonText);
        }
        return ConfirmDialog.this;
    }


    private void init(Context context) {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.tvDialogLeftButton){
            if(mOnButtonClickListener != null){
                mOnButtonClickListener.onLeftButtonClick(ConfirmDialog.this,v);
            }
            dismiss();
        }else if(id == R.id.tvDialogRightButton){
            if(mOnButtonClickListener != null){
                mOnButtonClickListener.onRightButtonClick(ConfirmDialog.this,v);
            }
            dismiss();
        }
    }

    private OnButtonClickListener mOnButtonClickListener;

    public ConfirmDialog setOnButtonClickListener(OnButtonClickListener listner){
        this.mOnButtonClickListener = listner;
        return ConfirmDialog.this;
    }

    public interface OnButtonClickListener {
        void onLeftButtonClick(ConfirmDialog dialog, View view);
        void onRightButtonClick(ConfirmDialog dialog, View view);
    }
}
