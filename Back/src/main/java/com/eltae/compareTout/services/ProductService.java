package com.eltae.compareTout.services;

import com.eltae.compareTout.converter.ProductConverter;
import com.eltae.compareTout.dto.CriteriaProductDto;
import com.eltae.compareTout.dto.ShortProductDto;
import com.eltae.compareTout.entities.*;
import com.eltae.compareTout.repositories.CategoryRepository;
import com.eltae.compareTout.repositories.CriteriaProductRepository;
import com.eltae.compareTout.repositories.ProductRepository;
import com.eltae.compareTout.repositories.product.ProductCriteria;
import com.eltae.compareTout.repositories.product.ProductSpecification;
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
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductConverter productConverter, CategoryService categoryService, CriteriaService criteriaService, CriteriaProductRepository criteriaProductRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
        this.categoryService = categoryService;
        this.criteriaService = criteriaService;
        this.criteriaProductRepository = criteriaProductRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ShortProductDto> getAll() {
        return productConverter.listEntityToShortDto(this.productRepository.findAll());
    }

    public List<ShortProductDto> getAllProductsByCategory(long idCategory) {
        return productConverter.listEntityToShortDto(this.productRepository.findAllByCategoryId(idCategory));
    }

    public List<CriteriaProductDto> getAllCriteriaByProduct(long idProduct) {
        return productConverter.entityListToDtoList(this.productRepository.findProductById(idProduct)).get(0).getCriteriaProducts();
    }

    public List<ShortProductDto> getAllProductByCategoryAndCriteria(long categoryId, List<Long> idCriteria) {
        return productConverter.listEntityToShortDto(this.productRepository.findAll(
                new ProductSpecification(
                        new ProductCriteria(categoryId, idCriteria)
                )
        ));
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

        Category category = categoryService.getCategoryWithId(Long.parseLong(records[3]));
        if (category == null) {
            System.out.println("La catégorie indiquée n'existe pas dans la base (lors de l'ajout du produit " + records[0] + ")");
        }

        Product product = Product.builder()
                .category(category)
                .name(records[0].toLowerCase())
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
                    System.out.println("Le critère indiqué n'existe pas dans la base (lors de l'ajout du produit " + product.getName() + ")");
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
        //category.addProduct(product);
        // categoryRepository.save(category);

        productRepository.save(product);
        productRepository.flush();
        return true;
    }

}