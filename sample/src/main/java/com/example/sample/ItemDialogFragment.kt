package com.example.sample

import android.app.Dialog
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.comicsforall.AddAccessibilityItem
import com.example.comicsforall.DeleteAccessibilityItem
import com.example.comicsforall.model.AccessibilityItem
import com.example.sample.databinding.ItemDialogBinding

class ItemDialogFragment(
    private val item: AccessibilityItem
) : DialogFragment() {

    private lateinit var binding: ItemDialogBinding
    private lateinit var listener: TextDialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val layout = ItemDialogBinding.inflate(layoutInflater).run {
            binding = this
            root
        }

        binding.apply {
            txtDescription.setText(item.text)
            txtNumber.setText(item.position?.toString())
        }

        return AlertDialog.Builder(requireContext())
            .setView(layout)
            .setTitle(getString(R.string.title_text_description))
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                listener.onSendEvent(
                    AddAccessibilityItem(
                        AccessibilityItem(
                            text = binding.txtDescription.text.toString(),
                            rect = item.rect,
                            position = binding.txtNumber.text.toString().toInt()
                        )
                    )
                )
            }
            .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                dismiss()
            }
            .setNeutralButton(getString(R.string.delete)) { _, _ ->
                listener.onSendEvent(DeleteAccessibilityItem(item))
            }
            .create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as TextDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                (context.toString() +
                        " must implement TextDialogListener")
            )
        }
    }

    companion object {
        const val TAG = "ItemDialogFragment"
    }
}