package eliorcohen.com.multiplicationtable;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private Spinner spinnerSizeNum, spinnerFormat;
    private String[] sizeNum = {"3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
    private String[] myFormat = {"Decimal", "Binary", "Hex"};
    private Button btnApply;
    private ArrayAdapter<String> spinnerArrayAdapterSize, spinnerArrayAdapterFormat;
    private ArrayList[] listNums;
    private int mp_tableSize, size, row, column;
    private String formatMy, result;
    private View view, primaryNum;
    private TextView tv;
    private boolean clicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        initSpinner();
        checkMPTable();
        clickGridView();
    }

    private void initUI() {
        spinnerSizeNum = findViewById(R.id.spinnerSizeNum);
        spinnerFormat = findViewById(R.id.spinnerFormat);
        gridView = findViewById(R.id.gridView);
        btnApply = findViewById(R.id.btnApply);

        mp_tableSize = 10;
        listNums = new ArrayList[1];
    }

    private void initSpinner() {
        spinnerArrayAdapterSize = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sizeNum);
        spinnerArrayAdapterSize.setDropDownViewResource(android.R.layout.simple_list_item_1); // The drop down view
        spinnerSizeNum.setAdapter(spinnerArrayAdapterSize);

        spinnerArrayAdapterFormat = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myFormat);
        spinnerArrayAdapterFormat.setDropDownViewResource(android.R.layout.simple_list_item_1); // The drop down view
        spinnerFormat.setAdapter(spinnerArrayAdapterFormat);
    }

    private void checkMPTable() {
        clicked = false;
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listNums[0] = new ArrayList<Integer>();
                formatMy = spinnerFormat.getSelectedItem().toString();
                for (int i = 1; i <= mp_tableSize; i++) {
                    for (int j = 1; j <= mp_tableSize; j++) {
                        switch (formatMy) {
                            case "decimal":
                                listNums[0].add(i * j);
                                break;
                            case "binary":
                                listNums[0].add(Integer.toBinaryString(i * j));
                                break;
                            case "hex":
                                listNums[0].add(Integer.toHexString(i * j));
                                break;
                        }
                    }
                }

                size = Integer.parseInt(String.valueOf(spinnerSizeNum.getSelectedItem()));
                getGridViewAdapter(size);

                clicked = true;
            }
        });

        if (!clicked) {
            listNums[0] = new ArrayList<Integer>();
            for (int i = 1; i <= mp_tableSize; i++) {
                for (int j = 1; j <= mp_tableSize; j++) {
                    listNums[0].add(i * j);
                }
            }

            getGridViewAdapter(10);
        }
    }

    private void clickGridView() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                row = position / 10 + 1;
                column = position % 10 + 1;
                result = (String) ((TextView) v).getText();
                Toast.makeText(getApplicationContext(), column + "*" + row + "=" + result, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getGridViewAdapter(final int size) {
        gridView.setAdapter(new ArrayAdapter<Integer>(MainActivity.this, android.R.layout.simple_list_item_1, listNums[0]) {
            public View getView(int position, View convertView, ViewGroup parent) {
                view = super.getView(position, convertView, parent);

                getTextSize(size);
                getPrimaryNum(position, parent);

                return tv;
            }
        });
    }

    private void getTextSize(int size) {
        tv = (TextView) view;
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
    }

    private void getPrimaryNum(int position, ViewGroup parent) {
        for (int i = 2; i <= position / 2; ++i) {
            if (position % i == 0) {
                primaryNum = parent.getChildAt(i - 1);
                primaryNum.setBackgroundColor(Color.parseColor("#FF78CF69"));
                break;
            }
        }
    }

}
