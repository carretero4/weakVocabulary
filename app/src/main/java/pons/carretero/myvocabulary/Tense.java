package pons.carretero.myvocabulary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import static pons.carretero.myvocabulary.R.id.verb;

public class Tense extends AppCompatActivity {

    String descTxt = "";
    String formTxt = "";
    String usesTxt = "";
    String expressionsTxt = "";
    String examplesTxt = "";
    String constructionTxt = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tense);

        String verbTense =  getIntent().getStringExtra("Tense");
        setTitle(verbTense);

        TextView description = (TextView) findViewById(R.id.description);
        TextView form = (TextView) findViewById(R.id.form);
        TextView uses = (TextView) findViewById(R.id.uses);
        TextView expressions = (TextView) findViewById(R.id.timeExpresions);
        TextView examples = (TextView) findViewById(R.id.examples);
        TextView construcion = (TextView) findViewById(R.id.construction);

        switch (verbTense){

            /////////////PRESENTS//////////////////////
            case "Present simple":
                constructionTxt = "<u>subject + base verb</u>";
                descTxt = "The <font color=#000000>present simple</font> tense is the base form of the verb: I <font color=#000000><b>live</b></font> in Cardiff. " +
                        "<br />But the third person (she/he/it) adds an <font color=#000000><b>-s</b></font> or <font color=#000000><b>-es</b></font>: She " +
                        "<font color=#000000><b>live<u>s</u></b></font> in Cardiff." +
                        "<br/>For the verb <font color=#000000><b>to be</b></font>, we do not use an auxiliary, even for questions and negatives";
                formTxt = "<font color=#000000>General</font>:" +
                        "<br/><font color=#000000>+</font> I <font color=#000000><b>go</b></font>. / He <font color=#000000><b>goes</b></font>." +
                        "<br/><font color=#000000>-&nbsp;</font> I <font color=#000000><b>don't go</b></font>. / He <font color=#000000><b>doesn't go</b></font>." +
                        "<br/><font color=#000000>?&nbsp;</font><font color=#000000><b> Do</b></font> you <font color=#000000><b>go</b></font>? / <font color=#000000><b>Does</b></font> he <font color=#000000><b>go</b></font>?" +
                        "<br/><br/><font color=#000000>To be</font>:" +
                        "<br/><font color=#000000>+</font> I<font color=#000000><b>'m </b></font>tall. / He <font color=#000000><b>is</b></font> tall. / We <font color=#000000><b>are</b></font> tall." +
                        "<br/><font color=#000000>-&nbsp;</font> I<font color=#000000><b>'m not</b></font> tall. / He <font color=#000000><b>isn't</b></font> tall. / We <font color=#000000><b>aren't</b></font> tall." +
                        "<br/><font color=#000000>?&nbsp;&nbsp;</font><font color=#000000><b>Am</b></font> I tall? / <font color=#000000><b>Is</b></font> she tall? / <font color=#000000><b> Are</b></font> you tall?";;

                usesTxt = "<font color=#000000>- <u>Routines</u></font>: She <font color=#000000><b>gets</b></font> up at 8 o'clock every day." +
                        "<br/><font color=#000000>- <u>Facts</u></font>: The adult human body <font color=#000000><b>contains</b></font> 206 bones." +
                        "<br/><font color=#000000>- <u>Shedule/time table</u></font>: The train <font color=#000000><b>leaves</b></font> at 19:30 this evening.";
                expressionsTxt = "<font color=#000000>Always, usually, every day, twice a year, etc.</font>";
                examplesTxt = "- I <font color=#000000><b>do</b></font> yoga twice a week." +
                        "<br/>- <font color=#000000><b>Does</b></font> the gate <font color=#000000><b>close</b></font> at 10:50?" +
                        "<br/>- They always <font color=#000000><b>buy</b></font> biscuits." +
                        "<br/>- Swallows <font color=#000000><b>don't fly</b></font> north for the winter." +
                        "<br/>- She<font color=#000000><b> isn't</b></font> still a teenager." +
                        "<br/>- <font color=#000000><b>Am</b></font> I young?";
                break;
            case "Present continuous (present)":
                constructionTxt = "<u>subject + am/is/are + present participle</u>";
                descTxt = "The <font color=#000000>present continuous</font> tense is formed from the present tense of the verb <font color=#000000><b>to be</b></font> and " +
                        "the present participle (<font color=#000000><b>-ing</b></font> form) of a verb.";
                formTxt = "<font color=#000000>+</font> I<font color=#000000><b>'m eating</b></font>. / He <font color=#000000><b>is eating</b></font>. / We <font color=#000000><b>are eating</b></font>." +
                        "<br/><font color=#000000>-&nbsp;</font> I<font color=#000000><b>'m not eating</b></font>. / He <font color=#000000><b>isn't eating</b></font>. / We <font color=#000000><b>aren't eating</b></font>." +
                        "<br/><font color=#000000>?&nbsp;&nbsp;</font><font color=#000000><b>Am</b></font> I <font color=#000000><b>eating</b></font>? / <font color=#000000><b>Is</b></font> she <font color=#000000><b>eating</b></font>? / <font color=#000000><b> Are</b></font> you <font color=#000000><b>eating</b></font>?";;
                usesTxt = "<font color=#000000>- <u>Action in progres now</u></font>: My sister <font color=#000000><b>is cooking</b></font> dinner now" +
                        "<br/><font color=#000000>- <u>Temporary actions</u></font>: They <font color=#000000><b>are working</b></font> very hard this week";
                expressionsTxt = "<font color=#000000>Now, at this moment, today, this month,next week, tomorrow...</font>";
                examplesTxt = "- They <font color=#000000><b>are running</b></font> together now." +
                        "<br/>- <font color=#000000><b>Is</b></font> the baby <font color=#000000><b>crying</b></font>?" +
                        "<br/>- We <font color=#000000><b>aren't participating</b></font> on the competition." +
                        "<br/>- These days most people <font color=#000000><b>are using</b></font> email instead of <font color=#000000><b>writing</b></font> letters." +
                        "<br/>- Michael is at university. He<font color=#000000><b>’s lying</b></font>.";
                break;
            case "Present perfect":
                constructionTxt = "<u>subject + have/has + past participle</u>";
                descTxt = "The <font color=#000000>present perfect</font> is formed from the present " +
                        "tense of the verb <font color=#000000><b>to have (have/has)</b></font> and the <font color=#000000><b>past participle</b></font> of a verb.";
                formTxt = "<font color=#000000>+</font> I<font color=#000000><b> have drunk</b></font>. / She <font color=#000000><b>has drunk</b></font>." +
                        "<br/><font color=#000000>-&nbsp;</font> I<font color=#000000><b> haven't drunk</b></font>. / She <font color=#000000><b>hasn't drunk</b></font>." +
                        "<br/><font color=#000000>?&nbsp;</font><font color=#000000><b> Have</b></font> you <font color=#000000><b>drunk</b></font>? / <font color=#000000><b>Has</b></font> she <font color=#000000><b>drunk</b></font>?";;
                usesTxt = "<font color=#000000>- <u>Action that happened in the past, but we don't know exactly when it append</u></font>: " +
                        "<font color=#000000><b>Have</b></font> you ever <font color=#000000><b>seen</b></font> a ghost?" +
                        "<br/><font color=#000000>- <u>Action whitch happened in the past, and have a result now</u></font>: " +
                        "I can’t get in the house. I<font color=#000000><b>’ve lost</b></font> my keys." +
                        "<br/><font color=#000000>- <u>Action which started in the past and have just finished o continue now</u></font>: " +
                        "We <font color=#000000><b>have lived</b></font> in Cardiff since 2015.";
                expressionsTxt = "<font color=#000000>Ever, just, how long?, for, since, yet, already, so far...</font>";
                examplesTxt = "- <font color=#000000><b>Have</b></font> you <font color=#000000><b>finished</b></font> your homework yet?" +
                        "<br/>- She <font color=#000000><b>hasn't gonet</b></font> to Paris." +
                        "<br/>- <font color=#000000><b>Havet</b></font> you ever <font color=#000000><b>mett</b></font> George?" +
                        "<br/>- I <font color=#000000><b>haven't playedt</b></font> the guitar." +
                        "<br/>- He <font color=#000000><b>has writtent</b></font> three books and he is working on another one.";
                break;
            case "Present perfect continuous":
                constructionTxt = "<u>subject + have/has + been + past participle</u>";
                descTxt = "The <font color=#000000>present perfect continuous</font> is formed with <font color=#000000><b>have/has been</b></font> and " +
                        "the present participle (<font color=#000000><b>-ing</b></font> form) of a verb.";
                formTxt = "<font color=#000000>+</font> I<font color=#000000><b> have been drinking</b></font>. / She <font color=#000000><b>has been drinking</b></font>." +
                        "<br/><font color=#000000>-&nbsp;</font> I<font color=#000000><b> haven't been drinking</b></font>. / She <font color=#000000><b>hasn't been drinking</b></font>." +
                        "<br/><font color=#000000>?&nbsp;</font><font color=#000000><b> Have</b></font> you <font color=#000000><b>been drinking</b></font>? / <font color=#000000><b>Has</b></font> she <font color=#000000><b>been drinking</b></font>?";;
                usesTxt = "<font color=#000000>- <u>Actions and situations which started in the past and are still going on</u></font>: " +
                        "It<font color=#000000><b>'s been raining</b></font> for the last three days." +
                        "<br/><font color=#000000>- <u>Actions and situations that heve just stopped</u></font>: " +
                        "We<font color=#000000><b>’ve been planning</b></font> our vacation for over a month." +
                        "<br/><font color=#000000>- <u>Repeted actions</u></font>: " +
                        "He <font color=#000000><b>has been saying</b></font> the winter is coming the whole summer.";
                expressionsTxt = "<font color=#000000>All day, all week, recently...</font>";
                examplesTxt = "- <font color=#000000><b>Have</b></font> you <font color=#000000><b>been waiting</b></font> long?" +
                        "<br/>- <font color=#000000><b>Have</b></font> you <font color=#000000><b>been feeling</b></font> ok lately?" +
                        "<br/>- I’ve been working too much." +
                        "<br/>- Don’t worry, I <font color=#000000><b>haven’t been waiting</b></font> long." +
                        "<br/>- They <font color=#000000><b>have been talking</b></font> for three hours.";
                break;
            ///////////////////////////PASTS/////////////////////////////
            case "Past simple":
                constructionTxt = "<u>subject + past form</u>";
                descTxt = "The <font color=#000000>past simple</font> tense is the the past form for the verb. " +
                        "With most verbs the past tense is formed by adding <font color=#000000><b>-ed</b></font> like <font color=#000000><b>play<u>ed</u></b></font> or <font color=#000000><b>jump<u>ed</u></b></font>." +
                        "<br/> But there are a lot of irregular past tenses in English, for example: <font color=#000000><b>sang</b></font> or <font color=#000000><b>was</b></font>";
                formTxt = "<font color=#000000>+</font> I <font color=#000000><b>drank</b></font>." +
                        "<br/><font color=#000000>-&nbsp;</font> I <font color=#000000><b>didn't drink</b></font>." +
                        "<br/><font color=#000000>?&nbsp;</font><font color=#000000><b> Did</b></font> you <font color=#000000><b>drink</b></font>?";

                usesTxt = "<font color=#000000>- <u>Completed actions in the past</u></font>: She ran the race last weekend.";
                expressionsTxt = "<font color=#000000>Yesterday, last year, ago, etc.</font>";
                examplesTxt = "- I <font color=#000000><b>met</b></font> him three years ago." +
                        "<br/>- <font color=#000000><b>Did</b></font> they <font color=#000000><b>visit</b></font> China last year?" +
                        "<br/>- The cat <font color=#000000><b>didn't enjoy</b></font> its bath." +
                        "<br/>- He <font color=#000000><b>had</b></font> dinner and then <font color=#000000><b>watched</b></font> TV." +
                        "<br/>- She<font color=#000000><b> did</b></font> his homework yesterday." +
                        "<br/>- Where<font color=#000000><b> did</b></font> James meet his wife?";
                break;
            case "Past continuous":
                constructionTxt = "<u>subject + was/were + present participle</u>";
                descTxt = "The <font color=#000000>past continuous</font> tense is formed from the past tense of the verb <font color=#000000><b>to be</b></font> and " +
                        "the present participle (<font color=#000000><b>-ing</b></font> form) of a verb.";
                formTxt = "<font color=#000000>+</font> I<font color=#000000><b> was drinking</b></font>. / You <font color=#000000><b>were drinking</b></font>." +
                        "<br/><font color=#000000>-&nbsp;</font> I<font color=#000000><b> wasn't drinking</b></font>. / You <font color=#000000><b>weren't drinking</b></font>." +
                        "<br/><font color=#000000>?&nbsp;&nbsp;</font><font color=#000000><b>Was</b></font> I <font color=#000000><b>drinking</b></font>? / <font color=#000000><b> Were</b></font> you <font color=#000000><b>drinking</b></font>?";;
                usesTxt = "<font color=#000000>- <u>Action in progress at a specific time in the past</u></font>: Yesterday at 3 o'clock I <font color=#000000><b>was having</b></font> a nap." +
                        "<br/><font color=#000000>- <u>Incomplete actions taking place simultaneously</u></font>: While she <font color=#000000><b>was reading</b></font>, her father <font color=#000000><b>was listening</b></font> to the radio." +
                        "<br/><font color=#000000>- <u>Action interrupted by a completed action in the past</u></font>: I <font color=#000000><b>was walking</b></font> back home when I saw the accident.";
                expressionsTxt = "<font color=#000000>While, as, times (3 o'clock)...</font>";
                examplesTxt = "- The children <font color=#000000><b>were growing</b></font> up quickly." +
                        "<br/>- They <font color=#000000><b>were meeting</b></font> secretly after school." +
                        "<br/>- We <font color=#000000><b>weren't playing</b></font> football at 5pm yesterday" +
                        "<br/>- What <font color=#000000><b>was</b></font> she <font color=#000000><b>doing</b></font> when he arrived?" +
                        "<br/>- My head <font color=#000000><b>was aching</b></font>.";
                break;
            case "Past perfect":
                constructionTxt = "<u>subject + had + past participle</u>";
                descTxt = "The <font color=#000000>past perfect</font> is formed from the past " +
                        "tense of the verb <font color=#000000><b>to have (had)</b></font> and the <font color=#000000><b>past participle</b></font> of a verb.";
                formTxt = "<font color=#000000>+</font> I<font color=#000000><b> had drunk</b></font>." +
                        "<br/><font color=#000000>-&nbsp;</font> I<font color=#000000><b> hadn't drunk</b></font>." +
                        "<br/><font color=#000000>?&nbsp;</font><font color=#000000><b> Had</b></font> you <font color=#000000><b>drunk</b></font>?";;
                usesTxt = "<font color=#000000>- <u>Action which happend before another action in the past</u></font>: " +
                        "I<font color=#000000><b>had finished</b></font> my homework by the time he phoned me.";
                expressionsTxt = "<font color=#000000>By the time + past simple, past perfect." +
                        "<br/>Before + past simple, past perfect." +
                        "<br/>After + past perfect, past simple.</font>";
                examplesTxt = "- I <font color=#000000><b>had</b></font> never <font color=#000000><b>seen</b></font> such a beautiful beach before I went to Menorca." +
                        "<br/>- <font color=#000000><b>Had</b></font> you ever <font color=#000000><b>visited</b></font> the U.S. before your trip in 2006?" +
                        "<br/>- She only understood the movie because she <font color=#000000><b>had read</b></font> the book." +
                        "<br/>- After he moved to Alaska, he <font color=#000000><b>had</b></font> never <font color=#000000><b>seen</b></font> a bear." +
                        "<br/>- When we arrived, they <font color=#000000><b>had</b></font> already <font color=#000000><b>left</b></font>.";
                break;
            case "Past perfect continuous":
                constructionTxt = "<u>subject + had + been + past participle</u>";
                descTxt = "The <font color=#000000>past perfect continuous</font> is formed with <font color=#000000><b>had been</b></font> and " +
                        "the present participle (<font color=#000000><b>-ing</b></font> form) of a verb.";
                formTxt = "<font color=#000000>+</font> I<font color=#000000><b> had been drinking</b></font>." +
                        "<br/><font color=#000000>-&nbsp;</font> I<font color=#000000><b> hadn't been drinking</b></font>." +
                        "<br/><font color=#000000>?&nbsp;</font><font color=#000000><b> Had</b></font> you <font color=#000000><b>been drinking</b></font>?";;
                usesTxt = "<font color=#000000>- <u>Actions or situations which had continued up to the past moment that we are thinking about, or shortly before it</u></font>: " +
                        "We<font color=#000000><b> had been walking</b></font> since sunrise." +
                        "<br/><font color=#000000>- <u>To say how long somthing had been happening up to a past moment</u></font>: " +
                        "I went to the doctor because I <font color=#000000><b>had been sleeping</b></font> badly.";
                expressionsTxt = "<font color=#000000>For, since...</font>";
                examplesTxt = "- You <font color=#000000><b>had been waiting</b></font> there for more than two hours when she finally arrived." +
                        "<br/>- Louis gained weight because he <font color=#000000><b>had been overeating</b></font>." +
                        "<br/>- The dog <font color=#000000><b>had been waiting</b></font> since the owner left." +
                        "<br/>- How long <font color=#000000><b>had</b></font> you <font color=#000000><b>been studying</b></font> English before you moved to Cardiff?" +
                        "<br/>- She <font color=#000000><b>hadn't been watching</b></font> TV for 10 years.";
                break;
            ///////////////////////////FUTURE/////////////////////////////
            case "Future simple (Will)":
                constructionTxt = "<u>subject + will + infinitive</u>";
                descTxt = "The <font color=#000000>future simple (will)</font> tense  is composed of two parts: the modal auxiliaty will / shall + the " +
                        "infinitive <font color=#000000><b>without to.</b></font>";
                formTxt = "<font color=#000000>+</font> I <font color=#000000><b>will drink</b></font>." +
                        "<br/><font color=#000000>-&nbsp;</font> I <font color=#000000><b>won't drink</b></font>." +
                        "<br/><font color=#000000>?&nbsp;</font><font color=#000000><b> Will</b></font> you <font color=#000000><b>drink</b></font>?";
                usesTxt = "<font color=#000000>- <u>Predictions</u></font>: " +
                        "It<font color=#000000><b> will</b></font> rain tomorrow." +
                        "<br/><font color=#000000>- <u>Instant decisions</u></font>: " +
                        "I <font color=#000000><b>will pay</b></font> for the tickets by credit card." +
                        "<br/><font color=#000000>- <u>Opinions</u></font>: " +
                        "He <font color=#000000><b>will </b></font> probably <font color=#000000><b>come back</b></font> tomorrow." +
                        "<br/><font color=#000000>- <u>Promises</u></font>: " +
                        "I <font color=#000000><b>will not</b></font> play videogames tonight.";
                expressionsTxt = "<font color=#000000>Tomorrow, next week, in a year, etc.</font>";
                examplesTxt = "- I <font color=#000000><b>will call</b></font> you when i arrive." +
                        "<br/>- <font color=#000000><b>Will</b></font> you ever <font color=#000000><b>help</b></font> him?" +
                        "<br/>- Dont worry, I<font color=#000000><b>'ll be</b></font> careful." +
                        "<br/>- Pedro Smith <font color=#000000><b>won't be</b></font> the next president." +
                        "<br/>- What <font color=#000000><b> will</b></font> the weather  <font color=#000000><b>be </b></font>like?" +
                        "<br/>- I promise I <font color=#000000><b> will not tell</b></font> him about the surprise party.";
                break;
            case "Future simple (Be going to)":
                constructionTxt = "<u>subject + am/is/are + going to (gonna) + infinitive</u>";
                descTxt = "The <font color=#000000>future simple (be going to)</font> tense  is composed of three parts: verb to be (aam/is/are) + going to (gonna) " +
                        "+ infinitive <font color=#000000><b>without to.</b></font>";
                formTxt = "<font color=#000000>+</font> I <font color=#000000><b>'m going to drink</b></font>. / She <font color=#000000><b>is going to drink</b></font>." +
                        "<br/><font color=#000000>-&nbsp;</font> I <font color=#000000><b>'m not going to drink</b></font>. / She <font color=#000000><b>isn't going to drink</b></font>." +
                        "<br/><font color=#000000>?&nbsp;</font><font color=#000000><b> Am</b></font> I <font color=#000000><b>going to drink</b></font>? / </font><font color=#000000><b> Is</b></font> she <font color=#000000><b>going to drink</b></font>?";
                usesTxt = "<font color=#000000>- <u>Plans</u></font>: " +
                        "I <font color=#000000><b> am going to dinner </b></font> with a friend tomorrow." +
                        "<br/><font color=#000000>- <u>Evidences</u></font>: " +
                        "She <font color=#000000><b>is going to be</b></font> a brillant football player." +
                        "<br/><font color=#000000>- <u>Opinions</u></font>: " +
                        "He <font color=#000000><b>will </b></font> probably <font color=#000000><b>come back</b></font> tomorrow.";
                expressionsTxt = "<font color=#000000>Tomorrow, next week, in a year, etc.</font>";
                examplesTxt = "- They <font color=#000000><b>are going to </b></font> spend their holidays in Spain." +
                        "<br/>- <font color=#000000><b>Are</b></font> you <font color=#000000><b>going to cook </b></font> tomorrow?" +
                        "<br/>- Yes, they<font color=#000000><b> are gonna stay</b></font> in the party for whole nigth." +
                        "<br/>- Pedro Smith <font color=#000000><b>won't be</b></font> the next president." +
                        "<br/>- <font color=#000000><b>Are</b></font> you  <font color=#000000><b>going to meet </b></font>Jessica tonight?" +
                        "<br/>- Michelle <font color=#000000><b> is going to begin </b></font> medical school next year. ";
                break;
            case "Present continuos (future)":
                constructionTxt = "<u>subject + am/is/are + present participle + time expression</u>";
                descTxt = "The <font color=#000000>present continuous</font> tense is formed from the present tense of the verb <font color=#000000><b>to be</b></font> and " +
                        "the present participle (<font color=#000000><b>-ing</b></font> form) of a verb." +
                        "<br/>For the future, we need to specify when the action takes place";
                formTxt = "<font color=#000000>+</font> I<font color=#000000><b>'m leaving</b></font> tomorrow." +
                        "<br/><font color=#000000>-&nbsp;</font> I<font color=#000000><b>'m not leaving</b></font> tomorrow." +
                        "<br/><font color=#000000>?&nbsp;&nbsp;</font><font color=#000000><b>Am</b></font> I <font color=#000000><b>leaving</b></font> tomorrow?";;
                usesTxt = "<font color=#000000>- <u>Planned action in the future</u></font>: " +
                        "I <font color=#000000><b> am going</b></font> to Spain at Christmas.";
                expressionsTxt = "<font color=#000000>Tomorrow, next week, in a year, etc.</font>";
                examplesTxt = "- I <font color=#000000><b>'m studying</b></font> this evening." +
                        "<br/>- <font color=#000000><b>Is</b></font> he <font color=#000000><b>working</b></font> next week?" +
                        "<br/>- We <font color=#000000><b>are staying</b></font> with friends when we get to Madrid." +
                        "<br/>- They <font color=#000000><b>are playing</b></font> tennis this weekend." +
                        "<br/>- She <font color=#000000><b>’s taking</b></font> the train to Paris tomorrow.";
                break;
            case "Future continuous":
                constructionTxt = "<u>subject + will + be + present participle</u>";
                descTxt = "The <font color=#000000>future continuous</font> tense is formed from the present tense of the auxiliar <font color=#000000><b>will</b></font>," +
                        " the infinitvie of to be (<font color=#000000><b>be</font></b>) and the present participle (<font color=#000000><b>-ing</b></font> form) of a verb.";
                formTxt = "<font color=#000000>+</font> I<font color=#000000><b>'ll be eating</b></font>." +
                        "<br/><font color=#000000>-&nbsp;</font> I<font color=#000000><b>won't be eating</b></font>." +
                        "<br/><font color=#000000>?&nbsp;&nbsp;</font><font color=#000000><b>Will</b></font> I <font color=#000000><b>be eating</b></font>?";;
                usesTxt = "<font color=#000000>- <u>Action you know will be happening in the future</u></font>: " +
                        "I <font color=#000000><b>will be running</b></font> at 7:00 am.";
                expressionsTxt = "<font color=#000000>At this time tomorrow, next month, etc.</font>";
                examplesTxt = "- At five o’clock, I <font color=#000000><b>will be meeting</b></font> with the management about my raise." +
                        "<br/>- While Ellen is reading, Tim <font color=#000000><b>will be watching</b></font> television." +
                        "<br/>- <font color=#000000><b>Will</b></font> you <font color=#000000><b>be waiting</b></font> for her when her plane arrives tonight?" +
                        "<br/>- At midnight tonight, we <font color=#000000><b>will</b></font> still <font color=#000000><b>be driving</b></font> through the desert." +
                        "<br/>- By Christmas I <font color=#000000><b>will be skiing</b></font> like a pro.";
                break;
            case "Future perfect":
                constructionTxt = "<u>subject + will + have + past participle</u>";
                descTxt = "The <font color=#000000>future perfect</font> is formed from the auxilar <font color=#000000><b>will</b></font>, the present " +
                        "tense of the verb <font color=#000000><b>to have (had)</b></font> and the <font color=#000000><b>past participle</b></font> of a verb.";
                formTxt = "<font color=#000000>+</font> I<font color=#000000><b> will have arrived</b></font>." +
                        "<br/><font color=#000000>-&nbsp;</font> I<font color=#000000><b> won't have arrived</b></font>." +
                        "<br/><font color=#000000>?&nbsp;</font><font color=#000000><b> Will</b></font> you <font color=#000000><b>have arrived</b></font>?";;
                usesTxt = "<font color=#000000>- <u>Action you know will have happened in the future</u></font>: " +
                        "By this time tomorrow I<font color=#000000><b> will have finished</b></font> this homework.";
                expressionsTxt = "<font color=#000000>By this time tomorrow, etc.</font>";
                examplesTxt = "- I <font color=#000000><b>will have been</b></font> here for six months on June 23rd." +
                        "<br/>- By the time you read this I <font color=#000000><b>will have left</b></font>." +
                        "<br/>- You <font color=#000000><b>will have finished</b></font> your book by this time next week." +
                        "<br/>- <font color=#000000><b>Won't</b></font> they <font color=#000000><b>have arrived</b></font> by 5:00?" +
                        "<br/>- <font color=#000000><b>Will</b></font> you <font color=#000000><b>have eaten</b></font> when I pick you up?";
                break;
            case "Future perfect continuous":
                constructionTxt = "<u>subject + will + have + been + present participle</u>";
                descTxt = "The <font color=#000000>past perfect continuous</font> is formed from two elements" +
                        "the future perfect of the verb to be (<font color=#000000><b>will have been</b></font>) + the present participle " +
                        "(<font color=#000000>-ing</b></font> form) of the main verb";
                formTxt = "<font color=#000000>+</font> I<font color=#000000><b>will have been playing</b></font>." +
                        "<br/><font color=#000000>-&nbsp;</font> I<font color=#000000><b> won't have been playing</b></font>." +
                        "<br/><font color=#000000>?&nbsp;</font><font color=#000000><b> Will</b></font> you <font color=#000000><b>have been drinking</b></font>?";;
                usesTxt = "<font color=#000000>- <u>Describes actions that will continue up until a point in the future.</u></font>: " +
                        "In November, I <font color=#000000><b>will have been working</b></font> at my company for three years.";
                expressionsTxt = "<font color=#000000>For a minute, for two years, etc.</font>";
                examplesTxt = "- I <font color=#000000><b>will have been waiting </b></font>here for three hours by six o'clock." +
                        "<br/>- By 2021 I <font color=#000000><b>will have been living </b></font>in Cardiff for sixteen years." +
                        "<br/>- When I finish this course, I <font color=#000000><b>will have been learning </b></font>English for twenty years." +
                        "<br/>- When I come at 6:00, <font color=#000000><b>will</b></font> you <font color=#000000><b>have been practicing</b></font> long?" +
                        "<br/>- When I turn thirty, I <font color=#000000><b>will have been playing</b></font> piano for twenty-one years.";
                break;
            default:
                descTxt = "";
                formTxt = "";
                usesTxt = "";
                expressionsTxt = "";
                examplesTxt = "";
                break;
        }

        description.setText(Html.fromHtml(descTxt));
        form.setText(Html.fromHtml(formTxt));
        uses.setText(Html.fromHtml(usesTxt));
        expressions.setText(Html.fromHtml(expressionsTxt));
        examples.setText(Html.fromHtml(examplesTxt));
        construcion.setText(Html.fromHtml(constructionTxt));

    }
}
