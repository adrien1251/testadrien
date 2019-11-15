package com.eltae.compareTout.services;
import com.eltae.compareTout.converter.CategoryConverter;
import com.eltae.compareTout.converter.CriteriaConverter;
import com.eltae.compareTout.converter.product.ProductConverter;
import com.eltae.compareTout.dto.CategoryDto;
import com.eltae.compareTout.dto.CriteriaProductDto;
import com.eltae.compareTout.dto.ShortCategoryDto;
import com.eltae.compareTout.dto.product.ShortProductDto;
import com.eltae.compareTout.entities.Category;
import com.eltae.compareTout.entities.CategoryCriteria;
import com.eltae.compareTout.entities.Criteria;
import com.eltae.compareTout.exceptions.ApplicationException;
import com.eltae.compareTout.repositories.CategoryRepository;
import com.eltae.compareTout.repositories.ProductRepository;
import com.opencsv.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.transaction.Transactional;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;
    private final CriteriaConverter criteriaConverter;
    private final ProductConverter productConverter;
    private final ProductRepository productRepository;
    private File cat_file;
    private Map<Integer,String> errorMap;


    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryConverter catConv, CriteriaConverter critConv, ProductConverter prodConv, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryConverter = catConv;
        this.criteriaConverter =critConv;
        this.productConverter=prodConv;
        this.productRepository = productRepository;
    }

    public JSONObject create(MultipartFile multipartFile) throws ApplicationException {
        try {
            return this.readCSV(multipartFile.getInputStream());
        } catch (IOException e) {
            throw new ApplicationException(HttpStatus.resolve(400),"Wrong format file");
        }
    }

    public Category getCategoryWithId(long id){
        Optional<Category> cat = categoryRepository.findById(id);
        if (cat.isPresent())
            return cat.get();
        return null;
    }

    private JSONObject readCSV(InputStream inputStream) throws IOException {
        errorMap=new HashMap<>();
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(';')
                .withIgnoreQuotations(true)
                .build();
        CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).withCSVParser(parser).build();
        String[] nextRecord;
        Map<Integer,String> notAdded=new HashMap<Integer,String>();
        int nbLineAdd = 0;
        int line=0;
        JSONObject json = new JSONObject();
        while ((nextRecord = csvReader.readNext()) != null) {
            line++;
            String myline=Arrays.asList(nextRecord).toString();
            if (this.saveCat(nextRecord,line))
                nbLineAdd++;
            else
                notAdded.put(line,myline);
        }
         json.put("Lines_Added", nbLineAdd);
         json.put("Lines_not_Added",notAdded);
         json.put("Error_lines",this.errorMap);
        return json;
    }


    private boolean saveCat(String[] records,int line){
        String actualCategoryName;
        Category parent=null;
        Category child;
        int added=0;
        for(int i=1;i<records.length;i++){
            actualCategoryName=records[i];
            Optional<Category> ActCat = this.categoryRepository.findByName(actualCategoryName);
            if(actualCategoryName.trim().length()==0) return added>0;
            List<Category> childList;
            if(i==1) {
                if (!ActCat.isPresent()) {
                    parent=Category.builder().name(actualCategoryName).childList(new ArrayList<>()).build();
                    childList = new ArrayList<>();
                    parent.setChildList(childList);
                    this.categoryRepository.save(parent);
                    added=1;
                }
                else  parent = ActCat.get();
            }
            else{
                if(ActCat.isPresent()) {
                    if((ActCat.get().getParent() != null) && !ActCat.get().getParent().getId().equals(parent.getId())) {
                        this.errorMap.put(line,"Child category `" + ActCat.get().getName() + "` has already parent[`" + ActCat.get().getParent().getName() + "`]");
                        added=0;
                    }
                        if(ActCat.get().getParent()==null){
                             ActCat.get().setParent(parent);
                             childList=ActCat.get().getChildList();
                             childList.add(ActCat.get());
                             parent.setChildList(childList);
                             this.categoryRepository.save(parent);
                             this.categoryRepository.save(ActCat.get());
                             added=1;
                             parent=ActCat.get();
                            }
                         else parent=ActCat.get();
                }
                else{
                    child=Category.builder().name(actualCategoryName).childList(new ArrayList<>()).parent(parent).build();
                    childList = parent.getChildList();
                    childList.add(child);
                    parent.setChildList(childList);
                    this.categoryRepository.save(child);
                    parent=child;
                    added=1;
                    }
                }
        }
        return added>0;
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
        for(Criteria critere: crit){
            CategoryCriteria categoryCriteriaToFind = g.getCriteriaProductWithCriteriaName(critere.getId());
            pathcate=pathcate+"/"+categoryCriteriaToFind.getIsMandatory();
            pathcate=pathcate+"/"+critere.getName();
            pathcate=pathcate+"/"+critere.getUnit();
            pathcate=pathcate+"/"+critere.getType();
        }

        String[] row=pathcate.split("/");
        c.writeNext(row);
    }
    private  CSVWriter writeDataLineByLine(String filePath) {
        cat_file = new File(filePath);
        try {
            FileWriter outputfile = new FileWriter(cat_file);
            CSVWriter writer = new CSVWriter(outputfile, ';',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);
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

    public List<CriteriaProductDto> getCriteriaCategories(Long id) {
        return criteriaConverter.entityListToDtoList(categoryRepository.findById(id).get().getCriteriaList());

    }

    public List<ShortProductDto> getProductsCategory(Long id) {
        return productConverter.listEntityToShortDto(productRepository.findAllByCategoryId(id));
    }
}