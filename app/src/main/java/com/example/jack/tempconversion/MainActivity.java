package com.example.jack.tempconversion;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    double value;
    int checkedFrom = 0;
    int checkedTo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView conFormula = (TextView) findViewById(R.id.conFormula);
        conFormula.setText("");

        EditText convertValue = (EditText) findViewById(R.id.convertValue);
        convertValue.addTextChangedListener(convertValueListener);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(buttonClickListener);

        RadioGroup fromGroup = (RadioGroup) findViewById(R.id.fromGroup);
        fromGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.Fahrenheit) {
                    checkedFrom = 1;
                } else if (checkedId == R.id.Celsius) {
                    checkedFrom = 2;
                } else if (checkedId == R.id.Kelvin) {
                    checkedFrom = 3;
                }
                if (checkedTo > 0) {
                    pickFormula(checkedFrom,checkedTo);
                }
            }
        });

        RadioGroup toGroup = (RadioGroup) findViewById(R.id.toGroup);
        toGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.toFahrenheit) {
                    checkedTo = 1;
                } else if (checkedId == R.id.toCelsius) {
                    checkedTo = 2;
                } else if (checkedId == R.id.toKelvin) {
                    checkedTo = 3;
                }
                if (checkedFrom > 0) {
                    pickFormula(checkedFrom,checkedTo);
                }
            }
        });

    }
    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (checkedFrom == 1) {
                double result = fahConvert();
                display(result);
            } else if (checkedFrom == 2) {
                double result = CelConvert();
                display(result);
            } else if (checkedFrom == 3) {
                double result = KevConvert();
                display(result);
            }
        }
    };

    private double fahConvert() {
        if (checkedTo == 2) {
            return (((value - 32)*(5))/9);
        } else if (checkedTo == 3) {
            return (((value + 459.67) * (5))/9);
        }else {
            return 5;
        }
    }
    private double CelConvert() {
        if (checkedTo == 1) {
            return ((value * (9/5))+32);
        } else if (checkedTo == 3) {
            return (value + 273.15);
        }else {
            return 0;
        }
    }
    private double KevConvert() {
        if (checkedTo == 1) {
            return ((value * (9/5))-459.67);
        } else if (checkedTo == 2) {
            return (value - 273.15);
        }else {
            return 0;
        }
    }
    private void display(double k) {
        TextView convertedView = (TextView) findViewById(R.id.convertedView);
        if (checkedFrom == 1) {
            if (checkedTo == 1) {
                convertedView.setText("");
            } else if (checkedTo == 2) {
                convertedView.setText(value + " Fahrenheit = " + k + " Celsius");
            } else if (checkedTo == 3) {
                convertedView.setText(value + " Fahrenheit = " + k + " Kelvin");
            }
        } else if (checkedFrom == 2) {
            if (checkedTo == 1) {
                convertedView.setText(value + " Celsius = " + k + " Fahrenheit");
            } else if (checkedTo == 2) {
                convertedView.setText("");
            } else if (checkedTo == 3) {
                convertedView.setText(value + " Celsius = " + k + " Kelvin");
            }
        } else if (checkedFrom == 3) {
            if (checkedTo == 1) {
                convertedView.setText(value + " Kelvin = " + k + " Fahrenheit");
            } else if (checkedTo == 2) {
                convertedView.setText(value + " Kelvin = " + k + " Celsius");
            } else if (checkedTo == 3) {
                convertedView.setText("");
            }
        }
    }
    private void pickFormula(int from,int to) {
        Context context = getApplicationContext();
        Toast notTheSame = Toast.makeText(context, "Pick Separate Choices", Toast.LENGTH_SHORT);
        TextView conFormula = (TextView) findViewById(R.id.conFormula);
        switch (from) {
            case 1:
                if (to == 1) {
                    notTheSame.show();
                    conFormula.setText("");
                } else if (to == 2) {
                    conFormula.setText(R.string.Fah_to_Cel);
                }else if (to == 3) {
                    conFormula.setText(R.string.Fah_to_Kev);
                }break;
            case 2:
                if (to == 1) {
                    conFormula.setText(R.string.Cel_to_Fah);
                } else if (to == 2) {
                    notTheSame.show();
                    conFormula.setText("");
                }else if (to == 3) {
                    conFormula.setText(R.string.Cel_to_Kev);
                }break;
            case 3:
                if (to == 1) {
                    conFormula.setText(R.string.Kev_to_Fah);
                } else if (to == 2) {
                    conFormula.setText(R.string.Kev_to_Cel);
                }else if (to == 3) {
                    notTheSame.show();
                    conFormula.setText("");
                }break;
        }
    }



    private TextWatcher convertValueListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try
            {
                Double value = Double.parseDouble(s.toString());
                updateValue(value);
            }
            catch (NumberFormatException e)
            {
                updateValue(0);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            try
            {
                Double value = Double.parseDouble(s.toString());
                updateValue(value);
            }
            catch (NumberFormatException e)
            {
                s.clear();
                Context context = getApplicationContext();
                Toast numberform = Toast.makeText(context, "Numbers Only", Toast.LENGTH_SHORT);
                numberform.show();
            }
        }
    };
    private void updateValue(double x) {
        value = x;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
