package rhett.pezzuti.dailydose.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.favorites_fragment.*
import rhett.pezzuti.dailydose.viewmodels.HomeViewModel
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.databinding.HomeFragmentBinding
import rhett.pezzuti.dailydose.factory.HomeViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModelFactory: HomeViewModelFactory
    private lateinit var binding: HomeFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    /**
     *                              TODO BLOCK
     *
     * - Use Firebase as backend.
     *
     *
     *
     *
     *
     *
     *
     *
     *
     */

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.home_fragment,
            container,
            false
        )
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding.lifecycleOwner = this


        viewModel.eventFavorites.observe(viewLifecycleOwner, { event ->
            if (event == true){
                // TODO: Why is the Direction not generating?
            }
        })



        return binding.root
    }
}





class SongAdapter : RecyclerView.Adapter<SongAdapter.ViewHolder>(){

    var data = listOf(1,2,3)
        set(value){
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val view = layoutInflater.inflate(
            R.layout.song_list_item, parent, false
        )

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.songArtist.text = item.toString()
    }

    override fun getItemCount() = data.size


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val songImage: ImageView = itemView.findViewById(R.id.song_item_image)
        val songTitle: TextView = itemView.findViewById(R.id.song_item_track_title)
        val songArtist: TextView = itemView.findViewById(R.id.song_item_track_artist)
        val songFavorite: FloatingActionButton = itemView.findViewById(R.id.song_item_favorite_fab)
    }

}