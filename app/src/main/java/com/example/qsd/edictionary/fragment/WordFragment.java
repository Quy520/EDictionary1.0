package com.example.qsd.edictionary.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup.MarginLayoutParams;

import com.example.qsd.edictionary.R;
import com.example.qsd.edictionary.activitys.VedioPlayActivity;
import com.example.qsd.edictionary.adapter.SortGroupMemberAdapter;
import com.example.qsd.edictionary.sortList.SideBar;
import com.example.qsd.edictionary.utils.CharacterParser;
import com.example.qsd.edictionary.utils.GroupMemberBean;
import com.example.qsd.edictionary.utils.PinyinComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WordFragment extends Fragment {
    private View view;
    private ListView listView;
    private Activity activity;
    private SideBar sideBar;
    private TextView dialog,title,tvnowords;
    private LinearLayout linearLayout;
    private SortGroupMemberAdapter adapter;
    private EditText editText;

    /**
     * 上次第一个可见元素，用于滚动时记录标识。
     */
    private int lastFirstVisibleItem = -1;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<GroupMemberBean> SourceDateList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;
    public WordFragment() {

    }



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

            try{
                if (view==null){
                    view=inFlater(inflater);
                }
                return view;
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    private View inFlater(LayoutInflater inflater) {
        view=inflater.inflate(R.layout.fragment_word2,null,false);
        initView(view);
        return view;
    }

    private void initView(final View view) {
        activity = getActivity();
        linearLayout = (LinearLayout) view.findViewById(R.id.title_layout);
        title = (TextView) view.findViewById(R.id.title_layout_catalog);
        tvnowords = (TextView) view.findViewById(R.id.title_layout_no_friends);
        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();
        sideBar = (SideBar) view.findViewById(R.id.sidrbar);
        dialog = (TextView) view.findViewById(R.id.dialog);
        sideBar.setTextView(dialog);
        //对siderBar进行监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    listView.setSelection(position);
                }

            }
        });

        listView = (ListView) view.findViewById(R.id.country_lvcountry);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 这里要利用adapter.getItem(position)来获取当前position所对应的对象
                Toast.makeText(
                        activity,
                        ((GroupMemberBean) adapter.getItem(position)).getName(),
                        Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getContext(), VedioPlayActivity.class);
                startActivity(intent);
            }
        });

        SourceDateList= filledData(getResources().getStringArray(R.array.date));
        Log.i("qsd",getResources().getStringArray(R.array.date)+"1234");
        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new SortGroupMemberAdapter(activity, SourceDateList);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int section = getSectionForPosition(firstVisibleItem);
                int nextSection = getSectionForPosition(firstVisibleItem + 1);
                int nextSecPosition = getPositionForSection(+nextSection);
                if (firstVisibleItem != lastFirstVisibleItem) {
                    MarginLayoutParams params= (MarginLayoutParams) linearLayout.getLayoutParams();
                    params.topMargin=0;
                    linearLayout.setLayoutParams(params);
                    title.setText(SourceDateList.get(
                            getPositionForSection(section)).getSortLetters());
                }
                if (nextSecPosition == firstVisibleItem + 1) {
                    View childView = view.getChildAt(0);
                    if (childView != null) {
                        int titleHeight = linearLayout.getHeight();
                        int bottom = childView.getBottom();
                        MarginLayoutParams params = (MarginLayoutParams) linearLayout
                                .getLayoutParams();
                        if (bottom < titleHeight) {
                            float pushedDistance = bottom - titleHeight;
                            params.topMargin = (int) pushedDistance;
                            linearLayout.setLayoutParams(params);
                        } else {
                            if (params.topMargin != 0) {
                                params.topMargin = 0;
                                linearLayout.setLayoutParams(params);
                            }
                        }
                    }
                }
                lastFirstVisibleItem = firstVisibleItem;
            }
        });
        editText= (EditText) view.findViewById(R.id.search_et_input);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                linearLayout.setVisibility(view.GONE);
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private List<GroupMemberBean> filledData(String[] date) {
        List<GroupMemberBean> mSortList = new ArrayList<GroupMemberBean>();

        for (int i = 0; i < date.length; i++) {
            GroupMemberBean sortModel = new GroupMemberBean();
            sortModel.setName(date[i]);
            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }
    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<GroupMemberBean> filterDateList = new ArrayList<GroupMemberBean>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
            tvnowords.setVisibility(View.GONE);
        } else {
            filterDateList.clear();
            for (GroupMemberBean sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1
                        || characterParser.getSelling(name).startsWith(
                        filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
        if (filterDateList.size() == 0) {
            tvnowords.setVisibility(View.VISIBLE);
        }
    }



    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return SourceDateList.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < SourceDateList.size(); i++) {
            String sortStr = SourceDateList.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

}
