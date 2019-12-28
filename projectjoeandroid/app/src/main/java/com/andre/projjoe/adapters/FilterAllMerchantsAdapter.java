package com.andre.projjoe.adapters;


/**
 * Created by Dre on 5/30/2014.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andre.projjoe.R;
import com.andre.projjoe.fragments.FilterAllMerchantsFragment;
import com.andre.projjoe.fragments.MerchantDetailsFragment;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.andre.projjoe.models.Merchant;

public class FilterAllMerchantsAdapter extends BaseAdapter {
    private static LayoutInflater inflater=null;

    List<Merchant> merchantList;
    private FragmentInteractionInterface fragmentInteractionInterface;
    FilterAllMerchantsFragment filterAllMerchantsFragment;
    Activity activity;


    public FilterAllMerchantsAdapter(FilterAllMerchantsFragment filterAllMerchantsFragment,
                                     List<Merchant> merchantList, FragmentInteractionInterface fragmentInteractionInterface) {
        this.filterAllMerchantsFragment = filterAllMerchantsFragment;
        this.fragmentInteractionInterface = fragmentInteractionInterface;
        this.merchantList = merchantList;

        this.activity = this.filterAllMerchantsFragment.getActivity();
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return merchantList.size();
    }
    public Object getItem(int position) {
        return position;
    }
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.allmerch_adapter,null);
        /*for(int cntpass = 0;cntpass<4;cntpass++)
        {
        	HashMap<String,Integer> passtype = new HashMap<String,Integer>();
        	passtype.put("passtype", cntpass);
        	drawablelist.add(passtype);
        }
        */

      
        TextView tvtitle = (TextView) vi.findViewById(R.id.allMerchTxt);
        TextView tvdesc = (TextView) vi.findViewById(R.id.allMerchDesc);
        ImageView imgicon = (ImageView) vi.findViewById(R.id.allMerchImage);

        tvtitle.setText(merchantList.get(position).merchantName);
        tvdesc.setText(merchantList.get(position).merchantDetails);
        imgicon.setBackgroundResource(merchantList.get(position).merchantImageResource);
        imgicon.setTag(merchantList.get(position).merchantName);
        vi.setTag(position);
        vi.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			    MerchantDetailsFragment merchantDetailsFragment = MerchantDetailsFragment.newInstance();
			    if(v.getTag()!=null)
                    merchantDetailsFragment.selectedMerchant = merchantList.get((int)v.getTag());
			    else
                    merchantDetailsFragment.selectedMerchant = merchantList.get(0);
			    fragmentInteractionInterface.onSwitchFragment(filterAllMerchantsFragment, merchantDetailsFragment);
//				TextView tvdesc = (TextView) v.findViewById(R.id.allMerchDesc);
//				TextView tvslogan = (TextView) v.findViewById(R.id.allMerchTxt);
//				ImageView imgicon = (ImageView) v.findViewById(R.id.allMerchImage);
//				Intent i = new Intent(activity,MainTabs.class);
//
//				i.putExtra("desc",tvdesc.getText().toString());
//				i.putExtra("icon",imgicon.getTag().toString());
//				i.putExtra("slogan",String.valueOf(tvslogan.getText().toString()));
//				i.putExtra("mainpage", "fragment_merchantdetails");
//
//				Log.d("desc",tvdesc.getText().toString());
//				Log.d("icon",imgicon.getTag().toString());
//				Log.d("slogan",String.valueOf(tvslogan.getText().toString()));
//
//				activity.startActivity(i);
				
				
            }
        });
        		return vi;
            }
        }
