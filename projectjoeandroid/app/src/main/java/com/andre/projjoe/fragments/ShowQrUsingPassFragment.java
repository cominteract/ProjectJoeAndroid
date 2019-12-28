package com.andre.projjoe.fragments;


import com.andre.projjoe.Contents;
import com.andre.projjoe.IntentIntegrator;
import com.andre.projjoe.IntentResult;
import com.andre.projjoe.QrCodeEncoder;
import com.andre.projjoe.R;
import com.andre.projjoe.listeners.FragmentInteractionInterface;
import com.andre.projjoe.models.Pass;
import com.andre.projjoe.models.Transaction;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowQrUsingPassFragment extends Fragment {
	String userid = "1";
	String transcode = "14937293";
    public Pass selectedPass;
	FragmentInteractionInterface fragmentInteractionInterface;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	  View rootView = inflater.inflate(R.layout.fragment_showqr, container, false);
    	  
    	  //encodeBarcode("TEXT_TYPE", "192.168.1.135/qrcode/index.php?readqr=123");
    	  
    	  ImageView showQRImage= (ImageView) rootView.findViewById(R.id.showQRImage);
    	  TextView showQRTransactionCodeTxt = (TextView) rootView.findViewById(R.id.showQRTransactionCodeTxt);
    	  String qrData = "http://192.168.1.135/qrcode/index.php?transactioncode=" + userid + transcode + "&usepassqr=" + 123 + "&user=" + userid + "&passid=" + 2 + "&type=" + "3" ;
    	  int qrCodeDimention = 500;

    	  QrCodeEncoder qrCodeEncoder = new QrCodeEncoder(qrData, null,
    	          Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(), qrCodeDimention);

    	  try {
    	      Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
              showQRImage.setImageBitmap(bitmap);
    	  } catch (WriterException e) {
    	      e.printStackTrace();
    	  }

        rootView.findViewById(R.id.showQROkBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Transaction transaction = new Transaction(fragmentInteractionInterface.dataPassRetrieved().currentJoeUser()
                        ,fragmentInteractionInterface.dataPassRetrieved().randomDate()
                        ,selectedPass, Transaction.TransactionType.TransactionGivingPass);
                transaction.setTransactionOtherUser(null);
                Toast.makeText( getActivity(), " You have used " + selectedPass.passDescription, Toast.LENGTH_LONG).show();
                fragmentInteractionInterface.dataPassRetrieved().addTransaction(transaction);
                if(fragmentInteractionInterface.dataPassRetrieved().getCurrentPassList().contains(selectedPass))
                fragmentInteractionInterface.dataPassRetrieved().getCurrentPassList().remove(selectedPass);
                fragmentInteractionInterface.onSwitchFragment(ShowQrUsingPassFragment.this,
                        PassesFragment.newInstance());
            }
        });

          showQRTransactionCodeTxt.setText("xxxx-xxxx-xxx");
    	  return rootView;
    }
    
    private void encodeBarcode(CharSequence type, CharSequence data) {
        IntentIntegrator integrator = new IntentIntegrator(getActivity());
        integrator.shareText(data, type);
    }

    public static ShowQrUsingPassFragment newInstance() {
        ShowQrUsingPassFragment fragment = new ShowQrUsingPassFragment();
        return fragment;
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
      IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
      if (result != null) {
        String contents = result.getContents();
        if (contents != null) {
          showDialog(2,"sadsad");
        } else {
          showDialog(3,"asdasdsad");
        }
      }
    }
    
    private void showDialog(int title, CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        builder.setTitle("Congrats");
        builder.setMessage("You have earned this points");
        builder.setPositiveButton(R.string.ok_button, null);
        builder.show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteractionInterface) {

            fragmentInteractionInterface = (FragmentInteractionInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentInteractionInterface = null;
    }
}