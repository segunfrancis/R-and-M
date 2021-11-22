package com.segunfrancis.randm.ui.episode_detail

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.segunfrancis.randm.R
import com.segunfrancis.randm.databinding.FragmentEpisodeDetailBinding

class EpisodeDetailFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentEpisodeDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodeDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val bottomSheet =
                dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            val params = bottomSheet.layoutParams
            val height = Resources.getSystem().displayMetrics.heightPixels
            val maxHeight = (height * 1.0).toInt()
            params.height = maxHeight
            bottomSheet.layoutParams = params
        }
        return dialog
    }

    override fun getTheme(): Int = R.style.ModalBottomSheetDialog
}
