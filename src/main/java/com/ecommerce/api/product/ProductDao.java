package com.ecommerce.api.product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductDao {

    private final EntityManager em;
    private final ProductRepository productRepository;

    public List<Product> findAllByCriteria(GetAllProductRequest request) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();

        if (request.getProductName() != null) {
            Predicate partialNamePredicate = criteriaBuilder
                    .like(root.get("productName"), "%" + request.getProductName() + "%");
            predicates.add(partialNamePredicate);
        }

        if (request.getSku() != null) {
            Predicate skuPredicate = criteriaBuilder
                    .equal(root.get("sku"), request.getSku());
            predicates.add(skuPredicate);
        }

        if ((request.getLowPrice() != 0) && (request.getHighPrice() != 0)) {
            Predicate greaterThanPricePredicate = criteriaBuilder
                    .greaterThan(root.get("price"), request.getLowPrice());
            Predicate lessThanPricePredicate = criteriaBuilder
                    .lessThan(root.get("price"), request.getHighPrice());

            Predicate pricePredicate = criteriaBuilder.and(greaterThanPricePredicate, lessThanPricePredicate);
            predicates.add(pricePredicate);
        }

        if (!predicates.isEmpty()){
            criteriaQuery.where(
                    criteriaBuilder.and(predicates.toArray(predicates.toArray(new Predicate[0])))
            );
        }
        TypedQuery<Product> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }


    public static Specification<Product> filterBySQU( String sku ) {

        if ( sku == null )
            return Specification.where( null );

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("sku"), sku );
    }

    public List<Product> findAllBySpecification(GetAllProductRequest request) {
        Specification<Product> specification = filterBySQU(request.getSku() );
        return productRepository.findAll( specification );
    }
}
