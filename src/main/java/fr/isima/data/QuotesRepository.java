package fr.isima.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotesRepository extends CrudRepository<QuoteBean, Integer> {

}
