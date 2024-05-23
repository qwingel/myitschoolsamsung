package com.example.myitschoolsamsung;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

import static com.example.myitschoolsamsung.R.*;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TicketAdapter extends ArrayAdapter<String[]> {
    int idLL;
    final static int CONST_ID_TICKETID = 0;
    final static int CONST_ID_FROMWHERE = 1;
    final static int CONST_ID_TOWHERE = 2;
    final static int CONST_ID_MONTH = 3;
    final static int CONST_ID_DATE = 4;
    final static int CONST_ID_TIMEOUT = 5;
    final static int CONST_ID_TIMETO = 6;
    final static int CONST_ID_ISCHILD = 7;
    final static int CONST_ID_ISKID = 8;
    final static int CONST_ID_CLASS = 9;
    final static int CONST_ID_BAGGAGE = 10;
    final static int CONST_ID_TICKETSCOUNT = 11;
    final static int CONST_ID_PRICE = 12;

    public boolean isInArray(String required, String[] array){
        for (String value: array){
            if(value.equals(required)) return true;
        }
        return false;
    }

    public TicketAdapter(Context context, int layout,  String[][] data){
        super(context, layout, data);
        this.idLL = layout;
    }

    public int setBackTickets(String city){
        if (city.equalsIgnoreCase("Sochi")) return R.drawable.so4i;
        if (city.equalsIgnoreCase("Skrt")) return R.drawable.syk;
        if (city.equalsIgnoreCase("Moscow")) return R.drawable.msk__1_;
        if (city.equalsIgnoreCase("SPB")) return R.drawable.spb;

        return 0;
    }

    public void setBackSeparator(int position, View convertView){
        TextView tv = convertView.findViewById(R.id.lv_tvTicket);
        LinearLayout ll = convertView.findViewById(R.id.ll_separator);
        if (idLL == R.id.list_view){
            switch (position){
                case 0:
                    tv.setText("Ближайший рейс");
                    ll.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,100));
                    break;


                case 1:
                    tv.setText("Лучшая цена");
                    ll.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,100));
                    break;


                default:
//                LinearLayout lU = convertView.findViewById(R.id.lv_Up); lU.setLayoutParams(new LinearLayout.LayoutParams(-1, 0, 48f));
//                LinearLayout lD = convertView.findViewById(R.id.lv_Down); lD.setLayoutParams(new LinearLayout.LayoutParams(-1, 0, 48f));

                    tv.setText("");
                    ll.setLayoutParams(new LinearLayout.LayoutParams(0,0,0));
                    break;
            }
        } else {
            tv.setText("");
            ll.setBackground(getDrawable(getContext(), color.pr_light_grey));
            ll.setLayoutParams(new LinearLayout.LayoutParams(0,0,0));

        }
    }

    @Override
    public View getView(
            int position,
            View convertView,
            ViewGroup parent
    ){
        String[] lineData = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ticket, null);
        }

        TextView tv_Month = convertView.findViewById(R.id.tv_dateMonth);
        TextView tv_Date = convertView.findViewById(R.id.tv_dateNum);
        TextView tv_Price = convertView.findViewById(R.id.tv_Price);
        TextView tv_Time = convertView.findViewById(R.id.tv_Time);
        TextView tv_fromWhere = convertView.findViewById(R.id.tvTicketFromWhere);
        TextView tv_toWhere = convertView.findViewById(R.id.tvTicketToWhere);
        LinearLayout ll_BackTickets = convertView.findViewById(R.id.lv_Background);

        setBackSeparator(position, convertView);
        if (setBackTickets(lineData[CONST_ID_TOWHERE]) != 0) ll_BackTickets.setBackground(getDrawable(getContext(), setBackTickets(lineData[CONST_ID_TOWHERE])));

        ll_BackTickets.setOnClickListener(view -> {
            String[] ids = RequestToServe.getIds();
            if (!isInArray(lineData[CONST_ID_TICKETID], ids)) {
                RequestToServe.addId(lineData[CONST_ID_TICKETID]);
                Toast.makeText(getContext(), "Вы добавили билет в корзину", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Этот билет уже есть в вашей корзине", Toast.LENGTH_LONG).show();
            }
        });

        tv_Month.setText(lineData[CONST_ID_MONTH]);
        tv_Date.setText(lineData[CONST_ID_DATE]);
        tv_Price.setText(lineData[CONST_ID_PRICE] + "₽");
        tv_Time.setText(lineData[CONST_ID_TIMEOUT] + " - " + lineData[CONST_ID_TIMETO]);
        tv_toWhere.setText(lineData[CONST_ID_TOWHERE]);
        tv_fromWhere.setText(lineData[CONST_ID_FROMWHERE]);

        return convertView;
    }

}
