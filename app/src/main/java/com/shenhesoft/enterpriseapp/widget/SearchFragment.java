package com.shenhesoft.enterpriseapp.widget;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.utils.KeyBoardUtils;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends DialogFragment implements DialogInterface.OnKeyListener, CircularRevealAnim.AnimListener, View.OnClickListener {

    public static final String TAG = "SearchFragment";
    private ImageView ivSearchBack;
    private AutoCompleteTextView etSearchKeyword;
    private TextView tvConfirm;

    private View view;
    //动画
    private CircularRevealAnim mCircularRevealAnim;

    private SearchPupItenAdapter mSearchPupItenAdapter;

    private ArrayList<String> mStringArrayList = new ArrayList<>();

    public static SearchFragment newInstance() {
        Bundle bundle = new Bundle();
        SearchFragment searchFragment = new SearchFragment();
        searchFragment.setArguments(bundle);
        return searchFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogStyle);
    }

    @Override
    public void onStart() {
        super.onStart();
        initDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_search, container, false);

        init();//实例化

        return view;
    }

    private void init() {
        ivSearchBack = (ImageView) view.findViewById(R.id.iv_search_back);
        etSearchKeyword = (AutoCompleteTextView) view.findViewById(R.id.et_search_keyword);
        tvConfirm = (TextView) view.findViewById(R.id.tv_confirm);
        etSearchKeyword.setDropDownVerticalOffset(6);

        //实例化动画效果
        mCircularRevealAnim = new CircularRevealAnim();
        //监听动画
        mCircularRevealAnim.setAnimListener(this);
        mSearchPupItenAdapter=new SearchPupItenAdapter(getContext(), R.layout.item_auto_complete, mStringArrayList);
        etSearchKeyword.setAdapter(mSearchPupItenAdapter);
        etSearchKeyword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) v;
                if (hasFocus) {
                    autoCompleteTextView.showDropDown();
                }
            }
        });

        getDialog().setOnKeyListener(this);//键盘按键监听

        //监听编辑框文字改变
        etSearchKeyword.addTextChangedListener(new TextWatcherImpl());
        //监听点击
        ivSearchBack.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_search_back) {
            hideAnim();
        } else if (view.getId() == R.id.tv_confirm) {
            search();
        }
    }

    /**
     * 初始化SearchFragment
     */
    private void initDialog() {
        Window window = getDialog().getWindow();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = (int) (metrics.widthPixels * 0.98); //DialogSearch的宽
        window.setLayout(width, WindowManager.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.TOP);
        window.setWindowAnimations(R.style.DialogEmptyAnimation);//取消过渡动画 , 使DialogSearch的出现更加平滑
    }

    /**
     * 监听键盘按键
     */
    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            hideAnim();
        } else if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
            search();
        }
        return false;
    }


    /**
     * 搜索框动画隐藏完毕时调用
     */
    @Override
    public void onHideAnimationEnd() {
        etSearchKeyword.setText("");
        dismiss();
    }

    /**
     * 搜索框动画显示完毕时调用
     */
    @Override
    public void onShowAnimationEnd() {
        if (isVisible()) {
            KeyBoardUtils.openKeyboard(getContext(), etSearchKeyword);
        }
    }

    /**
     * 监听编辑框文字改变
     */
    private class TextWatcherImpl implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String keyword = editable.toString();
            if (!TextUtils.isEmpty(keyword.trim())) {
                mIOnTextWatcherListener.textChange(keyword);
            }
        }
    }


    private void hideAnim() {
        KeyBoardUtils.closeKeyboard(getContext(), etSearchKeyword);
        mCircularRevealAnim.hide(tvConfirm,view);
    }

    private void search() {
        String searchKey = etSearchKeyword.getText().toString();
        if (TextUtils.isEmpty(searchKey.trim())) {
            Toast.makeText(getContext(), "请输入关键字", Toast.LENGTH_SHORT).show();
        } else {
            hideAnim();
            iOnSearchClickListener.OnSearchClick(searchKey);
        }
    }

    public void addData(List<String> list){
        mStringArrayList.addAll(list);
        mSearchPupItenAdapter.notifyDataSetChanged();
    }

    public void setData(List<String> list) {
        mStringArrayList.clear();
        mStringArrayList.addAll(list);
        mSearchPupItenAdapter.notifyDataSetChanged();
    }

    public String getText(){
        return etSearchKeyword.getText().toString();
    }

    private IOnTextWatcherListener mIOnTextWatcherListener;

    public void setOnTextWatcherListener(IOnTextWatcherListener mIOnTextWatcherListener){
        this.mIOnTextWatcherListener = mIOnTextWatcherListener;
    }

    public interface IOnTextWatcherListener {
        void textChange(String s);
    }

    private IOnSearchClickListener iOnSearchClickListener;

    public void setOnSearchClickListener(IOnSearchClickListener iOnSearchClickListener) {
        this.iOnSearchClickListener = iOnSearchClickListener;
    }

    public interface IOnSearchClickListener{
        void OnSearchClick(String key);
    }
}
