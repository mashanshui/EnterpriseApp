package com.shenhesoft.enterpriseapp.ui.activity.im;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseActivity;

import io.rong.imkit.RongContext;
import io.rong.imkit.fragment.SubConversationListFragment;
import io.rong.imkit.widget.adapter.SubConversationListAdapter;

/**
 * @author 张继淮
 * @date 2017/10/11
 * @description 聚合会话列表activity
 */

public class SubConversationListActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_sub_conversation;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        SubConversationListFragment fragment = new SubConversationListFragment();
        fragment.setAdapter(new SubConversationListAdapter(RongContext.getInstance()));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.rong_content, fragment);
        transaction.commit();

        Intent intent = getIntent();
        if (intent.getData() == null) {
            return;
        }
        //聚合会话参数
        String type = intent.getData().getQueryParameter("type");

        if (type == null) {
            return;
        }

        if ("group".equals(type)) {
            setTitle(R.string.de_actionbar_sub_group);
        } else if ("private".equals(type)) {
            setTitle(R.string.de_actionbar_sub_private);
        } else if ("discussion".equals(type)) {
            setTitle(R.string.de_actionbar_sub_discussion);
        } else if ("system".equals(type)) {
            setTitle(R.string.de_actionbar_sub_system);
        } else {
            setTitle(R.string.de_actionbar_sub_defult);
        }
    }
}
