package com.eltae.compareTout.services;

import com.eltae.compareTout.converter.ProductConverter;
import com.eltae.compareTout.dto.CriteriaProductDto;
import com.eltae.compareTout.dto.ShortProductDto;
import com.eltae.compareTout.entities.Criteria;
import com.eltae.compareTout.entities.CriteriaProduct;
import com.eltae.compareTout.entities.CriteriaProductPK;
import com.eltae.compareTout.entities.Product;
import com.eltae.compareTout.repositories.CriteriaProductRepository;
import com.eltae.compareTout.repositories.ProductRepository;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private final CategoryService categoryService;
    private final CriteriaService criteriaService;
    private final CriteriaProductRepository criteriaProductRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductConverter productConverter, CategoryService categoryService, CriteriaService criteriaService, CriteriaProductRepository criteriaProductRepository) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
        this.categoryService = categoryService;
        this.criteriaService = criteriaService;
        this.criteriaProductRepository = criteriaProductRepository;
    }

    public List<ShortProductDto> getAll() {
        return productConverter.ListEntityToShortDto(this.productRepository.findAll());
    }

    public List<ShortProductDto> getAllProductsByCategory(long idCategory) {
        return productConverter.ListEntityToShortDto(this.productRepository.findAllByCategoryId(idCategory));
    }

    public List<CriteriaProductDto> getAllCriteriaByProduct(long idProduct) {
        return productConverter.entityListToDtoList(this.productRepository.findProductById(idProduct)).get(0).getCriteriaProducts();
    }


    public int insertProductsFromFile(MultipartFile multipartFile) {
        try {
            return this.readProductsCSV(multipartFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }


    private int readProductsCSV(InputStream inputStream) throws IOException {
        CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8), ';');
        String[] nextRecord = csvReader.readNext();
        int nbLineAdd = 0;
        while ((nextRecord = csvReader.readNext()) != null) if (this.insertProduct(nextRecord)) nbLineAdd++;
        return nbLineAdd;
    }

    private boolean insertProduct(String[] records) {
        String actualColumn;
        CriteriaProduct cp;
        Product product = Product.builder()
                .category(categoryService.getCategoryWithId(Long.parseLong(records[3])))
                .name(records[0])
                .description(records[1])
                .supplierLink(records[2])
                .criteriaProducts(new ArrayList<>())
                .build();
        productRepository.save(product);
        for (int i = 4; i < records.length; i++) {
            actualColumn = records[i];
            if (actualColumn.trim().length() == 0) return false;
            if (i % 2 == 0) {
                cp = null;
                Criteria criteria = criteriaService.getCriteriaProductWithIdCriteria(Long.parseLong(actualColumn));
                if (criteria == null) {
                    System.out.println("Le crit n'a pas été trouvé");
                    return false; // criteria non trouvé en base
                } else {
                    CriteriaProductPK primaryKey = new CriteriaProductPK();
                    primaryKey.setCriteria(criteria);
                    primaryKey.setProduct(product);
                    cp = CriteriaProduct.builder()
                            .pk(primaryKey)
                            .value(records[i + 1])
                            .build();
                    product.addCriteriaProduct(cp);
                    criteriaProductRepository.save(cp);
                }
            }
        }
        productRepository.save(product);
        return true;
    }

}