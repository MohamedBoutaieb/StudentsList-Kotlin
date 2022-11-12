package com.gl4.studentandroidapp.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.gl4.studentandroidapp.R
import com.gl4.studentandroidapp.enums.GenderEnum
import com.gl4.studentandroidapp.enums.StatusEnum
import com.gl4.studentandroidapp.models.Student
import java.util.*
import kotlin.collections.ArrayList

class StudentListAdapter(var students: ArrayList<Student>) :
    RecyclerView.Adapter<StudentListAdapter.ViewHolder>(), Filterable {
    var dataFilterList = ArrayList<Student>()
    var filterMode = 0;
    public fun set(n: Int) {
        filterMode = n;
    }

    init {
        dataFilterList = students
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.imageView)
        val imageView2 = itemView.findViewById<ImageView>(R.id.imageView2)
        val textView = itemView.findViewById<TextView>(R.id.textView)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StudentListAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.student_item, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(viewHolder: StudentListAdapter.ViewHolder, position: Int) {
        val student: Student = dataFilterList.get(position)
        val textView = viewHolder.textView
        val imageView = viewHolder.imageView
        val presenceView = viewHolder.imageView2
        presenceView.setOnClickListener {
            if (student.status == StatusEnum.present) {
                presenceView.setImageResource(R.drawable.cross)
                student.status = StatusEnum.absent
            } else {
                presenceView.setImageResource(R.drawable.check)
                student.status = StatusEnum.present
            }
        }
        textView.setText(student.lastName + ' ' + student.firstName)
        if (student.gender == GenderEnum.male) {
            imageView.setImageResource(R.drawable.man)
        } else {
            imageView.setImageResource(R.drawable.woman)
        }
        if (student.status == StatusEnum.present) {
            presenceView.setImageResource(R.drawable.check)
        } else {
            presenceView.setImageResource(R.drawable.cross)
        }
    }

    override fun getItemCount(): Int {
        return dataFilterList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    dataFilterList = students
                } else {
                    val resultList = ArrayList<Student>()

                    for (student in students) {
                        if (student.firstName.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT)) || student.lastName.lowercase(
                                Locale.ROOT
                            ).contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList.add(student)
                        }
                    }
                    dataFilterList = resultList
                }
                var data = ArrayList<Student>()
                println("$filterMode  this is filte rmode ")
                if (filterMode == 1) {
                    for (element in dataFilterList) {
                        if (element.status == StatusEnum.absent) {
                            data.add(element)
                        }
                    }
                } else if (filterMode == 2) {
                    for (element in dataFilterList) {
                        if (element.status == StatusEnum.present) {
                            data.add(element)
                        }
                    }
                } else {
                    data = dataFilterList
                }

                dataFilterList = data
                val filterResults = FilterResults()
                filterResults.values = dataFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                dataFilterList = results?.values as ArrayList<Student>
                notifyDataSetChanged()
            }

        }
    }
}