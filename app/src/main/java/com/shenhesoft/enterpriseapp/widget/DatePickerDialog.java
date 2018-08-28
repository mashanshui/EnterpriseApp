package com.shenhesoft.enterpriseapp.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.lib.WheelView;
import com.shenhesoft.enterpriseapp.R;

import java.util.Calendar;

/**
 * @author 张继淮
 * @date 2017/12/8
 * @desc 封装TimePickerView
 */

public class DatePickerDialog {
    private TimePickerView pvTime;

    public DatePickerDialog(Context context, TimePickerView.OnTimeSelectListener listener) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();//起始日期
        Calendar endDate = Calendar.getInstance();//结束日期
        startDate.set(1900, 1, 1);
        endDate.set(2050, 12, 31);
        pvTime = new TimePickerView.Builder(context, listener)
                .setType(new boolean[]{true, true, true, false, false, false})//只显示年月日
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.layout_date_picker, v -> {
                    TextView tvCancel = (TextView) v.findViewById(R.id.tv_cancel);
                    TextView tvConfirm = (TextView) v.findViewById(R.id.tv_confirm);
                    tvConfirm.setOnClickListener((View v1) -> {
                        pvTime.returnData();
                        pvTime.dismiss();
                    });
                    tvCancel.setOnClickListener(v1 -> pvTime.dismiss());
                })
                .isCenterLabel(false)
                .isDialog(true)
                .setDividerColor(context.getResources().getColor(R.color.blue))
                .setDividerType(WheelView.DividerType.WRAP)
                .setLineSpacingMultiplier(2)
                .setTextColorCenter(context.getResources().getColor(R.color.text_gray1))
                .setTextColorOut(context.getResources().getColor(R.color.text_gray2))
                .setContentSize(16)
                .build();
    }

    public void show() {
        pvTime.show();
    }

}
