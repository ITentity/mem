package com.example.mem.listen;

import android.view.View;

public interface OnYunyingStepHandleItemClickListener {
    void addStep(View view, int position);
    void stepNameChange(View view, int position, String text);
    void stepDescChange(View view, int position);
}
