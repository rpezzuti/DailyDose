package rhett.pezzuti.dailydose.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.database.TrackData

class TrackAdapter : RecyclerView.Adapter<ViewHolder>(){

    var data = listOf<TrackData>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.song_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}

class ViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
    val songName: TextView = itemView.findViewById(R.id.song_item_track_title)
    val songArtist: TextView = itemView.findViewById(R.id.song_item_track_artist)
    val songAlbumImage: ImageView = itemView.findViewById(R.id.song_item_album_image)
}