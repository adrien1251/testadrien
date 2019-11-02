export const apiEndpoints = {
    // Users
    getUsers: 'users',

    // Categories
    getMainCategories: '/main-categories',
    getChildCategories: (id: string) => `/children/${id}`,
  };
