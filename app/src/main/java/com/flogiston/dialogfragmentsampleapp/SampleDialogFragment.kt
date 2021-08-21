package com.flogiston.dialogfragmentsampleapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import com.flogiston.dialogfragmentsampleapp.SampleFragment.Companion.SAMPLE_FRAGMENT_ACTION
import com.flogiston.dialogfragmentsampleapp.databinding.DialogSampleBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SampleDialogFragment : DialogFragment() {
  private lateinit var binding: DialogSampleBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.dialog_sample, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding = DialogSampleBinding.bind(view)
    with(binding) {
      dialogTextTv.text = arguments?.getString(DIALOG_TEXT)
      savedInstanceState?.getString(BUTTON_TEXT)?.let { text -> actionB.text = text }
      actionB.setOnClickListener {
        setFragmentResult("1", bundleOf())
        lifecycleScope.launch {
          delay(3000L)
          dismiss()
        }
      }
    }
  }

  override fun onStart() {
    super.onStart()
    dialog?.window?.setLayout(
      ViewGroup.LayoutParams.MATCH_PARENT,
      ViewGroup.LayoutParams.MATCH_PARENT
    )
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    outState.putString(BUTTON_TEXT, binding.actionB.text.toString())
  }

  fun changeButtonText() = with(binding.actionB){
    text = if(text == "done") "action" else "done"
  }

  companion object {
    const val SAMPLE_DIALOG_TAG = "sample_dialog"
    const val DIALOG_TEXT = "dialog_text"
    private const val BUTTON_TEXT = "button_text"

    fun newInstance(text: String) =
      SampleDialogFragment().apply {
        arguments = bundleOf(DIALOG_TEXT to text)
      }
  }
}