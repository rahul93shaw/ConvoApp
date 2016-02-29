package com.example.rahulshaw.convotemp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Timer;
import java.util.TimerTask;

public class Temperature extends AppCompatActivity {
    Spinner sp;
    Spinner spin_temp;
    Spinner spin_dist;
    Spinner spin_height;
    Spinner spin_weight;
    Spinner spin_currency;
    Spinner toSpin_temp_cel;
    Spinner toSpin_temp_fahr;
    Spinner toSpin_temp_kel;
    Spinner toSpin_dist_met;
    Spinner toSpin_dist_cm;
    Spinner toSpin_dist_km;
    Spinner toSpin_dist_mil;
    Spinner toSpin_Hf;
    Spinner toSpin_Hi;
    Spinner toSpin_Hy;
    Spinner toSpin_Hme;
    Spinner toSpin_Hmi;
    Spinner toSpin_Wg;
    Spinner toSpin_Wk;
    Spinner toSpin_Wp;
    Spinner toSpin_Wo;
    Spinner toSpin_Wt;
    Spinner toSpin_Wq;
    Spinner toSpin_currency;
    EditText etInput;
    TextView etOutput;
    Button btnConv;
    AlertDialog alertDialog;

    public int to;
    public int from;
    //public String value[];
    public Handler handler;

    Boolean isInternetPresent = false;

    // Connection detector class
    ConnectionDetector cd;

    //Middle spinner - for temperature quantity
    public void fromTemp() {
        spin_temp = (Spinner) findViewById(R.id.myspin);
        ArrayAdapter<String> adapter1;
        final String temp[] = {"Celsius", "Fahrenheit", "Kelvin"};
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, temp);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_temp.setAdapter(adapter1);
        //spin_temp.setSelection(0);
        spin_temp.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    toTempCel();

                } else if (position == 1) {
                    toTempFahr();

                } else {
                    toTempKel();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // Middle spinner - for distance quantities
    public void fromDist() {
        spin_dist = (Spinner) findViewById(R.id.myspin);
        ArrayAdapter<String> adapter2;
        final String distance[] = {"metre", "centimetre", "kilometre", "miles"};
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, distance);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_dist.setAdapter(adapter2);
        spin_dist.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    toDistMet();

                } else if (position == 1) {
                    toDistCm();

                } else if (position == 2) {
                    toDistKm();

                } else {
                    toDistMil();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // Middle Spinner - from one American measuring unit to another(Except miles)
    public void fromHeight() {
        spin_height = (Spinner) findViewById(R.id.myspin);
        ArrayAdapter<String> adapterFh;
        final String height[] = {"foot", "inch", "yards", "metre", "miles"};
        adapterFh = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, height);
        adapterFh.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_height.setAdapter(adapterFh);
        spin_height.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    toHeightF();

                } else if (position == 1) {
                    toHeightI();

                } else if (position == 2) {
                    toHeightY();

                } else if (position == 3) {
                    toHeightMet();
                } else {
                    toHeightMil();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void fromWeight() {
        spin_weight = (Spinner) findViewById(R.id.myspin);
        ArrayAdapter<String> adapterFw;
        final String weight[] = {"gram", "Kilogram", "Pounds", "Ounces", "Tonne", "Quintal" };
        adapterFw = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, weight);
        adapterFw.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_weight.setAdapter(adapterFw);
        spin_weight.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    toWeightG();

                } else if (position == 1) {
                    toWeightK();

                } else if (position == 2) {
                    toWeightP();

                } else if (position == 3) {
                    toWeightO();
                } else if (position == 4) {
                    toWeightT();
                } else {
                    toWeightQ();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void Currency() {
        spin_currency = (Spinner) findViewById(R.id.myspin);
        toSpin_currency = (Spinner) findViewById(R.id.myspin1);

        ArrayAdapter<CharSequence> adapterC = ArrayAdapter.createFromResource(this, R.array.name,
                android.R.layout.simple_spinner_item);
        adapterC.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spin_currency.setAdapter(adapterC);
        spin_currency.setSelection(142);
        toSpin_currency.setAdapter(adapterC);
        toSpin_currency.setSelection(59);
        spin_currency.setOnItemSelectedListener(new spinOne(1));
        toSpin_currency.setOnItemSelectedListener(new spinOne(2));
        cd = new ConnectionDetector(getApplicationContext());
        btnConv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isInternetPresent = cd.isConnectingToInternet();
                if (from == to) {
                    Toast.makeText(getApplicationContext(), "Invalid conversion", Toast.LENGTH_LONG).show();
                    etOutput.setText(null);
                } else {
                    if (isInternetPresent) {

                        new DownloadData().execute();
                    } else {

                        // Print message for No Internet Connection !
                        Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_LONG).show();
                    }
                }
           }
        });
    }

    public String getJson(String url)throws ClientProtocolException, IOException {

        StringBuilder build = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        InputStream content = entity.getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
        String con;
        while ((con = reader.readLine()) != null) {
            build.append(con);
        }
        return build.toString();
    }

    class DownloadData extends AsyncTask<Void, Integer, String> {
        ProgressDialog pd = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Temperature.this);
            pd.setTitle("Converting...");
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();

            long delayInMillis = 10000;
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    pd.dismiss();
                }
            }, delayInMillis);
        }

        @Override
        protected String doInBackground(Void... params) {
            String s;
            String exResult = "";
            final String val[];
            val  = getResources().getStringArray(R.array.value);
                try {
                    s = getJson("http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22" + val[from] + val[to] + "%22)&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=");
                    JSONObject jObj;
                    jObj = new JSONObject(s);
                        exResult = jObj.getJSONObject("query").getJSONObject("results").getJSONObject("rate").getString("Rate");
                        System.out.println(exResult);
                    }catch(JSONException e){
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }catch(ClientProtocolException e){
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }catch(IOException e){
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                return exResult;


        }
        @Override
        protected void onPostExecute(String exResult) {
            super.onPostExecute(exResult);
            pd.dismiss();

            System.out.println("theResult:" + exResult);
            if(etInput.getText().toString().trim().length() > 0 ){
                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid value", Toast.LENGTH_LONG).show();
                } else {
                    Double cur = Double.parseDouble(etInput.getText().toString());
                    etOutput.setText(String.valueOf(Double.parseDouble(exResult) * cur));
                }
            } else {
                Toast.makeText(getApplicationContext(), "Please enter a valid value", Toast.LENGTH_LONG).show();
                etOutput.setText(null);
            }
        }
        @Override
        protected void onCancelled() {
            pd.dismiss();
            super.onCancelled();
        }
    }
    private class spinOne implements OnItemSelectedListener {
        int ide;

        spinOne(int i) {
            ide = i;
        }

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int index, long id) {
            if (ide == 1)
                from = index;
            else if (ide == 2)
                to = index;

        }

        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }



        // 3rd spinner - to convert celsius into other quantities (*F and Kelvin)
    public void toTempCel() {
        toSpin_temp_cel = (Spinner) findViewById(R.id.myspin1);
        ArrayAdapter<String> adapter3;
        final String celTempTo[] = {"Fahrenheit", "Kelvin"};
        adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, celTempTo);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpin_temp_cel.setAdapter(adapter3);
        toSpin_temp_cel.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid temperature", Toast.LENGTH_LONG).show();
                                } else {
                                    float fahr = Float.parseFloat(etInput.getText().toString()); // fetch editText value
                                    etOutput.setText(String.valueOf(convertctof(fahr)) + " Fahrenheit"); // convert *c to *F
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the temperature", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }
                        }
                    });
                } else {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.")
                                        || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid temperature", Toast.LENGTH_LONG).show();
                                } else {
                                    float kel = Float.parseFloat(etInput.getText().toString()); // fetch editText value
                                    etOutput.setText(String.valueOf(convertctok(kel) + " Kelvin")); //convert *c to kelvin
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the temperature", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }
                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Temperature.this, "Please select something to convert", Toast.LENGTH_SHORT).show();

            }
        });

    }

    // 3rd Spinner - to convert *F into *C and Kelvin
    public void toTempFahr() {
        toSpin_temp_fahr = (Spinner) findViewById(R.id.myspin1);
        ArrayAdapter<String> adapterFahr;
        final String FahrTempTo[] = {"Celsius", "Kelvin"};
        adapterFahr = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, FahrTempTo);
        adapterFahr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpin_temp_fahr.setAdapter(adapterFahr);
        toSpin_temp_fahr.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") ||
                                        etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid temperature", Toast.LENGTH_LONG).show();
                                } else {
                                    float cel = Float.parseFloat(etInput.getText().toString()); // fetch editText value
                                    etOutput.setText(String.valueOf(convertftoc(cel)) + " Celsius"); // convert *F to *C
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the temperature", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }
                        }
                    });
                } else {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") ||
                                        etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid temperature", Toast.LENGTH_LONG).show();
                                } else {
                                    float kel1 = Float.parseFloat(etInput.getText().toString()); //fetch editText value
                                    etOutput.setText(String.valueOf(convertftok(kel1)) + " Kelvin"); //convert *F to Kelvin
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Temperature.this, "Please select anything", Toast.LENGTH_LONG).show();

            }
        });
    }

    //3rd spinner - to convert Kelvin to *F and *C
    public void toTempKel() {
        toSpin_temp_kel = (Spinner) findViewById(R.id.myspin1);
        ArrayAdapter<String> adapterKel;
        final String KelTempTo[] = {"Celsius", "Fahrenheit"};
        adapterKel = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, KelTempTo);
        adapterKel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpin_temp_kel.setAdapter(adapterKel);
        toSpin_temp_kel.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") ||
                                        etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid temperature", Toast.LENGTH_LONG).show();
                                } else {
                                    float cel1 = Float.parseFloat(etInput.getText().toString());
                                    etOutput.setText(String.valueOf(convertktoc(cel1)) + " Celsius");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }
                        }
                    });
                } else {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid temperature", Toast.LENGTH_LONG).show();
                                } else {
                                    float fahr1 = Float.parseFloat(etInput.getText().toString());
                                    etOutput.setText(String.valueOf(convertktof(fahr1)) + " Fahrenheit");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Temperature.this, "Please select anything", Toast.LENGTH_LONG).show();

            }
        });
    }

    //3rd spinner to convert metre into cm. ,km. ,mi.
    public void toDistMet() {
        toSpin_dist_met = (Spinner) findViewById(R.id.myspin1);
        ArrayAdapter<String> adapter4;
        final String metDistTo[] = {"centimetre", "Kilometre", "miles"};
        adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, metDistTo);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpin_dist_met.setAdapter(adapter4);
        toSpin_dist_met.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double cm = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.2f", convertmtocm(cm)) + " cm.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else if (position == 1) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double km = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.6f", convertmtokm(km)) + " km.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double mil = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.6f", convertmtomil(mil)) + " mi.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Temperature.this, "Please select anything", Toast.LENGTH_LONG).show();

            }
        });
    }

    //3rd spinner to convert cm. into m. ,km. ,mi.
    public void toDistCm() {
        toSpin_dist_cm = (Spinner) findViewById(R.id.myspin1);
        ArrayAdapter<String> adapterCm;
        final String cmDistTo[] = {"metre", "Kilometre", "miles"};
        adapterCm = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cmDistTo);
        adapterCm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpin_dist_cm.setAdapter(adapterCm);
        toSpin_dist_cm.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double m = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.6f", convertcmtom(m)) + " met.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else if (position == 1) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double km1 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.6f", convertcmtokm(km1)) + " km.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double mil1 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.6f", convertcmtomil(mil1)) + " mi.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Temperature.this, "Please select anything", Toast.LENGTH_LONG).show();

            }
        });
    }

    //3rd spinner to convert Km. into m. ,cm. ,km. ,mi.
    public void toDistKm() {
        toSpin_dist_km = (Spinner) findViewById(R.id.myspin1);
        ArrayAdapter<String> adapterKm;
        final String kmDistTo[] = {"metre", "centimetre", "miles"};
        adapterKm = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, kmDistTo);
        adapterKm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpin_dist_km.setAdapter(adapterKm);
        toSpin_dist_km.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double m1 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.2f", convertkmtom(m1)) + " met.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else if (position == 1) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double cm1 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.2f", convertkmtocm(cm1)) + " cm.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double mil2 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.6f", convertkmtomil(mil2)) + "mi.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Temperature.this, "Please select anything", Toast.LENGTH_LONG).show();

            }
        });
    }

    //3rd spinner to convert mi. into m., cm. ,km.
    public void toDistMil() {
        toSpin_dist_mil = (Spinner) findViewById(R.id.myspin1);
        ArrayAdapter<String> adapterMil;
        final String milDistTo[] = {"metre", "centimetre", "kilometre"};
        adapterMil = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, milDistTo);
        adapterMil.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpin_dist_mil.setAdapter(adapterMil);
        toSpin_dist_mil.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double m2 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.2f", convertmiltomet(m2)) + " met.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else if (position == 1) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double cm2 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.2f", convertmiltocm(cm2)) + " cm.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double km2 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.2f", convertmiltokm(km2)) + " km.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Temperature.this, "Please select anything", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void toHeightF() {
        toSpin_Hf = (Spinner) findViewById(R.id.myspin1);
        ArrayAdapter<String> adapterHf;
        final String fHTo[] = {"inch", "yards", "metre", "miles"};
        adapterHf = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fHTo);
        adapterHf.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpin_Hf.setAdapter(adapterHf);
        toSpin_Hf.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double in1 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.2f", convertftoin(in1)) + " in.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the height", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                } else if (position == 1) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double y1 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.2f", convertftoy(y1)) + " yd.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else if (position == 2) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double meh1 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.2f", convertftome(meh1)) + " m.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double mih1 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.5f", convertftomi(mih1)) + " mi.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }
                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Temperature.this, "Please select anything", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void toHeightI() {
        toSpin_Hi = (Spinner) findViewById(R.id.myspin1);
        ArrayAdapter<String> adapterHi;
        final String iHTo[] = {"foot", "yards", "metre", "miles"};
        adapterHi = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, iHTo);
        adapterHi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpin_Hi.setAdapter(adapterHi);
        toSpin_Hi.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double ft1 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.3f", convertitoft(ft1)) + " ft.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else if (position == 1) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double y2 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.3f", convertitoy(y2)) + " yd.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else if (position == 2) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double meh2 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.4f", convertitome(meh2)) + " m.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double mih2 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.6f", convertitomi(mih2)) + " mi.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }
                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Temperature.this, "Please select anything", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void toHeightY() {
        toSpin_Hy = (Spinner) findViewById(R.id.myspin1);
        ArrayAdapter<String> adapterHy;
        final String yHTo[] = {"foot", "inch", "metre", "miles"};
        adapterHy = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, yHTo);
        adapterHy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpin_Hy.setAdapter(adapterHy);
        toSpin_Hy.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double ft2 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.2f", convertytoft(ft2)) + " ft.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else if (position == 1) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double in2 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.2f", convertytoin(in2)) + " in.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else if (position == 2) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double meh3 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.3f", convertytome(meh3)) + " m.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double mih3 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.5f", convertytomi(mih3)) + " mi.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }
                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Temperature.this, "Please select anything", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void toHeightMet() {
        toSpin_Hme = (Spinner) findViewById(R.id.myspin1);
        ArrayAdapter<String> adapterHme;
        final String meHTo[] = {"foot", "inch", "yard", "miles"};
        adapterHme = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, meHTo);
        adapterHme.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpin_Hme.setAdapter(adapterHme);
        toSpin_Hme.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double ft3 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.2f", convertmetoft(ft3)) + " ft.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else if (position == 1) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double in3 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.2f", convertmetoin(in3)) + " in.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else if (position == 2) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double y3 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.2f", convertmetoy(y3)) + " m.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double mih4 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.6f", convertmtomil(mih4)) + " mi.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }
                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Temperature.this, "Please select anything", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void toHeightMil() {
        toSpin_Hmi = (Spinner) findViewById(R.id.myspin1);
        ArrayAdapter<String> adapterHmi;
        final String miHTo[] = {"foot", "inch", "yard", "metre"};
        adapterHmi = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, miHTo);
        adapterHmi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpin_Hmi.setAdapter(adapterHmi);
        toSpin_Hmi.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double ft4 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.2f", convertmitoft(ft4)) + " ft.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the height", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else if (position == 1) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double in4 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.2f", convertmitoin(in4)) + " in.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else if (position == 2) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double y4 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.2f", convertmitoy(y4)) + " yd.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double meh4 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format(".2f", convertmiltomet(meh4)) + " m.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }
                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Temperature.this, "Please select anything", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void toWeightG() {
        toSpin_Wg = (Spinner) findViewById(R.id.myspin1);
        ArrayAdapter<String> adapterWg;
        final String gWTo[] = { "Kilogram", "Pounds", "Ounces", "Tonne", "Quintal" };
        adapterWg = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gWTo);
        adapterWg.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpin_Wg.setAdapter(adapterWg);
        toSpin_Wg.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double kg1 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.3f", convertgtokg(kg1)) + " kg.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else if (position == 1) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double p1 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.3f", convertgtop(p1)) + " lb.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else if (position == 2) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double o1 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.4f", convertgtoo(o1)) + " oz.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else if(position == 3) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double t1 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.6f", convertgtot(t1)) + " ton.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }
                        }
                    });
                } else {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double q1 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.6f", convertgtoq(q1)) + " quintal");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }
                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Temperature.this, "Please select anything", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void toWeightK() {
        toSpin_Wk = (Spinner) findViewById(R.id.myspin1);
        ArrayAdapter<String> adapterWk;
        final String kWTo[] = { "gram", "Pounds", "Ounces", "Tonne", "Quintal" };
        adapterWk = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, kWTo);
        adapterWk.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpin_Wk.setAdapter(adapterWk);
        toSpin_Wk.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double g1 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.2f", convertktog(g1)) + " gm.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else if (position == 1) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double p2 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.3f", convertktop(p2)) + " lb.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else if (position == 2) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double o2 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.3f", convertktoo(o2)) + " oz.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else if(position == 3) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double t2 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.4f", convertktot(t2)) + " ton.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }
                        }
                    });
                } else {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double q2 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.3f", convertktoq(q2)) + " quintal");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }
                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Temperature.this, "Please select anything", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void toWeightP() {
        toSpin_Wp = (Spinner) findViewById(R.id.myspin1);
        ArrayAdapter<String> adapterWp;
        final String pWTo[] = { "gram", "Kilogram", "Ounces", "Tonne", "Quintal" };
        adapterWp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pWTo);
        adapterWp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpin_Wp.setAdapter(adapterWp);
        toSpin_Wp.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double g2 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.3f", convertptog(g2)) + " gm.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else if (position == 1) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double k2 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.3f", convertptok(k2)) + " kg.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else if (position == 2) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double o3 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.2f", convertptoo(o3)) + " oz.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else if(position == 3) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double t3 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.6f", convertptot(t3)) + " ton.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }
                        }
                    });
                } else {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double q3 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.6f", convertptoq(q3)) + " quintal");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }
                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Temperature.this, "Please select anything", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void toWeightO() {
        toSpin_Wo = (Spinner) findViewById(R.id.myspin1);
        ArrayAdapter<String> adapterWo;
        final String oWTo[] = { "gram", "Kilogram", "Pounds", "Tonne", "Quintal" };
        adapterWo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, oWTo);
        adapterWo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpin_Wo.setAdapter(adapterWo);
        toSpin_Wo.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double g3 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.3f", convertotog(g3)) + " gm.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else if (position == 1) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double k3 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.3f", convertotok(k3)) + " kg.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else if (position == 2) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double p3 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.4f", convertotop(p3)) + " lb.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else if(position == 3) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double t4 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.6f", convertotot(t4)) + " ton.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }
                        }
                    });
                } else {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double q4 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.6f", convertotoq(q4)) + " quintal");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }
                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Temperature.this, "Please select anything", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void toWeightT() {
        toSpin_Wt = (Spinner) findViewById(R.id.myspin1);
        ArrayAdapter<String> adapterWt;
        final String tWTo[] = { "gram", "Kilogram", "Pounds", "Ounces", "Quintal" };
        adapterWt = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tWTo);
        adapterWt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpin_Wt.setAdapter(adapterWt);
        toSpin_Wt.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double g4 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.2f", convertttog(g4)) + " gm.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else if (position == 1) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double kg4 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.2f", convertttok(kg4)) + " kg.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else if (position == 2) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double o4 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.2f", convertttoo(o4)) + " oz.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else if(position == 3) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double p4 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.2f", convertttop(p4)) + " lb.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }
                        }
                    });
                } else {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double q5 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.2f", convertttoq(q5)) + " quintal");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }
                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Temperature.this, "Please select anything", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void toWeightQ() {
        toSpin_Wq = (Spinner) findViewById(R.id.myspin1);
        ArrayAdapter<String> adapterWq;
        final String qWTo[] = { "gram", "Kilogram", "Pounds", "Ounces", "Tonne" };
        adapterWq = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, qWTo);
        adapterWq.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpin_Wq.setAdapter(adapterWq);
        toSpin_Wq.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double g5 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.2f", convertqtog(g5)) + " gm.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else if (position == 1) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double k5 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.2f", convertqtok(k5)) + " kg.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else if (position == 2) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double p5 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.3f", convertqtop(p5)) + " lb.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }

                        }
                    });
                } else if(position == 3) {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double o5 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.2f", convertqtoo(o5)) + " oz.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }
                        }
                    });
                } else {
                    btnConv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (etInput.getText().toString().trim().length() > 0) {
                                if (etInput.getText().toString().equals(".") || etInput.getText().toString().equals("-.") || etInput.getText().toString().equals("-")) {
                                    Toast.makeText(Temperature.this, "Please enter valid value", Toast.LENGTH_LONG).show();
                                } else {
                                    double t5 = Double.parseDouble(etInput.getText().toString());
                                    etOutput.setText(String.format("%.3f", convertqtot(t5)) + " ton.");
                                }
                            } else {
                                Toast.makeText(Temperature.this, "Please enter the measurement", Toast.LENGTH_LONG).show();
                                etOutput.setText(null);
                            }
                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Temperature.this, "Please select anything", Toast.LENGTH_LONG).show();

            }
        });
    }

    ArrayAdapter<String> adapter;
    String quantity[] = { "Temperature", "Distance", "Height", "Mass", "Currency" }; // Quantities at spinner one
    // int quant_img[] = { R.drawable.thermometer, R.drawable.measure, R.drawable.height }; Spinner item images. Note - working on this later.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        sp = (Spinner) findViewById(R.id.spin_quant);
        etInput = (EditText) findViewById(R.id.put_temp);
        etOutput = (TextView) findViewById(R.id.get_temp);
        btnConv = (Button) findViewById(R.id.btnConv);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, quantity);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    fromTemp(); // this is redirected to action of middle spinner for temperature
                } else if (position == 1) {
                    fromDist(); // this is redirected to action of middle spinner for distance
                } else if (position == 2) {
                    fromHeight();
                } else if(position == 3) {
                    fromWeight();
                } else {
                    etInput.setText(String.valueOf(1));
                    Currency();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "Please select a unit", Toast.LENGTH_LONG).show();

            }
        });

        alertDialog = new AlertDialog.Builder(
                Temperature.this).create();

        // Setting Dialog Title
        alertDialog.setTitle("About Us");
        alertDialog.setIcon(R.drawable.measure);

        // Setting Dialog Message
        alertDialog.setMessage("Thanks for downloading ConvoApp - instant converter," +
                "developed to convert different units of different quantities. This app is developed by Rahul," +
                "software engineer by profession.");

        alertDialog.setCancelable(true);



    }

    private float convertctof(float celcius) {
        return ((9 * celcius) / 5 + 32);
    }

    private float convertctok(float celkel) {
        return (celkel + 273);
    }

    private float convertftoc(float farcel) {
        return ((5 * (farcel - 32)) / 9);
    }

    private float convertftok(float farkel) {
        return (273 + convertftoc(farkel));
    }

    private float convertktoc(float kelcel) {
        return (kelcel - 273);
    }

    private float convertktof(float kelfahr) {
        return (((9 * (kelfahr - 273)) / 5 + 32));
    }

    private double convertmtocm(double mcm) {
        return (100 * mcm);
    }

    private double convertmtokm(double mkm) {
        return (mkm / 1000);
    }

    private double convertmtomil(double mmil) {
        return (mmil * (0.00062));
    }

    private double convertcmtom(double cmm) {
        return (cmm * 0.01);
    }

    private double convertcmtokm(double cmkm) {
        return (cmkm * 0.00001);
    }

    private double convertcmtomil(double cmmil) {
        return (cmmil * (0.000006));
    }

    private double convertkmtom(double kmm) {
        return (kmm * 1000);
    }

    private double convertkmtocm(double kmcm) {
        return (kmcm * 100000);
    }

    private double convertkmtomil(double kmmil) {
        return (kmmil * 0.621371);
    }

    private double convertmiltomet(double milm) {
        return (milm * 1609.34);
    }

    private double convertmiltocm(double milcm) {
        return (milcm * 160934);
    }

    private double convertmiltokm(double milkm) {
        return (milkm * 1.60934);
    }

    private double convertftoin(double fin) {
        return (12 * fin);
    }

    private double convertftoy(double fy) {
        return (0.33 * fy);
    }

    private double convertftome(double fme) {
        return (fme / 3.28);
    }

    private double convertftomi(double fmi) {
        return (fmi / 5280);
    }

    private double convertitoft(double ift) {
        return (ift / 12);
    }

    private double convertitoy(double iy) {
        return (iy / 36);
    }

    private double convertitome(double ime) {
        return (ime / 39.37);
    }

    private double convertitomi(double imi) {
        return (imi / 63360);
    }

    private double convertytoft(double yft) {
        return (yft * 3);
    }

    private double convertytoin(double yin) {
        return (yin * 36);
    }

    private double convertytome(double yme) {
        return (yme / 1.09);
    }

    private double convertytomi(double ymi) {
        return (ymi / 1760);
    }

    private double convertmetoft(double mft) {
        return (3.28 * mft);
    }

    private double convertmetoin(double mei) {
        return (mei * 39.37);
    }

    private double convertmetoy(double mey) {
        return (mey * 1.09);
    }

    private double convertmitoft(double mif) {
        return (mif * 5280);
    }

    private double convertmitoin(double mii) {
        return (mii * 63360);
    }

    private double convertmitoy(double miy) {
        return (miy * 1760);

    }
    private double convertgtokg(double gk) {
        return (gk/1000);
    }
    private double convertgtop(double gp) {
        return (gp/453.592);
    }
    private double convertgtoo(double go) {
        return (go/28.3495);
    }
    private double convertgtot(double gt) {
        return (gt/1000000);
    }
    private double convertgtoq(double gq) {
        return (gq/100000);
    }
    private double convertktop(double kp) {
        return (kp * 2.20462);
    }
    private double convertktoo(double ko) {
        return (ko * 35.274);
    }
    private double convertktot(double kt) {
        return (kt/1000);
    }
    private double convertktoq(double kq) {
        return (kq/100);
    }
    private double convertktog(double kg) {
        return (kg * 1000);
    }
    private double convertptog(double pg) {
        return (pg * 453.592);
    }
    private double convertptok(double pk) {
        return (pk * 0.453592);
    }
    private double convertptoo(double po) {
        return (po * 16);
    }
    private double convertptot(double pt) {
        return (pt/2204.62);
    }
    private double convertptoq(double pq) {
        return (pq / 220.462);
    }
    private double convertotog(double og){
        return (og * 28.3495);
    }
    private double convertotok(double ok) {
        return (ok / 35.274);
    }
    private double convertotop(double op) {
        return (op / 16);
    }
    private double convertotot(double ot) {
        return (ot/35274);
    }
    private double convertotoq(double oq) {
        return (oq / 3527.4);
    }
    private double convertttog(double tg) {
        return (tg * 1000000);
    }
    private double convertttok(double tk) {
        return (tk * 1000);
    }
    private double convertttoo(double to) {
        return (to * 35274);
    }
    private double convertttop(double tp) {
        return (tp * 2204.62);
    }
    private double convertttoq(double tq) {
        return (tq * 10);
    }
    private double convertqtok(double qk) {
        return (qk * 100);
    }
    private double convertqtog(double qg) {
        return (qg * 100000);
    }
    private double convertqtop(double qp) {
        return (qp * 220.462);
    }
    private double convertqtoo(double qo) {
        return (qo * 3527.4);
    }
    private double convertqtot(double qt) {
        return (qt/10);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {


            // Showing Alert Message
            alertDialog.show();

        }
        return super.onOptionsItemSelected(item);
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }



}
