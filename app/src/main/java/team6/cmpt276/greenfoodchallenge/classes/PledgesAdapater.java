package team6.cmpt276.greenfoodchallenge.classes;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;
import de.codecrafters.tableview.TableDataAdapter;
import team6.cmpt276.greenfoodchallenge.R;


/**
 * A simple {@link TableDataAdapter} that allows to display 2D-String-Arrays in a {@link de.codecrafters.tableview.TableView}.
 *
 * @author ISchwarz
 */
public final class PledgesAdapater extends TableDataAdapter<String[]> {

    private static final String LOG_TAG = de.codecrafters.tableview.toolkit.SimpleTableDataAdapter.class.getName();

    private int paddingLeft = 20;
    private int paddingTop = 15;
    private int paddingRight = 20;
    private int paddingBottom = 15;
    private int textSize = 18;
    private int typeface = Typeface.NORMAL;
    private int textColor = 0x99000000;


    public PledgesAdapater(final Context context, final String[][] data) {
        super(context, data);
    }

    public PledgesAdapater(final Context context, final List<String[]> data) {
        super(context, data);
    }

    @Override
    public View getCellView(final int rowIndex, final int columnIndex, final ViewGroup parentView) {
        parentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,250));

        TextView textView = new TextView(getContext());
        textView.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        textView.setTypeface(textView.getTypeface(), typeface);
        textView.setTextSize(textSize);
        textView.setTextColor(textColor);
        textView.setSingleLine(false);
        textView.setEllipsize(TextUtils.TruncateAt.END);

        try {
            String textToShow = getItem(rowIndex)[columnIndex];
            if(textToShow.equals("cherry")) {
                ImageView imageView = new ImageView(getContext());
                imageView.setImageResource(R.drawable.cherry);
                imageView.setPadding(paddingLeft,paddingTop,paddingRight,paddingBottom);
                return imageView;
            } else if(textToShow.equals("peanut")) {
                ImageView imageView = new ImageView(getContext());
                imageView.setImageResource(R.drawable.peanut);
                imageView.setPadding(paddingLeft,paddingTop,paddingRight,paddingBottom);
                return imageView;
            } else if(textToShow.equals("bamboo")) {
                ImageView imageView = new ImageView(getContext());
                imageView.setImageResource(R.drawable.bamboo);
                imageView.setPadding(paddingLeft,paddingTop,paddingRight,paddingBottom);
                return imageView;
            } else if(textToShow.equals("earth")) {
                ImageView imageView = new ImageView(getContext());
                imageView.setImageResource(R.drawable.earth);
                imageView.setPadding(paddingLeft,paddingTop,paddingRight,paddingBottom);
                return imageView;
            } else if(textToShow.equals("heart")) {
                ImageView imageView = new ImageView(getContext());
                imageView.setImageResource(R.drawable.heart);
                imageView.setPadding(paddingLeft,paddingTop,paddingRight,paddingBottom);
                return imageView;
            } else if(textToShow.equals("tree")) {
                ImageView imageView = new ImageView(getContext());
                imageView.setImageResource(R.drawable.tree);
                imageView.setPadding(paddingLeft,paddingTop,paddingRight,paddingBottom);
                return imageView;
            }
            textView.setText(textToShow);
        } catch (final IndexOutOfBoundsException e) {
            Log.w(LOG_TAG, "No Sting given for row " + rowIndex + ", column " + columnIndex + ". "
                    + "Caught exception: " + e.toString());
            // Show no text
        }

        return textView;
    }

    /**
     * Sets the padding that will be used for all table cells.
     *
     * @param left   The padding on the left side.
     * @param top    The padding on the top side.
     * @param right  The padding on the right side.
     * @param bottom The padding on the bottom side.
     */
    public void setPaddings(final int left, final int top, final int right, final int bottom) {
        paddingLeft = left;
        paddingTop = top;
        paddingRight = right;
        paddingBottom = bottom;
    }

    /**
     * Sets the padding that will be used on the left side for all table cells.
     *
     * @param paddingLeft The padding on the left side.
     */
    public void setPaddingLeft(final int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    /**
     * Sets the padding that will be used on the top side for all table cells.
     *
     * @param paddingTop The padding on the top side.
     */
    public void setPaddingTop(final int paddingTop) {
        this.paddingTop = paddingTop;
    }

    /**
     * Sets the padding that will be used on the right side for all table cells.
     *
     * @param paddingRight The padding on the right side.
     */
    public void setPaddingRight(final int paddingRight) {
        this.paddingRight = paddingRight;
    }

    /**
     * Sets the padding that will be used on the bottom side for all table cells.
     *
     * @param paddingBottom The padding on the bottom side.
     */
    public void setPaddingBottom(final int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    /**
     * Sets the text size that will be used for all table cells.
     *
     * @param textSize The text size that shall be used.
     */
    public void setTextSize(final int textSize) {
        this.textSize = textSize;
    }

    /**
     * Sets the typeface that will be used for all table cells.
     *
     * @param typeface The type face that shall be used.
     */
    public void setTypeface(final int typeface) {
        this.typeface = typeface;
    }

    /**
     * Sets the text color that will be used for all table cells.
     *
     * @param textColor The text color that shall be used.
     */
    public void setTextColor(final int textColor) {
        this.textColor = textColor;
    }
}
