
package team6.cmpt276.greenfoodchallenge.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Comparator;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import team6.cmpt276.greenfoodchallenge.R;
import team6.cmpt276.greenfoodchallenge.classes.UserData;

// https://github.com/ISchwarz23/SortableTableView
// https://github.com/ISchwarz23/SortableTableView-ExampleApp/blob/master/app/src/main/java/com/sortabletableview/recyclerview/exampleapp/customdata/CustomDataExampleFragment.java
public class ViewAllPledges extends AppCompatActivity {
    private static final String[][] DATA_TO_SHOW = {    { "This", "is", "a", "test" },
                                                        { "and", "a", "second", "test" } };
    private static final String[] TABLE_HEADERS = { "This", "is", "a", "test" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_pledges);

        TableView tableView = (TableView) findViewById(R.id.tableView);

        TableColumnWeightModel columnModel = new TableColumnWeightModel(4);
        columnModel.setColumnWeight(1, 2);
        columnModel.setColumnWeight(2, 2);
        tableView.setColumnModel(columnModel);

        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, TABLE_HEADERS));
        tableView.setDataAdapter(new SimpleTableDataAdapter(this, DATA_TO_SHOW));
    }

    private static class UserDataComparator implements Comparator<UserData> {
        @Override
        public int compare(UserData car1, UserData car2) {
            //return car1.getProducer().getName().compareTo(car2.getProducer().getName());
            return 1;
        }
    }
}

