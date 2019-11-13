package com.eltae.compareTout.services;

import com.eltae.compareTout.converter.product.ProductConverter;
import com.eltae.compareTout.dto.CriteriaProductDto;
import com.eltae.compareTout.dto.product.ShortProductDto;
import com.eltae.compareTout.entities.*;
import com.eltae.compareTout.exceptions.WrongParameters;
import com.eltae.compareTout.repositories.CategoryCriteriaRepository;
import com.eltae.compareTout.repositories.CriteriaRepository;
import com.eltae.compareTout.repositories.ProductRepository;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@Transactional
public class CriteriaService {

    private final ProductConverter productConverter;
    private final ProductRepository productRepository;
    private final CriteriaRepository criteriaRepository;
    private final CategoryService categoryService;
    private final CategoryCriteriaRepository categoryCriteriaRepository;

    @Autowired
    public CriteriaService(ProductConverter prodConv, ProductRepository prodRep, CriteriaRepository criteriaRepository, CategoryService categoryService, CategoryCriteriaRepository categoryCriteriaRepository) {
        this.productRepository = prodRep;
        this.productConverter = prodConv;
        this.criteriaRepository = criteriaRepository;
        this.categoryService = categoryService;
        this.categoryCriteriaRepository = categoryCriteriaRepository;
    }

    public List<ShortProductDto> getProductsCriteria(Long id, List<Long> crit) {
        List<Product> myProducts = new ArrayList<Product>();
        myProducts = productRepository.findAllByCategoryId(id);
        List<Product> filterProduct = new ArrayList<Product>();

        for (Product p : myProducts)

            if (p.getCrit().containsAll(crit))
                filterProduct.add(p);

        return productConverter.listEntityToShortDto(filterProduct);

    }

    public Criteria getCriteriaProductWithIdCriteria(Long idCriteria) {
        Optional<Criteria> c = criteriaRepository.findById(idCriteria);
        if (c.isPresent())
            return c.get();
        return null;
    }

    public List<ShortProductDto> getProductsStrictCriteria(Long id, Long[] idCrit, String[] valuesCrit) {

        if (id == null || idCrit.length != valuesCrit.length)
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


    public int create(MultipartFile multipartFile) {
        try {
            return this.readCSV(multipartFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int readCSV(InputStream inputStream) throws IOException {
        CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8), ';');
        String[] nextRecord = csvReader.readNext();
        int nbLineAdd = 0;
        while ((nextRecord = csvReader.readNext()) != null) if (this.saveCrit(nextRecord)) nbLineAdd++;
        return nbLineAdd;
    }

    private boolean saveCrit(String[] records) {
        String actualColumn;
        Category category = categoryService.getCategoryWithId(Long.parseLong(records[0]));
        for (int i = 0; i < records.length; i++) {
            actualColumn = records[i];
            if (actualColumn.trim().length() == 0) return false;
            if (i % 4 == 0 & i != 0) {
                Optional<Criteria> critInBase = criteriaRepository.findByName(records[i + 1].toLowerCase());
                if (!critInBase.isPresent()) { //Si le critère n'existe pas, on l'ajoute
                    if (!actualColumn.isEmpty()) {
                        Criteria newCritere = Criteria.builder()
                                //.isMandatory(Boolean.parseBoolean(actualColumn))
                                .name(records[i + 1].toLowerCase())
                                .unit(records[i + 2])
                                .type(TypeCriteria.valueOf(records[i + 3].toUpperCase()))
                                .categoryList(new ArrayList<>())
                                .build();
                        criteriaRepository.save(newCritere);

                        CategoryCriteriaPK categoryCriteriaPK = new CategoryCriteriaPK();
                        categoryCriteriaPK.setCategory(category);
                        categoryCriteriaPK.setCriteria_cat(newCritere);
                        CategoryCriteria categoryCriteria = CategoryCriteria.builder()
                                .isMandatory(Boolean.parseBoolean(actualColumn))
                                .pk(categoryCriteriaPK)
                                .build();
                        categoryCriteriaRepository.save(categoryCriteria);


                    }

                } else { // si le critere existe, il faut ajouter la category qui l'appelle
                    CategoryCriteria categoryCriteriaToFind = category.getCriteriaProductWithCriteriaName(records[i + 1].toLowerCase());
                    if (categoryCriteriaToFind!=null)
                        return false;
                    else {
                        CategoryCriteriaPK categoryCriteriaPK = new CategoryCriteriaPK();
                        categoryCriteriaPK.setCategory(category);
                        categoryCriteriaPK.setCriteria_cat(critInBase.get());
                        CategoryCriteria categoryCriteria = CategoryCriteria.builder()
                                .isMandatory(Boolean.parseBoolean(actualColumn))
                                .pk(categoryCriteriaPK)
                                .build();
                        categoryCriteriaRepository.save(categoryCriteria);
                    }


                    }
                }

            }
            return true;
        }


    }