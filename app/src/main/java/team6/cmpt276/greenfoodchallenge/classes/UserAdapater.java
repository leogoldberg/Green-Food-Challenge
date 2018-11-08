package team6.cmpt276.greenfoodchallenge.classes;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;

public class UserAdapater extends TableDataAdapter<UserData> {

    Context curContext;
    public UserAdapater(Context context, List<UserData> data) {
        super(context, data);

        curContext = context;
    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        UserData car = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {
            case 0:
                renderedView = test("0 car");
                break;
            case 1:
                renderedView = test("1 CAR");
                break;
            case 2:
                renderedView = test("2 CAR");
                break;
            case 3:
                renderedView = test("3 CAR");
                break;
        }

        return renderedView;
    }

    private View test(String text) {
        TextView textView = new TextView(this.curContext);
        textView.setText(text);

        return textView;
    }
}