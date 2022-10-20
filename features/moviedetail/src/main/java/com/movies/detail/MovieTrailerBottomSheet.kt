package com.movies.detail

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.movies.detail.databinding.BottomSheetMoviewTrailerBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class MovieTrailerBottomSheet : BottomSheetDialogFragment() {

    companion object {
        private const val VIDEO_YOUTUBE_KEY = "videoYoutubeKey"
        fun newInstance(videoYoutubeKey: String) = MovieTrailerBottomSheet().apply {
            arguments = Bundle().apply {
                putString(VIDEO_YOUTUBE_KEY, videoYoutubeKey)
            }
        }
    }

    private var _binding: BottomSheetMoviewTrailerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener { setupBottomSheet(it) }
        return dialog
    }

    private fun setupBottomSheet(dialogInterface: DialogInterface) {
        val bottomSheetDialog = dialogInterface as BottomSheetDialog
        val bottomSheet = bottomSheetDialog.findViewById<View>(
            com.google.android.material.R.id.design_bottom_sheet
        )
            ?: return
        bottomSheet.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
        bottomSheet.setBackgroundColor(Color.TRANSPARENT)
        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.peekHeight = resources.displayMetrics.heightPixels
        view?.requestLayout()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetMoviewTrailerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setYoutubeVideoPlayer()
        binding.closeBottomSheet.setOnClickListener {
            binding.youtubePlayerView.release()
            dialog?.dismiss()
        }
    }

    private fun setYoutubeVideoPlayer() {
        val videoYoutubeKey = arguments?.getString(VIDEO_YOUTUBE_KEY).orEmpty()

        lifecycle.addObserver(binding.youtubePlayerView)
        binding.youtubePlayerView.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                youTubePlayer.loadVideo(videoYoutubeKey, 0F)
            }
        })
    }

}