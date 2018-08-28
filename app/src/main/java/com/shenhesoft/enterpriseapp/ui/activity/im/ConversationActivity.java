package com.shenhesoft.enterpriseapp.ui.activity.im;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.base.BaseTitleActivity;

import java.util.Iterator;
import java.util.Locale;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.userInfoCache.RongUserInfoManager;
import io.rong.imlib.MessageTag;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.TypingMessage.TypingStatus;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Discussion;
import io.rong.imlib.model.PublicServiceProfile;
import io.rong.imlib.model.UserInfo;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

/**
 * @author 张继淮
 * @date 2017/10/9
 * @description 聊天界面activity
 */

public class ConversationActivity extends BaseTitleActivity {

    /**
     * 对方id
     */
    private String mTargetId;
    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType;
    /**
     * title
     */
    private String title;
    /**
     * 是否在讨论组内，如果不在讨论组内，则进入不到讨论组设置页面
     */
    private boolean isFromPush = false;
    public static final int SET_TEXT_TYPING_TITLE = 1;
    public static final int SET_VOICE_TYPING_TITLE = 2;
    public static final int SET_TARGET_ID_TITLE = 0;
    private final String TextTypingTitle = "对方正在输入...";
    private final String VoiceTypingTitle = "对方正在讲话...";

    private Handler mHandler;

    @Override
    protected void initTitle() {
        title = getIntent().getData().getQueryParameter("title");
        if (title == null) {
            title = "";
        }
        setTitle(title);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Intent intent = getIntent();
//        mRightButton = getHeadRightButton();
        try {
            if (intent == null || intent.getData() == null) {
                return;
            }
            mTargetId = intent.getData().getQueryParameter("targetId");
            //10000 为 Demo Server 加好友的 id，若 targetId 为 10000，则为加好友消息，默认跳转到 NewFriendListActivity
            // Demo 逻辑
//        if (mTargetId != null && mTargetId.equals("10000")) {
//            startActivity(new Intent(ConversationActivity.this, NewFriendListActivity.class));
//            return;
//        }
            mConversationType = Conversation.ConversationType.valueOf(intent.getData()
                    .getLastPathSegment().toUpperCase(Locale.US));
            title = intent.getData().getQueryParameter("title");
        } catch (Exception e) {

        }
        setActionBarTitle(mConversationType, mTargetId);

//        if (mConversationType.equals(Conversation.ConversationType.GROUP)) {
//            mRightButton.setBackground(getResources().getDrawable(R.drawable.icon2_menu));
//        } else if (mConversationType.equals(Conversation.ConversationType.PRIVATE) | mConversationType.equals(Conversation.ConversationType.PUBLIC_SERVICE) | mConversationType.equals(Conversation.ConversationType.DISCUSSION)) {
//            mRightButton.setBackground(getResources().getDrawable(R.drawable.icon1_menu));
//        } else {
//            mRightButton.setVisibility(View.GONE);
//            mRightButton.setClickable(false);
//        }
//        mRightButton.setOnClickListener(this);

        isPushMessage(intent);

        mHandler = new Handler(msg -> {
            switch (msg.what) {
                case SET_TEXT_TYPING_TITLE:
                    setTitle(TextTypingTitle);
                    break;
                case SET_VOICE_TYPING_TITLE:
                    setTitle(VoiceTypingTitle);
                    break;
                case SET_TARGET_ID_TITLE:
                    setActionBarTitle(mConversationType, mTargetId);
                    break;
                default:
                    break;
            }
            return true;
        });

        RongIMClient.setTypingStatusListener((type, targetId, typingStatusSet) -> {
            //当输入状态的会话类型和targetID与当前会话一致时，才需要显示
            if (type.equals(mConversationType) && targetId.equals(mTargetId)) {
                int count = typingStatusSet.size();
                //count表示当前会话中正在输入的用户数量，目前只支持单聊，所以判断大于0就可以给予显示了
                if (count > 0) {
                    Iterator iterator = typingStatusSet.iterator();
                    TypingStatus status = (TypingStatus) iterator.next();
                    String objectName = status.getTypingContentType();

                    MessageTag textTag = TextMessage.class.getAnnotation(MessageTag.class);
                    MessageTag voiceTag = VoiceMessage.class.getAnnotation(MessageTag.class);
                    //匹配对方正在输入的是文本消息还是语音消息
                    if (objectName.equals(textTag.value())) {
                        mHandler.sendEmptyMessage(SET_TEXT_TYPING_TITLE);
                    } else if (objectName.equals(voiceTag.value())) {
                        mHandler.sendEmptyMessage(SET_VOICE_TYPING_TITLE);
                    }
                } else {//当前会话没有用户正在输入，标题栏仍显示原来标题
                    mHandler.sendEmptyMessage(SET_TARGET_ID_TITLE);
                }
            }
        });

//        SealAppContext.getInstance().pushActivity(this);

        //CallKit start 2
//        RongCallKit.setGroupMemberProvider(new RongCallKit.GroupMembersProvider() {
//            @Override
//            public ArrayList<String> getMemberList(String groupId, final RongCallKit.OnGroupMembersResult result) {
//                getGroupMembersForCall();
//                mCallMemberResult = result;
//                return null;
//            }
//        });
        //CallKit end 2
    }

    /**
     * 判断是否是 Push 消息，判断是否需要做 connect 操作
     */
    private void isPushMessage(Intent intent) {

        if (intent == null || intent.getData() == null) {
            return;
        }


        enterFragment(mConversationType, mTargetId);

    }

    private ConversationFragment fragment;

    /**
     * 加载会话页面 ConversationFragmentEx 继承自 ConversationFragment
     *
     * @param mConversationType 会话类型
     * @param mTargetId         会话 Id
     */
    private void enterFragment(Conversation.ConversationType mConversationType, String mTargetId) {

        fragment = new ConversationFragment();

        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
                .appendQueryParameter("targetId", mTargetId).build();

        fragment.setUri(uri);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //xxx 为你要加载的 id
        transaction.add(R.id.conversation, fragment);
        transaction.commitAllowingStateLoss();
    }

    /**
     * 设置会话页面 Title
     *
     * @param conversationType 会话类型
     * @param targetId         目标 Id
     */
    private void setActionBarTitle(Conversation.ConversationType conversationType, String targetId) {

        if (conversationType == null) {
            return;
        }
        if (conversationType.equals(Conversation.ConversationType.PRIVATE)) {
//            UserInfo userInfo = RongUserInfoManager.getInstance().getUserInfo(targetId);
//            mTitleBar.setTitle(userInfo.getName());
//            mTitleBar.setTitle(targetId);
        } else if (conversationType.equals(Conversation.ConversationType.GROUP)) {
//            mTitleBar.setTitle(title);
        }
//
//        if (conversationType.equals(Conversation.ConversationType.PRIVATE)) {
//            setPrivateActionBar(targetId);
//        } else if (conversationType.equals(Conversation.ConversationType.GROUP)) {
//            setGroupActionBar(targetId);
//        } else if (conversationType.equals(Conversation.ConversationType.DISCUSSION)) {
//            setDiscussionActionBar(targetId);
//        } else if (conversationType.equals(Conversation.ConversationType.CHATROOM)) {
//            setTitle(title);
//        } else if (conversationType.equals(Conversation.ConversationType.SYSTEM)) {
//            setTitle(R.string.de_actionbar_system);
//        } else if (conversationType.equals(Conversation.ConversationType.APP_PUBLIC_SERVICE)) {
//            setAppPublicServiceActionBar(targetId);
//        } else if (conversationType.equals(Conversation.ConversationType.PUBLIC_SERVICE)) {
//            setPublicServiceActionBar(targetId);
//        } else if (conversationType.equals(Conversation.ConversationType.CUSTOMER_SERVICE)) {
//            setTitle(R.string.main_customer);
//        } else {
//            setTitle(R.string.de_actionbar_sub_defult);
//        }
    }

    /**
     * 设置私聊界面 ActionBar
     */
    private void setPrivateActionBar(String targetId) {
        if (!TextUtils.isEmpty(title)) {
            if ("null".equals(title)) {
                if (!TextUtils.isEmpty(targetId)) {
                    UserInfo userInfo = RongUserInfoManager.getInstance().getUserInfo(targetId);
                    if (userInfo != null) {
                        setTitle(userInfo.getName());
                    }
                }
            } else {
                setTitle(title);
            }

        } else {
            setTitle(targetId);
        }
    }

    /**
     * 设置群聊界面 ActionBar
     *
     * @param targetId 会话 Id
     */
    private void setGroupActionBar(String targetId) {
        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        } else {
            setTitle(targetId);
        }
    }

    /**
     * 设置应用公众服务界面 ActionBar
     */
    private void setAppPublicServiceActionBar(String targetId) {
        if (targetId == null) {
            return;
        }

        RongIM.getInstance().getPublicServiceProfile(Conversation.PublicServiceType.APP_PUBLIC_SERVICE
                , targetId, new RongIMClient.ResultCallback<PublicServiceProfile>() {
                    @Override
                    public void onSuccess(PublicServiceProfile publicServiceProfile) {
                        setTitle(publicServiceProfile.getName());
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {

                    }
                });
    }

    /**
     * 设置公共服务号 ActionBar
     */
    private void setPublicServiceActionBar(String targetId) {

        if (targetId == null) {
            return;
        }


        RongIM.getInstance().getPublicServiceProfile(Conversation.PublicServiceType.PUBLIC_SERVICE
                , targetId, new RongIMClient.ResultCallback<PublicServiceProfile>() {
                    @Override
                    public void onSuccess(PublicServiceProfile publicServiceProfile) {
                        setTitle(publicServiceProfile.getName());
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {

                    }
                });
    }

    /**
     * 设置讨论组界面 ActionBar
     */
    private void setDiscussionActionBar(String targetId) {

        if (targetId != null) {

            RongIM.getInstance().getDiscussion(targetId
                    , new RongIMClient.ResultCallback<Discussion>() {
                        @Override
                        public void onSuccess(Discussion discussion) {
                            setTitle(discussion.getName());
                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode e) {
                            if (e.equals(RongIMClient.ErrorCode.NOT_IN_DISCUSSION)) {
                                setTitle("不在讨论组中");
                                supportInvalidateOptionsMenu();
                            }
                        }
                    });
        } else {
            setTitle("讨论组");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_conversation;
    }
}
