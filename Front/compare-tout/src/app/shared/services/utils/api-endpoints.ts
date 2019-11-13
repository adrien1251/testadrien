export const apiEndpoints = {
<<<<<<< HEAD
  // START Translation
  getUsers: 'users',
};
=======
    // Users
    getUsers: 'users',

    // Categories
    getMainCategories: '/main-categories',
    getChildCategories: (id: string) => `/children/${id}`,

    // Criterias
    getCriteriaOfCategory: (idCat: string) =>  `/${idCat}`
  };
>>>>>>> develop
