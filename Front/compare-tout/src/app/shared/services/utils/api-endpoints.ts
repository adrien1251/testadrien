export const apiEndpoints = {
    // Users
    getUsers: 'users',
    login: 'login',
    registerSupplier: 'supplier',
    registerCustomer: 'customer',

    // Admin
    findAllSupplierWhoNeedValidate: 'admin/getAllSuppliersAccountToValid',
    validateSupplierAccount: 'admin/validateSupplierAccount',

    // Categories
    getMainCategories: 'main-categories',
    getCategories: 'categories',
    getCategoriesChild: (id?: string) => `categories?id=${id}`,

    // Criterias
    getCriteriaOfCategory: (idCat: string) =>  `criteria?id_category=${idCat}`,

    // Products
    getProductsByCategoryAndCriteria: (idCat: string) => `products/${idCat}`,
    getCriteriasOfProduct: (idProd: string) => `products/${idProd}`,
  };
