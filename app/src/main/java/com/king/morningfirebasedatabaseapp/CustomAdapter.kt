package com.king.morningfirebasedatabaseapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class CustomAdapter(var context: Context, var data:ArrayList<User>):BaseAdapter() {
    private class ViewHolder(row:View?){
        var mTxtName:TextView
        var mTxtNumber:TextView
        var mTxtEmail:TextView
        init {
            this.mTxtName = row?.findViewById(R.id.mTvName) as TextView
            this.mTxtNumber = row?.findViewById(R.id.mTvIdnumber) as TextView
            this.mTxtEmail = row?.findViewById(R.id.mTvEmail) as TextView
        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view:View?
        var viewHolder:ViewHolder
        if (convertView == null){
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.user_layout,parent,false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var item:User= getItem(position) as User
        viewHolder.mTxtName.text = item.name
        viewHolder.mTxtNumber.text = item.IdNumber
        viewHolder.mTxtEmail.text = item.email
        return view as View
    }

    override fun getItem(position: Int): Any {
        return  data.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.count()
    }
}