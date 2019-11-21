package com.eltae.compareTout.services;

import com.eltae.compareTout.converter.CriteriaConverter;
import com.eltae.compareTout.converter.product.ProductConverter;
import com.eltae.compareTout.dto.criteria.CriteriaProductDto;
import com.eltae.compareTout.dto.product.ShortProductDto;
import com.eltae.compareTout.entities.*;
import com.eltae.compareTout.exceptions.ApplicationException;
import com.eltae.compareTout.exceptions.WrongParameters;
import com.eltae.compareTout.repositories.CategoryCriteriaRepository;
import com.eltae.compareTout.repositories.CriteriaRepository;
import com.eltae.compareTout.repositories.ProductRepository;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.json.simple.JSONObject;
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
    private final CriteriaConverter criteriaConverter;
    private Map<Integer,String> errorMap;


    @Autowired
    public CriteriaService(ProductConverter prodConv, ProductRepository prodRep, CriteriaRepository criteriaRepository, CategoryService categoryService, CategoryCriteriaRepository categoryCriteriaRepository, CriteriaConverter criteriaConverter) {
        this.productRepository = prodRep;
        this.productConverter = prodConv;
        this.criteriaRepository = criteriaRepository;
        this.categoryService = categoryService;
        this.categoryCriteriaRepository = categoryCriteriaRepository;
        this.criteriaConverter = criteriaConverter;
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
    public JSONObject create(MultipartFile multipartFile) throws ApplicationException {
        try {
            return this.readCSV(multipartFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApplicationException(HttpStatus.resolve(400),"Wrong format file");
        }

    }
    private JSONObject readCSV(InputStream inputStream) throws IOException {
            errorMap=new HashMap<>();
            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(';')
                    .withIgnoreQuotations(true)
                    .build();
            CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).withCSVParser(parser).build();
            String[] nextRecord;
            Map<Integer,String> notAdded=new HashMap<>();
            int nbLineAdd = 0;
            int line=0;
            JSONObject json = new JSONObject();
            while ((nextRecord = csvReader.readNext()) != null) {
                line++;
                String myline=Arrays.asList(nextRecord).toString();
                if (this.saveCrit(nextRecord,line))
                    nbLineAdd++;
                else {
                    if(errorMap.get(line)==null) notAdded.put(line, myline);
                }
            }
            json.put("Lines_Added", nbLineAdd);
            json.put("Lines_not_Added",notAdded);
            json.put("Error_lines",this.errorMap);
            return json;
        }
    private boolean saveCrit(String[] records,int line) {
        int added=0;
        String actualColumn;
        Category category = null;
        category = categoryService.getCategoryWithId(Long.parseLong(records[0].trim()));
        if(category==null){
            errorMap.put(line,"Missing Category "+records[0]);
            return false;
        }
        // si la catégorie n'existe pas on insère pas les criteres
        for (int i = 1; i < records.length; i=i+4) {
            actualColumn = records[i];
            if (actualColumn.trim().length() == 0) return added > 0;
              Optional<Criteria> critInBase = criteriaRepository.findByName(records[i + 1].trim().toLowerCase());
                if (!critInBase.isPresent()) { //Si le critère n'existe pas, on l'ajoute
                    if (!actualColumn.isEmpty()) {
                        Criteria newCritere = Criteria.builder()
                                .name(records[i + 1].trim().toLowerCase())
                                .unit(records[i + 2].trim())
                                .type(TypeCriteria.valueOf(records[i + 3].trim().toUpperCase()))
                                .categoryList(new ArrayList<>())
                                .build();
                        criteriaRepository.save(newCritere);
                        added = 1;

                        CategoryCriteriaPK categoryCriteriaPK = new CategoryCriteriaPK();
                        categoryCriteriaPK.setCategory(category);
                        categoryCriteriaPK.setCriteria_cat(newCritere);
                        CategoryCriteria categoryCriteria = CategoryCriteria.builder()
                                .isMandatory(Boolean.parseBoolean(actualColumn.trim()))
                                .pk(categoryCriteriaPK)
                                .build();
                        categoryCriteriaRepository.save(categoryCriteria);
                    } else {
                        errorMap.put(line, "Missing field in this line");
                        added = 0;
                    }

                } else { // si le critere existe, il faut ajouter la category qui l'appelle
                    CategoryCriteria categoryCriteriaToFind = category.getCriteriaProductWithCriteriaName(records[i + 1].trim().toLowerCase());
                    if (categoryCriteriaToFind != null) {
                        return added > 0;
                    } else {
                        CategoryCriteriaPK categoryCriteriaPK = new CategoryCriteriaPK();
                        categoryCriteriaPK.setCategory(category);
                        categoryCriteriaPK.setCriteria_cat(critInBase.get());
                        CategoryCriteria categoryCriteria = CategoryCriteria.builder()
                                .isMandatory(Boolean.parseBoolean(actualColumn))
                                .pk(categoryCriteriaPK)
                                .build();
                        categoryCriteriaRepository.save(categoryCriteria);
                        added = 1;
                    }
                }
            }


        return true;
    }
    public ArrayList<Long> getAllMandatoryCriteriasIdWithIdCategory(Category category) {
        ArrayList<Long> res = new ArrayList<>();
        List<CategoryCriteria> list = categoryCriteriaRepository.findByPk_Category(category);
        for (CategoryCriteria cc : list) {
            if (cc.getIsMandatory())
                res.add(cc.getCriteria().getId());
        }
        return res;
    }
    public List<CriteriaProductDto> getAllcriteria() {
        return this.criteriaConverter.entityListToDtoList(this.criteriaRepository.findAll());
    }

}
