package com.hibernet.movieSystem.service;

import com.hibernet.movieSystem.NotFoundException;
import com.hibernet.movieSystem.entity.Award;
import com.hibernet.movieSystem.entity.Cast;
import com.gfg.jbdl14restfulapi.model.*;
import com.hibernet.movieSystem.entity.Movie;
import com.hibernet.movieSystem.model.*;
import com.hibernet.movieSystem.repository.CastRepository;
import com.hibernet.movieSystem.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    MovieRepository movieRepository;

    @Autowired
    CastRepository castRepository;

    @Override
    public void create(MovieRequest movieRequest) {
        List<CastRequest> castRequests = movieRequest.getCastRequest();
        List<Cast> casts = new ArrayList<>();
        List<Award> awards = new ArrayList<>();


        for(CastRequest castRequest : castRequests){
            casts.add(castRepository.findByName(castRequest.getName()).orElse(Cast.builder().name(castRequest.getName()).build()));
        }
        for(AwardRequest awardRequest : movieRequest.getAwardRequests()){
            awards.add(Award.builder().name(awardRequest.getName()).year(awardRequest.getYear()).build());
        }

        Movie movie = Movie.builder()
                .genre(movieRequest.getGenre())
                .language(movieRequest.getLanguage())
                .movieName(movieRequest.getName())
                .casts(casts)
                .awards(awards)
                .build();

        movieRepository.save(movie);
    }

    @Override
    public MovieResponse get(String name) throws NotFoundException {
        Movie movie = movieRepository.findByMovieName(name)
                .orElseThrow(()-> new NotFoundException("movie name not found"));

        List<CastResponse> castResponses = new ArrayList<>();
        for(Cast cast : movie.getCasts()){
            castResponses.add(CastResponse.builder().name(cast.getName()).build());
        }
        List<AwardResponse> awardResponses = new ArrayList<>();
        for(Award award : movie.getAwards()){
            awardResponses.add(AwardResponse.builder().name(award.getName()).year(award.getYear()).build());
        }

        return MovieResponse.builder()
                .genre(movie.getGenre())
                .name(movie.getMovieName())
                .language(movie.getLanguage())
                .castResponse(castResponses)
                .awardResponses(awardResponses)
                .build();
    }

    @Override
    public void update(String name, MovieRequest movieRequest) throws NotFoundException {
        Movie movie = movieRepository.findByMovieName(name)
                .orElseThrow(()-> new NotFoundException("movie name not found"));
        if(movieRequest.getGenre() != null)
            movie.setGenre(movieRequest.getGenre());
        if(movieRequest.getLanguage() !=null)
            movie.setLanguage(movieRequest.getLanguage());
        movieRepository.save(movie);
    }

    @Override
    public void delete(String name) throws NotFoundException {
        Movie movie = movieRepository.findByMovieName(name)
                .orElseThrow(()-> new NotFoundException("movie name not found"));
        if(movie!=null) {
            movieRepository.delete(movie);
        }
    }
}
