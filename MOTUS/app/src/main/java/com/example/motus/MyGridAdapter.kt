import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.motus.R

class MyGridAdapter(context: Context, private var data: MutableList<MutableList<String>>) : BaseAdapter() {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    fun updateData(newData: MutableList<MutableList<String>>) {
        this.data = newData
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return data.size * data[0].size
    }

    override fun getItem(position: Int): Any {
        val row = position / data[0].size
        val col = position % data[0].size
        return data[row][col]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val vh: ViewHolder

        if (convertView == null) {
            view = mInflater.inflate(R.layout.grid_item, parent, false)
            vh = ViewHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ViewHolder
        }

        vh.textView.text = getItem(position) as String

        return view
    }

    private class ViewHolder(view: View) {
        val textView: TextView = view.findViewById(R.id.grid_item_text)
    }
}
