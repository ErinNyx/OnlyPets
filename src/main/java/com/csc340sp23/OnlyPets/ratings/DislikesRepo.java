package com.csc340sp23.OnlyPets.ratings;

import com.csc340sp23.OnlyPets.ratings.Dislikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DislikesRepo extends JpaRepository<Dislikes, Integer> {

}