package pons.carretero.myvocabulary;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import pons.carretero.myvocabulary.Adapters.IrregularAdapter;
import pons.carretero.myvocabulary.Objects.IrregularVerb;

public class IrregularVerbsActivity extends AppCompatActivity {
    ArrayList<IrregularVerb> irregularVerbs;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irregular_verbs);

        irregularVerbs = new ArrayList<IrregularVerb>();

        addIrregularVerbs();
        IrregularAdapter itemsAdapter = new IrregularAdapter(this, irregularVerbs);
        listView = (ListView) findViewById(R.id.listviewIrregular);
        registerForContextMenu(listView);
        listView.setAdapter(itemsAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem serchItem = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(serchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<IrregularVerb> tempList = new ArrayList<IrregularVerb>();

                for(IrregularVerb temp: irregularVerbs){
                    if(temp.getVerb().toLowerCase().contains(newText.toLowerCase())){
                        tempList.add(temp);
                    }else if(temp.getPastSimple().toLowerCase().contains(newText.toLowerCase())){
                        tempList.add(temp);
                    }else if(temp.getPastParticiple().toLowerCase().contains(newText.toLowerCase())){
                        tempList.add(temp);
                    }else if(temp.getEsp().toLowerCase().contains(newText.toLowerCase())){
                        //tempList.add(temp);
                    }
                }
                IrregularAdapter itemsAdapter = new IrregularAdapter(IrregularVerbsActivity.this, tempList);
                listView.setAdapter(itemsAdapter);
                return true;
            }
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    public void addIrregularVerbs(){
        irregularVerbs.add(new IrregularVerb("be","was/were","been","ser/estar"));
        irregularVerbs.add(new IrregularVerb("beat","beat","beaten","golpear"));
        irregularVerbs.add(new IrregularVerb("become","became","become","convertirse"));
        irregularVerbs.add(new IrregularVerb("begin","began","begun","empezar"));
        irregularVerbs.add(new IrregularVerb("bend","bent","bent","doblar"));
        irregularVerbs.add(new IrregularVerb("bite","bit","bitten","morder"));
        irregularVerbs.add(new IrregularVerb("blow","blew","blown","soplar"));
        irregularVerbs.add(new IrregularVerb("break","broke","broken","romper"));
        irregularVerbs.add(new IrregularVerb("bring","brougth","brougth","traer"));
        irregularVerbs.add(new IrregularVerb("build","built","built","construir"));
        irregularVerbs.add(new IrregularVerb("burn","burned/ burnet","burned/ burnet","quemar"));
        irregularVerbs.add(new IrregularVerb("burst","burst","burst","rebentar"));
        irregularVerbs.add(new IrregularVerb("buy","bought","boutght","comprar"));
        irregularVerbs.add(new IrregularVerb("beat","beat","beaten","golpear"));
        irregularVerbs.add(new IrregularVerb("can","could","be able","poder"));
        irregularVerbs.add(new IrregularVerb("catch","caugth","caugth","coger"));
        irregularVerbs.add(new IrregularVerb("choose","chose","chosen","escoger"));
        irregularVerbs.add(new IrregularVerb("come","came","come","venir"));
        irregularVerbs.add(new IrregularVerb("cost","cost","cost","costar"));
        irregularVerbs.add(new IrregularVerb("cut","cut","cut","cortar"));
        irregularVerbs.add(new IrregularVerb("dig","dug","dug","cavar"));
        irregularVerbs.add(new IrregularVerb("do","did","done","hacer"));
        irregularVerbs.add(new IrregularVerb("draw","drew","drawn","dibujar"));
        irregularVerbs.add(new IrregularVerb("dream","dreamed/ dreamet","dreamed/ dreamet","soñar"));
        irregularVerbs.add(new IrregularVerb("drink","drank","drunk","beber"));
        irregularVerbs.add(new IrregularVerb("drive","drove","driven","conducir"));
        irregularVerbs.add(new IrregularVerb("eat","ate","eaten","comer"));
        irregularVerbs.add(new IrregularVerb("fall","fell","fallen","caer"));
        irregularVerbs.add(new IrregularVerb("feed","fed","fed","alimentar"));
        irregularVerbs.add(new IrregularVerb("feel","felt","felt","sentir"));
        irregularVerbs.add(new IrregularVerb("fight","fought","fought","luchar"));
        irregularVerbs.add(new IrregularVerb("find","found","found","encontrar"));
        irregularVerbs.add(new IrregularVerb("fly","flew","flown","volar"));
        irregularVerbs.add(new IrregularVerb("forget","forgot","forgotten","olvidar"));
        irregularVerbs.add(new IrregularVerb("forgive","forgave","forgiven","olvidar"));
        irregularVerbs.add(new IrregularVerb("freeze","froze","frozen","congelar"));
        irregularVerbs.add(new IrregularVerb("get","got","got","obtener"));
        irregularVerbs.add(new IrregularVerb("give","gave","given","dar"));
        irregularVerbs.add(new IrregularVerb("cut","cut","cut","cortar"));
        irregularVerbs.add(new IrregularVerb("go","went","gona/ been","ir"));
        irregularVerbs.add(new IrregularVerb("grow","grew","grown","crecer"));
        irregularVerbs.add(new IrregularVerb("hang","hung","hanged/ hung","colgar"));
        irregularVerbs.add(new IrregularVerb("have","had","had","tener"));
        irregularVerbs.add(new IrregularVerb("hear","heard","heard","oír"));
        irregularVerbs.add(new IrregularVerb("hide","hid","hidden","ocultar"));
        irregularVerbs.add(new IrregularVerb("hit","hit","hit","golpear"));
        irregularVerbs.add(new IrregularVerb("hold","held","held","sostener"));
        irregularVerbs.add(new IrregularVerb("hurt","hurt","hurt","herir"));
        irregularVerbs.add(new IrregularVerb("keep","kept","kept","mantener/ guardar"));
        irregularVerbs.add(new IrregularVerb("kneel","knelt","knelt","arrodillarse"));
        irregularVerbs.add(new IrregularVerb("know","knwe","known","saber"));
        irregularVerbs.add(new IrregularVerb("lay","laid","laid","estender"));
        irregularVerbs.add(new IrregularVerb("lead","led","led","guiar"));
        irregularVerbs.add(new IrregularVerb("learn","learnd/ learnt","learnd/ learnt","aprender"));
        irregularVerbs.add(new IrregularVerb("leave","left","left","dejar"));
        irregularVerbs.add(new IrregularVerb("lend","lent","lent","prestar"));
        irregularVerbs.add(new IrregularVerb("let","let","let","dejar"));
        irregularVerbs.add(new IrregularVerb("lie","lay","lian","mentir"));
        irregularVerbs.add(new IrregularVerb("light","lit","lit","encender"));
        irregularVerbs.add(new IrregularVerb("lose","lost","lost","perder"));
        irregularVerbs.add(new IrregularVerb("make","made","made","hacer/ crear"));
        irregularVerbs.add(new IrregularVerb("mean","meant","meant","significar"));
        irregularVerbs.add(new IrregularVerb("meet","met","met","conocer"));
        irregularVerbs.add(new IrregularVerb("must","had to","had to","deber"));
        irregularVerbs.add(new IrregularVerb("pay","paid","paid","pagar"));
        irregularVerbs.add(new IrregularVerb("read","read","read","leer"));
        irregularVerbs.add(new IrregularVerb("ride","rode","ridden","monar"));
        irregularVerbs.add(new IrregularVerb("ring","rang","rung","sonar"));
        irregularVerbs.add(new IrregularVerb("rise","rose","risen","subir"));
        irregularVerbs.add(new IrregularVerb("run","ran","run","correr"));
        irregularVerbs.add(new IrregularVerb("say","said","said","decir"));
        irregularVerbs.add(new IrregularVerb("see","saw","seen","ver"));
        irregularVerbs.add(new IrregularVerb("sell","sold","sold","vender"));
        irregularVerbs.add(new IrregularVerb("send","sent","sent","enviar"));
        irregularVerbs.add(new IrregularVerb("set","set","set","establecer"));
        irregularVerbs.add(new IrregularVerb("shake","shook","shaken","sacudir"));
        irregularVerbs.add(new IrregularVerb("shine","shone","shone","brillar"));
        irregularVerbs.add(new IrregularVerb("shoot","shot","shot","disparar"));
        irregularVerbs.add(new IrregularVerb("show","shhowed","shown","mostrar"));
        irregularVerbs.add(new IrregularVerb("shut","shut","shut","cerrar"));
        irregularVerbs.add(new IrregularVerb("sing","sang","sung","cantar"));
        irregularVerbs.add(new IrregularVerb("sink","sank","sunk","hundir"));
        irregularVerbs.add(new IrregularVerb("sit","sat","sat","sentar"));
        irregularVerbs.add(new IrregularVerb("sleep","slept","slept","dormir"));
        irregularVerbs.add(new IrregularVerb("slide","slid","slid","deslicar"));
        irregularVerbs.add(new IrregularVerb("smell","smelled/ smellet","smelled/ smellet","oler"));
        irregularVerbs.add(new IrregularVerb("speak","spoke","spoken","hablar"));
        irregularVerbs.add(new IrregularVerb("spend","spent","spent","gastar/ pasar"));
        irregularVerbs.add(new IrregularVerb("spill","spilled/ spilt","spilled/ spilt","deletrear"));
        irregularVerbs.add(new IrregularVerb("spoil","spoiled/ spoilt","spoiled/ spoilt","arruinar"));
        irregularVerbs.add(new IrregularVerb("stand","stood","stood","estar"));
        irregularVerbs.add(new IrregularVerb("steal","stole","stolen","robar"));
        irregularVerbs.add(new IrregularVerb("stick","stuck","stuck","pegar"));
        irregularVerbs.add(new IrregularVerb("swim","swam","swum","nadar"));
        irregularVerbs.add(new IrregularVerb("take","took","taken","tomar"));
        irregularVerbs.add(new IrregularVerb("teach","taught","taught","enseñar"));
        irregularVerbs.add(new IrregularVerb("tear","tore","torn","rasgar"));
        irregularVerbs.add(new IrregularVerb("tell","told","told","contar"));
        irregularVerbs.add(new IrregularVerb("think","thought","thougth","pensar"));
        irregularVerbs.add(new IrregularVerb("throw","threw","thrown","tirar"));
        irregularVerbs.add(new IrregularVerb("understand","understood","understood","entender"));
        irregularVerbs.add(new IrregularVerb("wake","woke","woken","despertar"));
        irregularVerbs.add(new IrregularVerb("wear","wore","worn","vestir"));
        irregularVerbs.add(new IrregularVerb("win","won","won","ganar"));
        irregularVerbs.add(new IrregularVerb("write","wrote","written","cortar"));


    }
}
