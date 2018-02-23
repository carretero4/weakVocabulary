package pons.carretero.myvocabulary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import pons.carretero.myvocabulary.R;

public class pronounsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pronouns);

        TextView sp = (TextView) findViewById(R.id.sp);
        String spText = "<font color=#000000><b>She</b></font> likes to ride the bike.";
        sp.setText(Html.fromHtml(spText));

        TextView op = (TextView) findViewById(R.id.op);
        String opText = "My mother gave <font color=#000000><b>me</b></font> a bike.";
        op.setText(Html.fromHtml(opText));

        TextView pp = (TextView) findViewById(R.id.pp);
        String ppText = "This bike is <font color=#000000><b>yours</b></font>.";
        pp.setText(Html.fromHtml(ppText));

        TextView rp = (TextView) findViewById(R.id.rp);
        String rpText = "He can ride a bike by <font color=#000000><b>himself</b></font>.";
        rp.setText(Html.fromHtml(rpText));

        TextView pa = (TextView) findViewById(R.id.pa);
        String paText = "The car crashed with <font color=#000000><b>our</b></font> bikes.";
        pa.setText(Html.fromHtml(paText));

    }
}
