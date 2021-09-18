package com.app.riyasewana.Inflators;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.riyasewana.R;
import com.app.riyasewana.ReportSearch;
import com.app.riyasewana.model.Report;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SugarReportList extends BaseAdapter {

    private Activity context;
    private List<Report> List;
    private ArrayList<Report> arraylist;
    LayoutInflater inflater;

    public SugarReportList(Activity context, List<Report>  List){
//        super(context, R.layout.reportrow,List);
        this.context = context;
        this. List = List;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<Report>();
        this.arraylist.addAll(ReportSearch.sugarList);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return ReportSearch.sugarList.size();
    }

    @Override
    public Report getItem(int position) {
        return ReportSearch.sugarList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.reportrow,null,true);
        TextView  CusID = (TextView)listViewItem.findViewById(R.id.CusID);
        TextView  patientName = (TextView)listViewItem.findViewById(R.id.patientname);
        TextView  ReportID = (TextView)listViewItem.findViewById(R.id.reportID);
        TextView date = (TextView) listViewItem.findViewById(R.id.IssuedDate);


        Report report = List.get(position);

        CusID.setText(report.getcustomerID());
        patientName.setText(report.getPatientName());
        ReportID.setText(report.getReportID());
        date.setText(report.getCurrentDate());

        return listViewItem;

    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        ReportSearch.sugarList.clear();
        if (charText.length() == 0) {
            ReportSearch.sugarList.addAll(arraylist);
        } else {
            for (Report wp : arraylist) {
                if (wp.getPatientName().toLowerCase(Locale.getDefault()).contains(charText)) {
                   ReportSearch.sugarList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
