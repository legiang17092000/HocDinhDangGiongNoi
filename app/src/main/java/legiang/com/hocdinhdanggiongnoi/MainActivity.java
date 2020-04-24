package legiang.com.hocdinhdanggiongnoi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

import legiang.com.hocdinhdanggiongnoi.databinding.ActivityGoogleRecognitionWithoutDialogBinding;
import legiang.com.hocdinhdanggiongnoi.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity  {

    ActivityMainBinding binding;
    HashMap<String,String> dictionary=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        makeDictionary();
        addEvents();
    }

    private void makeDictionary() {
        dictionary.put("school","Trường học");
        dictionary.put("university","đại học");
        dictionary.put("student","Học sinh");
        dictionary.put("beautiful","đẹp");
        dictionary.put("light","đèn");
        dictionary.put("nice","tốt đẹp");
        dictionary.put("Why","Tại sao");

    }

    private void addEvents() {
        binding.btnGiongNoi1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyGiongNoiGoogleAI();
            }
        });
        binding.btnTaoTuTiengAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taoTuTiengAnh();
            }
        });
        binding.btnCach2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,GoogleRecognitionWithoutDialogActivity.class);
                startActivity(intent);
            }
        });
    }

    private void taoTuTiengAnh() {
        Random random=new Random();
        int index=random.nextInt(dictionary.size());
        String word=(String) dictionary.keySet().toArray()[index];
        binding.txtEnglish.setText(word);
    }

    private void xuLyGiongNoiGoogleAI() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Please talk something");
        try {
            startActivityForResult(intent,113);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Điện thoại cùi bắp này không hỗ trợ Google AI Recognition",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==113 && resultCode==RESULT_OK && data!=null)
        {
            //Google trar veef 1 mangr cacs chuooix maf nos phaan tichs ra ddwocj
            //thwongf phaan twr ddaauf tieen laf ddungs nhaats
            //nos trar veef bao nhieeu tuyf vaof ta caaus hinhf
            ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            binding.txtSpeechToTest.setText(result.get(0));


        }
    }


}
