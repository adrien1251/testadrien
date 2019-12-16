export const apiEndpoints = {
    // Users
    // getUsers: 'users',
    login: 'auth/_login',
    registerSupplier: 'suppliers',
    registerCustomer: 'customers',

    // Admin

    // Supplier
    sendProducts: 'products',
    getSupplier: (id?: string) => `suppliers?id_supplier=${id}`,
    findAllSupplierWhoNeedValidate: 'suppliers?filter=notValidate',
    validateSupplierAccount: (idSupplier: string) => `suppliers/${idSupplier}`,

  // Categories
    getCategories: 'categories/_search',
    getCategoriesChild: (id?: string) => `categories/_search?category_id=${id}`,

    // Criterias
    getCriteriaOfCategory: (idCat: string) =>  `criteria?id_category=${idCat}`,
    getCriteriaValuesOfCategory: (idCat: string) =>  `criteria?id_category=${idCat}&allValues=true`,
    getCriteriasOfProduct: (idProd: string) => `criteria?id_product=${idProd}`,

    // Products
    getProductsByCategoryAndCriteria: (idCat: string) => `products/_search/${idCat}`,
  };
