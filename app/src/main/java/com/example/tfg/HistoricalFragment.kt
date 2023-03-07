package com.example.tfg


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tfg.controllers.*
import com.example.tfg.databinding.FragmentHistoricalBinding
import com.example.tfg.models.Datos
import java.util.*

@Suppress("DEPRECATION")
class HistoricalFragment : Fragment() {
    private var _binding: FragmentHistoricalBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoricalBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hasMap: HashMap<String, Int> = hashMapOf()
        hasMap!!["configuration"] = R.id.action_historicalFragment_to_ConfigurationFragment
        hasMap["stadistics"] = R.id.action_historicalFragment_to_stadisticsFragment
        hasMap["historical"] = R.id.action_historicalFragment_self

        val motionLayout: MotionLayout = view.findViewById(R.id.motion)
        val controllerMotionLayout = ControllerMotionLayout(motionLayout,findNavController(),hasMap)
        //Inicialization of reciclerView of the main View of this screen
        val recyclerView = binding.recycler
        recyclerView.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = HistoricalAdapter(this.context!!, demoList())
    }
    //Function demo for addd information for testing class adapter
    fun demoList(): MutableList<Datos> {
        return mutableListOf(
            Datos(Date(2002, 1, 1, 10, 30, 15), 60, 2, null, 10, true),
            Datos(Date(84, 6, 1, 15, 30, 15), 70, 5, true, null, true),
            Datos(Date(2012, 4, 1, 24, 30, 15), 80, null, false, 100, true),
            Datos(Date(94, 7, 1, 3, 30, 15), 90, 3, false, 45, null),
            Datos(Date(2010, 2, 1, 9, 30, 1), 100, null, false, null, true),
            Datos(Date(2003, 10, 1, 12, 30, 15), 110, 7, true, null, true),
            Datos(Date(2004, 10, 1, 12, 30, 15), 120, 7, true, null, true),
            Datos(Date(2005, 10, 1, 12, 30, 15), 130, 7, true, null, true),
            Datos(Date(2006, 10, 1, 12, 30, 15), 140, 7, true, null, true),
            Datos(Date(2007, 10, 1, 12, 30, 15), 150, 7, true, null, true),
            Datos(Date(2008, 10, 1, 12, 30, 15), 160, 7, true, null, true),
            Datos(Date(2003, 10, 1, 12, 30, 15), 170, 7, true, null, true),
            Datos(Date(2003, 10, 1, 12, 30, 15), 180, 7, true, null, true),
            Datos(Date(2003, 10, 1, 12, 30, 15), 190, 7, true, null, true),
            Datos(Date(2003, 10, 1, 12, 30, 15), 200, 7, true, null, true),
            Datos(Date(2003, 10, 1, 12, 30, 15), 65, 7, false, null, true)

        )
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}