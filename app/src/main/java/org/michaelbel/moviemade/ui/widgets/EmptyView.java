package org.michaelbel.moviemade.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.utils.DrawableUtil;
import org.michaelbel.moviemade.utils.EmptyViewMode;
import org.michaelbel.moviemade.utils.SpannableUtil;

import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

public class EmptyView extends FrameLayout {

    private AppCompatImageView emptyIcon;
    private AppCompatTextView emptyText;
    private AppCompatTextView valueText;

    public EmptyView(Context context) {
        super(context);
        initialize();
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    private void initialize() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_empty, this, true);
        emptyIcon = view.findViewById(R.id.empty_icon);
        emptyText = view.findViewById(R.id.empty_text);
        valueText = view.findViewById(R.id.value_text);
    }

    public EmptyView setMode(@EmptyViewMode int mode) {
        if (mode == EmptyViewMode.MODE_NO_CONNECTION) {
            setIcon(R.drawable.ic_offline);
            setText(R.string.no_connection);
        } else if (mode == EmptyViewMode.MODE_NO_MOVIES) {
            setIcon(R.drawable.ic_movieroll);
            setText(R.string.no_movies);
        } else if (mode == EmptyViewMode.MODE_NO_PEOPLE) {
            setIcon(R.drawable.ic_account_circle);
            setText(R.string.no_people);
        } else if (mode == EmptyViewMode.MODE_NO_REVIEWS) {
            setIcon(R.drawable.ic_review); // change
            setText(R.string.no_reviews);
        } else if (mode == EmptyViewMode.MODE_NO_RESULTS) {
            setIcon(R.drawable.ic_database_search);
            setText(R.string.no_results);
        } else if (mode == EmptyViewMode.MODE_NO_TRAILERS) {
            setIcon(R.drawable.ic_trailer);
            setText(R.string.no_trailers);
        }

        return this;
    }

    private void setIcon(int icon) {
        emptyIcon.setImageDrawable(DrawableUtil.INSTANCE.getIcon(getContext(), icon, ContextCompat.getColor(getContext(), R.color.iconActive)));
    }

    private void setText(@StringRes int textId) {
        emptyText.setText(getContext().getString(textId));
    }

    public void setValue(@StringRes int textId) {
        valueText.setText(SpannableUtil.replaceTags(getContext().getString(textId)));
    }
}