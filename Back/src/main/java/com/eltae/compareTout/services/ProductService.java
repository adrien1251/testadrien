package com.eltae.compareTout.services;

import com.eltae.compareTout.converter.product.ProductConverter;
import com.eltae.compareTout.converter.product.ProductForFrontConverter;
import com.eltae.compareTout.dto.CriteriaFilterDto;
import com.eltae.compareTout.dto.CriteriaProductDto;
import com.eltae.compareTout.dto.product.ProductDtoForFront;
import com.eltae.compareTout.dto.product.ShortProductDto;
import com.eltae.compareTout.entities.*;
import com.eltae.compareTout.exceptions.ApplicationException;
import com.eltae.compareTout.repositories.CategoryRepository;
import com.eltae.compareTout.repositories.CriteriaProductRepository;
import com.eltae.compareTout.repositories.CriteriaRepository;
import com.eltae.compareTout.repositories.ProductRepository;
import com.eltae.compareTout.repositories.product.CriteriaFilter;
import com.eltae.compareTout.repositories.product.CriteriaFilterSpecification;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
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
    private final ProductForFrontConverter productForFrontConverter;
    private final CategoryService categoryService;
    private final CriteriaService criteriaService;
    private final CriteriaProductRepository criteriaProductRepository;
    private final CategoryRepository categoryRepository;

    private final CriteriaRepository criteriaRepository;
    private final EntityManagerFactory entityManagerFactory;
    @Autowired
    public ProductService(ProductRepository productRepository, ProductConverter productConverter, ProductForFrontConverter productForFrontConverter, CategoryService categoryService, CriteriaService criteriaService, CriteriaProductRepository criteriaProductRepository, CategoryRepository categoryRepository, CriteriaRepository criteriaRepository, EntityManagerFactory entityManagerFactory) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
        this.productForFrontConverter = productForFrontConverter;
        this.categoryService = categoryService;
        this.criteriaService = criteriaService;
        this.criteriaProductRepository = criteriaProductRepository;
        this.categoryRepository = categoryRepository;
        this.criteriaRepository = criteriaRepository;
        this.entityManagerFactory = entityManagerFactory;
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

    public List<ProductDtoForFront> getAllProductByCategoryAndCriteria(long categoryId, List<CriteriaFilterDto> idCriteria) {
        List<OurCriteria> criteriaBuilders = new ArrayList<>();
        idCriteria.forEach(cb -> {
            Criteria criteria = criteriaRepository
                    .findById(cb.getIdCriteria())
                    .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "Criteria " + cb.getIdCriteria() + "not found"));

            criteriaBuilders.add(OurCriteria.builder()
                    .criteria(criteria)
                    .value(cb.getValue())
                    .minValue(cb.getMinValue())
                    .maxValue(cb.getMaxValue())
                    .build());
        });

        Category category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "Category " + categoryId + "not found"));

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Product> query = criteriaBuilder.createQuery(Product.class);
        Root<Product> productRoot = query.from(Product.class);
        Join<Product, CriteriaProduct> productJoin = productRoot.join("criteriaProducts", JoinType.LEFT);

        query.where(new CriteriaFilterSpecification(
                new CriteriaFilter(category, criteriaBuilders)
        ).toPredicate(productJoin, criteriaBuilder));

        query.groupBy(productRoot.get("id"));
        query.multiselect(productRoot);
        query.having(criteriaBuilder.equal(criteriaBuilder.count(productRoot.get("id")), idCriteria.size()));
        TypedQuery<Product> typedQuery = entityManager.createQuery(query);
        return productForFrontConverter.entityListToDtoList(typedQuery.getResultList());
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