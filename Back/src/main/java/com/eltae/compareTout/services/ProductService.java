package com.eltae.compareTout.services;

import com.eltae.compareTout.converter.product.ProductConverter;
import com.eltae.compareTout.converter.product.ProductForFrontConverter;
import com.eltae.compareTout.dto.criteria.CriteriaFilterDto;
import com.eltae.compareTout.dto.criteria.CriteriaProductDto;
import com.eltae.compareTout.dto.product.ProductDto;
import com.eltae.compareTout.dto.product.ProductDtoForFront;
import com.eltae.compareTout.dto.product.ShortProductDto;
import com.eltae.compareTout.entities.*;
import com.eltae.compareTout.exceptions.ApplicationException;
import com.eltae.compareTout.repositories.*;
import com.eltae.compareTout.repositories.product.CriteriaFilter;
import com.eltae.compareTout.repositories.product.CriteriaFilterSpecification;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.json.simple.JSONObject;
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
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
    private final InsertionErrorsRepository insertionErrorsRepository;
    private Map<Integer, String> errorMap;
    private Map<Integer, String> criteriaMap;

    @Autowired
    public ProductService(SupplierService supplierService, ProductRepository productRepository, ProductConverter productConverter, ProductForFrontConverter productForFrontConverter, CategoryService categoryService, CriteriaService criteriaService, CriteriaProductRepository criteriaProductRepository, CategoryRepository categoryRepository, CriteriaRepository criteriaRepository, EntityManagerFactory entityManagerFactory, InsertionErrorsRepository insertionErrorsRepository) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
        this.productForFrontConverter = productForFrontConverter;
        this.categoryService = categoryService;
        this.criteriaService = criteriaService;
        this.criteriaProductRepository = criteriaProductRepository;
        this.categoryRepository = categoryRepository;
        this.criteriaRepository = criteriaRepository;
        this.entityManagerFactory = entityManagerFactory;
        this.insertionErrorsRepository = insertionErrorsRepository;
    }

    public List<ShortProductDto> getAll() {
        return productConverter.listEntityToShortDto(this.productRepository.findAll());
    }

    public List<ProductDtoForFront> getAllProductsByCategory(long idCategory) {
        categoryRepository
                .findById(idCategory)
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "Category " + idCategory + " not found"));
        return productForFrontConverter.entityListToDtoList(this.productRepository.findAllByCategoryId(idCategory));
    }

    public List<CriteriaProductDto> getAllCriteriaByProduct(long idProduct) {
        productRepository
                .findById(idProduct)
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "Product " + idProduct + " not found"));
        return productConverter.entityListToDtoList(this.productRepository.findProductById(idProduct)).get(0).getCriteriaProducts();
    }

    public List<ProductDtoForFront> getAllProductByCategoryAndCriteria(long categoryId, List<CriteriaFilterDto> criteriaFilterDtos) {
        Category category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "Category " + categoryId + " not found"));

        List<OurCriteria> criteriaBuilders = new ArrayList<>();
        criteriaFilterDtos.forEach(cb -> {
            Criteria criteria = criteriaRepository
                    .findById(cb.getIdCriteria())
                    .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "Criteria " + cb.getIdCriteria() + " not found"));

            criteriaBuilders.add(OurCriteria.builder()
                    .criteria(criteria)
                    .value(cb.getValue() == null ? null : cb.getValue().stream().map(String::toLowerCase).collect(Collectors.toList()))
                    .minValue(cb.getMinValue())
                    .maxValue(cb.getMaxValue())
                    .build());
        });

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
        query.having(criteriaBuilder.equal(criteriaBuilder.count(productRoot.get("id")), criteriaFilterDtos.size()));
        TypedQuery<Product> typedQuery = entityManager.createQuery(query);
        List<ProductDtoForFront> list = productForFrontConverter.entityListToDtoList(typedQuery.getResultList());
        entityManager.close();
        return list;
    }

    public JSONObject insertProductsFromFile(MultipartFile multipartFile, Supplier supplier) {
        try {
            return this.readProductsCSV(multipartFile.getInputStream(),supplier);
        } catch (IOException e) {
            throw new ApplicationException(HttpStatus.resolve(400), "Wrong format file");
        }
    }

    private JSONObject readProductsCSV(InputStream inputStream,Supplier supplier) throws IOException {
        errorMap = new HashMap<>();
        criteriaMap=new HashMap<>();
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(';')
                .withIgnoreQuotations(true)
                .build();
        CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).withCSVParser(parser).build();
        String[] nextRecord;
        Map<Integer, String> notAdded = new HashMap<Integer, String>();
        int nbLineAdd = 0;
        int line = 0;
        JSONObject json = new JSONObject();
        csvReader.readNext();
        while ((nextRecord = csvReader.readNext()) != null) {
            line++;
            String myline = Arrays.asList(nextRecord).toString();
            if (this.insertProduct(nextRecord, line,supplier))
                nbLineAdd++;
            else
                notAdded.put(line, myline);
        }
        json.put("Supplier",supplier.getId());
        json.put("Lines_Added", nbLineAdd);
        json.put("Lines_not_Added", notAdded);
        json.put("Error_lines", this.errorMap);
        json.put("Missing_criteria_line", this.criteriaMap);
        this.insertIntoErrors(supplier);
        return json;
    }

    private void insertIntoErrors(Supplier supplier) {
        if(!errorMap.isEmpty())
            for(Integer j :errorMap.keySet()) {
                {
                    String val=errorMap.get(j);
                    InsertionErrors error = InsertionErrors.builder()
                            .insertionDate(LocalDate.now())
                            .lineNumber(j)
                            .supplier(supplier)
                            .description(val)
                            .build();
                    this.insertionErrorsRepository.save(error);
                }
            }
        if(!criteriaMap.isEmpty())
            for(Integer j :criteriaMap.keySet()) {
                {
                    String val=criteriaMap  .get(j);
                    InsertionErrors error = InsertionErrors.builder()
                            .insertionDate(LocalDate.now())
                            .lineNumber(j)
                            .supplier(supplier)
                            .description(val)
                            .build();
                    this.insertionErrorsRepository.save(error);
                }
            }
    }

    private boolean insertProduct(String[] records, int line,Supplier supplier) {
        String actualColumn;
        CriteriaProduct cp;
        int added = 0;
        Category category = categoryService.getCategoryWithId(Long.parseLong(records[3]));
        if (category == null) {
            this.errorMap.put(line, "Category identification not exits in database, can't add product (for the product :  " + records[0] + ")");
            return false;
        }
        if (!category.getChildList().isEmpty()) {
            this.errorMap.put(line, "Category : " + category.getId() + " cannot have products, can't add product (for the product :  " + records[0] + ")");
            return false;
        }

        if (!checkAllMandatoryCriteriaPresentInFile(records, criteriaService.getAllMandatoryCriteriasIdWithIdCategory(category))) {
            this.errorMap.put(line, "Mandatories criteria missing, can't add product (for the product " + records[0] + ")");
            return false;
        }
        Product product = Product.builder()
                .category(category)
                .name(records[0].toLowerCase())
                .description(records[1])
                .supplierLink(records[2])
                .criteriaProducts(new ArrayList<>())
                .supplier(supplier)
                .build();
        productRepository.save(product);



        for (int i = 4; i < records.length; i++) {
            actualColumn = records[i];
            if (actualColumn.trim().length() == 0) return added > 0;
            if (i % 2 == 0) {
                Criteria criteria = criteriaService.getCriteriaProductWithIdCriteria(Long.parseLong(actualColumn));
                if (criteria == null) {
                    this.errorMap.put(line, "Criteria identification not exits in database, can't add product (for product :  " + product.getName() + ")");
                    return false; // criteria non trouvé en base
                } else {
                    if (!category.getCriteriaList().contains(criteria))
                        this.criteriaMap.put(line, "Missing criteria " + criteria.getId() +
                                " in category : " + category.getId() +" (for the product : " +product.getName()+ " , product_Id : "+product.getId()+" )"); //criteria non present dans categorie
                    else {

                        CriteriaProductPK primaryKey = new CriteriaProductPK();
                        primaryKey.setCriteria(criteria);
                        primaryKey.setProduct(product);
                        cp = CriteriaProduct.builder()
                                .pk(primaryKey)
                                .value(records[i + 1])
                                .build();
                        product.addCriteriaProduct(cp);
                        criteriaProductRepository.save(cp);
                        added = 1;
                    }
                }
            }
        }
        productRepository.save(product);
        productRepository.flush();

        return true;
    }

    private boolean checkAllMandatoryCriteriaPresentInFile(String[] records, ArrayList<Long> mandatoryCriteriaList) {
        ArrayList<Long> res = new ArrayList<>();
        String actualColumn;
        for (int i = 4; i < records.length; i++) {
            actualColumn = records[i];
            if (actualColumn.trim().length() > 0) {
                for (Long id : mandatoryCriteriaList) {
                    if (id.equals(Long.parseLong(actualColumn))) {
                        res.add(id);
                    }
                }
            }
            i++;
        }
        if (mandatoryCriteriaList.size() == res.size())  //true si tous les criteres sont présents
            return true;
        return false;
    }


    public List<ProductDto> getAllProductsBySupplier(Long id) {

        return productConverter.entityListToDtoList(this.productRepository.findAllBySupplierId(id));

    }

    public List<ShortProductDto> getSupplierProducts(Long id) {
        return productConverter.listEntityToShortDto(productRepository.findAllBySupplierId(id));
    }
}