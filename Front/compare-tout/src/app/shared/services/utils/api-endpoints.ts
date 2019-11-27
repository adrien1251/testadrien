export const apiEndpoints = {
    // Users
    getUsers: 'users',
    login: 'login',
    registerSupplier: 'supplier',

    // Categories
    getMainCategories: '/main-categories',
    getCategories: (id?: string) => `/categories/${id}`,

    // Criterias
    getCriteriaOfCategory: (idCat: string) =>  `/${idCat}`
  };
