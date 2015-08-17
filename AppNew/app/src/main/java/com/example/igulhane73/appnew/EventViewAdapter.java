package com.example.igulhane73.appnew;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Collections;
import java.util.List;

/**
 * Created by igulhane73 on 8/6/15.
 */
public class EventViewAdapter extends RecyclerView.Adapter<EventViewAdapter.EventViewHolder> {
    private Context mContext;
    private LayoutInflater inflator;
    List<UserEvent> eventList = Collections.emptyList();

    public void delete(int position) {
        int id = eventList.get(position).getId();
        eventList.remove(position);
        notifyItemRemoved(position);
    }


    public EventViewAdapter(Context context, List<UserEvent> list) {
        inflator = LayoutInflater.from(context);
        eventList = list;
        mContext = context;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row, null);
        EventViewHolder eventViewHolder = new EventViewHolder(view);

        return eventViewHolder;
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        UserEvent event = eventList.get(position);
        holder.textView.setText(event.getTitle());
        holder.start_time.setText(event.getStart_time());
        holder.end_time.setText(event.getEnd_time());
        /*holder.sunday.setTextColor(getColor(event.getSun()));
        holder.monday.setTextColor(getColor(event.getMon()));
        holder.tuesday.setTextColor(getColor(event.getTue()));
        holder.wednesday.setTextColor(getColor(event.getWed()));
        holder.thursday.setTextColor(getColor(event.getThur()));
        holder.friday.setTextColor(getColor(event.getFri()));
        holder.saturday.setTextColor(getColor(event.getSat()));*/
        holder.days_set[0]=event.getSun()==1?true:false;
        holder.days_set[1]=event.getMon()==1?true:false;
        holder.days_set[2]=event.getTue()==1?true:false;
        holder.days_set[3]=event.getWed()==1?true:false;
        holder.days_set[4]=event.getThur()==1?true:false;
        holder.days_set[5]=event.getFri()==1?true:false;
        holder.days_set[6]=event.getSat()==1?true:false;
        holder.toggle.setChecked(event.isActive());
        String s=event.mode;
        if(event.mode.equals("All")){
            holder.modebutton.setImageResource(R.drawable.all);
            holder.modebutton.setTag("All");
        }else if(event.mode.equals("Priority")){
            holder.modebutton.setImageResource(R.drawable.silent);
            holder.modebutton.setTag("Priority");
        }else if(event.mode.equals("None")){
            holder.modebutton.setImageResource(R.drawable.dnd);
            holder.modebutton.setTag("None");
        }
    }

    public int getColor(int i){
        if(i==1){
            return Color.RED;
        }
        return Color.BLACK;
    }

    @Override
    public int getItemCount() {
        return (null != eventList ? eventList.size() : 0);
    }

    class EventViewHolder extends RecyclerView.ViewHolder {
        EditText textView;
        TextView start_time;
        TextView end_time;
        Switch toggle;
        ImageButton imageButton;
        ImageButton modebutton;
        ImageButton weeklySchedule;
        boolean days_set[]= new boolean[7];
        /*TextView sunday;
        TextView tuesday;
        TextView wednesday;
        TextView thursday;
        TextView friday;
        TextView saturday;
        TextView monday;*/

        public EventViewHolder(View itemView) {
            super(itemView);
            textView = (EditText) itemView.findViewById(R.id.event_name);
            textView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
               /*     final String s1= (String) s;
                    AlertDialog.Builder builder = new AlertDialog.Builder(imageButton.getContext());
                    builder.setTitle("Update Name");
                    builder.setMessage("Do you want to save changes ?");
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            textView.setText(s1);
                            notifyDataSetChanged();
                        }
                    });
                    builder.show();*/
                }

                @Override
                public void afterTextChanged(final Editable s) {


                }
            });
            start_time = (TextView) itemView.findViewById(R.id.start_time);
            start_time.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int hour = 0, minutes = 0;
                    TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hour, int minutes) {
                            String time = getTime(hour, minutes);
                            if (start_time.getText() != time) {
                                start_time.setText(time);
                                //Update query here
                            }

                        }
                    }, hour, minutes, false);
                    timePickerDialog.show();

                }
            });

            end_time = (TextView) itemView.findViewById(R.id.end_time);
            end_time.setOnClickListener( new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int hour = 0  , minutes = 0;
                    TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hour, int minutes) {
                            String time=getTime(hour, minutes);
                            if(end_time.getText()!=time) {
                                end_time.setText(time);
                                //Update query here
                            }
                        }
                    } ,  hour, minutes, false);
                    timePickerDialog.show();
                }
            });
            modebutton= (ImageButton) itemView.findViewById(R.id.mode);
            modebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(modebutton.getTag().toString()=="All"){
                        modebutton.setImageResource(R.drawable.silent);
                        modebutton.setTag("Priority");
                    }else if(modebutton.getTag().toString()=="Priority"){
                        modebutton.setImageResource(R.drawable.dnd);
                        modebutton.setTag("None");
                    }else if(modebutton.getTag().toString()=="None"){
                        modebutton.setImageResource(R.drawable.all);
                        modebutton.setTag("All");
                    }

                }
            });
            imageButton = (ImageButton) itemView.findViewById(R.id.delete);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(imageButton.getContext());
                    builder.setTitle("Delete Event");
                    builder.setMessage("Do you want to remove event ?");
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            delete(getPosition());
                        }
                    });
                    builder.show();

                }
            });
            final CharSequence[] items = {" Sunday "," Monday "," Tuesday "," Wednesday " ," Thursday ",
            " Friday ", " Saturday "};
            final boolean temp[]= new boolean[7];
            for (int i=0;i<7;i++){
                temp[i]=days_set[i];
            }
            weeklySchedule = (ImageButton) itemView.findViewById(R.id.week_schedule);
            weeklySchedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(weeklySchedule.getContext());
                    builder.setTitle("Current Schedule");
                    builder.setMultiChoiceItems(items, days_set, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            if (isChecked) {
                                days_set[which] = true;
                            } else {
                                days_set[which] = false;
                            }
                        }
                    });

                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            for (int i=0;i<7;i++){
                                days_set[i]=temp[i];
                            }
                        }
                    });
                    builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            for (int i=0;i<7;i++){
                                temp[i]=days_set[i];
                            }
                            //Update query here
                        }
                    });
                    builder.show();
                }
            });

            toggle = (Switch) itemView.findViewById(R.id.event_switch);
            toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        eventList.get(getPosition()).setActive(true);
                    } else {
                        eventList.get(getPosition()).setActive(false);
                    }

                }
            });



            /*sunday = (TextView) itemView.findViewById(R.id.Sunday);
            sunday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sunday.getCurrentTextColor()== Color.BLACK) {
                        sunday.setTextColor(Color.RED);
                    }else {
                        sunday.setTextColor(Color.BLACK);
                    }
                }
            });
            monday = (TextView) itemView.findViewById(R.id.Monday);
            monday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (monday.getCurrentTextColor()== Color.BLACK) {
                        monday.setTextColor(Color.RED);
                    }else {
                        monday.setTextColor(Color.BLACK);
                    }

                }
            });
            tuesday = (TextView) itemView.findViewById(R.id.Tuesday);
            tuesday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tuesday.getCurrentTextColor()== Color.BLACK) {
                        tuesday.setTextColor(Color.RED);
                    }else {
                        tuesday.setTextColor(Color.BLACK);
                    }
                }
            });
            wednesday = (TextView) itemView.findViewById(R.id.Wednesday);
            wednesday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (wednesday.getCurrentTextColor()== Color.BLACK) {
                        wednesday.setTextColor(Color.RED);
                    }else {
                        wednesday.setTextColor(Color.BLACK);
                    }

                }
            });
            thursday = (TextView) itemView.findViewById(R.id.Thrusday);
            thursday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (thursday.getCurrentTextColor()== Color.BLACK) {
                        thursday.setTextColor(Color.RED);
                    }else {
                        thursday.setTextColor(Color.BLACK);
                    }

                }
            });
            friday = (TextView) itemView.findViewById(R.id.Friday);
            friday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (friday.getCurrentTextColor()== Color.BLACK) {
                        friday.setTextColor(Color.RED);
                    }else {
                        friday.setTextColor(Color.BLACK);
                    }
                }
            });
            saturday = (TextView) itemView.findViewById(R.id.Saturday);
            saturday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (saturday.getCurrentTextColor()== Color.BLACK) {
                        saturday.setTextColor(Color.RED);
                    }else {
                        saturday.setTextColor(Color.BLACK);
                    }

                }
            });*/
        }

        public String getTime(int hour,int minute){
            String am_pm = "AM";
            if (hour > 12) {
                am_pm = "PM";
                hour = hour % 12;
            }
            StringBuffer time= new StringBuffer();
            if(hour<10){
                time.append("0"+hour+":");
            }else{
                time.append(hour+":");
            }

            if(minute<10){
                time.append("0"+minute+" ");
            }else{
                time.append(minute+" ");
            }
            time.append(am_pm);
            return time.toString();
        }

    }



    public void addEvent(UserEvent event) {
        eventList.add(event);
        notifyDataSetChanged();
    }
}
