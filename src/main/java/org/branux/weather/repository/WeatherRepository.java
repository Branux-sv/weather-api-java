package org.branux.weather.repository;

import org.branux.weather.model.Weather;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface WeatherRepository extends JpaRepository<Weather, Integer> {

    @Query("select w from Weather w where (upper(w.city) in :cities or :cities is null) and (w.date = :date or :date is null)")
    List<Weather> findAllByCitiesOrDate(@Param("cities") List<String> cities, @Param("date") Date date, Sort sort);

    //@Query("select w from Weather w where (upper(w.city) in :cities or :cities is null) and (w.date = :date or :date is null)")
    //Page<Weather> findAllByCitiesOrDate(@Param("cities") List<String> cities, @Param("date") Date date, Sort sort, Pageable page);
}
