package com.enesgemci.dubbizle.moviedetail.ui

import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.enesgemci.dubbizle.R
import com.enesgemci.dubbizle.core.base.BaseFragment
import com.enesgemci.dubbizle.core.dagger.component.FragmentComponent
import com.enesgemci.dubbizle.core.extension.getFormattedDate
import com.enesgemci.dubbizle.core.extension.underline
import com.enesgemci.dubbizle.core.navigation.AutoLayoutNavigationBuilder.navigation
import com.enesgemci.dubbizle.core.navigation.NavigationBuilder
import com.enesgemci.dubbizle.core.navigation.ToolbarStyle
import com.enesgemci.dubbizle.helper.GlideApp
import com.enesgemci.dubbizle.moviedetail.ui.model.ui.MovieDetailsUIModel
import com.enesgemci.dubbizle.movies.ui.model.ui.MovieUIModel
import kotlinx.android.synthetic.main.fragment_movie_details.*

class MovieDetailsFragment : BaseFragment<MovieDetailsUIView, MovieDetailsUIPresenter>(), MovieDetailsUIView {

    private var movie: MovieUIModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let { ActivityCompat.postponeEnterTransition(it) }

    }

    override fun onFragmentStarted() {
        arguments?.let {
            movie = it.getParcelable(MOVIE)

            movie?.let {

                GlideApp.with(this)
                    .load(movie?.posterPath)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageViewPoster)

                setToolbarTitle(it.title)
                textViewOriginalTitle.text = it.originalTitle
                textViewReleaseDate.text = it.releaseDate.getFormattedDate()

                activity?.let { ActivityCompat.startPostponedEnterTransition(it) }

                presenter.getMovieDetails(it.id)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (movieDetailsGroup.isVisible) {
            loadingView.isVisible = false
        }
    }

    override fun injectFragment(component: FragmentComponent) {
        component.inject(this)
    }

    override fun buildNavigation(): NavigationBuilder<*> {
        return navigation(R.layout.fragment_movie_details)
            .includeToolbar()
            .toolbarNavigationIcon(ToolbarStyle.NavigationIconType.BACK)
    }

    override fun setMovieDetails(movieDetailsUIModel: MovieDetailsUIModel) {

        setToolbarTitle(movieDetailsUIModel.title)

        textViewTitle.text = movieDetailsUIModel.title
        textViewOriginalTitle.text = movieDetailsUIModel.originalTitle
        textViewReleaseDate.text = movieDetailsUIModel.releaseDate.getFormattedDate()
        textViewOverview.text = movieDetailsUIModel.overview
        textViewImdb.text = movieDetailsUIModel.imdbUrl.underline()

        loadingView.isVisible = false
        movieDetailsGroup.isVisible = true
    }

    companion object {

        private const val MOVIE = "MOVIE"

        fun newInstance(movie: MovieUIModel) = MovieDetailsFragment().apply {
            arguments = bundleOf(MOVIE to movie)
        }
    }
}