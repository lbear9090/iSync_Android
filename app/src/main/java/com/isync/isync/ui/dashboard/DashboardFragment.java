package com.isync.isync.ui.dashboard;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.isync.isync.DataObject.DashboardData;
import com.isync.isync.DataObject.SnapshotData;
import com.isync.isync.R;
import com.isync.isync.databinding.FragmentDashboardBinding;
import com.isync.isync.helper.Global;

import java.util.ArrayList;
import java.util.Collections;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;

    LineChart lineChart;
    PieChart pieChart;
    Spinner spinner;
    TextView txtTotalSale, txtActivePartnersPro, txtActivePartnersCount, txtActivePartnersInc,
            txtPartnersWaitingPro, txtPartnersWaitingCount, txtPartnersWaitingInc,
            txtEmailSentPro, txtEmailSentCount, txtEmailSentInc;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(getActivity()).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        lineChart = binding.chart;
        pieChart = binding.pieChart;
        spinner = binding.spinner;
        txtTotalSale = binding.txtTotalSale;

        txtActivePartnersPro = binding.txtActivePartnersPro;
        txtActivePartnersCount = binding.txtActivePartnersCount;
        txtActivePartnersInc = binding.txtActivePartnersInc;
        txtPartnersWaitingPro = binding.txtPartnersWaitingPro;
        txtPartnersWaitingCount = binding.txtPartnersWaitingCount;
        txtPartnersWaitingInc = binding.txtPartnersWaitingInc;
        txtEmailSentPro = binding.txtEmailsSentPro;
        txtEmailSentCount = binding.txtEmailsSentCount;
        txtEmailSentInc = binding.txtEmailsSentInc;

        dashboardViewModel.getSnapshot().observe(getViewLifecycleOwner(), new Observer<SnapshotData>() {
            @Override
            public void onChanged(SnapshotData snapshotData) {
                updateData(snapshotData);
            }
        });

        dashboardViewModel.getDashboard().observe(getViewLifecycleOwner(), new Observer<DashboardData>() {
            @Override
            public void onChanged(DashboardData dashboardData) {
                updateDashboard(dashboardData);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(Global.dashboardData != null){
                    switch (position){
                        case 0:
                            txtTotalSale.setText(Global.dashboardData.sales.daily_sales);
                            break;
                        case 1:
                            txtTotalSale.setText(Global.dashboardData.sales.last_weekly_sales);
                            break;
                        case 2:
                            txtTotalSale.setText(Global.dashboardData.sales.monthly_sales);
                            break;
                        case 3:
                            txtTotalSale.setText(Global.dashboardData.sales.year_to_date_sales);
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return root;
    }

    void updateDashboard(DashboardData dashboardData){
        txtTotalSale.setText(dashboardData.sales.daily_sales);
        int nAP = 5;
        int nPW = 3;
        int nES = dashboardData.total_proposal_sent;

        txtActivePartnersPro.setText(String.format("%.1f%%", (float)(nAP * 100.0f / (nAP + nPW + nES))));
        txtActivePartnersCount.setText(String.valueOf(nAP));
        txtActivePartnersInc.setText("+ " + nAP);

        txtPartnersWaitingPro.setText(String.format("%.1f%%", (float)(nPW * 100.0f / (nAP + nPW + nES))));
        txtPartnersWaitingCount.setText(String.valueOf(nPW));
        txtPartnersWaitingInc.setText("+ " + nPW);

        txtEmailSentPro.setText(String.format("%.1f%%", (float)(nES * 100.0f / (nAP + nPW + nES))));
        txtEmailSentCount.setText(String.valueOf(nES));
        txtEmailSentInc.setText("+ " + nES);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieChart.setTransparentCircleRadius(0);
        pieChart.getDescription().setEnabled(false);
        Legend l = pieChart.getLegend();
        l.setEnabled(false);
//        l.setTextColor(Color.WHITE);

        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.

        entries.add(new PieEntry(nAP));
        entries.add(new PieEntry(nPW));
        entries.add(new PieEntry(nES));

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(ColorTemplate.rgb("#07EA83"));
        colors.add(ColorTemplate.rgb("#2D7EFE"));
        colors.add(ColorTemplate.rgb("#E20049"));

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.TRANSPARENT);
//        data.setValueTypeface(tfLight);
        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();
    }
    public void updateData(SnapshotData snapshot){
        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < snapshot.data.length; i++) {

            values.add(new Entry(i, snapshot.data[i]));
        }

        LineDataSet set1;

        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            set1.notifyDataSetChanged();
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "Snapshot Data");

            set1.setDrawIcons(false);

            // draw dashed line
//            set1.enableDashedLine(10f, 5f, 0f);

            // black lines and points
            set1.setColor(Color.parseColor("#2B7EFE"));
            set1.setCircleColor(Color.parseColor("#327BFF"));

            // line thickness and point size
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);

            // draw points as solid circles
            set1.setDrawCircleHole(false);

            // customize legend entry
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            // text size of values
            set1.setValueTextSize(9f);

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setValueTextColor(Color.parseColor("#2B7EFE"));
            // set the filled area
            set1.setDrawFilled(true);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return lineChart.getAxisLeft().getAxisMinimum();
                }
            });

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets

            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            XAxis xAxis = lineChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setAxisMinimum(0f);
            xAxis.setGranularity(1f);
            xAxis.setTextColor(Color.parseColor("#2B7EFE"));
            xAxis.setTextSize(9f);
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getAxisLabel(float value, AxisBase axis) {
                    return snapshot.labels[(int) value];
                }
            });
            lineChart.getDescription().setEnabled(false);
            lineChart.getAxisRight().setTextColor(Color.WHITE);
            lineChart.getAxisLeft().setTextColor(Color.WHITE);
            // set data
            Legend l = lineChart.getLegend();
            l.setEnabled(false);

            lineChart.setData(data);

            set1.notifyDataSetChanged();
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
            lineChart.invalidate();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}