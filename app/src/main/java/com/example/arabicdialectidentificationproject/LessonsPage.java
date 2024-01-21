package com.example.arabicdialectidentificationproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class LessonsPage extends AppCompatActivity {

    String[] egy_exercises = new String[]{
            "غالي جدا الاطباع غاليه جدا مزعل في جمارك على الورق وعلى مستلزمات الطباعه وعلى وعلى الاحبه.",
            "يعني بالازم ارجو ان تسمع حليب نصف دقيقه عزه نفسي وعزي اسر الشهداء ابنائنا في القوات المسلحه زهراء هذا حادث الارهاب",
            "المئات من الارهابيين دول تم تصفيتهم انتم عارفين انك هتبقى عامله ازاي؟",
            "الرئيس السيسي لم يحدث بالضبط مندير",
            "من الشعب المصري هو ده الشعب اللي طلعت اقفل السيسي وده اللي طلعت اقفل محمد ابراهيم محلب ومقلب جاي",
            "من هنا في المستشفيات",
    };

    String[] msa_exercises = new String[]{
            "المراقب للاحداث يؤكد ان المستفيد الاول من هذه النظره هو السياسي بصرف النظر عن انتمائه العرقيه او الطائفيه",
            "المحكمه الاتحاديه و قرار المحكمه الاتحاديه قرار ملزم غير قابل للطعن",
            "اسئله عن دخلت المدينه في قلب المواجهه العسكريه جات تخسره جارها الرهان مرتين ودفع الثمن مضاعفا",
            "في الواقع ان هذا القرار يصب في اتجاه صب الزيت على نار الانقسام فعلا كنا نعمل ان يبقى المصرف المركزي مؤسسه المحايده بين السياسيه ولا تتجاذب الاطراف السياسيه حتى",
            "وقال بيان صادر عن الديوان الملكي السعوديه ان مصر وقطر استجابه لمبادره خادم الحرمين الشريفين الملك عبد الله بن عبد العزيز التي دعا فيها الى توطيد العلاقات وازاله ما يدعو الى اثاره النزاع والشقاق بينهما واشار البيان الى حرص القياده السعوديه على ازاله ما يشربوا العلاقات بين الشقيقتين",
            "قصف جوي يستهدف مطار معيتيقه الدولي بالعاصمه الليبيه طرابلس مرتين فى اقل من 24 ساعه الحق اضرارا بمنازل تقع في محيط المطار وجرح مدنيين ياتي ذلك بينما يشهد مطار طرابلس الدولي توقفات اما منذ يوليو تموز الماضي بسبب اشتباكات مسلحه اسفرت عن تدمير اجزاء كبيره من هم",
    };

    ArrayList<ExerciseModel> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        Toolbar toolbar = findViewById(R.id.myToolbar);
        toolbar.setTitle("All Lessons");
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String dialect = intent.getStringExtra("dialect");
        int lesson = intent.getIntExtra("lesson",1);

        exercises = new ArrayList<>();

        int i_start = 0;

        if(lesson==1)
        {
            i_start=0;
        }
        else if(lesson==2)
        {
            i_start=5;
        }
        else if (lesson==3)
        {
            i_start=10;
        }

        switch (dialect) {
            case "EGY":
                for (int i=i_start; i<i_start+5; i++) {
                    exercises.add(new ExerciseModel(ELTexts.egy_exercises[i]));
                }
                break;
            case "NOR":
                for (int i=i_start; i<i_start+5; i++) {
                    exercises.add(new ExerciseModel(ELTexts.nor_exercises[i]));
                }
                break;
            case "LAV":
                for (int i=i_start; i<i_start+5; i++) {
                    exercises.add(new ExerciseModel(ELTexts.lav_exercises[i]));
                }
                break;
            case "GLF":
                for (int i=i_start; i<i_start+5; i++) {
                    exercises.add(new ExerciseModel(ELTexts.glf_exercises[i]));
                }
                break;
            case "MSA":
                for (int i=i_start; i<i_start+5; i++) {
                    exercises.add(new ExerciseModel(ELTexts.msa_exercises[i]));
                }
                break;
        }


        // Lookup the recyclerview in activity layout
        RecyclerView rvContacts = findViewById(R.id.rxExercises);

        // Initialize contacts
        // Create adapter passing in the sample user data
        LessonsAdapter adapter = new LessonsAdapter(exercises, this);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
    }
}