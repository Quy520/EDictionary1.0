package com.example.qsd.edictionary.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qsd.edictionary.R;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExplainFragment extends Fragment {
private TextView textView;
    private String text="<div class=\"dictionary-output\"> <div class=\"dictionary-title clearfix\"> <h3 class=\"strong\">hello</h3>  <div class=\"dictionary-spell\">   <span class=\"phonetic-transcription\"> <span>英</span> <b>[wɜ:d]</b>  <a href=\"javascript:void(0);\" data-sound-lan=\"uk&amp;lock\" data-sound-text=\"hello\" class=\"op-sound\"> <span></span> </a> <a href=\"javascript:void(0);\" data-sound-lan=\"uk&amp;lock\" data-sound-text=\"hello\" data-hover-tip-text=\"复读\" class=\"op-repeat data-hover-tip\"> <span></span> </a>  </span>   <span class=\"phonetic-transcription\"> <span>美</span> <b>[wɜ:rd]</b>  <a href=\"javascript:void(0);\" data-sound-lan=\"en&amp;lock\" data-sound-text=\"hello\" class=\"op-sound\"> <span></span> </a> <a href=\"javascript:void(0);\" data-sound-lan=\"en&amp;lock\" data-sound-text=\"hello\" data-hover-tip-text=\"复读\" class=\"op-repeat data-hover-tip\"> <span></span> </a>  </span>  </div>  </div> <div class=\"dictionary-comment\">  <p>  <b>int.</b>   <strong class=\"dict-comment-mean\"> <span>打招呼</span><span class=\"dict-margin\">;</span><span>哈喽，喂</span><span class=\"dict-margin\">;</span><span>你好，您好</span><span class=\"dict-margin\">;</span><span>表示问候</span> </strong>  </p>  <p>  <b>n.</b>   <strong class=\"dict-comment-mean\"> <span>“喂”的招呼声或问候声</span> </strong>  </p>  <p>  <b>vi.</b>   <strong class=\"dict-comment-mean\"> <span>喊“喂”</span> </strong>  </p>   </div> </div>";
    public ExplainFragment() {
    }
    public static ExplainFragment newInstance(String value) {
        ExplainFragment fragment=new ExplainFragment();
        Bundle bundle=new Bundle();
        bundle.putString("KEY1",value);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_explain, container, false);
    }
    public void onViewCreated(View  view , @Nullable Bundle saveInstanceState){
        super.onViewCreated(view,saveInstanceState);
        textView= (TextView) view.findViewById(R.id.explain_tv);
        if (getArguments()!=null) {
            text = getArguments().getString("KEY1");
            Log.i("qsd", text + "danci传递过来的类型id");

        }

        textView.setText(Html.fromHtml("<font size='20'>"+text+"</font>"));

    }

}
