export const apiEndpoints = {
    // Users
    getUsers: 'users',
    login: 'login',
    registerSupplier: 'supplier',
    registerCustomer: 'customer',

    // Admin
    findAllSupplierWhoNeedValidate: 'admin/getAllSuppliersAccountToValid',
    validateSupplierAccount: (idSupplier: string) => `admin/validateSupplierAccount/${idSupplier}`,

    // Supplier
    sendProducts: 'products',
    getSupplier: (id?: string) => `supplier/?id_supplier=${id}`,


  // Categories
    getMainCategories: 'main-categories',
    getCategories: 'categories',
    getCategoriesChild: (id?: string) => `categories?id=${id}`,

    // Criterias
    getCriteriaOfCategory: (idCat: string) =>  `criteria?id_category=${idCat}`,
    getCriteriaValuesOfCategory: (idCat: string) =>  `criteria/getAllValueForEachCriteriaOfACategory?id_category=${idCat}`,


    // Products
    getProductsByCategoryAndCriteria: (idCat: string) => `products/${idCat}`,
    getCriteriasOfProduct: (idProd: string) => `products/${idProd}`,
  };
