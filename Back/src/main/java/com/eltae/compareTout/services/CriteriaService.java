package com.eltae.compareTout.services;

import com.eltae.compareTout.converter.ProductConverter;
import com.eltae.compareTout.dto.ShortProductDto;
import com.eltae.compareTout.entities.Product;
import com.eltae.compareTout.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
public class CriteriaService {

    private final ProductConverter productConverter;
    private final ProductRepository productRepository;

    @Autowired
    public CriteriaService(ProductConverter prodConv ,ProductRepository prodRep) {

        this.productRepository=prodRep;
        this.productConverter=prodConv;
    }

    public List<ShortProductDto> getProductsCriteria(Long id, List<Long> crit) {
        System.out.println("je suis entree");
                List<Product> myProducts=new ArrayList<Product>();
                myProducts= productRepository.findAllByCategoryId(id);
                System.out.println(myProducts.size());
                List<Product> filterProduct =new ArrayList<Product>();

                for(Product p:myProducts)
                    if(p.getCrit().containsAll(crit))
                        filterProduct.add(p);

        return productConverter.ListEntityToShortDto(filterProduct);

    }
}
