package com.eltae.compareTout.services;

import com.eltae.compareTout.converter.CategoryConverter;
import com.eltae.compareTout.converter.CriteriaConverter;
import com.eltae.compareTout.converter.ProductConverter;
import com.eltae.compareTout.dto.CategoryDto;
import com.eltae.compareTout.dto.CriteriaProductDto;
import com.eltae.compareTout.dto.ShortCategoryDto;
import com.eltae.compareTout.dto.ShortProductDto;
import com.eltae.compareTout.entities.Category;
import com.eltae.compareTout.entities.Criteria;
import com.eltae.compareTout.exceptions.BadCsvLine;
import com.eltae.compareTout.repositories.CategoryRepository;
import com.eltae.compareTout.repositories.ProductRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;
    private final CriteriaConverter criteriaConverter;
    private final ProductConverter productConverter;
    private final ProductRepository productRepository;
    private File cat_file;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryConverter catConv, CriteriaConverter critConv, ProductConverter prodConv, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryConverter = catConv;
        this.criteriaConverter=critConv;
        this.productConverter=prodConv;
        this.productRepository = productRepository;
    }

    public int create(MultipartFile multipartFile) {
        try {
            return this.readCSV(multipartFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Category getCategoryWithId(long id){
        Optional<Category> cat = categoryRepository.findById(id);
        if (cat.isPresent())
            return cat.get();
        return null;
    }

    private int readCSV(InputStream inputStream) throws IOException {
        CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8), ';');
        String[] nextRecord;
        int nbLineAdd = 0;
        while ((nextRecord = csvReader.readNext()) != null) if (this.saveCat(nextRecord)) nbLineAdd++;
        return nbLineAdd;
    }


    private boolean saveCat(String[] records){
        String actualCategoryName;
        Category parent=null;
        Category child;
        for(int i=1;i<records.length;i++){
            actualCategoryName=records[i];
            Optional<Category> ActCat = this.categoryRepository.findByName(actualCategoryName);
            if(actualCategoryName.trim().length()==0)return false;
            List<Category> childList;
            if(i==1) {
                if (!ActCat.isPresent()) {
                    parent=Category.builder().name(actualCategoryName).childList(new ArrayList<>()).build();
                    childList = new ArrayList<>();
                    parent.setChildList(childList);
                    this.categoryRepository.save(parent);
                }
                else
                    parent=ActCat.get();
            }
            else{
                if(ActCat.isPresent()) {
                    if((ActCat.get().getParent() != null) && !ActCat.get().getParent().getId().equals(parent.getId())) {
                        throw new BadCsvLine(HttpStatus.resolve(566), "Child category `" + ActCat.get().getName() + "` has already parent[`" + ActCat.get().getParent().getName() + "`]");
                    }
                        if(ActCat.get().getParent()==null){
                             ActCat.get().setParent(parent);
                             childList=ActCat.get().getChildList();
                             childList.add(ActCat.get());
                             parent.setChildList(childList);
                             this.categoryRepository.save(parent);
                             this.categoryRepository.save(ActCat.get());
                             parent=ActCat.get();
                            }
                         else{
                             parent=ActCat.get();
                            }
                }
                else{
                    child=Category.builder().name(actualCategoryName).childList(new ArrayList<>()).parent(parent).build();
                    childList = parent.getChildList();
                    childList.add(child);
                    parent.setChildList(childList);
                    this.categoryRepository.save(child);
                    parent=child;
                }
                }
        }
        return true;
    }
    private boolean saveCategorie(String[] records, int idx) {
        if (!(idx < records.length) || records[idx].isEmpty()) return true;
        String actualCategoryName = records[idx];
        String childCategoryName = idx + 1 < records.length && !records[idx + 1].isEmpty() ? records[idx + 1] : null;
        idx++;

        Optional<Category> OActualCategory = this.categoryRepository.findByName(actualCategoryName);
        if (!OActualCategory.isPresent()) {
            Category actualCategory = Category.builder().name(actualCategoryName).build();
            List<Category> childList = new ArrayList<>();
            /* Crée le fils uniquement si il peut en avoir un */
            if (childCategoryName != null) {
                Optional<Category> OChildCategory = this.categoryRepository.findByName(childCategoryName);
                Category childCategory = null;
                if (!OChildCategory.isPresent()) {
                    childCategory = Category.builder().name(childCategoryName).parent(actualCategory).build();
                } else {
                    childCategory = OChildCategory.get();
                    if (childCategory.getParent() != null) {
                        throw new BadCsvLine(HttpStatus.resolve(566), "Child category `" + childCategory.getName() + "` has already parent[`" + childCategory.getParent().getName() + "`]");
                    }
                }
                childList.add(childCategory);
            }
            actualCategory.setChildList(childList);
            this.categoryRepository.save(actualCategory);
        } else {
            if (childCategoryName == null) {
                return this.saveCategorie(records, idx);
            }
            //Categ existe et on doit lui rajouter un fils
            Category actualCategory = OActualCategory.get();
            Optional<Category> OChildCategory = this.categoryRepository.findByName(childCategoryName);
            if (OChildCategory.isPresent()) {
                if (OChildCategory.get().getParent() == null) {
                    OChildCategory.get().setParent(actualCategory);
                    List<Category> childList = actualCategory.getChildList();
                    if (childList == null) childList = new ArrayList<>();
                    childList.add(OChildCategory.get());
                    this.categoryRepository.save(actualCategory);

                } else {
                    if (actualCategory.getId().equals(OChildCategory.get().getParent().getId())) {
                        return this.saveCategorie(records, idx);
                    } else {
                        throw new BadCsvLine(HttpStatus.resolve(566), "Child category `" + OChildCategory.get().getName() + "` has already parent[`" + OChildCategory.get().getParent().getName() + "`]");
                    }
                }
            }

            Category childCategory = Category.builder().name(childCategoryName).childList(new ArrayList<>()).parent(actualCategory).build();
            List<Category> childList = actualCategory.getChildList();
            if(childList == null) childList = new ArrayList<>();
            childList.add(childCategory);

            this.categoryRepository.save(actualCategory);
//            this.categoryRepository.save(childCategory);
        }
        return this.saveCategorie(records, idx);

    }
    public File getCategories(){

        List<Category> mother;

        mother = this.categoryRepository.findByParent_idIsNull();
        CSVWriter writer=  writeDataLineByLine("Categorie_file.csv");
       for (Category c:mother) {
        parcoursProfondeur(c,"",writer);
        }
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cat_file;

    }
    private void parcoursProfondeur(Category g, String pathcate,CSVWriter c) {
        Iterator<Category> j = g.getChildList().iterator();
        if(pathcate.length()!=0) {
            pathcate = pathcate + "/" + g.getName();
            if(!j.hasNext()) {
                pathcate=g.getId()+"/"+pathcate;
                this.writeInCsv(g,pathcate,c);
            }
        }
        else {
            pathcate = g.getName();
            if(!j.hasNext()) {
                pathcate=g.getId()+"/"+pathcate;
                this.writeInCsv(g,pathcate,c);
            }
        }
        while (j.hasNext()) {
            Category s = j.next();
            parcoursProfondeur(s, pathcate,c);
        }
    }
    private void writeInCsv(Category g, String pathcate,CSVWriter c){
        List<Criteria> crit=null;
        crit=g.getCriteriaList();
        String[] row1=pathcate.split("/");
            if(row1.length<6){
            for(int i=row1.length;i<6;i++)
                pathcate=pathcate+"/";
        }
        for(Criteria critete: crit){
//            pathcate=pathcate+"/"+critete.isMandatory();
            pathcate=pathcate+"/"+critete.getName();
            pathcate=pathcate+"/"+critete.getUnit();
            pathcate=pathcate+"/"+critete.getType();
        }

        String[] row=pathcate.split("/");
        c.writeNext(row);
    }
    private  CSVWriter writeDataLineByLine(String filePath) {
        // first create file object for file placed at location
        // specified by filepath
        cat_file = new File(filePath);
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(cat_file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile, ';',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            // adding header to csv
            String[] header = {"Category's last child Id"  ,"Main category", "Child_1 category", "Child_2 category", "Child_3 category", "Child_4 category",
                                "Is_mandatory_criteria_1","Criteria_1","Unit_1","Type_1", "Is_mandatory_criteria_2","Criteria_2","Unit_2","Type_2", "Is_mandatory_criteria_3","Criteria_3","Unit_3","Type_3",
                                "Is_mandatory_criteria_4","Criteria_4","Unit_4","Type_4","Is_mandatory_criteria_5","Criteria_5","Unit_5","Type_5", "Is_mandatory_criteria_6","Criteria_6","Unit_6","Type_6",
                                "Is_mandatory_criteria_7","Criteria_7","Unit_7","Type_7", "Is_mandatory_criteria_8","Criteria_8","Unit_8","Type_8",
                                "Is_mandatory_criteria_9","Criteria_9","Unit_9","Type_9","Is_mandatory_criteria_10","Criteria_10","Unit_10","Type_10" };

            writer.writeNext(header);
            return writer;
        }
            catch(IOException e){
                e.printStackTrace();
            }
        return null;
    }


    public List<CategoryDto> getMainCategories() {
            return categoryConverter.entityListToDtoList(categoryRepository.findByParent_idIsNull());
    }

    public List<ShortCategoryDto> getChildCategories(long id) {
        return  categoryConverter.entityListToShortDtoList(categoryRepository.findById(id).get().getChildList());
            }

    public List<CriteriaProductDto> getCriteriadCategories(Long id) {
        return criteriaConverter.entityListToDtoList(categoryRepository.findById(id).get().getCriteriaList());

    }

    public List<ShortProductDto> getProductsCategory(Long id) {
        return productConverter.listEntityToShortDto(productRepository.findAllByCategoryId(id));
    }
}