package com.example.thrishna.make24;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import android.support.design.widget.Snackbar;

import org.mariuszgromada.math.mxparser.Expression;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    int num1 = 0;
    int num2 = 0;
    int num3 = 0;
    int num4 = 0;
    int num_selected = 1;
    int attempts = 1;
    int succeeded = 0;
    int skipped = 0;
    String text_num1 = null;
    String text_num2 = null;
    String text_num3 = null;
    String text_num4 = null;
    String solution = null;
    Button one_button;
    Button two_button;
    Button three_button;
    Button four_button;
    Button add_button;
    Button sub_button;
    Button mul_button;
    Button div_button;
    Button left_paran_button;
    Button right_paran_button;
    Button backspace_button;
    Button done_button;
    TextView text_view;
    TextView text_attemt;
    TextView text_succeeded;
    TextView text_skipped;
    TextView text_time;
    boolean newPuzzle = false;
    private RelativeLayout relativeLayout;
    DrawerLayout mDrawerLayout;
    long tStart;
    MakeNumber make_num = new MakeNumber();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        setRandomNumbers(make_num);
        one_button = (Button)findViewById(R.id.button1);
        two_button = (Button)findViewById(R.id.button2);
        three_button = (Button)findViewById(R.id.button3);
        four_button = (Button)findViewById(R.id.button4);
        add_button = (Button)findViewById(R.id.buttonAdd);
        sub_button = (Button)findViewById(R.id.buttonSub);
        mul_button = (Button)findViewById(R.id.buttonMul);
        div_button = (Button)findViewById(R.id.buttonDiv);
        left_paran_button = (Button)findViewById(R.id.button_ParLef);
        right_paran_button = (Button)findViewById(R.id.button_ParRgt);
        backspace_button = (Button)findViewById(R.id.button_Clear);
        done_button = (Button)findViewById(R.id.button_Done);
        text_view = (TextView)findViewById(R.id.txtScreen);
        text_attemt = (TextView)findViewById(R.id.edit_text_Attempt);
        text_succeeded = (TextView)findViewById(R.id.edit_text_success);
        text_skipped = (TextView)findViewById(R.id.edit_text_skipped);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        text_time = (TextView)findViewById(R.id.edit_text_time);
        text_view.getSystemUiVisibility();
        done_button.setEnabled(false);
        text_attemt.setText((String.valueOf(attempts)));
        tStart = System.currentTimeMillis();
        final Handler handler =new Handler();
        final Runnable r = new Runnable() {
            public void run() {

                handler.postDelayed(this, 1000);
                if(newPuzzle == true)
                {
                    tStart = System.currentTimeMillis();
                    newPuzzle = false;
                }
                long tEnd = System.currentTimeMillis();
                long tDelta = tEnd - tStart;
                String elapsedTime = formatMillis(tDelta);
                text_time.setText(elapsedTime);
            }
        };
        handler.postDelayed(r, 0000);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        switch (menuItem.getItemId()) {
                            case R.id.show_me:
                                int b1_num = Integer.parseInt(one_button.getText().toString());
                                int b2_num = Integer.parseInt(two_button.getText().toString());
                                int b3_num = Integer.parseInt(three_button.getText().toString());
                                int b4_num = Integer.parseInt(four_button.getText().toString());
                                String sol = make_num.getSolution(b1_num,b2_num,b3_num,b4_num);
                                if(!Objects.equals(sol,null)) {
                                    customDialog("Solution is ",sol,"Ok");
                                }
                                else
                                {
                                    customDialog("","Sorry, there are actually no solutions","Ok");
                                }
                                return true;
                            case R.id.assign_numbers:
                                if(done_button.isEnabled()==true)
                                {

                                   if(num_selected==1)
                                   {
                                       skipped++;
                                       text_skipped = (TextView)findViewById(R.id.edit_text_skipped);
                                       text_skipped.setText(String.valueOf(skipped));
                                       attempts=1;
                                       text_attemt.setText(String.valueOf(attempts));
                                       text_view.setText("");
                                       done_button.setEnabled(false);
                                   }

                                }
                                show();
                                return true;

                        }

                        return true;
                    }
                });
        one_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                one_button.setEnabled(false);
                text_view.setText(text_view.getText()+text_num1);
            }
        });
        two_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                two_button.setEnabled(false);
                text_view.setText(text_view.getText()+text_num2);
            }
        });
        three_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                three_button.setEnabled(false);
                text_view.setText(text_view.getText()+text_num3);
            }
        });
        four_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                four_button.setEnabled(false);
                text_view = (TextView)findViewById(R.id.txtScreen);
                text_view.setText(text_view.getText()+text_num4);
            }
        });
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_view = (TextView)findViewById(R.id.txtScreen);
                text_view.setText(text_view.getText()+"+");
            }
        });
        sub_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_view = (TextView)findViewById(R.id.txtScreen);
                text_view.setText(text_view.getText()+"-");
            }
        });
        mul_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_view = (TextView)findViewById(R.id.txtScreen);
                text_view.setText(text_view.getText()+"*");
            }
        });
        div_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_view = (TextView)findViewById(R.id.txtScreen);
                text_view.setText(text_view.getText()+"/");
            }
        });
        left_paran_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_view = (TextView)findViewById(R.id.txtScreen);
                text_view.setText(text_view.getText()+"(");
            }
        });
        right_paran_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_view = (TextView)findViewById(R.id.txtScreen);
                text_view.setText(text_view.getText()+")");
            }
        });
        backspace_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_view = (TextView)findViewById(R.id.txtScreen);
                    String str = text_view.getText().toString();
                    if (!Objects.equals(str,"")) {
                        String del_char = str.substring(str.length() - 1);
                        enableButtonIfNumberDeleted(del_char);
                        if (str.length() > 1) {
                            str = str.substring(0, str.length() - 1);
                            text_view.setText(str);
                        } else if (str.length() <= 1) {
                            text_view.setText("");
                        }
                    }




            }
        });
        done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attempts++;
                text_attemt.setText((String.valueOf(attempts)));
                done_button.setEnabled(false);
                String str = text_view.getText().toString();
                Expression e = new Expression(str);
                double result = e.calculate();
                boolean isCorrect = make_num.bingo(result);
                relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout);

                if(!isCorrect)
                {
                    Snackbar.make(relativeLayout, "Incorrect. Please try again!", Snackbar.LENGTH_LONG).show();
                }
                else
                {
                    customDialog("You Won!","Binggo! "+str+"=24", "nextPuzzle");
                }

            }
        });

        text_view.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                if(done_button.isEnabled()==false)
                {
                    done_button.setEnabled(true);
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_skip:
                setRandomNumbers(make_num);
                text_view.setText("");
                attempts = 1;
                text_attemt.setText(String.valueOf(attempts));
                skipped++;
                text_skipped.setText(String.valueOf(skipped));
                return true;
            case R.id.action_clear:
                text_view.setText("");
                one_button.setEnabled(true);
                two_button.setEnabled(true);
                three_button.setEnabled(true);
                four_button.setEnabled(true);
                return true;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }


    /*Load new random numbers*/
    private void nextPuzzle(String puzzle){

        /*Intent myIntent = new Intent(MainActivity.this,
                MainActivity.class);
        startActivity(myIntent);*/
        newPuzzle = true;
        setRandomNumbers(make_num);
        text_view.setText("");
        attempts = 1;
        text_attemt.setText(String.valueOf(attempts));
        num_selected = 1;
        if(puzzle.equals("nextPuzzle")) {
            succeeded++;
            text_succeeded.setText(String.valueOf(succeeded));
        }
        else if(puzzle.equals("Ok"))
        {
            skipped++;
            text_skipped.setText(String.valueOf(skipped));
        }
        done_button.setEnabled(false);


    }

    /*Creates custom dialog to show user has won the puzzle*/
    public void customDialog(String title, String message, final String nextPuzzle)
    {
        final android.support.v7.app.AlertDialog.Builder builderSingle = new android.support.v7.app.AlertDialog.Builder(this);
        builderSingle.setTitle(title);
        if(!Objects.equals(message,null))
        {
            builderSingle.setMessage(message);
        }
        if(nextPuzzle.equals("nextPuzzle")) {
            builderSingle.setNegativeButton(
                    "Next Puzzle",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (nextPuzzle.equals("nextPuzzle")) {
                                nextPuzzle(nextPuzzle);
                            }
                        }
                    });

        }
        else if(nextPuzzle.equals("Ok"))
        {
            builderSingle.setNegativeButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (nextPuzzle.equals("Ok")) {
                                nextPuzzle(nextPuzzle);
                            }
                        }
                    });
        }


        builderSingle.show();
    }

    /*Enables number button corresponding to number deleted from text view when backspace button is pressed)*/
    private void enableButtonIfNumberDeleted(String del_char)
    {
        if(Objects.equals(del_char,text_num1))
        {
            one_button.setEnabled(true);
        }
        else if(Objects.equals(del_char,text_num2))
        {
            two_button.setEnabled(true);
        }
        else if(Objects.equals(del_char,text_num3))
        {
            three_button.setEnabled(true);
        }
        else if(Objects.equals(del_char,text_num4))
        {
            four_button.setEnabled(true);
        }


    }

    /*Generate Random Numbers util it has a solution and set the value in Button*/
    private void setRandomNumbers(MakeNumber make_num) {
        do {
            /*Random rand = new Random();
            num1 = rand.nextInt(9) + 1;
            num2 = rand.nextInt(9) + 1;
            num3 = rand.nextInt(9) + 1;
            num4 = rand.nextInt(9) + 1;*/
            generateUniqueNumbers();
            solution = make_num.getSolution(num1,num2,num3,num4);
        }while(Objects.equals(solution,null));
        text_num1 = String.valueOf(num1);
        text_num2 = String.valueOf(num2);
        text_num3 = String.valueOf(num3);
        text_num4 = String.valueOf(num4);
        one_button = (Button)findViewById(R.id.button1);
        one_button.setText(text_num1);
        one_button.setEnabled(true);
        two_button = (Button)findViewById(R.id.button2);
        two_button.setText(text_num2);
        two_button.setEnabled(true);
        three_button = (Button)findViewById(R.id.button3);
        three_button.setText(text_num3);
        three_button.setEnabled(true);
        four_button = (Button)findViewById(R.id.button4);
        four_button.setText(text_num4);
        four_button.setEnabled(true);
    }

    /*generate unique random numbers from 1-9*/
    private void generateUniqueNumbers() {
            ArrayList<Integer> list = new ArrayList<Integer>();
            for (int i=1; i<10; i++) {
                list.add(new Integer(i));
            }
            Collections.shuffle(list);
            num1 = list.get(0);
            num2 = list.get(1);
            num3 = list.get(2);
            num4 = list.get(3);

        }

    public void show()
    {

        final Dialog d = new Dialog(MainActivity.this);
        d.setContentView(R.layout.dialog);
        d.setTitle("e");
        Button Cancel = (Button) d.findViewById(R.id.Cancel_NumPick_button);
        Button Set = (Button) d.findViewById(R.id.Set_NumPick_button);
        TextView text_assign = (TextView)d.findViewById(R.id.text_view_numpick_message);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(9); // max value 100
        np.setMinValue(1);   // min value 0
        np.setWrapSelectorWheel(false);
        if(num_selected>=5)
        {
            text_assign.setText("You can select only maximum of 4 numbers for a Puzzle");
            Set.setEnabled(false);
        }
        else
        {
            Set.setEnabled(true);
        }

        Set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(num_selected==1)
                {
                    newPuzzle = true;
                    text_view.setText("");
                    one_button.setText(String.valueOf(np.getValue()));
                    d.dismiss();

                }
                if(num_selected==2) {
                    two_button.setText(String.valueOf(np.getValue()));
                    d.dismiss();
                }
                if(num_selected==3) {
                    three_button.setText(String.valueOf(np.getValue()));
                    d.dismiss();
                }
                if(num_selected==4) {
                    four_button.setText(String.valueOf(np.getValue()));
                    d.dismiss();
                }
                num_selected++;

            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });
        d.show();

    }
    static public String formatMillis(long val) {
        StringBuilder                       buf=new StringBuilder(20);
        String                              sgn="";

        if(val<0) { sgn="-"; val=Math.abs(val); }

        //append(buf,sgn,0,(val/3600000)); val%=3600000;
        val%=3600000;
        append(buf,sgn,2,(val/  60000)); val%=  60000;
        append(buf,":",2,(val/   1000)); val%=   1000;
        //append(buf,".",3,(val        ));
        return buf.toString();
    }

    /** Append a right-aligned and zero-padded numeric value to a `StringBuilder`. */
    static private void append(StringBuilder tgt, String pfx, int dgt, long val) {
        tgt.append(pfx);
        if(dgt>1) {
            int pad=(dgt-1);
            for(long xa=val; xa>9 && pad>0; xa/=10) { pad--;           }
            for(int  xa=0;   xa<pad;        xa++  ) { tgt.append('0'); }
        }
        tgt.append(val);
    }





}