package com.eltae.compareTout.services;

import com.eltae.compareTout.converter.ProductConverter;
import com.eltae.compareTout.dto.CriteriaProductDto;
import com.eltae.compareTout.dto.ShortProductDto;
import com.eltae.compareTout.entities.CriteriaProduct;
import com.eltae.compareTout.entities.Product;
import com.eltae.compareTout.exceptions.BadCsvLine;
import com.eltae.compareTout.exceptions.WrongParameters;
import com.eltae.compareTout.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

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
                List<Product> myProducts=new ArrayList<Product>();
                myProducts= productRepository.findAllByCategoryId(id);
                List<Product> filterProduct =new ArrayList<Product>();

                for(Product p:myProducts)

                    if(p.getCrit().containsAll(crit))
                        filterProduct.add(p);

        return productConverter.ListEntityToShortDto(filterProduct);

    }

    public List<ShortProductDto> getProductsStrictCriteria(Long id, Long[] idCrit, String[] valuesCrit) {

        if(id==null || idCrit.length!=valuesCrit.length)
        throw new WrongParameters(HttpStatus.resolve(566), "you must define a category id and two list one with " +
                "criterias id and another with values of criterias. Both list have same size ");
        else {
            List<ShortProductDto> shortProductListFinal = new ArrayList<ShortProductDto>();
            List<Long> keysList = new ArrayList<>();
            keysList = new ArrayList(Arrays.asList(idCrit));
            boolean add = true;
            List<ShortProductDto> shortProductList = this.getProductsCriteria(id, keysList);

            for (ShortProductDto shortP : shortProductList) {
                List<CriteriaProductDto> critProd = shortP.getCriteriaProducts();
                for (CriteriaProductDto p : critProd) {
                    for (int i = 0; i < idCrit.length; i++) {
                        if (p.getId() == idCrit[i]) {
                            if (!valuesCrit[i].equals(p.getValue())) {
                                add = false;
                            }
                        }
                    }
                }
                if (add)
                    shortProductListFinal.add(shortP);
            }
            return shortProductListFinal;
        }
    }


}
