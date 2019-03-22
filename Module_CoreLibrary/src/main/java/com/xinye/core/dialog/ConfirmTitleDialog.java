package com.xinye.core.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.xinye.core.R;

/**
 * 具有确定、取消按钮并且具有标题和Message的Dialog
 *
 * @author wangheng
 */
public class ConfirmTitleDialog extends AbsDialog implements View.OnClickListener{

    private TextView mTitleView,mMessageView;
    private TextView mLeftButton, mRightButton;
    private String mTitle,mMessage, mLeftButtonText, mRightButtonText;

    public ConfirmTitleDialog(Context context) {
        super(context);
        init(context);
    }

    protected ConfirmTitleDialog(Context context,
                                 boolean cancelable,
                                 DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirm_title);

        mTitleView = (TextView) findViewById(R.id.tvDialogTitle);
        mMessageView = (TextView) findViewById(R.id.tvDialogMessage);
        mLeftButton = (TextView) findViewById(R.id.tvDialogLeftButton);
        mRightButton = (TextView) findViewById(R.id.tvDialogRightButton);

        if(mTitle != null){
            mTitleView.setText(mTitle);
        }
        if(mMessage != null){
            mMessageView.setText(mMessage);
        }
        if(mLeftButtonText != null){
            mLeftButton.setText(mLeftButtonText);
        }
        if(mRightButton != null){
            mRightButton.setText(mRightButtonText);
        }
        mLeftButton.setOnClickListener(ConfirmTitleDialog.this);
        mRightButton.setOnClickListener(ConfirmTitleDialog.this);
    }

    public ConfirmTitleDialog setMessage(String message){
        mMessage = message;
        if(mMessageView != null){
            mMessageView.setText(mMessage);
        }
        return ConfirmTitleDialog.this;
    }

    public ConfirmTitleDialog setTitle(String title){
        mTitle = title;
        if(mTitleView != null){
            mTitleView.setText(mTitle);
        }
        return ConfirmTitleDialog.this;
    }

    public ConfirmTitleDialog setLeftButtonText(String text){
        mLeftButtonText = text;
        if(mLeftButton != null){
            mLeftButton.setText(mLeftButtonText);
        }
        return ConfirmTitleDialog.this;
    }

    public ConfirmTitleDialog setRightButtonText(String text){
        mRightButtonText = text;
        if(mRightButton != null){
            mRightButton.setText(mRightButtonText);
        }
        return ConfirmTitleDialog.this;
    }


    private void init(Context context) {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.tvDialogLeftButton){
            if(mOnButtonClickListener != null){
                mOnButtonClickListener.onLeftButtonClick(ConfirmTitleDialog.this,v);
            }
            dismiss();
        }else if(id == R.id.tvDialogRightButton){
            if(mOnButtonClickListener != null){
                mOnButtonClickListener.onRightButtonClick(ConfirmTitleDialog.this,v);
            }
            dismiss();
        }
    }

    private OnButtonClickListener mOnButtonClickListener;

    public ConfirmTitleDialog setOnButtonClickListener(OnButtonClickListener listner){
        this.mOnButtonClickListener = listner;
        return ConfirmTitleDialog.this;
    }

    public interface OnButtonClickListener {
        void onLeftButtonClick(ConfirmTitleDialog dialog, View view);
        void onRightButtonClick(ConfirmTitleDialog dialog, View view);
    }
}
