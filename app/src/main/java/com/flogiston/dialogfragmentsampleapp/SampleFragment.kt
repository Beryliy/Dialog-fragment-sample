package com.flogiston.dialogfragmentsampleapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.flogiston.dialogfragmentsampleapp.SampleDialogFragment.Companion.SAMPLE_DIALOG_TAG
import com.flogiston.dialogfragmentsampleapp.databinding.FragmentSampleBinding

class SampleFragment : Fragment() {
  private var _binding: FragmentSampleBinding? = null
  private val binding: FragmentSampleBinding
    get() = _binding!!


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    childFragmentManager.setFragmentResultListener("1", this) { key, bundle ->
      Toast.makeText(requireContext(), "result trigered", Toast.LENGTH_SHORT).show()
      (childFragmentManager.findFragmentByTag(SAMPLE_DIALOG_TAG) as SampleDialogFragment)
        .changeButtonText()
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    // Inflate the layout for this fragment
    _binding = FragmentSampleBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.showDialogB.setOnClickListener {
      val dialog = SampleDialogFragment.newInstance("sample text")
      dialog.show(childFragmentManager, SAMPLE_DIALOG_TAG)
    }
  }

  override fun onDestroyView() {
    _binding = null
    super.onDestroyView()
  }

  companion object {
    const val SAMPLE_FRAGMENT_ACTION = "sample_fragment_action"
  }
}