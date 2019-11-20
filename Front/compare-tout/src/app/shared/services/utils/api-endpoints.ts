export const apiEndpoints = {
    // Users
    getUsers: 'users',

    // Categories
    getMainCategories: 'main-categories',
    getCategories: 'categories',
    getCategoriesChild: (id?: string) => `categories?id=${id}`,

    // Criterias
    getCriteriaOfCategory: (idCat: string) =>  `criteria?id_category=${idCat}`
  };
