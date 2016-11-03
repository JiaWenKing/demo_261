package com.example.demo_261;



import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnRefreshListener,OnScrollListener{
	Handler mhandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				mList.clear();
				ls.removeHeaderView(view);
				Toast.makeText(MainActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
				
				List<String> li=(List<String>) msg.obj;
				mList.addAll(li);
				adapter.notifyDataSetChanged();
				sr.setRefreshing(false);
				break;
			case 2:
				ls.removeFooterView(view2);

				List<String> listinfo = (List<String>) msg.obj;

				mList.addAll(listinfo);
				adapter.notifyDataSetChanged();

				break;
			default:
				break;
			}
		};
	};
	SwipeRefreshLayout sr;
	private CommonAdapter<String> adapter;
	private ListView ls;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		sr=(SwipeRefreshLayout) findViewById(R.id.sr);
		sr.setColorScheme(android.R.color.holo_blue_light, android.R.color.holo_red_light, 
				android.R.color.holo_orange_light, android.R.color.holo_green_light);
		sr.setOnRefreshListener(this);
		
		ls=(ListView) findViewById(R.id.listview);
		ls.setOnScrollListener(this);
		
		view = getLayoutInflater().inflate(R.layout.listview_item, null);
		view2 = getLayoutInflater().inflate(R.layout.bottom_head, null);
		
		mList = getdata();
		adapter = new CommonAdapter<String>(this, mList, R.layout.listview_main){
			@Override
			public void setViewData(View currentView, String item) {
				// TODO Auto-generated method stub
				super.setViewData(currentView, item);
				TextView textview = CommonAdapter.get(currentView, R.id.textview);
				textview.setText(item);
			}
		}; 
		ls.setAdapter(adapter); 
	}  
	int page=1;
	List<String> getdata(){
		 List<String> list = new ArrayList<String>();
		
		for (int i = 0; i < page*15; i++) {
			if (page==1) {
				list.add(""+i);
			}else if (i>=(page-1)*15) {
					list.add(""+i);
				
			}
			
		}
		return list;
	}
	private List<String> mList;
	private TextView tv;
	private View view;
	private View view2;
	@Override
	public void onRefresh() {
		
		ls.addHeaderView(view);
		tv=(TextView) view.findViewById(R.id.tv);
		tv.setText("正在刷新");
		page=1;
		List<String> list=getdata();
		
		
		Message message = mhandler.obtainMessage();
		message.what=1;
		message.obj=list;
		mhandler.sendMessageDelayed(message, 2000);
		
	}

	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if (OnScrollListener.SCROLL_STATE_TOUCH_SCROLL==scrollState
				&& index==(page*15)) {
			ls.addFooterView(view2);

			page = page + 1;
			List<String> data = getdata();
			Message message = mhandler.obtainMessage();
			message.what = 2;
			message.obj = data;
			mhandler.sendMessageDelayed(message, 1000);

		}
		
	}
	int index=0;
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		index=firstVisibleItem+visibleItemCount;
			 
			

	}
	

//	private List<String> getdat() {
//		// TODO Auto-generated method stub
//list = new ArrayList<String>();
//		
//		for (int i = 0; i < page*15; i++) {
//			if (page==1) {
//				list.add("aaa"+i);
//			}else{
//				if (i>=page*15) {
//					list.add("aaa"+i);
//				}
//			}
//			
//		}
//		return list;
//	}
}